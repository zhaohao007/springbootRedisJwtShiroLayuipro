package com.hinmu.lims.model;

import com.hinmu.lims.model.enums.LoginClientTypeEnum;

/**
 * 常量
 * @author dolyw.com
 * @date 2018/9/3 16:03
 */
public class Constant {

    /**
     * redis-OK
     */
    public final static String OK = "OK";

    /**
     * redis过期时间，以秒为单位，一分钟
     */
    public final static int EXRP_MINUTE = 60;

    /**
     * redis过期时间，以秒为单位，一小时
     */
    public final static int EXRP_HOUR = 60 * 60;

    /**
     * redis过期时间，以秒为单位，一天
     */
    public final static int EXRP_DAY = 60 * 60 * 24;

    /**
     * redis-key-前缀-shiro:cache:
     */
    public final static String PREFIX_SHIRO_CACHE = "shiro:cache:";
    public  static String getPREFIX_SHIRO_CACHE(LoginClientTypeEnum loginClientTypeEnum){
        return PREFIX_SHIRO_CACHE+loginClientTypeEnum.name()+":";
    }

    /**
     * redis-key-前缀-shiro:access_token:
     */
    public final static String PREFIX_SHIRO_ACCESS_TOKEN = "shiro:access_token:";
    public  static String getPREFIX_SHIRO_ACCESS_TOKEN(LoginClientTypeEnum loginClientTypeEnum){
        return PREFIX_SHIRO_ACCESS_TOKEN+loginClientTypeEnum.name()+":";
    }
    /**
     * redis-key-前缀-shiro:refresh_token:
     */
    public final static String PREFIX_SHIRO_REFRESH_TOKEN = "shiro:refresh_token:";
    public  static String getPREFIX_SHIRO_REFRESH_TOKEN(LoginClientTypeEnum loginClientTypeEnum){
        return PREFIX_SHIRO_REFRESH_TOKEN+loginClientTypeEnum.name()+":";
    }
    /**
     * JWT-loginClientType
     */
    public final static String LOGINCLIENTTYPE = "loginClientType";
   /**
     * JWT-userid:
     */
    public final static String USERID = "userid";

    /**
     * JWT-account:
     */
    public final static String ACCOUNT = "account";

    /**
     * JWT-currentTimeMillis:
     */
    public final static String CURRENT_TIME_MILLIS = "currentTimeMillis";

    /**
     * PASSWORD_MAX_LEN
     */
    public static final Integer PASSWORD_MAX_LEN = 8;

}
