package com.tx.coin.utils;

import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.LayeredConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.net.ssl.SSLContext;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Created by 你慧快乐 on 2018-1-9.
 */
public class HttpUtil {
    static PoolingHttpClientConnectionManager cm = null;
    private static RequestConfig requestConfig;
    private static final int MAX_TIMEOUT = 3000;

    private static Logger logger = LoggerFactory.getLogger(HttpUtil.class);

    private static void init() {
        LayeredConnectionSocketFactory sslsf = null;
        try {
            sslsf = new SSLConnectionSocketFactory(SSLContext.getDefault());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create()
                .register("https", sslsf)
                .register("http", new PlainConnectionSocketFactory())
                .build();
        cm = new PoolingHttpClientConnectionManager(socketFactoryRegistry);
        cm.setMaxTotal(200);
        cm.setDefaultMaxPerRoute(20);

        RequestConfig.Builder configBuilder = RequestConfig.custom();
        //从连接池中获取连接的超时时间
        configBuilder.setConnectionRequestTimeout(MAX_TIMEOUT);
        // 设置连接超时
        configBuilder.setConnectTimeout(MAX_TIMEOUT);
        // 设置读取超时
        configBuilder.setSocketTimeout(MAX_TIMEOUT);
        // 设置从连接池获取连接实例的超时
        configBuilder.setConnectionRequestTimeout(MAX_TIMEOUT);
        requestConfig = configBuilder.build();
    }

    public static CloseableHttpClient getHttpClient() {
        if (cm == null) {
            init();
        }
        CloseableHttpClient httpClient = HttpClients.custom()
                .setConnectionManager(cm)
                .build();
        /*CloseableHttpClient httpClient = HttpClients.createDefault();//如果不采用连接池就是这种方式获取连接*/
        return httpClient;
    }

    public static String doGetSSL(String apiUrl, Map<String, String> params) {
        return doGetSSL(apiUrl, params, false);
    }

    /**
     * https协议异步get请求
     *
     * @param apiUrl
     * @param params
     * @param isAjax
     * @return
     */
    public static String doGetSSL(String apiUrl, Map<String, String> params, boolean isAjax) {
        CloseableHttpClient httpClient = getHttpClient();
        StringBuffer param = new StringBuffer();
        int i = 0;
        if (params != null) {
            for (String key : params.keySet()) {
                if (i == 0) {
                    param.append("?");
                } else {
                    param.append("&");
                }
                param.append(key).append("=").append(params.get(key));
                i++;
            }
        }
        apiUrl += param;
        logger.info("ssl get请求地址:{}", apiUrl);
        HttpGet httpGet = new HttpGet(apiUrl);
        httpGet.setConfig(requestConfig);
        if (isAjax) {
            httpGet.setHeader("X-Requested-With", "XMLHttpRequest");
        }
        CloseableHttpResponse response = null;
        String httpStr = null;
        try {
            response = httpClient.execute(httpGet);
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode != HttpStatus.SC_OK) {
                return null;
            }
            HttpEntity entity = response.getEntity();
            if (entity == null) {
                return null;
            }
            httpStr = EntityUtils.toString(entity, "utf-8");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (response != null) {
                try {
                    EntityUtils.consume(response.getEntity());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            httpGet.releaseConnection();
        }
//        logger.info("ssl get响应:{}",httpStr);
        return httpStr;
    }

    public static String doPostSSL(String apiUrl, String json) {
        CloseableHttpClient httpClient = getHttpClient();
        HttpPost httpPost = new HttpPost(apiUrl);
        CloseableHttpResponse response = null;
        String httpStr = null;
        try {
            httpPost.setConfig(requestConfig);
            StringEntity stringEntity = new StringEntity(json, "UTF-8");
            stringEntity.setContentEncoding("UTF-8");
            stringEntity.setContentType("application/json");
            httpPost.setEntity(stringEntity);
            response = httpClient.execute(httpPost);
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode != HttpStatus.SC_OK) {
                return null;
            }
            HttpEntity entity = response.getEntity();
            if (entity == null) {
                return null;
            }
            httpStr = EntityUtils.toString(entity, "utf-8");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (response != null) {
                try {
                    EntityUtils.consume(response.getEntity());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            httpPost.releaseConnection();
        }
        return httpStr;
    }

    public static String doPostSSL(String apiUrl, Map<String, String> param) {
//        logger.info("ssl post请求:{}",JsonMapper.nonDefaultMapper().toJson(param));
        CloseableHttpClient httpClient = getHttpClient();
        HttpPost httpPost = new HttpPost(apiUrl);
        httpPost.setHeader("Content-Type", "application/x-www-form-urlencoded");
        CloseableHttpResponse response = null;
        String httpStr = null;
        try {
            httpPost.setConfig(requestConfig);
            List<NameValuePair> valuePairs = convertMap2PostParams(param);
            UrlEncodedFormEntity urlEncodedFormEntity = new UrlEncodedFormEntity(valuePairs, Consts.UTF_8);
            httpPost.setEntity(urlEncodedFormEntity);
            response = httpClient.execute(httpPost);
            int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode != HttpStatus.SC_OK) {
                return null;
            }
            HttpEntity entity = response.getEntity();
            if (entity == null) {
                return null;
            }
            httpStr = EntityUtils.toString(entity, "utf-8");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (response != null) {
                try {
                    EntityUtils.consume(response.getEntity());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            httpPost.releaseConnection();
        }
//        logger.info("ssl post响应:{}",httpStr);
        return httpStr;
    }

    public static List<NameValuePair> convertMap2PostParams(Map<String, String> params) {
        List<String> keys = new ArrayList<String>(params.keySet());
        if (keys.isEmpty()) {
            return null;
        }
        int keySize = keys.size();
        List<NameValuePair> data = new LinkedList<NameValuePair>();
        for (int i = 0; i < keySize; i++) {
            String key = keys.get(i);
            String value = params.get(key);
            data.add(new BasicNameValuePair(key, value));
        }
        return data;
    }
}

