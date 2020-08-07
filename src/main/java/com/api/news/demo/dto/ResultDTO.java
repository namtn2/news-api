package com.api.news.demo.dto;

import com.api.news.demo.utils.Constants;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class ResultDTO {
    private String key;
    private String message;
    private String id;
    private List lst;
    private Object object;

    public ResultDTO() {
        this.key = Constants.RESULT.FAIL;
        this.message = Constants.RESULT.FAIL;
    }
}
