package com.kary.hahaha3.controller.pagesAndIndexControllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.kary.hahaha3.exceptions.JsonException;
import com.kary.hahaha3.exceptions.expired.SessionExpireException;
import com.kary.hahaha3.pojo.JsonResult;
import com.kary.hahaha3.pojo.User;
import com.kary.hahaha3.service.RecordVOService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * @author:123
 */
//TODO css样式表太丑啦！求一个好看的样式。
@RestController
@Tag(name = "首页展示")
public class IndexController {
    @Autowired
    @Qualifier("RecordVOService")
    private RecordVOService recordVOService;
    @GetMapping({"/","/index"})
    @Operation(summary = "返回首页",description = "return to index")
    public JsonResult index0(HttpSession session) throws Exception {
        User myAccount= (User) session.getAttribute("myAccount");
        if(myAccount==null){
            throw new SessionExpireException("你尚未登录");
        }
        String recordVOJson;
        try {
            recordVOJson = new ObjectMapper().writeValueAsString(recordVOService.getGamesByIds(myAccount.getUsername(), 1,1));
        } catch (JsonProcessingException e) {
            throw new JsonException("返回比赛记录错误",e);
        }
        return JsonResult.ok(recordVOJson,"这是战绩");
    }
}
