package com.issuemarket.configs.interceptors;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * 사이트 설정 유지
 *
 */
@Component
@RequiredArgsConstructor
public class SiteConfigInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        
        /** 사이트 설정 조회 */

        request.setAttribute("cssJsVersion", 1);

        // error


        return true;
    }
}
