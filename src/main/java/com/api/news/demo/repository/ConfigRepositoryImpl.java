package com.api.news.demo.repository;

import com.api.news.demo.model.Config;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@Log4j
public class ConfigRepositoryImpl implements ConfigRepository {

    @Autowired
    EntityManager entityManager;

    @Override
    public List<Config> findConfigByGroup(String group) {
        return entityManager.createQuery("select t from Config t where UPPER(t.group) = UPPER(:p_group)", Config.class).setParameter("p_group", group).getResultList();
    }
}
