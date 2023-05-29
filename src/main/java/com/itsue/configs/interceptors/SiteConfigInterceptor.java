package com.itsue.configs.interceptors;

import com.fasterxml.jackson.core.type.TypeReference;
import com.itsue.commons.configs.ConfigInfoService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Map;

/**
 * 사이트 설정 유지
 *
 */
@Component
@RequiredArgsConstructor
public class SiteConfigInterceptor implements HandlerInterceptor {

    private final ConfigInfoService infoService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        
        /** 사이트 설정 조회 */
        Map<String, String> siteConfigs = infoService.get("siteConfig",
                new TypeReference<Map<String, String>>(){});

        request.setAttribute("siteConfig", siteConfigs);

        return true;
    }
}
