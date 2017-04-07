package com.weixin.util;

/**
 * Project Name:keegoo-service
 * File Name:DataTypeUtils.java
 * Package Name:com.keegoo.util
 * Date:2015年8月27日上午10:51:48
 * Copyright (c) 2015, sid Jenkins All Rights Reserved.
 *
 *
 */
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * ClassName:DataTypeUtils
 * Reason:	 增加类型判断工具类.
 * Date:     2015年8月27日 上午10:51:48
 * @author   sid
 * @see
 */
public class DataTypeUtils {


    /**
     * 判断当前参数列表是否均不为空
     * @param objects
     * @return
     */
    @SuppressWarnings("rawtypes")
    public static boolean isNotEmpty(Object... objects) {
        if (objects == null) {
            return false;
        }
        for (Object obj : objects) {
            if (obj == null) {
                return false;
            }
            if (obj instanceof String) {
                if ("null".equals(((String) obj).trim())||"".equals(((String) obj).trim())) {
                    return false;
                }
            }
            if (obj instanceof List){
                if (((List) obj).size()==0) {
                    return false;
                }
            }
            if (obj instanceof Iterable){
                if (!((Iterable) obj).iterator().hasNext()) {
                    return false;
                }
            }
            if (obj instanceof Map){
                if (((Map) obj).keySet().isEmpty()) {
                    return false;
                }
            }
        }

        return true;
    }

    /**
     * 填充字符串；不足自动前置补充
     * @param source
     * @param length
     * @param sign
     * @return
     */
    public static String fillDataPre(String source,int length,String sign){
        String result = "";
        int num = length-source.length();
        for (int i = 0; i < num; i++) {
            result+=sign;
        }
        result+=source;
        return result;
    }

    /**
     * 手机号验证
     *
     * @param  str
     * @return 验证通过返回true
     */
    public static boolean isMobile(String str) {
        Pattern p = null;
        Matcher m = null;
        boolean b = false;
        p = Pattern.compile("^((1[3,5,8][0-9])|(14[5,7])|(17[0,6,7,8]))\\d{8}$"); // 验证手机号
        m = p.matcher(str);
        b = m.matches();
        return b;
    }

    /**
     *
     * isBiggerThan:
     *
     * @author sid
     * @param a
     * @param b
     * @return a>b true;a<b false
     */
    public static boolean isBiggerThan(String a,String b){
        return a.compareTo(b)>0?true:false;
    }

    public static void main(String[] args) {
        System.out.println(DataTypeUtils.isMobile("15010331011"));
    }
}


