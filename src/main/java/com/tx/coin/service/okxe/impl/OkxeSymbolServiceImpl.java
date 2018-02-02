package com.tx.coin.service.okxe.impl;

import com.tx.coin.context.SystemConstant;
import com.tx.coin.service.ISymbolService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.*;

/**
 * @author 你慧快乐
 * @version V1.0
 * @Package com.tx.coin.service.okxe.impl
 * @Description okxe的货币对，静态配置
 * @date 2018-2-2 22:04
 */
@Service
public class OkxeSymbolServiceImpl implements ISymbolService {
    private Set<String> baseSymbol;
    private Set<String> askSymbol;
    private Map<String, Set<String>> symbols;
    @Value("${config.okxe.baseSymbol}")
    private String baseSym;
    @Value("${config.okxe.askSymbol}")
    private String askSym;

    @PostConstruct
    public void init() {
        baseSymbol = new TreeSet<>();
        askSymbol = new TreeSet<>();
        symbols = new HashMap<>(5);
        baseSymbol.addAll(Arrays.asList(baseSym.split(",")));
        askSymbol.addAll(Arrays.asList(askSym.split(",")));
        symbols.put(SystemConstant.SYMBOL_BASE, baseSymbol);
        symbols.put(SystemConstant.SYMBOL_ASK, askSymbol);
    }

    @Override
    public Map<String, Set<String>> getSymbolPairs() {
        return symbols;
    }
}
