package com.api.news.demo.service;

import com.api.news.demo.dto.ResultDTO;
import com.api.news.demo.model.LogModel;
import com.api.news.demo.model.LogType;
import com.api.news.demo.model.User;
import com.api.news.demo.repository.MongoExtendRepository;
import com.api.news.demo.repository.UserRepository;
import com.api.news.demo.utils.Constants;
import com.api.news.demo.utils.Utils;
import java.util.ArrayList;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

@Service
@Slf4j
public class LogService {

    @Autowired
    MongoExtendRepository mongoExtendRepository;

    @Autowired
    MongoTemplate mongoTemplate;

    @Autowired
    UserRepository userRepository;

    @Value("${log-manage.order.field:null}")
    private String orderField;

    public void save(Object object, LogType logType, ResultDTO res) {
        try {
            LogModel logModel = new LogModel();
            logModel.setContent(object);
            logModel.setCreateTime(System.currentTimeMillis());
            logModel.setLogType(logType);
            logModel.setStatus(res.getKey());
            logModel.setMessage(res.getMessage());

            //ghi log
            UserDetails u = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            ResultDTO resultUser = userRepository.searchUser(new User(u.getUsername()));
            Long id = 0l;
            if (resultUser != null && resultUser.getObject() != null && Constants.RESULT.SUCCESS.equals(resultUser.getKey())) {
                User us = (User) resultUser.getObject();
                id = us.getId();
            }
            logModel.setUserId(id);

            mongoExtendRepository.save(logModel);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void save(Object object, Long userId, LogType logType, ResultDTO res) {
        try {
            LogModel logModel = new LogModel();
            logModel.setContent(object);
            logModel.setCreateTime(System.currentTimeMillis());
            logModel.setLogType(logType);
            logModel.setStatus(res.getKey());
            logModel.setMessage(res.getMessage());
            logModel.setUserId(userId);

            mongoExtendRepository.save(logModel);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Page<LogModel> search(LogModel logModel, int rowStart, int maxRow) {
        Page<LogModel> lstPaging = null;
        try {

            Query query = prepareQuery(logModel, new Query());
            long count = count(query);
            List lst = searchLog(rowStart, maxRow, query);

            Map<Long, List<LogModel>> collect = (Map<Long, List<LogModel>>) lst.stream().collect(Collectors.groupingBy(LogModel::getUserId, Collectors.toList()));
            ResultDTO res = userRepository.findUserById(new ArrayList<>(collect.keySet()));
            if (res != null && !res.getLst().isEmpty()) {
                List<User> lstUser = (List<User>) res.getLst();
                for (User u : lstUser) {
                    List<LogModel> lstValues = collect.get(u.getId());
                    if (lstValues != null && !lstValues.isEmpty()) {
                        lstValues.forEach(o -> {
                            o.setEmail(u.getEmail());
                            o.setName(u.getName());
                        });
                    }
                }
            }
            Pageable pageable = preparePageable(rowStart, maxRow);
            lstPaging = new PageImpl<>(lst, pageable, count);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return lstPaging;
    }

    public Query prepareQuery(LogModel logModel, Query query) {
        query = new Query();
        Criteria c = new Criteria();

        if (!Utils.isStringNullOrEmpty(logModel.getEmail())) {
            User u = new User();
            u.setEmail(logModel.getEmail());
            List<User> lstUser = userRepository.searchUserByNameAndEmail(u);
            List<Long> lstUserId = lstUser.stream().map(User::getId).collect(Collectors.toList());
            query.addCriteria(c.and("userId").in(lstUserId));
        }
        if (!Utils.isStringNullOrEmpty(logModel.getStatus())) {
            query.addCriteria(c.and("status").is(logModel.getStatus()));
        }
        if (logModel.getLogType() != null) {
            query.addCriteria(c.and("logType").is(logModel.getLogType()));
        }
        if (!Utils.isStringNullOrEmpty(logModel.getMessage())) {
            query.addCriteria(c.and("message").regex(".*" + logModel.getMessage() + ".*", "i"));
        }
        return query;
    }

    private long count(Query query) {
        return mongoTemplate.count(query, LogModel.class);
    }

    public List searchLog(int rowStart, int maxRow, Query query) {
        Pageable pageable = preparePageable(rowStart, maxRow);
        query.with(pageable);
        List lst = mongoTemplate.find(query, LogModel.class);

        return lst;
    }

    private Pageable preparePageable(int rowStart, int maxRow) {
        Sort sort = Sort.by(orderField).descending();
        Pageable pageable = PageRequest.of(rowStart, maxRow, sort);
        return pageable;
    }
}
