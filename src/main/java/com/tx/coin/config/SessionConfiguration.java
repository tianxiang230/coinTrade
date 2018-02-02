package com.tx.coin.config;

import com.tx.coin.interceptor.SessionInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * @author 你慧快乐
 * @version V1.0
 * @Package com.tx.coin.config
 * @Description 拦截器配置
 * @date 2018-2-2 15:55
 */
@Configuration
public class SessionConfiguration extends WebMvcConfigurerAdapter {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        super.addInterceptors(registry);
        registry.addInterceptor(new SessionInterceptor());
    }
}
