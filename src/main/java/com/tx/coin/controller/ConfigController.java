package com.tx.coin.controller;

import com.tx.coin.entity.PlatFormConfig;
import com.tx.coin.enums.PlatType;
import com.tx.coin.repository.PlatFormConfigRepository;
import com.tx.coin.service.ISymbolService;
import com.tx.coin.utils.DateUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Map;
import java.util.Set;

/**
 * @author 你慧快乐
 * @version V1.0
 * @Package com.tx.coin.controller
 * @Description 系统配置
 * @date 2018-1-16 16:36
 */
@Controller
public class ConfigController {
    private final String SessionName = "isLogin";
    @Autowired
    private PlatFormConfigRepository configRepository;
    @Autowired
    @Qualifier(value = "binSymbolServiceImpl")
    private ISymbolService binSymbolServiceImpl;
    @Autowired
    @Qualifier(value = "zbSymbolServiceImpl")
    private ISymbolService zbSystemServiceImpl;
    @Autowired
    @Qualifier(value = "okxeSymbolServiceImpl")
    private ISymbolService okxeSymbolServiceImpl;

    @RequestMapping(value = "/login.html", method = RequestMethod.GET)
    public String login(String username, String password, ModelMap model, HttpServletRequest request) {
        if (StringUtils.isBlank(username) || StringUtils.isBlank(password)) {
            return "login";
        }
        if (username.equals("adminUser") && password.equals("adminUser")) {
            request.getSession().setAttribute(SessionName, new Date());
            return "redirect:plat.html";
        } else {
            model.addAttribute("errorInfo", "用户名或密码错误");
            return "login";
        }
    }

    @RequestMapping(value = "/config.html", method = RequestMethod.GET)
    public String config(ModelMap model, String plat) {
        Map<String, Set<String>> symbols = null;
        if (plat != null && plat.equals(PlatType.BIN.getCode())) {
            symbols = binSymbolServiceImpl.getSymbolPairs();
        } else if (plat != null && plat.equals(PlatType.OKXE.getCode())) {
            symbols = okxeSymbolServiceImpl.getSymbolPairs();
        } else if (plat != null && plat.equals(PlatType.ZB.getCode())) {
            symbols = zbSystemServiceImpl.getSymbolPairs();
        }
        model.addAttribute("symbols", symbols);
        if (StringUtils.isNotBlank(plat)) {
            PlatType platType = PlatType.getType(plat);
            model.addAttribute("platName", platType.getName());
            PlatFormConfig config = configRepository.selectByPlat(plat);
            if (config == null) {
                config = new PlatFormConfig();
            }
            model.addAttribute("config", config);
        }
        return "config";
    }

    @ResponseBody
    @RequestMapping(value = "/modifyConfig", method = RequestMethod.POST)
    public Boolean saveConfig(PlatFormConfig config) {
        config.setCreateDate(DateUtil.getFormatDateTime(new Date()));
        config = configRepository.save(config);
        return Boolean.TRUE;
    }

    @RequestMapping(value = "/plat.html")
    public String selectPlat(ModelMap model) {
        model.addAttribute("plats", PlatType.values());
        return "plat";
    }

    @RequestMapping(value = "/logout")
    public String logout(HttpServletRequest request){
        request.getSession().setAttribute(SessionName, null);
        return "redirect:login.html";
    }

    @RequestMapping(value = "/",method = RequestMethod.GET)
    public String defaultPage(){
        return "redirect:config.html";
    }
}
