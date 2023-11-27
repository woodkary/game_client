package com.kary.hahaha3.controller;

import com.kary.hahaha3.pojo.JsonResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author:123
 */
@RestController
public class ErrorController {
    @ExceptionHandler(Exception.class)
    public JsonResult emptyInput(Throwable e){
        return JsonResult.error(e.getMessage());
    }
}
