package com.spiderdt.common.utils;

/**
 * Created by fivebit on 2017/7/12.
 */
public class Constants {
    public static final int APP_ERROR_STATUS = 5055;

    public static final String APP_ERROR_CODE = "0";

    public static final Integer CODE_LENGTH_MAX = 6;

    public static final Integer ENCODE_URL_BATCH_MAX= 50;

    //url redis中失效的时间10天
    public static final Integer REDIS_URL_EXP = 864000;
    //保持长链接对应的短链，对一天内相同的长链，返回相同的短链
    public static final Integer REDIS_URL_SAME_EXP = 86400;

    public static final String ACCESS_TOKEN= "2.005IvWNGoAN9dD586707c2a90CCEPa";
}
