package com.tx.coin.service;

import java.util.Map;
import java.util.Set;

/**
 * @author 你慧快乐
 * @version V1.0
 * @Package com.tx.coin.service.binance.impl
 * @Description 获取货币对
 * @date 2018-2-2 20:57
 */
public interface ISymbolService {
    /**
     * 获取货币对
     *
     * @return
     */
    Map<String, Set<String>> getSymbolPairs();
}
