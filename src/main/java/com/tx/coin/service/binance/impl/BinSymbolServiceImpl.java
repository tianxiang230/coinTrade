package com.tx.coin.service.binance.impl;

import com.binance.api.client.BinanceApiRestClient;
import com.binance.api.client.domain.general.ExchangeInfo;
import com.binance.api.client.domain.general.SymbolInfo;
import com.tx.coin.context.SystemConstant;
import com.tx.coin.service.ISymbolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author 你慧快乐
 * @version V1.0
 * @Package com.tx.coin.service.binance.impl
 * @Description
 * @date 2018-2-2 21:04
 */
@Service
public class BinSymbolServiceImpl implements ISymbolService {
    @Autowired
    private BinanceApiRestClient restClient;

    @Override
    public Map<String, Set<String>> getSymbolPairs() {
        Map<String, Set<String>> result = new HashMap<>(5);
        ExchangeInfo exchangeInfo = restClient.getExchangeInfo();
        List<SymbolInfo> symbolInfos = exchangeInfo.getSymbols();
        Set<String> base = new TreeSet<>();
        Set<String> ask = new TreeSet<>();
        for (SymbolInfo symbolInfo : symbolInfos) {
            base.add(symbolInfo.getBaseAsset());
            ask.add(symbolInfo.getQuoteAsset());
        }
        result.put(SystemConstant.SYMBOL_BASE, base);
        result.put(SystemConstant.SYMBOL_ASK, ask);
        return result;
    }
}
