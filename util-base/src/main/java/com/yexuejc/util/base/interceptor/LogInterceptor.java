package com.yexuejc.util.base.interceptor;

import com.yexuejc.util.base.constant.RespsConstant;
import com.yexuejc.util.base.util.LogUtil;
import com.yexuejc.util.base.util.NetUtil;
import org.springframework.http.HttpHeaders;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 访问日志拦截器，用于记录用户访问日志
 *
 * @author PHY
 */
public class LogInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        // 写日志
        String uri = request.getRequestURI();
        String userAgent = LogUtil.format(request.getHeader(HttpHeaders.USER_AGENT));
        String xuserAgent = request.getHeader(RespsConstant.HEADER_X_USER_AGENT);
        String ip = NetUtil.getIp(request);

        LogUtil.accessLogger.info("{};{};{};{}", uri, userAgent, xuserAgent, ip);
        return true;
    }

}
