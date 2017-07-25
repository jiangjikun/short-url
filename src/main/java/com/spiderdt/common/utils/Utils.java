package com.spiderdt.common.utils;

import com.alibaba.fastjson.JSONObject;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by fivebit on 2017/5/11.
 * common tools about string/date/others
 */
@Repository
public class Utils {

    /**
     * 检测token的格式，要符合xxxxxxxx-xxxx-xxxx-xxxxxx-xxxxxxxxxx
     * @param token
     * @return true/false
     */
    public static Boolean checkTokenFormat(String token){
        Boolean status = true;
        if(token == null || token.isEmpty() == true || token.length() !=36 ){
            status = false;
        }else {
            status = token.matches("[a-zA-Z0-9]{8}-[a-zA-Z0-9]{4}-[a-zA-Z0-9]{4}-[a-zA-Z0-9]{4}-[a-zA-Z0-9]{12}");
        }
        Jlog.info("check token format:"+token+" and ret:"+status);
        return status;
    }

    /**
     * 规范过期时间，在0-1800之间
     * @param expire_ts
     * @return
     */
    public static int formatExpiredTime(int expire_ts){
        int default_et = Constants.APP_ERROR_STATUS;
        if(expire_ts > 0 && expire_ts < default_et*2){
            return expire_ts;
        }
        return default_et;
    }

    public static String encodePassword(String password){
        //return  Jencode.MD5(password);
        //return  new BCryptPasswordEncoder().encode(password);

        return null;
    }
    public static String getNewToken(){
        return UUID.randomUUID().toString();
    }

    /**
     * 检测password是否符合要求，eg. length,
     * @param password
     * @return
     */
    public static Boolean checkPasswordFormat(String password){
        return true;
    }

    public static String getRespons(int status,String code,Object data){
        JSONObject oper = new JSONObject();
        oper.put("status",status);
        oper.put("code",code);
        oper.put("data",data);
        return oper.toJSONString();
    }
    public static String getRespons(String code,Object data){
        return Utils.getRespons(200,code,data);

    }
    public static String getRespons(Object data){
        return Utils.getRespons("0",data);

    }
    public static String getRespons(){
        return Utils.getRespons("0","");

    }
    public static Boolean checkString(String st,int length){
        if( null == st || st.isEmpty() == true || st.length() > length ){
            return false;
        }
        return true;
    }
    public static String replaceString(String src,String rep,int start,int length){
        if(src.length()<start+length){
            Jlog.error("replace index error:src:"+src+" start:"+start+" length:"+length);
            return src;
        }
        char[] src_char = src.toCharArray();
        char[] rep_char = rep.toCharArray();
        int ret_char_len = src.length()-length+rep.length();    //结果长度
        char[] ret_char = new char[ret_char_len];               //结果数组
        int i = 0;
        for(i=0;i<start;i++){
            ret_char[i] = src_char[i];
        }
        for(i=0;i<rep.length();i++){
            ret_char[i+start] = rep_char[i];
        }
        for(i=0;i<src.length()-length-start;i++){
            ret_char[i+start+rep.length()] = src_char[i+start+length];
        }
        return String.valueOf(ret_char);
    }

    /**
     * 合并两个map
     * @param src
     * @param dest
     * @return
     */
    public static Map<String,String> mergMap(Map<String,String> src,List<Map<String,String>> dest){
        if(src == null ){
            src = new HashMap<String,String>();
        }
        if(dest == null){
            return src;
        }
        for(Map<String,String> item:dest){
            for(Map.Entry<String,String> _set:item.entrySet()){
                src.put(_set.getKey(),_set.getValue());
            }
        }
        return src;

    }

    /***
     * 合并两个map.
     * @param src
     * @param dest
     * @return
     */
    public static Map<String,String> mergMap(Map<String,String> src,Map<String,String> dest){
        if(src == null ){
            src = new HashMap<String,String>();
        }
        if(dest == null){
            return src;
        }
        for(Map.Entry<String,String> _set:dest.entrySet()){
            src.put(_set.getKey(),_set.getValue());
        }
        return src;

    }

    /**
     * 从map转成对应的json object
     * @param srcmap
     * @return
     */
    public static JSONObject map2Json(Map<String,String> srcmap){
        JSONObject json_map = new JSONObject();
        if(srcmap != null) {
            for (Map.Entry<String, String> entry : srcmap.entrySet()) {
                json_map.put(entry.getKey(), entry.getValue());
            }
        }
        return json_map;
    }

    public static Map<String,String> json2map(JSONObject srcjson){
        Map<String,String> map_json = new HashMap<>();
        if(srcjson != null){
            for(Map.Entry<String, Object> entry: srcjson.entrySet()) {
                map_json.put(entry.getKey(), entry.getValue().toString());
            }
        }
        return map_json;
    }

    public static String getUUID(){
        return UUID.randomUUID().toString().replace("-", "");
    }

    public static Boolean isPhone(String item){
        return true;
    }
    public static Boolean isEmail(String item){
        return true;
    }
    private static final char[] h62_array = {
            'W', 'm', 'A', 'b', 'M', 'c', 'J', 'z', 'H', 'd',
            'U', '6', 'p', 'P', 'r', 'e', 'h', 'f', '7', 'K',
            'Q', 'C', 'i', '5', 'j', 'R', 'g', 'D', 'k', 'Z',
            'I', '8', 'n', '4', 'F', 'L', 'E', 'o', '9', 'X',
            'O', 'T', 's', 'B', '3', 't', 'V', 'S', 'u', 'Y',
            'N', '2', 'w', 'x', '1', 'y', 'a', '0', 'v', 'l',
            'G', 'q'
    };

    //long类型的转换成62进制编码
    public static String long2H62(long number) {
        Long rest = number;
        Stack<Character> stack = new Stack<>();
        StringBuilder result = new StringBuilder(6);
        do {
            stack.add(h62_array[new Long((rest - (rest / 62) * 62)).intValue()]);
            rest = rest / 62;
        } while (rest != 0);
        for (; !stack.isEmpty(); ) {
            result.append(stack.pop());
        }
        return result.toString();
    }
    public static Boolean checkHttpUrl(String url){
        Pattern patternHttp = Pattern.compile("(?<!\\d)(?:(?:[\\w[.-://]]*\\.[com|cn|net|tv|gov|org|biz|cc|uk|jp|edu]+[^\\s|^\\u4e00-\\u9fa5]*))");
        Matcher matcher = patternHttp.matcher(url);
        if(matcher.find()){
            return true;
        }
        return false;
    }

}

