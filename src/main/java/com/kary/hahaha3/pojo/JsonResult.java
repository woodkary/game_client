package com.kary.hahaha3.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author:123
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(value ={"hibernateLazyInitializer","handler","fieldHandler"})
@EqualsAndHashCode
public class JsonResult implements Serializable {
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
        return ok("", message);
    }
    public static JsonResult error(Object data,String message){
        return new JsonResult(400,data,message);
    }
    public static JsonResult error(String message){
        return error("",message);
    }
}
