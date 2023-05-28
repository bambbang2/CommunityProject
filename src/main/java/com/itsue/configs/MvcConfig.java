package com.itsue.configs;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.web.filter.HiddenHttpMethodFilter;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableJpaAuditing
public class MvcConfig implements WebMvcConfigurer {

    @Value("${file.upload.path}")
    private String fileUploadPath;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        // 파일 업로드 경로 설정
        registry.addResourceHandler("/uploads/**")
                .addResourceLocations("file:///" + fileUploadPath);
    }

    /**
     * 메세지 설정
     *  - messages.commons : 공통 메세지
     *  - messages.errors : 에러 관련 메세지
     *  - messages.validations : 유효성 검사 관련 메세지
     */

    @Bean
    public MessageSource messageSource() {
        ResourceBundleMessageSource ms = new ResourceBundleMessageSource();
        ms.setDefaultEncoding("UTF-8");
        ms.setBasenames("messages.commons", "messages.errors", "messages.validations");
        return ms;
    }

    /**
     * <메인 페이지> 컨트롤러
     * @param registry
     */
    @Override
    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/")
                .setViewName("front/main/index");
    }
    @Bean
    public HiddenHttpMethodFilter hiddenHttpMethodFilter() { // GET, POST 외에 DELETE, PATCH, PUT... 사용 가능

        return new HiddenHttpMethodFilter();
    }
}
