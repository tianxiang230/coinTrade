package com.tx.coin;

import com.tx.coin.repository.UserRepository;
import com.tx.coin.entity.User;
import com.tx.coin.utils.JsonMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created by 你慧快乐 on 2018-1-9.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ApplicationTest {
    @Autowired
    UserRepository repository;

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
}
