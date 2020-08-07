package com.api.news.demo.repository;

import com.api.news.demo.model.LogModel;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface MongoExtendRepository extends MongoRepository<LogModel, String> {

}
