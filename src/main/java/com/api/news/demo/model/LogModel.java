package com.api.news.demo.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Id;

@Document(collection = "log")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LogModel {

    @Id
    private String id;
    
    private Object content;
    private long createTime;
    private long idRecord;
    private LogType logType;

}
