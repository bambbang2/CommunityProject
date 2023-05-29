package com.itsue.commons.configs;

import com.itsue.entities.Configs;
import com.itsue.repositories.ConfigsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ConfigDeleteService {

    private final ConfigsRepository repository;

    public void delete(String code){

        Configs configs = repository.findById(code).orElse(null);

        if (configs == null) {
            return;
        }

        repository.delete(configs);
        repository.flush();

    }

}
