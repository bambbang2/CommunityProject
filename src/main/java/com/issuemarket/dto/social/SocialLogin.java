package com.issuemarket.dto.social;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.issuemarket.configs.SocialConfig;
import com.issuemarket.entities.Member;
import com.issuemarket.repositories.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

@Component
@RequiredArgsConstructor
public class SocialLogin {

    private final MemberRepository repository;

    public boolean exists(String channel, Long id) {
        return repository.exists(channel, String.valueOf(id));
    }

    public void login(String channel, Long id) {
        Member member = repository.findBySocialChannelAndSocialId(channel, String.valueOf(id));
    }

    public String getAuthUrl() {
        String url = String.format("https://kauth.kakao.com/oauth/authorize?client_id=%s&redirect_uri=%s&response_type=code",
                SocialConfig.restApiKey, SocialConfig.restApiCallback);

        return url;
    }

    public String getAccessToken(String code) {
        /**
         * https://kauth.kakao.com/oauth/token
         * grant_type=authorization_code
         * client_id=
         * redirect_uri=
         * code=
         */
        String accessToken = null;

        HttpURLConnection conn = null;
        try {
            URL url = new URL("https://kauth.kakao.com/oauth/token");

            conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setDoInput(true);

            /** 요청 S */
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");

            String params = String.format("grant_type=authorization_code&client_id=%s&redirect_uri=%s&code=%s", SocialConfig.restApiKey, SocialConfig.restApiCallback, code);

            try (OutputStream out = conn.getOutputStream()) {
                // Body 출력
                out.write(params.getBytes());
            }
            /** 요청 E */
            /** 응답 S */
            StringBuffer sb = new StringBuffer(7000);
            try(InputStream in = conn.getInputStream();
                InputStreamReader isr = new InputStreamReader(in, "UTF-8");
                BufferedReader br = new BufferedReader(isr)) {
                String line = null;
                while((line = br.readLine()) != null) {
                    sb.append(line);
                }
            }

            String result = sb.toString();
            ObjectMapper om = new ObjectMapper();
            TokenInfo tokenInfo = om.readValue(result, TokenInfo.class);
            accessToken = tokenInfo.getAccess_token();
            /** 응답 E */


        } catch (Exception e) {
            e.printStackTrace();
            if (conn != null) { // 응답 코드가 200대가 아닌 경우
                StringBuffer sb = new StringBuffer(7000);
                try (InputStream in = conn.getErrorStream();
                     InputStreamReader isr = new InputStreamReader(in, "UTF-8");
                     BufferedReader br = new BufferedReader(isr)) {

                    String line = null;
                    while((line = br.readLine()) != null) {
                        sb.append(line);
                    }

                    System.out.println(sb.toString());
                } catch (IOException e2) {
                    e2.printStackTrace();
                }
            }

        }

        return accessToken;
    }

    public ProfileResult getProfile(String code) {
        String accessToken = getAccessToken(code);
        if (accessToken == null || accessToken.isBlank()) {
            throw new RuntimeException("잘못된 요청 접근 입니다.");
        }

        /**
         * https://kapi.kakao.com/v2/user/me
         * Authorization	Authorization: Bearer ${ACCESS_TOKEN}
         */
        HttpURLConnection conn = null;
        try {
            URL url = new URL("https://kapi.kakao.com/v2/user/me");
            conn = (HttpURLConnection) url.openConnection();
            conn.setDoInput(true);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");
            conn.setRequestProperty("Authorization", "Bearer " + accessToken);

            StringBuffer sb = new StringBuffer(7000);
            try (InputStream in = conn.getInputStream();
                 InputStreamReader isr = new InputStreamReader(in, "UTF-8");
                 BufferedReader br = new BufferedReader(isr)) {

                String line = null;
                while((line = br.readLine()) != null) {
                    sb.append(line);
                }

                String result = sb.toString();
                ObjectMapper om = new ObjectMapper();
                om.registerModule(new JavaTimeModule());
                ProfileResult profileResult = om.readValue(result, ProfileResult.class);

                return profileResult;

            }
        } catch (Exception e) {
            e.printStackTrace();
            if (conn != null) {
                StringBuffer sb = new StringBuffer(7000);
                try (InputStream in = conn.getErrorStream();
                     InputStreamReader isr = new InputStreamReader(in, "UTF-8");
                     BufferedReader br = new BufferedReader(isr)) {
                    String line = null;
                    while((line = br.readLine()) != null) {
                        sb.append(line);
                    }

                    System.out.println(sb.toString());
                } catch (IOException e2) {
                    e2.printStackTrace();
                }
            }
        }

        return null;
    }
}