package com.tx.coin.service;

import java.util.List;

/**
 * 计算各价格服务接口
 * Created by 你慧快乐 on 2018-1-11.
 */
public interface IPriceService {

    double calcuMd(List<Double> priceList);
}
