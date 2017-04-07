package com.weixin.redis;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.weixin.bean.CallBack;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;


/**
 * Created by wangshuang on 17/4/7.
 */
@Repository
public class WeixinCallBackRedisDbManager {

    @Autowired
    JedisPool jedisPool;

    /**
     * 用户信息过期时间：半小时
     */
    private static int EXPIRE_TIME = 60 * 30;

    // 微信回调key
    private static final String WEI_XIN_CALL_BACK = "WEI_XIN_CALL_BACK" + ":";

    // 保存微信回调数据
    public void saveCallBackInfo(CallBack callBack, String orderNo) {

        Jedis jedis = jedisPool.getResource();

        String key = WEI_XIN_CALL_BACK + orderNo;

        String weixinInfo = JSONObject.toJSONString(callBack);

        jedis.set(key, weixinInfo);

        jedis.expire(key, EXPIRE_TIME);

        jedis.close();
    }

    // 判断微信回调key是否存在
    public boolean checkCallBackKey(String orderNo){

        Jedis jedis = jedisPool.getResource();

        String key = WEI_XIN_CALL_BACK + orderNo;

        boolean flag = jedis.exists(key);

        jedis.close();

        return flag;

    }

    // 获取微信回调数据
    public CallBack getCallBackInfo(String orderNo){

        Jedis jedis = jedisPool.getResource();

        String key = WEI_XIN_CALL_BACK + orderNo;

        CallBack callback = (CallBack) JSON.parseObject(jedis.get(key), CallBack.class);

        jedis.close();

        return callback;

    }

}
