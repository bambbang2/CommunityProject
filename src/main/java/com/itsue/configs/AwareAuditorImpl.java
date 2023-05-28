package com.itsue.configs;

import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

import java.util.Optional;

/** 나중에 Member 엔티티, info 구현 후 수정 필요!!!!! */
@Component
public class AwareAuditorImpl implements AuditorAware<String> {

    @Override
    public Optional<String> getCurrentAuditor() {
        return Optional.empty();
    }
}