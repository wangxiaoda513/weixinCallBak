package com.weixin.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.weixin.bean.CallBack;
import com.weixin.config.WeixinConfig;
import com.weixin.redis.WeixinCallBackRedisDbManager;
import com.weixin.util.OrderUtils;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.net.UnknownHostException;
import java.util.List;
import java.util.Map;

/**
 * Created by wangshuang on 17/4/6.
 */
@Controller
@RequestMapping("/weixin")
public class CallBakController {

    @Autowired
    private WeixinCallBackRedisDbManager weixinCallBackRedisDbManager;

    @RequestMapping(value = "/pay/callback", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject getTeachersByCategory(@RequestParam(required = true) String orderNo) {

        JSONObject returnObj = new JSONObject();

        CallBack callBack = new CallBack();

        if(StringUtils.isEmpty(orderNo)){
            returnObj.put("status", "10001");
            returnObj.put("msg", "订单编号不能为空");
            return returnObj;
        }

        int overTime = 30; // 设置过期时间
        List<NameValuePair> packageParams = null;

        try {

            // 获取微信回调信息
            if(weixinCallBackRedisDbManager.checkCallBackKey(orderNo)){

                callBack = weixinCallBackRedisDbManager.getCallBackInfo(orderNo);

            }

            if(null == callBack){
                returnObj.put("status", "10001");
                returnObj.put("msg", "未匹配订单编号");
                return returnObj;
            }

            packageParams = OrderUtils.CallBakInfo(
                    callBack.getOut_trade_no(),
                    callBack.getTotal_fee(),
                    callBack.getTrade_type(),
                    callBack.getOpenid(),
                    callBack.getAppid(),
                    callBack.getMch_id(),
                    overTime,
                    callBack.getTransaction_id());

            String sign = OrderUtils.genWeixinSign(packageParams, WeixinConfig.GZH_KEY);
            packageParams.add(new NameValuePair("sign", sign));
            String params = OrderUtils.toXml(packageParams);
            Map<String, String> paramsMap = OrderUtils.sendWeixinPost(params);

            returnObj = (JSONObject) JSON.toJSON(paramsMap);
            return returnObj;

        } catch (UnknownHostException e) {
            returnObj.put("status", "500");
            returnObj.put("msg", "访问无效");
            return returnObj;
        } catch (Exception e) {
            returnObj.put("status", "500");
            returnObj.put("msg", "模拟微信支付回调失败");
            return returnObj;
        }


    }

}
