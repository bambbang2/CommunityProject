package com.issuemarket.configs;

import com.issuemarket.exception.LoginFailureHandler;
import com.issuemarket.exception.LoginSuccessHandler;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.formLogin()
                .loginPage("/member/login")
                .usernameParameter("userId")
                .passwordParameter("userPw")
                .successHandler(new LoginSuccessHandler())
                .failureHandler(new LoginFailureHandler())
                .and()
                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/member/logout"))
                .logoutSuccessUrl("/member/login");

        /** 회원가입 구현 후 주석 해제 예정 */
        http.authorizeHttpRequests()
                .requestMatchers("/", "/member/**", "/error/**").permitAll()
                .requestMatchers("/member/mypage/**").hasAuthority("USER")
//                .requestMatchers("/admin/**").hasAuthority("ADMIN")
                .anyRequest().authenticated();

        /**
         *  관리자 페이지에 권한없는 요청 URL 접속시 401 코드 및 오류 페이지 이동
         *  그 외에는 로그인 페이지로 이동
         */
        http.exceptionHandling()
                .authenticationEntryPoint((req, res, e) -> {
                    String URI = req.getRequestURI();

                    if (URI.indexOf("/admin") != -1) { // 관리자 페이지
                        res.sendError(HttpServletResponse.SC_UNAUTHORIZED, "NOT AUTHORIZED");
                    } else { // 회원 전용 페이지
                        String redirectURL = req.getContextPath() + "/member/login";
                        res.sendRedirect(redirectURL);
                    }
                });

        http.headers().frameOptions().sameOrigin();

        return http.build();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return w -> w.ignoring() // 시큐리티가 무시할 정적 경로
                .requestMatchers("/images/**", "/js/**", "/css/**", "/uploads/**", "/error/**");
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}