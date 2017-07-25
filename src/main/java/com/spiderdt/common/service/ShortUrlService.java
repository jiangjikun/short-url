package com.spiderdt.common.service;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.spiderdt.common.components.Slog;
import com.spiderdt.common.components.Sredis;
import com.spiderdt.common.dao.UrlsDao;
import com.spiderdt.common.entity.UrlsEntity;
import com.spiderdt.common.filters.AppException;
import com.spiderdt.common.tasks.TaskService;
import com.spiderdt.common.utils.Constants;
import com.spiderdt.common.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.URLDecoder;
import java.util.List;
import java.util.Map;

/**
 * Created by fivebit on 2017/7/12.
 */
@Service
public class ShortUrlService {
    @Autowired
    Slog slog;
    @Autowired
    Sredis sredis;
    @Autowired
    UrlsDao urlsDao;

    @Autowired
    TaskService taskService;

    @Value("${shorturl.incrkey}")
    String short_url_index;
    @Value("${shorturl.http_prefix}")
    String http_prefix;
    public String getOrgUrlByCode(String code){
        String org_url = sredis.getString(code);
        if(org_url == null){
            UrlsEntity urlsEntity = urlsDao.getUrlInfoByCode(code);
            if(urlsEntity != null){
                org_url = urlsEntity.getOrgUrl();
            }
        }
        final String long_url = org_url;
        slog.info("get org url by code:"+code+" org:"+org_url);
        taskService.submit(() -> {
            updateShortUrlInfo(code,long_url);
            return 0;
        });
        return org_url;
    }
    public List<Map<String,String>> makeShortUrl(List<String> urls_long) throws AppException {
        if(urls_long.size() > Constants.ENCODE_URL_BATCH_MAX){
            throw new AppException("0","url list must less:"+Constants.ENCODE_URL_BATCH_MAX);
        }
        List<Map<String,String>> ret_maps = Lists.newArrayList();
        List<Map<String,String>> ret_todb = Lists.newArrayList();
        for(String url: urls_long){
            Map<String,String> item_map = Maps.newHashMap();
            Map<String,String> db_item = Maps.newHashMap();
            Long long_code = null;
            String old_code = sredis.getString(url);
            String code = "";
            String url_decode = URLDecoder.decode(url);
            if(Utils.checkHttpUrl(url_decode) == false){
                slog.info("make short url input not http:"+url_decode);
                item_map.put("url_short","");
                item_map.put("url_long",url_decode);
                item_map.put("type","0");
                item_map.put("result","false");
                ret_maps.add(item_map);
                continue;
            }
            if(old_code != null){
                item_map.put("url_short",getHttpUrlByCode(old_code));
                item_map.put("url_long",url_decode);
                item_map.put("type","0");
                item_map.put("result","true");
                ret_maps.add(item_map);
            }else {
                try {
                    long_code = sredis.getAndIncr(short_url_index);
                } catch (Exception ee) {
                    slog.error("get incre index from redis error:" + url);
                    continue;
                }
                code = Utils.long2H62(long_code);
                item_map.put("url_short", getHttpUrlByCode(code));
                item_map.put("url_long", url);
                item_map.put("type", "0");
                item_map.put("result", "true");

                db_item.put("code", code);
                db_item.put("url_long", url);
                ret_todb.add(db_item);
                ret_maps.add(item_map);
            }
            try {
                sredis.addString(code, url_decode, Constants.REDIS_URL_EXP);
                sredis.addString(url,code,Constants.REDIS_URL_SAME_EXP);
            }catch(Exception ee){
                slog.error("make shor url save to redis error:"+code+" url:"+url);
            }
        }
        //save to psql ansyc
        taskService.submit(() -> {
            saveUrlInfoToDbBatch(ret_todb);
            return 0;
        });
        return ret_maps;
    }

    public String getHttpUrlByCode(String code){
        return http_prefix+code;
    }
    public Boolean updateShortUrlInfo(String code,String org_url){
        slog.info("updateShortUrlInfo begin:"+code+" org_url"+org_url);
        try {
            if (org_url != null) {
                sredis.addString(code, org_url, Constants.REDIS_URL_EXP);
                urlsDao.updateUrlStatus(code, "click");
            }
        }catch (Exception ee){
            slog.error("update url status error:"+code+" url:"+org_url);
        }
        return true;
    }
    //@Async Future<String>
    public Boolean  saveUrlInfoToDbBatch(List<Map<String,String>> urls) throws AppException{
        slog.info("saveUrlInfoToDbBatch begin:"+urls.toString());
        if(urls == null || urls.size() < 1){
            return true;
        }
        List<UrlsEntity> entitys = Lists.newArrayList();
        for(Map<String,String> url: urls){
            UrlsEntity entity = new UrlsEntity();
            entity.setCode(url.get("code"));
            entity.setOrgUrl(url.get("url_long"));
            entitys.add(entity);
        }
        try {
            urlsDao.createUrlsInfoBatch(entitys);
        }catch (Exception ee){
            slog.error(" createUrlsInfoBatch error:"+ee.getMessage());
        }
        slog.info("saveUrlInfoToDbBatch end:"+urls.toString());
        return true;
    }
}
