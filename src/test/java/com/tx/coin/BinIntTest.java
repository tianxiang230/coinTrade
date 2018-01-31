package com.tx.coin;

import com.alibaba.fastjson.JSON;
import com.tx.coin.dto.UserInfoDTO;
import com.tx.coin.service.IUserInfoService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author 你慧快乐
 * @version V1.0
 * @Package com.tx.coin
 * @Description
 * @date 2018-1-31 20:53
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class BinIntTest {
    @Autowired
    @Qualifier(value = "binUserInfoServiceImpl")
    private IUserInfoService userInfoService;

    @Test
    public void testUserInfo() {
        UserInfoDTO userInfoDTO = userInfoService.getUserInfo();
        System.out.println(JSON.toJSONString(userInfoDTO));
    }
}
