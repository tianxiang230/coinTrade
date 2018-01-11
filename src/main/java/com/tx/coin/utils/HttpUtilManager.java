package com.tx.coin.utils;

import org.apache.http.*;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.conn.ConnectionKeepAliveStrategy;
import org.apache.http.impl.client.DefaultConnectionKeepAliveStrategy;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;


/**
 * 封装HTTP get post请求，简化发送http请求
 * @author zhangchi
 *
 */
public class HttpUtilManager {
    Logger logger= LoggerFactory.getLogger(HttpUtilManager.class);
    private static HttpUtilManager instance = new HttpUtilManager();
    private static HttpClient client;
    private static long startTime = System.currentTimeMillis();
    public  static PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager();
    private static ConnectionKeepAliveStrategy keepAliveStrat = new DefaultConnectionKeepAliveStrategy() {

        public long getKeepAliveDuration(
                HttpResponse response,
                HttpContext context) {
            long keepAlive = super.getKeepAliveDuration(response, context);

            if (keepAlive == -1) {
                keepAlive = 5000;
            }
            return keepAlive;
        }

    };
    private HttpUtilManager() {
        client = HttpClients.custom().setConnectionManager(cm).setKeepAliveStrategy(keepAliveStrat).build();
    }

    public static void IdleConnectionMonitor(){
        if(System.currentTimeMillis()-startTime>30000){
            startTime = System.currentTimeMillis();
            cm.closeExpiredConnections();
            cm.closeIdleConnections(30, TimeUnit.SECONDS);
        }
    }

    private static RequestConfig requestConfig = RequestConfig.custom()
            .setSocketTimeout(20000)
            .setConnectTimeout(20000)
            .setConnectionRequestTimeout(20000)
            .build();


    public static HttpUtilManager getInstance() {
        return instance;
    }

    public HttpClient getHttpClient() {
        return client;
    }

    private HttpPost httpPostMethod(String url) {
        return new HttpPost(url);
    }

    private  HttpGet httpGetMethod(String url) {
        return new  HttpGet(url);
    }

    public String requestHttpGet(String url,String param) throws HttpException, IOException {
        logger.info("get请求地址:{},参数:{}",url,param);
        IdleConnectionMonitor();
        if(param!=null && !param.equals("")){
            if(url.endsWith("?")){
                url = url+param;
            }else{
                url = url+"?"+param;
            }
        }
        HttpRequestBase method = this.httpGetMethod(url);
        method.setConfig(requestConfig);
        method.setHeader("Content-Type", "application/x-www-form-urlencoded");
        HttpResponse response = client.execute(method);
        HttpEntity entity =  response.getEntity();
        if(entity == null){
            return "";
        }
        InputStream is = null;
        String responseData = "";
        try{
            responseData = EntityUtils.toString(entity,"UTF-8");
        }finally{
            if(is!=null){
                is.close();
            }
        }
        logger.info("get响应:{}",responseData);
        return responseData;
    }

    public String requestHttpPost(String url,Map<String,String> params) throws HttpException, IOException{
        logger.info("post请求地址:{},参数:{}",url,JsonMapper.nonDefaultMapper().toJson(params));
        IdleConnectionMonitor();
        HttpPost method = this.httpPostMethod(url);
        method.setHeader("Content-Type", "application/x-www-form-urlencoded");
        List<NameValuePair> valuePairs = HttpUtil.convertMap2PostParams(params);
        UrlEncodedFormEntity urlEncodedFormEntity = new UrlEncodedFormEntity(valuePairs, Consts.UTF_8);
        method.setEntity(urlEncodedFormEntity);
        method.setConfig(requestConfig);
        HttpResponse response = client.execute(method);
        HttpEntity entity =  response.getEntity();
        if(entity == null){
            return "";
        }
        InputStream is = null;
        String responseData = "";
        try{
            responseData = EntityUtils.toString(entity,"UTF-8");
        }finally{
            if(is!=null){
                is.close();
            }
        }
        logger.info("post响应:{}",responseData);
        return responseData;

    }

}

