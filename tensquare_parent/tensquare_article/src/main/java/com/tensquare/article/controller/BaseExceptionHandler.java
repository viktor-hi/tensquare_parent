package com.tensquare.article.controller;

import com.tensquare.entity.Result;
import com.tensquare.entity.StatusCode;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author haixin
 * @time 2020/2/5
 */
@ControllerAdvice
@ResponseBody
public class BaseExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    public Result handleException(Exception e){
        e.printStackTrace();
        return new Result(false, StatusCode.ERROR, e.getMessage());
    }
}
