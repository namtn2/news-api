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

    public ResultDTO(String key, String message) {
        this.key = key;
        this.message = message;
    }

    public ResultDTO setResultDtoSuccess() {
        return new ResultDTO(Constants.RESULT.SUCCESS, Constants.RESULT.SUCCESS);
    }
}
