package com.tx.coin.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * @author 你慧快乐
 * @version V1.0
 * @Package com.tx.coin.enums
 * @Description
 * @date 2018-2-5 15:09
 */
public class ZbResponseCode {
    private static Map<Integer, String> responseCode = new HashMap<>();

    static {
        responseCode.put(1000, "调用成功");
        responseCode.put(1001, "一般错误提示");
        responseCode.put(1002, "内部错误");
        responseCode.put(1003, "验证不通过");
        responseCode.put(1004, "资金安全密码锁定");
        responseCode.put(1005, "资金安全密码错误，请确认后重新输入");
        responseCode.put(1006, "实名认证等待审核或审核不通过");
        responseCode.put(1009, "此接口维护中");
        responseCode.put(2001, "人民币账户余额不足");
        responseCode.put(2002, "比特币账户余额不足");
        responseCode.put(2003, "莱特币账户余额不足");
        responseCode.put(2005, "以太币账户余额不足");
        responseCode.put(2006, "ETC币账户余额不足");
        responseCode.put(2007, "BTS币账户余额不足");
        responseCode.put(2009, "账户余额不足");
        responseCode.put(3001, "挂单没有找到");
        responseCode.put(3002, "无效的金额");
        responseCode.put(3003, "无效的数量");
        responseCode.put(3004, "用户不存在");
        responseCode.put(3005, "无效的参数");
        responseCode.put(3006, "无效的IP或与绑定的IP不一致");
        responseCode.put(3007, "请求时间已失效");
        responseCode.put(3008, "交易记录没有找到");
        responseCode.put(4001, "API接口被锁定或未启用");
        responseCode.put(4002, "请求过于频繁");
    }
}
