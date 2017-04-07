package com.weixin.util;

import com.weixin.util.MD5Util;

import java.util.*;

/**
 * Created by wangshuang on 17/4/6.
 */
public class PayTest {

    //http://mch.weixin.qq.com/wiki/doc/api/index.php?chapter=4_3
    private static String Key = "192006250b4c09247ec02edce69f6a2d";

    /**
     * @param args
     */
    public static void main(String[] args) {
        System.out.println(">>>模拟微信支付<<<");
        System.out.println("==========华丽的分隔符==========");
        //微信api提供的参数
        String appid = "wxd930ea5d5a258f4f";
        String mch_id = "10000100";
        String device_info = "1000";
        String body = "test";
        String nonce_str = "ibuaiVcKdpRxkhJA";

        SortedMap<Object,Object> parameters = new TreeMap<Object,Object>();
        parameters.put("appid", appid);
        parameters.put("mch_id", mch_id);
        parameters.put("device_info", device_info);
        parameters.put("body", body);
        parameters.put("nonce_str", nonce_str);

        String characterEncoding = "UTF-8";
        String weixinApiSign = "9A0A8659F005D6984697E2CA0A9CF3B7";
        System.out.println("微信的签名是：" + weixinApiSign);
        String mySign = createSign(characterEncoding,parameters);
        System.out.println("我     的签名是："+mySign);

        if(weixinApiSign.equals(mySign)){
            System.out.println("恭喜你成功了~");
        }else{
            System.out.println("注定了你是个失败者~");
        }

        String userAgent = "Mozilla/5.0(iphone;CPU iphone OS 5_1_1 like Mac OS X) AppleWebKit/534.46(KHTML,like Geocko) Mobile/9B206 MicroMessenger/5.0";

        char agent = userAgent.charAt(userAgent.indexOf("MicroMessenger")+15);

        System.out.println("微信的版本号："+new String(new char[]{agent}));
    }

    /**
     * 微信支付签名算法sign
     * @param characterEncoding
     * @param parameters
     * @return
     */
    @SuppressWarnings("unchecked")
    public static String createSign(String characterEncoding,SortedMap<Object,Object> parameters){
        StringBuffer sb = new StringBuffer();
        Set es = parameters.entrySet();//所有参与传参的参数按照accsii排序（升序）
        Iterator it = es.iterator();
        while(it.hasNext()) {
            Map.Entry entry = (Map.Entry)it.next();
            String k = (String)entry.getKey();
            Object v = entry.getValue();
            if(null != v && !"".equals(v)
                    && !"sign".equals(k) && !"key".equals(k)) {
                sb.append(k + "=" + v + "&");
            }
        }
        sb.append("key=" + Key);
        String sign = MD5Util.MD5Encode(sb.toString(), characterEncoding).toUpperCase();
        return sign;
    }

}
