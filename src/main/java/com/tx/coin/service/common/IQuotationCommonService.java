package com.tx.coin.service.common;

import com.tx.coin.enums.PlatType;

import java.util.List;

/**
 * @author 你慧快乐
 * @version V1.0
 * @Package com.tx.coin.service.common
 * @Description
 * @date 2018-1-31 18:08
 */
public interface IQuotationCommonService {
    int DATA_SIZE = 20;

    /**
     * 计算最近20次交易行情数据
     *
     * @param symbol
     * @param platType 平台类型
     * @return
     */
    List<Double> getLocalNewPrice(String symbol, PlatType platType);

    /**
     * 获取前20个整点价格行情
     *
     * @param symbol
     * @param platType 平台类型
     * @return
     */
    List<Double> getHourPrice(String symbol, PlatType platType);
}
