package com.kary.hahaha3.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
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
    @Schema(name = "code",description = "状态码",example = "400")
    private int code;
    @Schema(name = "data",description = "数据",example = "\"\"")
    private Object data;
    @Schema(name = "message",description = "消息",example = "错误，请重试")
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
