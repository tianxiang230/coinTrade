package com.tx.coin.interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author 你慧快乐
 * @version V1.0
 * @Package com.tx.coin.interceptor
 * @Description
 * @date 2018-2-2 15:50
 */
public class SessionInterceptor implements HandlerInterceptor {
    private final String SessionName = "isLogin";
    Logger logger = LoggerFactory.getLogger(SessionInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        String requestUri = httpServletRequest.getRequestURI();
        logger.info("拦截到请求url:{}", requestUri);
        if (requestUri.startsWith("/login") || requestUri.startsWith("/logout") || requestUri.startsWith("error") || requestUri.startsWith("/webjars")) {
            return true;
        } else {
            Object session = httpServletRequest.getSession().getAttribute(SessionName);
            if (session == null) {
                httpServletResponse.sendRedirect("login.html");
                return false;
            }
            return true;
        }
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
        logger.info("拦截请求处理中");
    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
        logger.info("拦截处理完毕");
    }
}
