package com.weixin.util;

/**
 *
 */

import org.apache.commons.httpclient.*;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.protocol.HTTP;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * @author Jack
 *
 */
public class HttpClientUtil {

    protected static Logger logger = LoggerFactory.getLogger(HttpClientUtil.class);

    private static HttpClient httpClient = null;

    public static String NONE = "none";

    public synchronized static HttpClient getHttpClient(){
        if(httpClient==null && !(httpClient instanceof HttpClient)){
            MultiThreadedHttpConnectionManager connectionManager = new MultiThreadedHttpConnectionManager();
            //最大连接数
            connectionManager.setMaxConnectionsPerHost(10);
            //最大活动连接数
            connectionManager.setMaxTotalConnections(25);
            httpClient = new HttpClient(connectionManager);
        }
        return httpClient;
    }

    /**
     * 处理http请求，此处为通信异常
     * @param httpMethod
     * @return
     * @throws ConnectTimeoutException
     * @throws HttpException
     * @throws IOException
     */
    public static String httpRequest(HttpMethod httpMethod) throws ConnectTimeoutException,HttpException,IOException{

        String response =  NONE;
        int status = 500;
        HttpClient client = HttpClientUtil.getHttpClient();
        client.getParams().setContentCharset(HTTP.UTF_8);
        client.getParams().setVersion(org.apache.commons.httpclient.HttpVersion.HTTP_1_1);
        //请求超时
        client.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 60000);
        //读取超时
        client.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 60000);
        //设置重试次数
        client.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler(3/*三次重试机会*/, false));
        // 执行postMethod
        try{
            status = client.executeMethod(httpMethod);
            //处理301，302后继服务
            if (status == HttpStatus.SC_MOVED_PERMANENTLY  ||  status == HttpStatus.SC_MOVED_TEMPORARILY) {
                // 从头中取出转向的地址
                Header locationHeader = httpMethod.getResponseHeader("location");
                String location = null;
                if (locationHeader != null) {
                    location = locationHeader.getValue();
                    logger.warn("The page was redirected to:" + location);
                }
            }
            if (status!= HttpStatus.SC_OK) {
                throw new Exception("http请求失败!");
            } else if (status== HttpStatus.SC_OK) {
                response=httpMethod.getResponseBodyAsString();
            }
        }catch(Exception e){
            logger.warn("通信异常", e);
        }finally{
            httpMethod.releaseConnection();//释放连接
            //http通信请求返回信息
            logger.debug("Http status="+status+"||Response:"+response);
        }
        return response;
    }
}

