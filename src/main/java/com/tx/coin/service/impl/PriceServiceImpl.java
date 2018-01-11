package com.tx.coin.service.impl;

import com.tx.coin.service.IPriceService;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Created by 你慧快乐 on 2018-1-11.
 */
@Component
public class PriceServiceImpl implements IPriceService {
    @Override
    public double calcuMd(List<Double> priceList) {
        if (priceList==null || priceList.size()!=39){
            throw new IllegalArgumentException("收盘价格数不足");
        }
        double total=0;
        for(int i=0;i<20;i++){
            Double singlePrice=priceList.get(i);
            double sum=0;
            for(int j=i;j<20+i;j++){
                sum+=priceList.get(j);
            }
            double totalAverage=sum/20.0;
            double s=singlePrice-totalAverage;
            total+=s*s;
        }
        return Math.sqrt(total/20.0);
    }
}
