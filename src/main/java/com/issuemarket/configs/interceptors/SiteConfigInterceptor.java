package com.issuemarket.configs.interceptors;

import com.fasterxml.jackson.core.type.TypeReference;
import com.issuemarket.commons.configs.ConfigInfoService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.HashMap;
import java.util.Map;

/**
 * 사이트 설정 유지
 *
 */
@Component("siteConf")
@RequiredArgsConstructor
public class SiteConfigInterceptor implements HandlerInterceptor {

    private final ConfigInfoService infoService;
    private final HttpServletRequest request;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        
        /** 사이트 설정 조회 */
        Map<String, String> siteConfigs = infoService.get("siteConfig",
                new TypeReference<Map<String, String>>(){});

        if (siteConfigs == null) {
            siteConfigs = new HashMap<>();
            siteConfigs.put("siteTitle", "");
            siteConfigs.put("siteDescription", "");
            siteConfigs.put("cssJsVersion", "" + 1);
            siteConfigs.put("joinTerms", "");

        }

        request.setAttribute("siteConfig", siteConfigs);

        return true;
    }

    public String get(String name)  {
        Map<String, String> siteConfig = (Map<String, String>) request.getAttribute("siteConfig");
        String value = siteConfig == null ? "" : siteConfig.get(name);

        return value;

    }
}

