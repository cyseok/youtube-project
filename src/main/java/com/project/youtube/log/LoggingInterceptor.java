package com.project.youtube.log;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;

@Slf4j
public class LoggingInterceptor implements HandlerInterceptor {

    // private static final Logger logger = LoggerFactory.getLogger(LoggingInterceptor.class);

    @Override
    public boolean preHandle(jakarta.servlet.http.HttpServletRequest request, jakarta.servlet.http.HttpServletResponse response, Object handler) throws Exception {

        String ipAddress = request.getHeader("X-Forwarded-For");
        //String ipAddress = request.getRemoteAddr();
        if(ipAddress == null || ipAddress.length() == 0 || ipAddress == "" ) {
            ipAddress = request.getRemoteAddr();
        }

        String url = request.getRequestURL().toString();
        String queryString = request.getQueryString();
        if (queryString != null) {
            url += "?" + queryString;
        }
        String method = request.getMethod();
        String userAgent = request.getHeader("User-Agent");

        log.info("User IP: {}, Method: {}, URL: {}, User-Agent: {}",
                ipAddress, method, url, userAgent);

        return HandlerInterceptor.super.preHandle(request, response, handler);
    }

}