package com.api.news.demo.service;

import com.api.news.demo.dto.ResultDTO;
import com.api.news.demo.model.LogModel;
import com.api.news.demo.model.LogType;
import com.api.news.demo.repository.MongoExtendRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LogService {

    @Autowired
    MongoExtendRepository mongoExtendRepository;

    public void save(ResultDTO resultDTO, Long userId, LogType logType) {
        try {
            LogModel logModel = new LogModel();
            logModel.setContent(resultDTO);
            logModel.setCreateTime(System.currentTimeMillis());
            logModel.setUserId(userId);
            logModel.setLogType(logType);
            mongoExtendRepository.save(logModel);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
