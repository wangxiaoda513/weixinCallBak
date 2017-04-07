package com.weixin.config;


import com.weixin.util.ConfigUtil;

public class WeixinConfig {


    private static final String sdkVersion = "";

    private static String key = "";
    public static String GZH_KEY = "";
    public static String GZH_KEY_SECRET = "";

    private static String appID = "";
    public static String GZH_APPID = "";

    private static String mchID = "";
    public static String GZH_MCHID = "";

    private static String subMchID = "";

    private static boolean useThreadToDoReport = true;

    private static String ip = "";

    public static String ORDER_API = "";

    public static String PAY_API = "";

    public static String PAY_QUERY_API = "";

    public static String REFUND_API = "";

    public static String REFUND_QUERY_API = "";

    public static String REVERSE_API = "";

    public static String DOWNLOAD_BILL_API = "";

    public static String REPORT_API = "";

    public static String USERVICE_URL = ConfigUtil.getProperty("uServiceUrl");

    public static boolean isUseThreadToDoReport() {
        return useThreadToDoReport;
    }

    public static void setUseThreadToDoReport(boolean useThreadToDoReport) {
        WeixinConfig.useThreadToDoReport = useThreadToDoReport;
    }

    public static String HttpsRequestClassName = "";

    public static void setKey(String key) {
        WeixinConfig.key = key;
    }

    public static void setAppID(String appID) {
        WeixinConfig.appID = appID;
    }

    public static void setMchID(String mchID) {
        WeixinConfig.mchID = mchID;
    }

    public static void setSubMchID(String subMchID) {
        WeixinConfig.subMchID = subMchID;
    }

    public static void setIp(String ip) {
        WeixinConfig.ip = ip;
    }

    public static String getKey(){
        return key;
    }

    public static String getAppid(){
        return appID;
    }

    public static String getMchid(){
        return mchID;
    }

    public static String getSubMchid(){
        return subMchID;
    }

    public static String getIP(){
        return ip;
    }

    public static void setHttpsRequestClassName(String name){
        HttpsRequestClassName = name;
    }

    public static String getSdkVersion(){
        return sdkVersion;
    }

}
