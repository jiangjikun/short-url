package com.spiderdt.common.dao;

import com.spiderdt.common.entity.UrlsEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by fivebit on 2017/7/12.
 */
@Component
public interface UrlsDao {

    public UrlsEntity getUrlInfoByCode(@Param("code") String code);

    public Boolean createUrlsInfoBatch(@Param("items") List<UrlsEntity> items);

    public Boolean updateUrlStatus(@Param("code") String code,@Param("status") String status);

}
