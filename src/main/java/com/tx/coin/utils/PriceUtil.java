package com.tx.coin.utils;

import java.util.List;

/**
 * @author 你慧快乐
 * @version V1.0
 * @Package com.tx.coin.utils
 * @Description 价格计算工具
 * @date 2018-1-26 18:42
 */
public class PriceUtil {
    public static double calcuMd(List<Double> priceList) {
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
