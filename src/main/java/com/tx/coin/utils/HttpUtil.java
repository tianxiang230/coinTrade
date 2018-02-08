package com.tx.coin.utils;

import org.apache.http.*;
import org.apache.http.client.ClientProtocolException;
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
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author 你慧快乐
 * @version V1.0
 * @Package com.tx.coin.utils
 * @Description
 * @date 2018-2-5 15:53
 */
public class HttpUtil {
    private Logger logger = LoggerFactory.getLogger(HttpUtil.class);
    private static HttpUtil instance = new HttpUtil();
    private static HttpClient client;
    private static long startTime = System.currentTimeMillis();
    public static PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager();
    private static ConnectionKeepAliveStrategy keepAliveStrat = new DefaultConnectionKeepAliveStrategy() {
        @Override
        public long getKeepAliveDuration(HttpResponse response, HttpContext context) {
            long keepAlive = super.getKeepAliveDuration(response, context);

            if (keepAlive == -1) {
                keepAlive = 5000;
            }
            return keepAlive;
        }

    };

    private HttpUtil() {
        client = HttpClients.custom().setConnectionManager(cm).setKeepAliveStrategy(keepAliveStrat).build();
    }

    public static void IdleConnectionMonitor() {
        if (System.currentTimeMillis() - startTime > 30000) {
            startTime = System.currentTimeMillis();
            cm.closeExpiredConnections();
            cm.closeIdleConnections(30, TimeUnit.SECONDS);
        }
    }

    private static RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(20000).setConnectTimeout(20000)
            .setConnectionRequestTimeout(20000).build();

    public static HttpUtil getInstance() {
        return instance;
    }

    public HttpClient getHttpClient() {
        return client;
    }

    private HttpPost httpPostMethod(String url) {
        return new HttpPost(url);
    }

    private HttpRequestBase httpGetMethod(String url) {
        return new HttpGet(url);
    }

    public String requestHttpGet(String url, Map<String, String> params) {
        String pas = "";
        for (Map.Entry<String, String> entry : params.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            pas += key + "=" + value + "&";
        }
        pas = pas.substring(0, pas.length() - 1);
        return this.requestHttpGet(url, pas);
    }

    public String requestHttpGet(String url, String param) {
        logger.info("Http请求,url:{},param:{}", url, param);
        IdleConnectionMonitor();
        if (param != null && !param.equals("")) {
            if (url.endsWith("?")) {
                url = url + param;
            } else {
                url = url + "?" + param;
            }
        }
        HttpRequestBase method = this.httpGetMethod(url);
        method.setConfig(requestConfig);
        String responseData = null;
        try {
            HttpResponse response = client.execute(method);
            HttpEntity entity = response.getEntity();
            if (entity == null) {
                return "";
            }
            responseData = EntityUtils.toString(entity, "utf-8");
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return responseData;
    }

    public String requestHttpPost(String url, Map<String, String> params) {
        IdleConnectionMonitor();
        HttpPost method = this.httpPostMethod(url);
        List<NameValuePair> valuePairs = this.convertMap2PostParams(params);
        UrlEncodedFormEntity urlEncodedFormEntity = new UrlEncodedFormEntity(valuePairs, Consts.UTF_8);
        method.setEntity(urlEncodedFormEntity);
        method.setConfig(requestConfig);
        String responseData = null;
        try {
            HttpResponse response = client.execute(method);
            HttpEntity entity = response.getEntity();
            if (entity == null) {
                return null;
            }
            responseData = EntityUtils.toString(entity, Consts.UTF_8);
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return responseData;

    }

    private List<NameValuePair> convertMap2PostParams(Map<String, String> params) {
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
