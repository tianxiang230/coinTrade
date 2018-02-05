package com.tx.coin.service.zb.impl;

import com.tx.coin.context.PlatConfigContext;
import com.tx.coin.dto.OrderInfoDTO;
import com.tx.coin.entity.PlatFormConfig;
import com.tx.coin.enums.PlatType;
import com.tx.coin.repository.PlatFormConfigRepository;
import com.tx.coin.service.IOrderInfoService;
import com.tx.coin.utils.Digests;
import com.tx.coin.utils.HttpUtil;
import com.tx.coin.utils.SortMapUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 你慧快乐
 * @version V1.0
 * @Package com.tx.coin.service.zb.impl
 * @Description
 * @date 2018-2-5 20:17
 */
@Service
public class ZbOrderInfoService implements IOrderInfoService {
    @Autowired
    private PlatFormConfigRepository configRepository;
    private final String remoteUrl = "https://trade.zb.com/api/getUnfinishedOrdersIgnoreTradeType";
    private final String method = "getUnfinishedOrdersIgnoreTradeType";
    private final String defaultPageIndex = "1";
    private final String defaultPageSize = "10";

    @Override
    public List<OrderInfoDTO> getOpenOrderInfo(String orderId, String symbol) {
        PlatFormConfig zbPropertyConfig = PlatConfigContext.getCurrentConfig();
        if (zbPropertyConfig == null) {
            zbPropertyConfig = configRepository.selectByPlat(PlatType.ZB.getCode());
        }
        Map<String, String> params = new HashMap<>(10);
        String accessKey = zbPropertyConfig.getApiKey();
        String secretKey = zbPropertyConfig.getSecretKey();
        params.put("accesskey", accessKey);
        String digest = Digests.SHA(secretKey, null).toLowerCase();
        params.put("method", method);
        params.put("currency", symbol);
        params.put("pageIndex", defaultPageIndex);
        params.put("pageSize", defaultPageSize);
        // 参数按照ASCII值排序
        String sortParam = SortMapUtil.toStringMap(params);
        String sign = Digests.hmacSign(sortParam, digest, "MD5").toLowerCase();

        params.put("sign", sign);
        params.put("reqTime", System.currentTimeMillis() + "");
        String json = HttpUtil.getInstance().requestHttpPost(remoteUrl, params);
        /**
         * currency : 交易类型
         id : 委托挂单号
         price : 单价
         status : 挂单状态(0：待成交,1：取消,2：交易完成,3：待成交未交易部份)
         total_amount : 挂单总数量
         trade_amount : 已成交数量
         trade_date : 委托时间
         trade_money : 已成交总金额
         trade_price : 成交均价
         type : 挂单类型 1/0[buy/sell]
         */
        return null;
    }
}
