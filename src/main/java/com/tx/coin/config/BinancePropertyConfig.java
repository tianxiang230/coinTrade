package com.tx.coin.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * @author 你慧快乐
 * @version V1.0
 * @Package com.tx.coin.config
 * @Description
 * @date 2018-1-27 15:24
 */
@PropertySource(value = "classpath:/application.yml", ignoreResourceNotFound = true)
@Configuration
@ConfigurationProperties(prefix = "config.binance")
public class BinancePropertyConfig {
    private String apiKey;

    private String secretKey;

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public String getSecretKey() {
        return secretKey;
    }

    public void setSecretKey(String secretKey) {
        this.secretKey = secretKey;
    }
}
