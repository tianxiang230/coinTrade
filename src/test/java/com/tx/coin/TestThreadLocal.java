package com.tx.coin;

import com.tx.coin.context.PlatConfigContext;
import com.tx.coin.entity.PlatFormConfig;

/**
 * @author 你慧快乐
 * @version V1.0
 * @Package com.tx.coin
 * @Description
 * @date 2018-2-1 14:25
 */
public class TestThreadLocal {

    public static void main(String[] args) {
        PlatFormConfig config = new PlatFormConfig();
        config.setApiKey("98fds9afd0s0a8");
        PlatConfigContext.setCurrentConfig(config);
        exeFun();
    }

    private static void exeFun() {
        PlatFormConfig currentConfig = PlatConfigContext.getCurrentConfig();
        System.out.println("apikey in exe:" + currentConfig.getApiKey());
        currentConfig = new PlatFormConfig();
        currentConfig.setApiKey("34567");
        PlatConfigContext.setCurrentConfig(currentConfig);
        exeFun2();
    }

    private static void exeFun2() {
        PlatFormConfig currentConfig = PlatConfigContext.getCurrentConfig();
        System.out.println("apikey in exe2:" + currentConfig.getApiKey());
    }
}
