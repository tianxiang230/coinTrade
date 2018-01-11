package com.tx.coin.service.impl;

import com.tx.coin.service.ICoinTradeService;
import com.tx.coin.utils.EncryptHelper;
import com.tx.coin.utils.HttpUtilManager;
import org.apache.http.HttpException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by 你慧快乐 on 2018-1-10.
 */
@Service
public class CoinTradeServiceImpl implements ICoinTradeService {
    @Value("${interface.apiKey}")
    private String apiKey;
    @Value("${interface.secretKey}")
    private String secretKey;
    @Value("${coin.remote.trade}")
    private String tradeUrl;

    Logger logger = LoggerFactory.getLogger(CoinQuotationsServiceImpl.class);

    @Override
    public boolean coinTrade(String symbol, String type, double price, double amount) {
        Map<String,String> param=new HashMap<>();
        param.put("api_key",apiKey);
        param.put("symbol",symbol);
        param.put("type",type);
        param.put("price",String.valueOf(price));
        param.put("amount",String.valueOf(amount));
        String sign = EncryptHelper.sign(param,secretKey,"utf-8");
        param.put("sign",sign);
        param.put("secret_key",secretKey);
        String result=null;
        try {
         result  = HttpUtilManager.getInstance().requestHttpPost(tradeUrl,param);
        } catch (HttpException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        logger.info("币币交易返回:{}",result);
        return false;
    }
}
