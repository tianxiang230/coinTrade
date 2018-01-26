package com.tx.coin.controller;

import com.tx.coin.config.OkxePropertyConfig;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * @author 你慧快乐
 * @version V1.0
 * @Package com.tx.coin.controller
 * @Description 系统配置
 * @date 2018-1-16 16:36
 */
@Controller
public class ConfigController {
    @Autowired
    private OkxePropertyConfig okxePropertyConfig;
    private final String SessionName = "isLogin";

    @RequestMapping(value = "/login.html", method = RequestMethod.GET)
    public String login(String username, String password, ModelMap model, HttpServletRequest request) {
        if (StringUtils.isBlank(username) || StringUtils.isBlank(password)) {
            return "login";
        }
        if (username.equals("adminUser") && password.equals("adminUser")) {
            request.getSession().setAttribute(SessionName, new Date());
            return "redirect:config.html";
        } else {
            model.addAttribute("errorInfo", "用户名或密码错误");
            return "login";
        }
    }

    @RequestMapping(value = "/config.html")
    public String config(ModelMap model, OkxePropertyConfig config, HttpServletRequest request) {
        Object isLogin = request.getSession().getAttribute(SessionName);
        if (isLogin == null) {
            return "redirect:login.html";
        }
        if (config.getTradeOrNot() != null) {
            okxePropertyConfig.setTradeOrNot(config.getTradeOrNot());
        }
        if (config.getD3() != null) {
            okxePropertyConfig.setD3(config.getD3());
        }
        if (config.getD1() != null) {
            okxePropertyConfig.setD1(config.getD1());
        }
        if (config.getU1() != null) {
            okxePropertyConfig.setU1(config.getU1());
        }
        if (config.getU2() != null) {
            okxePropertyConfig.setU2(config.getU2());
        }
        if (config.getB1() != null) {
            okxePropertyConfig.setB1(config.getB1());
        }
        if (config.getS1() != null) {
            okxePropertyConfig.setS1(config.getS1());
        }
        if (config.getY1() != null) {
            okxePropertyConfig.setY1(config.getY1());
        }
        if (config.getY2() != null) {
            okxePropertyConfig.setY2(config.getY2());
        }
        if (config.getY3() != null) {
            okxePropertyConfig.setY3(config.getY3());
        }
        if (config.getY4() != null) {
            okxePropertyConfig.setY4(config.getY4());
        }
        if (config.getY5() != null) {
            okxePropertyConfig.setY5(config.getY5());
        }
        model.addAttribute("config", okxePropertyConfig);
        return "config";
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
