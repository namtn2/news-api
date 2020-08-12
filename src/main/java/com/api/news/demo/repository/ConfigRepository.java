package com.api.news.demo.repository;

import com.api.news.demo.model.Config;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConfigRepository {
    List<Config> findConfigByGroup(String group);
}
