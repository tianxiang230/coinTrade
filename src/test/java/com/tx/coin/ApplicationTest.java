package com.tx.coin;

import com.tx.coin.entity.Quotations;
import com.tx.coin.repository.QuotationsRepository;
import com.tx.coin.repository.UserRepository;
import com.tx.coin.entity.User;
import com.tx.coin.service.IPriceService;
import com.tx.coin.utils.JsonMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Map;

/**
 * Created by 你慧快乐 on 2018-1-9.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ApplicationTest {
    @Autowired
    UserRepository repository;
    @Autowired
    IPriceService priceService;

    @Autowired
    QuotationsRepository quotationsRepository;

    @Test
    public void insertUser(){
        User user=new User();
        user.setAge(18);
        user.setName("lihua");
        user=repository.save(user);
        System.out.println(user.getId());
    }

    @Test
    public void select(){
        User user=repository.findByName("lihua");
        System.out.println(JsonMapper.nonDefaultMapper().toJson(user));
    }

    @Test
    public void count(){
        long count=repository.countByAge(18);
        System.out.println(count);
    }

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
        List<Double> priceList=quotationsRepository.getLastPriceBySymbolOrderByDate("ltc_btc",39);
        System.out.println(priceList.size());
        double price=priceService.calcuMd(priceList);
        System.out.println(price);
    }
}
