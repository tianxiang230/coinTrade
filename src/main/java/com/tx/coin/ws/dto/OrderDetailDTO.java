package com.tx.coin.ws.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * @author 你慧快乐
 * @version V1.0
 * @Package com.tx.coin.ws.dto
 * @Description
 * @date 2018-1-13 15:47
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class OrderDetailDTO {
    private Integer binary;
    private String channel;
    private String[][] data;

    public Integer getBinary() {
        return binary;
    }

    public void setBinary(Integer binary) {
        this.binary = binary;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String[][] getData() {
        return data;
    }

    public void setData(String[][] data) {
        this.data = data;
    }
}
