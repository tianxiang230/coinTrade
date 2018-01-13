package com.tx.coin.ws.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * @author 你慧快乐
 * @version V1.0
 * @Package com.tx.coin.ws.dto
 * @Description
 * @date 2018-1-13 15:44
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ChannelStateDTO {
    private Integer binary;
    private String channel;
    private StateData data;

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

    public StateData getData() {
        return data;
    }

    public void setData(StateData data) {
        this.data = data;
    }

    public class StateData{
        private boolean result;

        private String channel;

        public boolean getResult() {
            return result;
        }

        public void setResult(boolean result) {
            this.result = result;
        }

        public String getChannel() {
            return channel;
        }

        public void setChannel(String channel) {
            this.channel = channel;
        }
    }
}
