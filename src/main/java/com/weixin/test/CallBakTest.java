package com.weixin.test;

import com.weixin.config.WeixinConfig;
import com.weixin.util.OrderUtils;
import org.apache.commons.httpclient.NameValuePair;

import java.net.UnknownHostException;
import java.util.List;
import java.util.Map;

/**
 * Created by wangshuang on 17/4/6.
 */
public class CallBakTest {


    public static void main(String args[]){


        int overTime = 30; // 设置过期时间
        List<NameValuePair> packageParams = null;
        try {
            packageParams = OrderUtils.CallBakInfo("20170405022931667666" + "KEEGOO" + "1",
                    "1",
                    "JSAPI",
                    "onFqcuP6OJA6oBq-ICTpVfF0V0Yo",
                    WeixinConfig.GZH_APPID,
                    WeixinConfig.GZH_MCHID,
                    overTime,
                    "4004372001201704055943476522");

        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

        String sign = OrderUtils.genWeixinSign(packageParams, WeixinConfig.GZH_KEY);
        packageParams.add(new NameValuePair("sign", sign));
        String params = OrderUtils.toXml(packageParams);
        Map<String, String> paramsMap = OrderUtils.sendWeixinPost(params);
        System.out.print(paramsMap);

    }

}
