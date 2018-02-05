package com.tx.coin.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * 接口响应错误代码
 * Created by 你慧快乐 on 2018-1-12.
 */
public class OkxeResponseCode {
    public static Map<Integer, String> responseCode = new HashMap<>();

    static {
        responseCode.put(10000, "必选参数不能为空");
        responseCode.put(10001, "用户请求频率过快，超过该接口允许的限额");
        responseCode.put(10002, "系统错误");
        responseCode.put(10004, "请求失败");
        responseCode.put(10005, "SecretKey不存在");
        responseCode.put(10006, "Api_key不存在");
        responseCode.put(10007, "签名不匹配");
        responseCode.put(10008, "非法参数");
        responseCode.put(10009, "订单不存在");
        responseCode.put(10010, "余额不足");
        responseCode.put(10011, "买卖的数量小于BTC/LTC最小买卖额度");
        responseCode.put(10012, "当前网站暂时只支持btc_usd ltc_usd");
        responseCode.put(10013, "此接口只支持https请求");
        responseCode.put(10014, "下单价格不得≤0或≥1000000");
        responseCode.put(10015, "下单价格与最新成交价偏差过大");
        responseCode.put(10016, "币数量不足");
        responseCode.put(10017, "API鉴权失败");
        responseCode.put(10018, "借入不能小于最低限额[usd:100,btc:0.1,ltc:1]");
        responseCode.put(10019, "页面没有同意借贷协议");
        responseCode.put(10020, "费率不能大于1%");
        responseCode.put(10021, "费率不能小于0.01%");
        responseCode.put(10023, "获取最新成交价错误");
        responseCode.put(10024, "可借金额不足");
        responseCode.put(10025, "额度已满，暂时无法借款");
        responseCode.put(10026, "OkxeResponseCode(含预约借款)及保证金部分不能提出 ");
        responseCode.put(10027, "修改敏感提币验证信息，24小时内不允许提现");
        responseCode.put(10028, "提币金额已超过今日提币限额");
        responseCode.put(10029, "账户有借款，请撤消借款或者还清借款后提币");
        responseCode.put(10031, "存在BTC/LTC充值，该部分等值金额需6个网络确认后方能提出");
        responseCode.put(10032, "未绑定手机或谷歌验证");
        responseCode.put(10033, "服务费大于最大网络手续费");
        responseCode.put(10034, "服务费小于最低网络手续费");
        responseCode.put(10035, "可用BTC/LTC不足");
        responseCode.put(10036, "提币数量小于最小提币数量");
        responseCode.put(10037, "交易密码未设置");
        responseCode.put(10040, "取消提币失败");
        responseCode.put(10041, "提币地址不存在或未认证 ");
        responseCode.put(10042, "交易密码错误");
        responseCode.put(10043, "合约权益错误，提币失败");
        responseCode.put(10044, "取消借款失败");
        responseCode.put(10047, "当前为子账户，此功能未开放");
        responseCode.put(10048, "提币信息不存在");
        responseCode.put(10049, "小额委托（<0.15BTC)的未成交委托数量不得大于50个");
        responseCode.put(10050, "重复撤单");
        responseCode.put(10052, "提币受限");
        responseCode.put(10064, "美元充值后的48小时内，该部分资产不能提出");
        responseCode.put(10100, "账户被冻结");
        responseCode.put(10101, "订单类型错误");
        responseCode.put(10102, "不是本用户的订单 ");
        responseCode.put(10103, "私密订单密钥错误 ");
        responseCode.put(10216, "非开放API ");
        responseCode.put(1002, "交易金额大于余额");
        responseCode.put(1003, "交易金额小于最小交易值");
        responseCode.put(1004, "交易金额小于0 ");
        responseCode.put(1007, "没有交易市场信息 ");
        responseCode.put(1008, "没有最新行情信息");
        responseCode.put(1009, "没有订单  ");
        responseCode.put(1010, "撤销订单与原订单用户不一致");
        responseCode.put(1011, "没有查询到该用户");
        responseCode.put(1013, "没有订单类型");
        responseCode.put(1014, "没有登录");
        responseCode.put(1015, "没有获取到行情深度信息");
        responseCode.put(1017, "日期参数错误");
        responseCode.put(1018, "下单失败");
        responseCode.put(1019, "撤销订单失败");
        responseCode.put(1024, "币种不存在");
        responseCode.put(1025, "没有K线类型");
        responseCode.put(1026, "没有基准币数量");
        responseCode.put(1027, "参数不合法可能超出限制");
        responseCode.put(1028, "保留小数位失败");
        responseCode.put(1029, "正在准备中");
        responseCode.put(1030, "有融资融币无法进行交易 ");
        responseCode.put(1031, "转账余额不足");
        responseCode.put(1032, "该币种不能转账");
        responseCode.put(1035, "密码不合法 ");
        responseCode.put(1036, "谷歌验证码不合法");
        responseCode.put(1037, "谷歌验证码不正确 ");
        responseCode.put(1038, "谷歌验证码重复使用");
        responseCode.put(1039, "短信验证码输错限制");
        responseCode.put(1040, "短信验证码不合法");
        responseCode.put(1041, "短信验证码不正确");
        responseCode.put(1042, "谷歌验证码输错限制");
        responseCode.put(1043, "登陆密码不允许与交易密码一致");
        responseCode.put(1044, "原密码错误");
        responseCode.put(1045, "未设置二次验证");
        responseCode.put(1046, "原密码未输入");
        responseCode.put(1048, "用户被冻结");
        responseCode.put(1050, "订单已撤销或者撤单中");
        responseCode.put(1051, "订单已完成交易");
        responseCode.put(1201, "账号零时删除 ");
        responseCode.put(1202, "账号不存在");
        responseCode.put(1203, "转账金额大于余额");
        responseCode.put(1204, "不同种币种不能转账");
        responseCode.put(1205, "账号不存在主从关系");
        responseCode.put(1206, "提现用户被冻结");
        responseCode.put(1207, "不支持转账");
        responseCode.put(1208, "没有该转账用户");
        responseCode.put(1209, "当前api不可用 ");
        responseCode.put(1216, "市价交易暂停，请选择限价交易");
        responseCode.put(1217, "您的委托价格超过最新成交价的±5%，存在风险，请重新下单");
        responseCode.put(1218, "下单失败，请稍后再试");
    }

}
