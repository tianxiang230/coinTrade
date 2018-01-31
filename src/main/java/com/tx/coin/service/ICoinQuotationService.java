package com.tx.coin.service;

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


}
