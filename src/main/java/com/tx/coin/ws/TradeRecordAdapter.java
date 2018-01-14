package com.tx.coin.ws;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tx.coin.entity.OrderRecord;
import com.tx.coin.utils.JsonMapper;
import com.tx.coin.ws.dto.ChannelStateDTO;
import com.tx.coin.ws.dto.OrderDetailDTO;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 你慧快乐
 * @version V1.0
 * @Package com.tx.coin.ws
 * @Description
 * @date 2018-1-13 14:05
 */
public class TradeRecordAdapter {
    private static ObjectMapper jsonMapper = new ObjectMapper();
    private final static String ADDCHANNEL = "addChannel";
    private final static String CHANNEL_PRE = "ok_sub_spot_";
    private static Logger logger = LoggerFactory.getLogger(TradeRecordAdapter.class);
    public volatile static Map<String, List<OrderRecordDetail>> tradeRecords = null;

    static {
        tradeRecords = new HashMap<>();
    }

    public synchronized static void updateRecordDetails(String details) {
        try {
            List<OrderDetailDTO> detailList = jsonMapper.readValue(details, new TypeReference<List<OrderDetailDTO>>() {
            });
            for (OrderDetailDTO detailDTO : detailList) {
                String symbol = getSymbol(detailDTO.getChannel());
                List<OrderRecordDetail> recordDetails = new ArrayList<>();
                String[][] data = detailDTO.getData();
                for (String[] record : data) {
                    OrderRecordDetail recordDetail = new OrderRecordDetail(record);
                    recordDetails.add(recordDetail);
                }
                tradeRecords.put(symbol, recordDetails);
            }
        } catch (JsonMappingException jsonMappingException) {
            try {
                List<ChannelStateDTO> stateList = jsonMapper.readValue(details, new TypeReference<List<ChannelStateDTO>>() {
                });
                for (ChannelStateDTO stateDTO : stateList) {
                    if (stateDTO.getData().getResult()) {
                        String symbol = getSymbol(stateDTO.getData().getChannel());
                        logger.info("订阅成交记录[{}]成功", symbol);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            logger.error("解析订阅交易记录数据出错:{}", ExceptionUtils.getStackTrace(e));
        }
    }

    private static String getSymbol(String channel) {
        return channel == null ? null : channel.substring(CHANNEL_PRE.length(), channel.lastIndexOf("_"));
    }

    public static void main(String[] args) {
        String details = "[{\"binary\":0,\"channel\":\"ok_sub_spot_eth_usdt_deals\",\"data\":[[\"46453240\",\"1317.9997\",\"0.00319767\",\"15:30:31\",\"ask\"],[\"46453242\",\"1317.9997\",\"0.0167299\",\"15:30:31\",\"ask\"],[\"46453261\",\"1317.9997\",\"0.15384615\",\"15:30:33\",\"ask\"],[\"46453290\",\"1317.9997\",\"0.15384591\",\"15:30:35\",\"ask\"],[\"46453293\",\"1317.9997\",\"1.0349914\",\"15:30:35\",\"ask\"],[\"46453305\",\"1317.9997\",\"0.01100152\",\"15:30:36\",\"ask\"],[\"46453314\",\"1317.9997\",\"0.21818669\",\"15:30:36\",\"ask\"],[\"46453320\",\"1317.9997\",\"0.00111973\",\"15:30:37\",\"ask\"],[\"46453322\",\"1317.9997\",\"0.051\",\"15:30:37\",\"ask\"],[\"46453324\",\"1317.9997\",\"0.045\",\"15:30:37\",\"ask\"],[\"46453326\",\"1317.9997\",\"0.05670477\",\"15:30:37\",\"ask\"],[\"46453348\",\"1317.9997\",\"0.02029523\",\"15:30:39\",\"ask\"],[\"46453350\",\"1317.9997\",\"0.133514\",\"15:30:39\",\"ask\"],[\"46453369\",\"1317.9997\",\"0.866486\",\"15:30:40\",\"ask\"],[\"46453371\",\"1317.9997\",\"0.077\",\"15:30:40\",\"ask\"],[\"46453373\",\"1317.9997\",\"0.115\",\"15:30:40\",\"ask\"],[\"46453375\",\"1317.9997\",\"0.077\",\"15:30:40\",\"ask\"],[\"46453377\",\"1317.9997\",\"0.864514\",\"15:30:40\",\"ask\"],[\"46453385\",\"1317.9997\",\"0.15380923\",\"15:30:41\",\"ask\"],[\"46453387\",\"1317.9997\",\"0.49018484\",\"15:30:41\",\"ask\"],[\"46453421\",\"1317.9997\",\"0.01\",\"15:30:43\",\"ask\"],[\"46453423\",\"1317.9997\",\"0.134\",\"15:30:43\",\"ask\"],[\"46453425\",\"1317.9997\",\"0.00980923\",\"15:30:43\",\"ask\"],[\"46453448\",\"1317.9997\",\"0.15380923\",\"15:30:45\",\"ask\"],[\"46453466\",\"1317.9997\",\"0.072\",\"15:30:47\",\"ask\"],[\"46453468\",\"1317.98\",\"0.08180923\",\"15:30:47\",\"ask\"],[\"46453492\",\"1317.9997\",\"0.036\",\"15:30:49\",\"ask\"],[\"46453494\",\"1317.98\",\"0.05819077\",\"15:30:49\",\"ask\"],[\"46453496\",\"1317.98\",\"0.09561846\",\"15:30:49\",\"ask\"],[\"46453501\",\"1317.9865\",\"0.35428472\",\"15:30:49\",\"bid\"],[\"46453520\",\"1317.9997\",\"3.07\",\"15:30:50\",\"ask\"],[\"46453529\",\"1317.9997\",\"0.018\",\"15:30:51\",\"ask\"],[\"46453533\",\"1317.9864\",\"0.15380923\",\"15:30:51\",\"ask\"],[\"46453536\",\"1317.9864\",\"0.94696991\",\"15:30:51\",\"ask\"],[\"46453558\",\"1317.9996\",\"0.15380923\",\"15:30:53\",\"ask\"],[\"46453583\",\"1317.9865\",\"0.15380923\",\"15:30:55\",\"ask\"],[\"46453607\",\"1317.9801\",\"0.15380912\",\"15:30:57\",\"ask\"],[\"46453633\",\"1317.9801\",\"0.15360983\",\"15:30:59\",\"ask\"],[\"46453658\",\"1317.9801\",\"0.15360971\",\"15:31:01\",\"ask\"],[\"46453660\",\"1317.9801\",\"0.72045214\",\"15:31:01\",\"ask\"],[\"46453676\",\"1317.9801\",\"0.43962185\",\"15:31:02\",\"ask\"],[\"46453686\",\"1317.9801\",\"0.15360971\",\"15:31:03\",\"ask\"],[\"46453688\",\"1317.9801\",\"0.6972753\",\"15:31:03\",\"ask\"],[\"46453702\",\"1317.9801\",\"0.28\",\"15:31:05\",\"ask\"],[\"46453712\",\"1317.9801\",\"0.15360971\",\"15:31:05\",\"ask\"],[\"46453714\",\"1317.9801\",\"1.07109687\",\"15:31:05\",\"ask\"],[\"46453736\",\"1317.9801\",\"0.15360971\",\"15:31:07\",\"ask\"],[\"46453757\",\"1317.9801\",\"0.15360971\",\"15:31:09\",\"ask\"],[\"46453758\",\"1317.9801\",\"0.82262877\",\"15:31:09\",\"ask\"],[\"46453762\",\"1317.9801\",\"0.16\",\"15:31:10\",\"ask\"],[\"46453774\",\"1317.9801\",\"1.15491065\",\"15:31:10\",\"ask\"],[\"46453776\",\"1317.98\",\"0.07438154\",\"15:31:10\",\"ask\"],[\"46453778\",\"1317.98\",\"1.08341312\",\"15:31:10\",\"ask\"],[\"46453779\",\"1317.98\",\"5.40053014\",\"15:31:10\",\"ask\"],[\"46453781\",\"1317.98\",\"0.50315374\",\"15:31:10\",\"ask\"],[\"46453783\",\"1317.98\",\"7.35633785\",\"15:31:10\",\"ask\"],[\"46453792\",\"1317.98\",\"0.12658688\",\"15:31:11\",\"ask\"],[\"46453794\",\"1317.95\",\"0.02702283\",\"15:31:11\",\"ask\"],[\"46453797\",\"1317.95\",\"0.64884511\",\"15:31:11\",\"ask\"],[\"46453799\",\"1317.95\",\"0.18173004\",\"15:31:11\",\"ask\"]]}]";
        updateRecordDetails(details);
//        System.out.println(getSymbol("ok_sub_spot_eth_usdt_deals"));
        System.out.println(JsonMapper.nonDefaultMapper().toJson(tradeRecords));
    }

    public static class OrderRecordDetail {

        OrderRecordDetail(String[] detail) {
            this.tradeSeriaId = detail[0];
            this.tradePrice = Double.valueOf(detail[1]);
            this.tradeAmount = Double.valueOf(detail[2]);
            this.tradeTime = detail[3];
            this.tradeType = detail[4];
        }

        OrderRecordDetail(String tradeSeriaId, String tradePrice, String tradeAmount, String tradeTime, String tradeType) {
            this.tradeAmount = Double.valueOf(tradeAmount);
            this.tradePrice = Double.valueOf(tradePrice);
            this.tradeSeriaId = tradeSeriaId;
            this.tradeTime = tradeTime;
            this.tradeType = tradeType;
        }

        /**
         * 交易序号
         */
        private String tradeSeriaId;
        /**
         * 成交价
         */
        private Double tradePrice;
        /**
         * 成交量
         */
        private Double tradeAmount;
        /**
         * 交易时间
         */
        String tradeTime;
        /**
         * 买卖类型
         */
        String tradeType;

        public String getTradeSeriaId() {
            return tradeSeriaId;
        }

        public void setTradeSeriaId(String tradeSeriaId) {
            this.tradeSeriaId = tradeSeriaId;
        }

        public Double getTradePrice() {
            return tradePrice;
        }

        public void setTradePrice(Double tradePrice) {
            this.tradePrice = tradePrice;
        }

        public Double getTradeAmount() {
            return tradeAmount;
        }

        public void setTradeAmount(Double tradeAmount) {
            this.tradeAmount = tradeAmount;
        }

        public String getTradeTime() {
            return tradeTime;
        }

        public void setTradeTime(String tradeTime) {
            this.tradeTime = tradeTime;
        }

        public String getTradeType() {
            return tradeType;
        }

        public void setTradeType(String tradeType) {
            this.tradeType = tradeType;
        }
    }
}
