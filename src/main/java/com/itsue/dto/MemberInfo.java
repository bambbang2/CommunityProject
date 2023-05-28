package com.itsue.dto;

import com.itsue.commons.constants.Role;
import lombok.Builder;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@Data
@Builder
public class MemberInfo implements UserDetails {


    /**
     * 유저 entity 기입
     */

    private Long userNo;
    private String userId;
    private String userPw;


    private Role roles;

    private Collection<GrantedAuthority> authorities;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return userPw; // 로그인에 사용 될 비밀번호
    }

    @Override
    public String getUsername() {
        return userId; // 로그인에 사용 될 아이디
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}