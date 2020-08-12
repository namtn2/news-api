package com.api.news.demo.repository;

import com.api.news.demo.model.LogModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface MongoExtendRepository extends MongoRepository<LogModel, String> {

}
