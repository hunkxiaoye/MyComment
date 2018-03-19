package com.comment.Interceptor;

import com.comment.common.CookieUtils;
import com.comment.common.ToolsUtils;
import com.comment.service.inf.IUserService;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginInterceptor implements HandlerInterceptor {

    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
                           Object o, ModelAndView modelAndView) throws Exception {
    }

    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
                                Object o, Exception e) throws Exception {
    }

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object o) throws Exception {
        //获取url地址
        String url = request.getServletPath();
        if (url.endsWith("login") || url.endsWith("loginVerify")) {
            return true;
        }
        if (!CookieUtils.isLogin(request)) {
            String returnurl = request.getContextPath() + request.getServletPath() + (request.getQueryString() == null ? "" : "?" + request.getQueryString());
            response.sendRedirect(request.getContextPath() + "/login?returnurl=" + ToolsUtils.urlEncode(returnurl));

            return false;
        } else {
            CookieUtils.refreshLogin(request, response, 1800);
            return true;
        }
    }
}
