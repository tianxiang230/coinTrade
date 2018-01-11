package com.tx.coin.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * 用户信息返回实体，部分
 * Created by 你慧快乐 on 2018-1-11.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserInfoDTO {
    private String bcd;
    private String bcc;
    private String bch;
    private String btc;
    private String ace;
    private String act;
    private String etc;
    private String eth;
    private String usdt;
    private String sbtc;

    public String getBcd() {
        return bcd;
    }

    public void setBcd(String bcd) {
        this.bcd = bcd;
    }

    public String getBcc() {
        return bcc;
    }

    public void setBcc(String bcc) {
        this.bcc = bcc;
    }

    public String getBch() {
        return bch;
    }

    public void setBch(String bch) {
        this.bch = bch;
    }

    public String getBtc() {
        return btc;
    }

    public void setBtc(String btc) {
        this.btc = btc;
    }

    public String getAce() {
        return ace;
    }

    public void setAce(String ace) {
        this.ace = ace;
    }

    public String getAct() {
        return act;
    }

    public void setAct(String act) {
        this.act = act;
    }

    public String getEtc() {
        return etc;
    }

    public void setEtc(String etc) {
        this.etc = etc;
    }

    public String getEth() {
        return eth;
    }

    public void setEth(String eth) {
        this.eth = eth;
    }

    public String getUsdt() {
        return usdt;
    }

    public void setUsdt(String usdt) {
        this.usdt = usdt;
    }

    public String getSbtc() {
        return sbtc;
    }

    public void setSbtc(String sbtc) {
        this.sbtc = sbtc;
    }
}
