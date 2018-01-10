package com.tx.coin.service;

import com.tx.coin.entity.Quotations;

/**
 * 币币行情接口
 * Created by 你慧快乐 on 2018-1-9.
 */
public interface ICoinQuotationService {
    Quotations getQuotation(String symbol);
}
