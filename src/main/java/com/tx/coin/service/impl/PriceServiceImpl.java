package com.tx.coin.service.impl;

import com.tx.coin.service.IPriceService;
import com.tx.coin.utils.MathUtil;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by 你慧快乐 on 2018-1-11.
 */
@Component
public class PriceServiceImpl implements IPriceService {
    @Override
    public double calcuMd(List<Double> priceList) {
        if (priceList == null || priceList.size() != 20) {
            throw new IllegalArgumentException("收盘价格数不足");
        }
        double[] priceArray = new double[priceList.size()];
        for (int i = 0; i < priceList.size(); i++) {
            priceArray[i] = priceList.get(i);
        }
        double stdevp = MathUtil.StandardDiviation(priceArray);
        return stdevp;
    }
}
