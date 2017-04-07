package com.weixin.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by wangshuang on 17/4/6.
 */
public class ConfigUtil {

    static Properties properties=new Properties();

    private static void load(){
        InputStream is= ConfigUtil.class.getResourceAsStream("/application.properties");
        try {
            properties.load(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static  String getProperty(String key){
        String value= properties.getProperty(key);
        if(value==null){
            load();
            value= properties.getProperty(key);
        }
        return value;
    }

    public static int getIntProperty(String key){
        String value=getProperty(key);
        if(value==null)return 0;
        return Integer.parseInt(value.trim());
    }


    public static void main(String[] args) {
        System.out.println(ConfigUtil.getProperty("uServiceUrl"));
    }

}
