package com.tx.coin.context;

import com.tx.coin.entity.PlatFormConfig;

/**
 * @author 你慧快乐
 * @version V1.0
 * @Package com.tx.coin.context
 * @Description 记录当前线程的平台配置
 * @date 2018-2-1 14:33
 */
public class PlatConfigContext {
    private static ThreadLocal<PlatConfigContext> context = new ThreadLocal() {
        @Override
        protected PlatConfigContext initialValue() {
            super.initialValue();
            return new PlatConfigContext();
        }
    };

    private PlatFormConfig config;

    public static PlatFormConfig getCurrentConfig() {
        return context.get().config;
    }

    public static PlatConfigContext getCurrentContext() {
        return context.get();
    }

    public static void setCurrentConfig(PlatFormConfig platFormConfig) {
        PlatConfigContext configContext = getCurrentContext();
        configContext.config = platFormConfig;
    }

    public static void clean() {
        context.remove();
    }
}
