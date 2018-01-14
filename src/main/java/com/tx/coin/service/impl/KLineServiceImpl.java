package com.tx.coin.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tx.coin.config.PropertyConfig;
import com.tx.coin.dto.KLineDataDTO;
import com.tx.coin.enums.TimeIntervalEnum;
import com.tx.coin.service.IKLineService;
import com.tx.coin.utils.EncryptHelper;
import com.tx.coin.utils.HttpUtil;
import com.tx.coin.utils.IteratorUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;

/**
 * @author 你慧快乐
 * @version V1.0
 * @Package com.tx.coin.service.impl
 * @Description
 * @date 2018-1-13 22:57
 */
@Service
public class KLineServiceImpl implements IKLineService {
    @Autowired
    private PropertyConfig propertyConfig;
    @Value("${coin.remote.kline}")
    private String klineUrl;

    private Logger logger= LoggerFactory.getLogger(KLineServiceImpl.class);

    private ObjectMapper objectMapper=new ObjectMapper();

    @Override
    public List<KLineDataDTO> pullLineData(String symbol, TimeIntervalEnum intervalTye, int size, Date since) {
        String apiKey = propertyConfig.getApiKey();
        String secretKey = propertyConfig.getSecretKey();
        Map<String, String> param = new HashMap<>(15);
        param.put("api_key", apiKey);
        param.put("symbol", symbol);
        param.put("type", intervalTye.getValue());
        param.put("size", String.valueOf(size));
        if (since!=null){
            param.put("since",String.valueOf(since.getTime()));
        }
        String sign = EncryptHelper.sign(param, secretKey, "utf-8");
        param.put("sign", sign);
        param.put("secret_key", secretKey);
        String result = null;
        try {
            result = HttpUtil.doGetSSL(klineUrl, param);
            try {
                JsonNode rootNode = objectMapper.readTree(result);
                List<KLineDataDTO> lineDatas=new ArrayList<>();
                Iterator<JsonNode> iterable = rootNode.elements();
                while(iterable.hasNext()){
                    JsonNode data = iterable.next();
                    List<JsonNode> detail=IteratorUtils.copyIterator(data.elements());
                    KLineDataDTO lineDataDTO=new KLineDataDTO();
                    lineDataDTO.setTime(new Date(detail.get(0).asLong()));
                    lineDataDTO.setOpen(detail.get(1).asDouble());
                    lineDataDTO.setHigh(detail.get(2).asDouble());
                    lineDataDTO.setLow(detail.get(3).asDouble());
                    lineDataDTO.setClose(detail.get(4).asDouble());
                    lineDataDTO.setAmount(detail.get(5).asDouble());
                    lineDatas.add(lineDataDTO);
                }
                return lineDatas;
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
