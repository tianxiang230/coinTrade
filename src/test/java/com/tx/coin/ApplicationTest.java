package com.tx.coin;

import com.tx.coin.entity.OrderRecord;
import com.tx.coin.entity.Quotations;
import com.tx.coin.enums.OrderType;
import com.tx.coin.enums.TradeType;
import com.tx.coin.repository.OrderRecordRepository;
import com.tx.coin.repository.QuotationsRepository;
import com.tx.coin.service.IPriceService;
import com.tx.coin.utils.JsonMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

/**
 * Created by 你慧快乐 on 2018-1-9.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ApplicationTest {
    @Autowired
    IPriceService priceService;

    @Autowired
    OrderRecordRepository orderRecordRepository;
    @Autowired
    QuotationsRepository quotationsRepository;

    @Test
    public void listByOrder(){
        Sort sort=new Sort(Sort.Direction.DESC,"date");
        PageRequest request=new PageRequest(0,10,sort);
        Page<Quotations> page=quotationsRepository.findAll(request);
        List<Quotations> list=page.getContent();
        System.out.println(page.getTotalElements());
        System.out.println(JsonMapper.nonDefaultMapper().toJson(list));
    }

    @Test
    public void findBySymbol(){
        List<Quotations> list=quotationsRepository.findDistinctBySymbolOrderByDate("ltc_btc",39);
        for(int i=0;i<list.size();i++){
            System.out.println(i+"\t"+list.get(i).getId());
        }
//        System.out.println(JsonMapper.nonDefaultMapper().toJson(list));
    }

    @Test
    public void getPrice(){
        List<Double> priceList=null;
//        quotationsRepository.getLastPriceBySymbolOrderByDate("ltc_btc",39);
        Double[] x = {31.45,32.0,32.555,32.347,32.376,31.694,32.106,32.402,32.916,32.834,33.457,32.982,33.291,33.261,33.532,33.531,33.348,33.303,33.591,33.611};
        priceList=Arrays.asList(x);
        System.out.println(priceList.size());
        double price=priceService.calcuMd(priceList);
        System.out.println(price);
    }

    @Test
    public void orderRecordTest(){
        OrderRecord record=new OrderRecord("test", TradeType.BUY, OrderType.COMPLETED,2.3,4.2);
        OrderRecord orderRecord = orderRecordRepository.save(record);
        System.out.println(orderRecord.getId());
    }
}
