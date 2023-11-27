package com.kary.hahaha3.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author:123
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class JsonResult {
    private int code;
    private Object data;
    private String message;
    public static JsonResult ok(Object data,String message){
        return new JsonResult(200,data,message);
    }
    public static JsonResult ok(Object data){
        return ok(data,"");
    }
    public static JsonResult ok(String message){
        return ok(new Object(),message);
    }
    public static JsonResult error(Object data,String message){
        return new JsonResult(400,data,message);
    }
    public static JsonResult error(Object data){
        return error(data,"错误，请重试");
    }
    public static JsonResult error(String message){
        return error(new Object(),message);
    }
}
