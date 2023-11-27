package com.kary.hahaha3.controller.rankInformation;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kary.hahaha3.controller.BaseController;
import com.kary.hahaha3.exceptions.JsonException;
import com.kary.hahaha3.exceptions.errorInput.UsernameErrorException;
import com.kary.hahaha3.mapper.UserMapper;
import com.kary.hahaha3.pojo.JsonResult;
import com.kary.hahaha3.pojo.User;
import com.kary.hahaha3.pojo.vo.RecordVO;
import com.kary.hahaha3.service.RecordVOService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author:123
 */
//TODO 加入支持筛选更多模式
@RestController
@Tag(name = "查询排位信息")
@RequestMapping("/ranks")
public class UserRankInformationController extends BaseController {
    @Autowired
    @Qualifier("RecordVOService")
    private RecordVOService recordVOService;
    @Autowired
    private UserMapper userMapper;
    @GetMapping("/{page}")
    @Operation(summary = "获取自己或别人的比赛记录信息")
    public JsonResult othersRankInformation(@RequestParam("username")String username, @PathVariable int page) throws Exception {
        User account= userMapper.selectUserByName(username);
        if(account==null){
            throw new UsernameErrorException("找不到该用户");
        }
        String recordVOJson;
        try {
            recordVOJson = new ObjectMapper().writeValueAsString(recordVOService.getGamesByIds(account.getUsername(), 1,page));
        } catch (JsonProcessingException e) {
            throw new JsonException("返回比赛记录错误",e);
        }
        return JsonResult.ok(recordVOJson,"这是战绩");
    }
}
