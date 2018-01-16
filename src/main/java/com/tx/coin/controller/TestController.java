package com.tx.coin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * @author 你慧快乐
 * @version V1.0
 * @Package com.tx.coin.controller
 * @Description
 * @date 2018-1-14 20:58
 */
@Controller
public class TestController {

    @RequestMapping("/test.html")
    public String test(ModelMap model, HttpServletRequest request) {
        model.addAttribute("var", "老郭");
        return "test";
    }
}
