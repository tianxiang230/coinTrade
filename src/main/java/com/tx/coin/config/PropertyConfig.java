package com.tx.coin.config;

import com.tx.coin.utils.JsonMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

/**
 * 业务配置
 * Created by 你慧快乐 on 2018-1-12.
 */
@PropertySource(value = "classpath:/application.yml",ignoreResourceNotFound=true)
@Configuration
@ConfigurationProperties(prefix = "config")
public class PropertyConfig {

    private String apiKey;

    private String secretKey;
    /**
     * 是否交易
     */
    private Boolean tradeOrNot;
    /**
     * 底仓方
     */
    private String u1;
    /**
     * ASK方
     */
    private String u2;
    /**
     * 底仓数量
     */
    private Double d1 ;

    private Double d3 ;
    /**
     * 基本手数
     */
    private Double s1 ;
    /**
     * 浮盈率
     */
    private Double y1 , y2 , y3 , y4 , y5 ;
    /**
     * 补仓位置
     */
    private Double b1;

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

    public String getU1() {
        return u1;
    }

    public void setU1(String u1) {
        this.u1 = u1;
    }

    public String getU2() {
        return u2;
    }

    public void setU2(String u2) {
        this.u2 = u2;
    }

    public Double getD1() {
        return d1;
    }

    public void setD1(Double d1) {
        this.d1 = d1;
    }

    public Double getD3() {
        return d3;
    }

    public void setD3(Double d3) {
        this.d3 = d3;
    }

    public Double getS1() {
        return s1;
    }

    public void setS1(Double s1) {
        this.s1 = s1;
    }

    public Double getY1() {
        return y1;
    }

    public void setY1(Double y1) {
        this.y1 = y1;
    }

    public Double getY2() {
        return y2;
    }

    public void setY2(Double y2) {
        this.y2 = y2;
    }

    public Double getY3() {
        return y3;
    }

    public void setY3(Double y3) {
        this.y3 = y3;
    }

    public Double getY4() {
        return y4;
    }

    public void setY4(Double y4) {
        this.y4 = y4;
    }

    public Double getY5() {
        return y5;
    }

    public void setY5(Double y5) {
        this.y5 = y5;
    }

    public Double getB1() {
        return b1;
    }

    public void setB1(Double b1) {
        this.b1 = b1;
    }

    private Logger logger= LoggerFactory.getLogger(PropertyConfig.class);

    public Boolean getTradeOrNot(){
        return tradeOrNot;
    }

    public void setTradeOrNot(Boolean tradeOrNot) {
        this.tradeOrNot = tradeOrNot;
    }

    @Override
    public String toString() {
        Map<String,Object> params=getParams();
        return JsonMapper.nonDefaultMapper().toJson(params);
    }

    public Map<String,Object> getParams(){
        Map<String,Object> params=new HashMap<>();
        params.put("apiKey",getApiKey());
        params.put("secretKey",getSecretKey());
        params.put("b1",getB1());
        params.put("d1",getD1());
        params.put("d3",getD3());
        params.put("s1",getS1());
        params.put("u1",getU1());
        params.put("u2",getU2());
        params.put("y1",getY1());
        params.put("y2",getY2());
        params.put("y3",getY3());
        params.put("y4",getY4());
        params.put("y5",getY5());
        params.put("isTrade",getTradeOrNot());
        return params;
    }

    @PostConstruct
    public void complete(){
        logger.info("创建配置参数完成:{}", toString());
    }
}
