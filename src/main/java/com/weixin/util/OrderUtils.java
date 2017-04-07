package com.weixin.util;

import com.weixin.config.AlipayCore;
import com.weixin.config.AlipayConfig;
import com.weixin.config.WeixinConfig;
import org.apache.commons.httpclient.ConnectTimeoutException;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.*;

/**
 * Created by wangshuang on 17/4/6.
 */
public class OrderUtils {


    private static final Logger logger = LoggerFactory.getLogger(OrderUtils.class);

    /**
     * alipaySign: 生成支付宝订单签名
     * @author sid
     * @param privateKey
     * @param input_charset
     * @return
     */
    public static String alipaySign(String content, String privateKey, String input_charset) {
        return RSA.sign(content, privateKey, input_charset);
    }

    /**
     * weixinSign: 生成微信订单签名
     * @author sid
     * @return
     */
    public static String weixinSign(String content) {
        String packageSign = MD5.getMessageDigest(content.toString().getBytes()).toUpperCase();
        return packageSign;
    }

    /**
     * 发送微信请求 sendWeixinPost:
     *
     * @author sid
     * @param xml
     * @return
     */
    public static Map<String, String> sendWeixinPost(String xml) {

        PostMethod post = new PostMethod(WeixinConfig.USERVICE_URL);
        post.setRequestHeader("Pragma:", "no-cache");
        post.setRequestHeader("Cache-Control", "no-cache");
        post.setRequestHeader("Content-Type", "text/xml");

        post.setRequestBody(xml);
        try {
            String res = HttpClientUtil.httpRequest(post);
            // 获取微信POST过来反馈信息
            Map<String, String> params = XmlConverUtil.xmltoMap(res);
            return params;
        } catch (ConnectTimeoutException e) {
            e.printStackTrace();
        } catch (HttpException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 四舍五入 formatDoubel:
     *
     * @author sid
     * @param f
     *            需要精确的float类型
     * @param scale
     *            需要精确的位数
     * @return
     */
    public static float formatDoubel(float f, int scale) {
        BigDecimal b = new BigDecimal(f);
        float f1 = b.setScale(scale, BigDecimal.ROUND_HALF_UP).floatValue();
        b = null;
        return f1;
    }

    /**
     * 直接舍弃 formatDoubelDown:
     *
     * @author sid
     * @param f
     *            需要精确的float类型
     * @param scale
     *            需要精确的位数
     * @return
     */
    public static float formatDoubelDown(float f, int scale) {
        BigDecimal b = new BigDecimal(f);
        float f1 = b.setScale(scale, BigDecimal.ROUND_DOWN).floatValue();
        b = null;
        return f1;
    }

    /**
     * 拼成订单信息字符串 getParamsFromMap:
     *
     * @author sid
     * @param map
     * @return
     */
    public static String getParamsFromMap(Map<String, String> map) {
        return AlipayCore.createLinkString(map);
    }

    /**
     *
     * genNonceStr:
     *
     * @author sid
     * @return
     */
    public static String genNonceStr() {
        Random random = new Random();
        return MD5.getMessageDigest(String.valueOf(random.nextInt(10000)).getBytes());
    }

    /**
     * 获取支付宝订单 getAlipayOrderInfo:
     *
     * @author sid
     * @param callback
     * @param out_trade_no
     * @param total_fee
     * @param subject
     * @param body
     * @return
     */
    public static String getAlipayOrderInfo(String service, String callback, String out_trade_no, String total_fee,
                                            String subject, String body, int overTime) {
        // 签约合作者身份ID
        StringBuffer signInfo = new StringBuffer();
        signInfo.append("partner=").append("\"").append(AlipayConfig.partner).append("\"");
        // 签约卖家支付宝账号
        signInfo.append("&seller_id=").append("\"").append(AlipayConfig.seller).append("\"");
        // 商户网站唯一订单号
        signInfo.append("&out_trade_no=").append("\"").append(out_trade_no).append("\"");
        // 商品名称
        signInfo.append("&subject=").append("\"").append(subject).append("\"");
        // 商品详情
        signInfo.append("&body=").append("\"").append(body).append("\"");
        // 商品金额
        signInfo.append("&total_fee=").append("\"").append(total_fee).append("\"");
        // 服务器异步通知页面路径
        signInfo.append("&notify_url=").append("\"").append(callback).append("\"");
        // 服务接口名称， 固定值
        signInfo.append("&service=\"").append(service).append("\"");
        // 支付类型， 固定值
        signInfo.append("&payment_type=\"1\"");
        // 参数编码， 固定值
        signInfo.append("&_input_charset=\"utf-8\"");
        // 设置未付款交易的超时时间
        // 默认30分钟，一旦超时，该笔交易就会自动被关闭。
        // 取值范围：1m～15d。
        // m-分钟，h-小时，d-天，1c-当天（无论交易何时创建，都在0点关闭）。
        // 该参数数值不接受小数点，如1.5h，可转换为90m。
        signInfo.append("&it_b_pay=\"").append(overTime).append("m\"");
        // extern_token为经过快登授权获取到的alipay_open_id,带上此参数用户将使用授权的账户进行支付
        // orderInfo += "&extern_token=" + "\"" + extern_token + "\"";
        // 支付宝处理完请求后，当前页面跳转到商户指定页面的路径，可空
        // orderInfo += "&return_url=\"m.alipay.com\"";
        // 调用银行卡支付，需配置此参数，参与签名， 固定值 （需要签约《无线银行卡快捷支付》才能使用）
        // orderInfo += "&paymethod=\"expressGateway\"";
        return signInfo.toString();
    }

    /**
     * 获取支付宝订单JSAPI getAlipayOrderJSAPIInfo:
     *
     * @author sid
     * @param callback
     * @param out_trade_no
     * @param total_fee
     * @param subject
     * @param body
     * @return
     */
    public static String getAlipayOrderJSAPIInfo(String service, String callback, String out_trade_no, String total_fee,
                                                 String subject, String body, String return_url, int overTime) {
        Map<String, String> map = new HashMap<String, String>();
        map.put("partner", AlipayConfig.partner);
        map.put("seller_id", AlipayConfig.seller);
        map.put("out_trade_no", out_trade_no);
        map.put("subject", subject);
        map.put("body", body);
        map.put("total_fee", total_fee);
        map.put("notify_url", callback);
        map.put("return_url", return_url);
        map.put("service", service);
        map.put("payment_type", String.valueOf(1));
        map.put("_input_charset", "utf-8");
        map.put("it_b_pay", overTime + "m");
        return AlipayCore.createLinkString(map);
    }

    /**
     *
     * getWeixinOrderInfo:
     *
     * @author sid
     * @param callback
     * @param out_trade_no
     * @param total_fee
     * @param body
     * @return
     * @throws UnknownHostException
     */
    public static List<NameValuePair> getWeixinOrderInfo(String callback, String out_trade_no, String total_fee,
                                                         String body, String type, String openid, String product_id, String appId, String mchId, int overTime)
            throws UnknownHostException {
        String time_expire = DateUtils.dateToString(DateUtils.getDateAddMinutes(new Date(), overTime), "yyyyMMddHHmmss");
        String ip = InetAddress.getLocalHost().getHostAddress();
        String nonceStr = OrderUtils.genNonceStr();

        BigDecimal v1 = new BigDecimal(total_fee);
        BigDecimal v2 = new BigDecimal("100");
        Double b = v1.multiply(v2).doubleValue();
        int fee = b.intValue();

        List<NameValuePair> packageParams = new LinkedList<NameValuePair>();
        //公众号ID
        if (!StringUtils.isNotBlank(appId)) {
            logger.info("weixin appid Can't null");
        }
        packageParams.add(new NameValuePair("appid", appId));
        //商品描述
        if (!StringUtils.isNotBlank(body)) {
            logger.info("weixin body Can't null");
        }
        packageParams.add(new NameValuePair("body", body));
        //商户号
        if (!StringUtils.isNotBlank(mchId)) {
            logger.info("weixin mchId Can't null");
        }
        packageParams.add(new NameValuePair("mch_id", mchId));
        //随机字符串
        if (!StringUtils.isNotBlank(mchId)) {
            logger.info("weixin nonce_str Can't null");
        }
        packageParams.add(new NameValuePair("nonce_str", nonceStr));
        //通知地址
        if (!StringUtils.isNotBlank(mchId)) {
            logger.info("weixin notify_url Can't null");
        }
        packageParams.add(new NameValuePair("notify_url", callback));
        if (DataTypeUtils.isNotEmpty(openid)) {
            packageParams.add(new NameValuePair("openid", openid));
        }
        //商户订单号

        if (!StringUtils.isNotBlank(out_trade_no)) {
            logger.info("weixin out_trade_no Can't null");
        }
        packageParams.add(new NameValuePair("out_trade_no", out_trade_no));

        //产品编号
        if (DataTypeUtils.isNotEmpty(product_id)) {
            packageParams.add(new NameValuePair("product_id", product_id));
        }
        //终端IP
        if (!StringUtils.isNotBlank(ip)) {
            logger.info("weixin spbill_create_ip Can't null");
        }
        packageParams.add(new NameValuePair("spbill_create_ip", ip));
        //交易结束时间
        packageParams.add(new NameValuePair("time_expire", time_expire));
        //总金额

        packageParams.add(new NameValuePair("total_fee", String.valueOf(fee)));
        //交易类型
        packageParams.add(new NameValuePair("trade_type", type));

        return packageParams;
    }

    /**
     *
     * CallBakInfo:
     *
     * @throws UnknownHostException
     */
    public static List<NameValuePair> CallBakInfo(String out_trade_no, String total_fee, String type,  String openid, String appId, String mchId, int overTime, String transaction_id)
            throws UnknownHostException {
        String time_expire = DateUtils.dateToString(DateUtils.getDateAddMinutes(new Date(), overTime), "yyyyMMddHHmmss");
        String nonceStr = OrderUtils.genNonceStr();

        BigDecimal v1 = new BigDecimal(total_fee);
        BigDecimal v2 = new BigDecimal("100");
        Double b = v1.multiply(v2).doubleValue();
        int fee = b.intValue();

        List<NameValuePair> packageParams = new LinkedList<NameValuePair>();
        //公众号ID
        if (!StringUtils.isNotBlank(appId)) {
            logger.info("weixin appid Can't null");
        }
        packageParams.add(new NameValuePair("appid", appId));
        //付款银行
        packageParams.add(new NameValuePair("bank_type", "CFT"));
        //现金支付金额
        packageParams.add(new NameValuePair("cash_fee", String.valueOf(fee)));
        //货币种类
        packageParams.add(new NameValuePair("fee_type", "CNY"));
        //是否关注公众账号
        packageParams.add(new NameValuePair("is_subscribe", "Y"));
        //商户号
        if (!StringUtils.isNotBlank(mchId)) {
            logger.info("weixin mchId Can't null");
        }
        packageParams.add(new NameValuePair("mch_id", mchId));
        //随机字符串
        if (!StringUtils.isNotBlank(nonceStr)) {
            logger.info("weixin nonce_str Can't null");
        }
        packageParams.add(new NameValuePair("nonce_str", nonceStr));
        //openid
        if (!StringUtils.isNotBlank(openid)) {
            logger.info("weixin nonce_str Can't null");
        }
        packageParams.add(new NameValuePair("openid", openid));
        //商户订单号
        if (!StringUtils.isNotBlank(out_trade_no)) {
            logger.info("weixin out_trade_no Can't null");
        }
        packageParams.add(new NameValuePair("out_trade_no", out_trade_no));
        //业务结果
        packageParams.add(new NameValuePair("result_code", "SUCCESS"));
        //返回状态码
        packageParams.add(new NameValuePair("return_code", "SUCCESS"));
        //支付完成时间
        packageParams.add(new NameValuePair("time_end", time_expire));
        //总金额
        packageParams.add(new NameValuePair("total_fee", String.valueOf(fee)));
        //交易类型
        packageParams.add(new NameValuePair("trade_type", type));
        //微信支付订单号
        packageParams.add(new NameValuePair("transaction_id", transaction_id));

        return packageParams;
    }

    /**
     *
     * getWeixinPaymentInfo:
     *
     * @author sid
     * @param prepayid
     * @return
     */
    public static List<NameValuePair> getWeixinPaymentInfoAPP(String prepayid) {
        String nonceStr = OrderUtils.genNonceStr();
        String timeStamp = String.valueOf(genTimeStamp());
        List<NameValuePair> packageParams = new LinkedList<NameValuePair>();
        packageParams.add(new NameValuePair("appid", WeixinConfig.getAppid()));
        packageParams.add(new NameValuePair("noncestr", nonceStr));
        packageParams.add(new NameValuePair("package", "Sign=WXPay"));
        packageParams.add(new NameValuePair("partnerid", WeixinConfig.getMchid()));
        packageParams.add(new NameValuePair("prepayid", prepayid));
        packageParams.add(new NameValuePair("timestamp", timeStamp));
        return packageParams;
    }

    /**
     *
     * getWeixinJSAPIInfo:
     *
     * @author sid
     * @param prepayid
     * @return
     */
    public static List<NameValuePair> getWeixinJSAPIInfo(String prepayid) {
        String nonceStr = OrderUtils.genNonceStr();
        String timeStamp = String.valueOf(genTimeStamp());
        List<NameValuePair> packageParams = new LinkedList<NameValuePair>();
        packageParams.add(new NameValuePair("appId", WeixinConfig.GZH_APPID));
        packageParams.add(new NameValuePair("nonceStr", nonceStr));
        packageParams.add(new NameValuePair("package", "prepay_id=" + prepayid));
        packageParams.add(new NameValuePair("signType", "MD5"));
        packageParams.add(new NameValuePair("timeStamp", timeStamp));
        return packageParams;
    }

    /**
     * 微信获取时间戳 genTimeStamp:
     *
     * @author sid
     * @return
     */
    private static long genTimeStamp() {
        return System.currentTimeMillis() / 1000;
    }

    /**
     * 生成签名 genWeixinSign:
     *
     * @author sid
     * @param params
     * @return
     */
    public static String genWeixinSign(List<NameValuePair> params, String key) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < params.size(); i++) {
            sb.append(params.get(i).getName()).append('=');
            sb.append(params.get(i).getValue()).append('&');
        }
        sb.append("key=").append(key);
        logger.info("create weixin pay signParams: {}", sb.toString());
        String packageSign = MD5.getMessageDigest(sb.toString().getBytes()).toUpperCase();
        return packageSign;
    }

    /**
     * 生成签名 genWeixinSign:
     *
     * @author sid
     * @param params
     * @return
     */
    public static String genWeixinSign(String params, String key) {
        StringBuilder sb = new StringBuilder();
        sb.append(params + "&key=").append(key);
        String packageSign = MD5.getMessageDigest(sb.toString().getBytes()).toUpperCase();
        return packageSign;
    }

    /**
     *
     * toXml:
     *
     * @author sid
     * @param params
     * @return
     */
    public static String toXml(List<NameValuePair> params) {
        StringBuilder sb = new StringBuilder();
        sb.append("<xml>");
        for (int i = 0; i < params.size(); i++) {
            sb.append("<" + params.get(i).getName() + ">");
            sb.append(params.get(i).getValue());
            sb.append("</" + params.get(i).getName() + ">");
        }
        sb.append("</xml>");
        return sb.toString();
    }

    /**
     * getRandomStringByLength: 获取一定长度的随机字符串
     *
     * @author sid
     * @param length
     *            指定字符串长度
     * @return 一定长度的字符串
     */
    public static String getRandomStringByLength(int length) {

        String base = "abcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }
        return sb.toString();
    }

}
