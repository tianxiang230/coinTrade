package com.tx.coin.config;

import com.binance.api.client.BinanceApiClientFactory;
import com.binance.api.client.BinanceApiRestClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * @author 你慧快乐
 * @version V1.0
 * @Package com.tx.coin.config
 * @Description 币安配置
 * @date 2018-1-31 11:19
 */
@Configuration
@Import(BinancePropertyConfig.class)
public class BinanceConfig {
    @Bean
    public BinanceApiRestClient getClient(BinancePropertyConfig config) {
        BinanceApiClientFactory factory = BinanceApiClientFactory.newInstance(config.getApiKey(), config.getSecretKey());
        BinanceApiRestClient client = factory.newRestClient();
        return client;
    }
}
