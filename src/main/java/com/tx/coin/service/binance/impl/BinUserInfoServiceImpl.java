package com.tx.coin.service.binance.impl;

import com.binance.api.client.BinanceApiRestClient;
import com.binance.api.client.domain.account.Account;
import com.binance.api.client.domain.account.AssetBalance;
import com.tx.coin.dto.UserInfoDTO;
import com.tx.coin.service.IUserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 你慧快乐
 * @version V1.0
 * @Package com.tx.coin.service.binance.impl
 * @Description
 * @date 2018-1-31 19:38
 */
@Service
public class BinUserInfoServiceImpl implements IUserInfoService {
    @Autowired
    private BinanceApiRestClient restClient;

    @Override
    public Map<String, Object> getUserInfo() {
        Account account = restClient.getAccount();
        return convertToUserInfo(account);
    }


    private Map<String,Object> convertToUserInfo(Account account) {
        Map<String,Object> userInfo = new HashMap<>();
        if (account == null || account.getBalances() == null || account.getBalances().size() == 0) {
            return userInfo;
        }
        List<AssetBalance> balances = account.getBalances();
        for (AssetBalance assetBalance : balances) {
            userInfo.put(assetBalance.getAsset(),assetBalance.getFree());
        }
        return userInfo;
    }
}
