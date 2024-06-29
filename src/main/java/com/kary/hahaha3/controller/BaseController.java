package com.kary.hahaha3.controller;

import com.kary.hahaha3.pojo.JsonResult;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * @author:123
 */
public class BaseController {
    @ExceptionHandler(Exception.class)
    public JsonResult emptyInput(Throwable e, HttpServletResponse response){
        response.setStatus(400);
        String eMessage=e.getMessage();
        StringBuffer message=new StringBuffer((eMessage!=null)?eMessage:"");
        if(message.isEmpty()){
            for (StackTraceElement stackTraceElement : e.getStackTrace()) {
                message.append(stackTraceElement+System.lineSeparator());
            }
        }
        e.printStackTrace();
        return JsonResult.error(e.getClass().getSimpleName(),message.toString());
    }
}
