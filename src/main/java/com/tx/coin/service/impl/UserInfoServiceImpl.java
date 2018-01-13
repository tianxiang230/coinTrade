package com.tx.coin.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tx.coin.config.PropertyConfig;
import com.tx.coin.dto.UserInfoDTO;
import com.tx.coin.service.IUserInfoService;
import com.tx.coin.utils.EncryptHelper;
import com.tx.coin.utils.HttpUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by 你慧快乐 on 2018-1-11.
 */
@Service
public class UserInfoServiceImpl implements IUserInfoService {
   @Autowired
   private PropertyConfig propertyConfig;
    @Value("${coin.remote.userinfo}")
    private String userInfoUrl;
    private ObjectMapper mapper=new ObjectMapper();

    private Logger logger= LoggerFactory.getLogger(UserInfoServiceImpl.class);
    @Override
    public UserInfoDTO getUserInfo() {
        String apiKey= propertyConfig.getApiKey();
        String secretKey= propertyConfig.getSecretKey();
        Map<String,String> param=new HashMap<>();
        param.put("api_key",apiKey);
        String sign = EncryptHelper.sign(param,secretKey,"utf-8");
        param.put("secret_key",secretKey);
        param.put("sign",sign);
        String result=null;
        try {
            result = HttpUtil.doPostSSL(userInfoUrl,param);
            if (StringUtils.isNotBlank(result)){
                JsonNode rootNode=mapper.readTree(result);
                boolean success=rootNode.get("result").asBoolean();
                if (success){
                    JsonNode freeNode=rootNode.get("info").get("funds").get("free");
                    String freeStr=freeNode.toString();
                    UserInfoDTO userInfo=mapper.readValue(freeStr,UserInfoDTO.class);
                    return userInfo;
                }else{
                    logger.info("获取用户信息失败");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
