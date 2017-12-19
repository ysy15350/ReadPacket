package api.base.model;

import common.CommFun;
import common.string.MD5Util;

public class Config {

    public final static boolean isDebug = false;

    /**
     * 私钥
     */
    public final static String PRIVATE_KEY = "cUa3dixDR7nHTcX3gZ5SBHfga04SvW0u";

    // http://101.201.238.253:8080/yljy/sys/sysuser/login?phone=admin&password=123456
    /**
     * 网络连接类型；0:mobile(手机网路);1:WIFI;默认-1
     */
    private static int NETWORKCONNECTEDTYPE = -1;


    // "{\"account\":\"hhlvyou2017\",\"password\":\"hhlvyou_123456\"}");
    private static final String TOKEN_USERNAME = "qukoubei2017";// "huanghelvyou2017";//
    // token账号

    private static final String TOKEN_PASSWORD = "qukoubei_123456";// "batele_wel_passer";//
    // token密码

    private static final String AUTHORIZATION_PRE = "Qukoubei_";// 前缀

    //Qidian_asfsdfsdf

    /**
     * 第一次获取token传入
     */
    private static final String AUTHORIZATION = "header-token";// Gouxiang_header-token
    // ，header信息header_token


    /**
     * token有效时间
     */
    private static final int TOKEN_EFFECTIVE_TIME = 3600 * 1000;// 单位：毫秒

    /**
     * token最后更新时间，时间戳
     */
    private static long TOKEN_REFRESH_TIME;

    /**
     * 心跳时间(后台刷新时间)毫秒
     */
    private static long HeartBeatTime = 1000;

    /**
     * 登录心跳时间(毫秒)
     */
    private static long Login_HeartBeatTime = 60000;

    public static String getTokenUsername() {
        return TOKEN_USERNAME;
    }

    public static String getTokenPassword() {
        return TOKEN_PASSWORD;
    }

    public static String getAuthorizationPre() {
        return AUTHORIZATION_PRE;
    }

    public static String getAUTHORIZATION() {
        return AUTHORIZATION;
    }


    public static int getTokenEffectiveTime() {
        return TOKEN_EFFECTIVE_TIME;
    }

    public static long getTokenRefreshTime() {
        return TOKEN_REFRESH_TIME;
    }

    public static void setTokenRefreshTime(long tokenRefreshTime) {
        TOKEN_REFRESH_TIME = tokenRefreshTime;
    }

    public static long getHeartBeatTime() {
        return HeartBeatTime;
    }

    public static void setHeartBeatTime(long heartBeatTime) {
        HeartBeatTime = heartBeatTime;
    }

    public static long getLogin_HeartBeatTime() {
        return Login_HeartBeatTime;
    }

    public static void setLogin_HeartBeatTime(long login_HeartBeatTime) {
        Login_HeartBeatTime = login_HeartBeatTime;
    }

    /**
     * 获取签名
     *
     * @param aid
     * @param cid
     * @return
     */
    public static String getMd5Sign(int aid, int cid) {

        try {
            if (!CommFun.isNullOrEmpty(PRIVATE_KEY) && aid != 0 && cid != 0) {
                String str = String.format("%s#%d#%d", PRIVATE_KEY, aid, cid);
                return MD5Util.GetMD5Code(str);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }
}
