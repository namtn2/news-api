package com.api.news.demo.service;

import com.api.news.demo.model.Config;
import com.api.news.demo.repository.ConfigRepository;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
@Service
@Log4j
public class ConfigServiceImpl implements ConfigService {

    @Autowired
    ConfigRepository configRepository;

    @Override
    public List<Config> findConfigByGroup(String group) {
        return configRepository.findConfigByGroup(group);
    }
}
