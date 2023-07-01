package com.srtcrm.controller.utils;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

//@RestControllerAdvice

//开发过程暂时不打开!
public class ProjectExceptionAdvice {
    //@ExceptionHandler
    public R doException(Exception ex){
        ex.printStackTrace();
        return new R(false,null,"服务器错误");
    }
}
