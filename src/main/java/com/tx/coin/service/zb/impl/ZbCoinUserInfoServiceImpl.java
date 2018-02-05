package com.tx.coin.service.zb.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tx.coin.context.PlatConfigContext;
import com.tx.coin.entity.PlatFormConfig;
import com.tx.coin.enums.PlatType;
import com.tx.coin.repository.PlatFormConfigRepository;
import com.tx.coin.service.IUserInfoService;
import com.tx.coin.utils.Digests;
import com.tx.coin.utils.HttpUtil;
import com.tx.coin.utils.SortMapUtil;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * @author 你慧快乐
 * @version V1.0
 * @Package com.tx.coin.service.zb.impl
 * @Description
 * @date 2018-2-5 17:07
 */
@Service
public class ZbCoinUserInfoServiceImpl implements IUserInfoService {
    @Autowired
    private PlatFormConfigRepository configRepository;

    private ObjectMapper objectMapper = new ObjectMapper();

    private Logger logger = LoggerFactory.getLogger(ZbCoinUserInfoServiceImpl.class);
    private String remoteUrl = "https://trade.zb.com/api/getAccountInfo";
    private String method = "getAccountInfo";

    @Override
    public Map<String, Object> getUserInfo() {
        PlatFormConfig zbPropertyConfig = PlatConfigContext.getCurrentConfig();
        Map<String, Object> resultMap = null;
        if (zbPropertyConfig == null) {
            zbPropertyConfig = configRepository.selectByPlat(PlatType.ZB.getCode());
        }
        Map<String, String> params = new HashMap<>(10);
        String accessKey = zbPropertyConfig.getApiKey();
        String secretKey = zbPropertyConfig.getSecretKey();
        params.put("accesskey", accessKey);
        String digest = Digests.SHA(secretKey, null).toLowerCase();
        params.put("method", method);
        // 参数按照ASCII值排序
        String sortParam = SortMapUtil.toStringMap(params);
        String sign = Digests.hmacSign(sortParam, digest, "MD5").toLowerCase();

        params.put("sign", sign);
        params.put("reqTime", System.currentTimeMillis() + "");
        String json = HttpUtil.getInstance().requestHttpPost(remoteUrl, params);
        logger.info("ZB获取用户信息接口返回:{}", json);
        try {
            JsonNode rootNode = objectMapper.readTree(json);
            JsonNode messageNode = rootNode.get("message");
            if (messageNode != null) {
                logger.info("ZB获取用户信息出错:{}", messageNode.asText());
                return resultMap;
            }
            JsonNode coinsNode = rootNode.get("result").get("coins");
            Iterator<JsonNode> elements = coinsNode.elements();
            resultMap = new HashMap<>(40);
            while (elements.hasNext()) {
                JsonNode coinNode = elements.next();
                String key = coinNode.get("key").asText();
                Double amount = coinNode.get("available").asDouble();
                resultMap.put(key, amount);
            }
        } catch (IOException e) {
            logger.info("ZB获取用户信息出错，{}", ExceptionUtils.getStackTrace(e));
        }
        return resultMap;
    }
}
