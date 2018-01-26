package com.tx.coin.service.okxe;

import com.tx.coin.entity.Quotations;

import java.util.List;

/**
 * 币币行情接口
 * Created by 你慧快乐 on 2018-1-9.
 */
public interface ICoinQuotationService {
    /**
     * 获取最新数据并保持
     * @param symbol
     * @return
     */
    Quotations getQuotation(String symbol);

    /**
     * 计算最近20次交易行情数据
     * @param symbol
     * @return
     */
    List<Double> getLocalNewPrice(String symbol);

    /**
     * 获取前20个整点价格行情
     * @param symbol
     * @return
     */
    List<Double> getHourPrice(String symbol);
}
