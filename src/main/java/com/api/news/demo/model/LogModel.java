package com.api.news.demo.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;
import javax.persistence.Transient;
import org.springframework.data.annotation.PersistenceConstructor;

@Document(collection = "log")
@Getter
@Setter
//@AllArgsConstructor
//@NoArgsConstructor
public class LogModel {

    @Id
    private String id;
    
    private Object content;
    private long createTime;
    private long userId;
    private LogType logType;
    private String status;
    private String message;

    @Transient
    private String email;
    @Transient
    private String name;

    @PersistenceConstructor
    public LogModel(String id, Object content, long createTime, long userId, LogType logType, String status, String message) {
        this.id = id;
        this.content = content;
        this.createTime = createTime;
        this.userId = userId;
        this.logType = logType;
        this.status = status;
        this.message = message;
    }

    @PersistenceConstructor
    public LogModel() {
    }
}
