package com.api.news.demo.service;

import com.api.news.demo.model.Config;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ConfigService {

    List<Config> findConfigByGroup(String group);
}
