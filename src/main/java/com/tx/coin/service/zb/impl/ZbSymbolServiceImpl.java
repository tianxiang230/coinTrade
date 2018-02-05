package com.tx.coin.service.zb.impl;

import com.tx.coin.context.SystemConstant;
import com.tx.coin.service.ISymbolService;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

/**
 * @author 你慧快乐
 * @version V1.0
 * @Package com.tx.coin.service.zb.impl
 * @Description 获取ZB可选币种
 * @date 2018-2-5 16:50
 */
@Service
public class ZbSymbolServiceImpl implements ISymbolService {

    private Set<String> baseSymbol;
    private Set<String> askSymbol;
    private Map<String, Set<String>> symbols;

    @PostConstruct
    public void init() {
        baseSymbol = new TreeSet<>();
        askSymbol = new TreeSet<>();
        symbols = new HashMap<>(5);
        String selectedSym = "btc_qc,btc_usdt,bcc_usdt,ubtc_usdt,ltc_usdt,eth_usdt,etc_usdt,bts_usdt,eos_usdt,qtum_usdt,hsr_usdt,x" +
                "rp_usdt,bcd_usdt,dash_usdt,bcc_qc,ubtc_qc,ltc_qc,eth_qc,etc_qc,bts_qc,eos_qc,qtum_qc,hsr_qc,xrp_qc,b" +
                "cd_qc,dash_qc,bcc_btc,ubtc_btc,ltc_btc,eth_btc,etc_btc,bts_btc,eos_btc,qtum_btc,hsr_btc,xrp_btc,bcd_" +
                "btc,dash_btc,sbtc_usdt,sbtc_qc,sbtc_btc,ink_usdt,ink_qc,ink_btc,tv_usdt,tv_qc,tv_btc,bcx_usdt,bcx_qc" +
                ",bcx_btc,bth_usdt,bth_qc,bth_btc,lbtc_usdt,lbtc_qc,lbtc_btc,chat_usdt,chat_qc,chat_btc,hlc_usdt,hlc_" +
                "qc,hlc_btc,bcw_usdt,bcw_qc,bcw_btc,btp_usdt,btp_qc,btp_btc,bitcny_qc,topc_usdt,topc_qc,topc_btc,ent_" +
                "usdt,ent_qc,ent_btc,bat_usdt,bat_qc,bat_btc,1st_usdt,1st_qc,1st_btc,safe_usdt,safe_qc,safe_btc,qun_u" +
                "sdt,qun_qc,qun_btc,btn_usdt,btn_qc,btn_btc,true_usdt,true_qc,true_btc,cdc_usdt,cdc_qc,cdc_btc,ddm_us" +
                "dt,ddm_qc,ddm_btc,bite_btc,hotc_usdt,hotc_qc,hotc_btc,qc_usdt,usdt_qc,xuc_qc,xuc_btc,epc_qc,epc_btc";
        String[] symPairs = selectedSym.split(",");
        for (String symbolPair : symPairs) {
            String[] pair = symbolPair.split("_");
            baseSymbol.add(pair[0]);
            askSymbol.add(pair[1]);
        }
        symbols.put(SystemConstant.SYMBOL_BASE, baseSymbol);
        symbols.put(SystemConstant.SYMBOL_ASK, askSymbol);
    }

    @Override
    public Map<String, Set<String>> getSymbolPairs() {

        return symbols;
    }
}
