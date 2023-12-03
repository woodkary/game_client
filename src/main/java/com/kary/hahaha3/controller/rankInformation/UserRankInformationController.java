package com.kary.hahaha3.controller.rankInformation;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kary.hahaha3.controller.BaseController;
import com.kary.hahaha3.exceptions.JsonException;
import com.kary.hahaha3.exceptions.errorInput.UsernameErrorException;
import com.kary.hahaha3.exceptions.expired.SessionExpireException;
import com.kary.hahaha3.mapper.UserMapper;
import com.kary.hahaha3.pojo.JsonResult;
import com.kary.hahaha3.pojo.User;
import com.kary.hahaha3.pojo.vo.Records;
import com.kary.hahaha3.service.RecordsService;
import com.kary.hahaha3.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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
    @Qualifier("RecordsService")
    RecordsService recordsService;
    @GetMapping("/myAllRecords")
    @Operation(summary = "统计所有战绩信息，即全部场次部分，仅返回一个Records对象。请看Records类")
    public JsonResult getMyAllGame(HttpSession session) throws SessionExpireException {
        User myAccount= (User) session.getAttribute("myAccount");
        if(myAccount==null){
            throw new SessionExpireException("您尚未登陆，或登录信息已过期");
        }
        Records records=recordsService.getAllGame(myAccount.getUsername());
        return JsonResult.ok(records,"你的所有战绩信息");
    }
    @GetMapping("/myMonthRecords")
    @Operation(summary = "统计本月战绩信息，即本月场次部分，仅返回一个Records对象。请看Records类")
    public JsonResult getMyMonthGame(HttpSession session) throws SessionExpireException {
        User myAccount= (User) session.getAttribute("myAccount");
        if(myAccount==null){
            throw new SessionExpireException("您尚未登陆，或登录信息已过期");
        }
        Records records=recordsService.getAllGameThisMonth(myAccount.getUsername());
        return JsonResult.ok(records,"你的本月战绩信息");
    }

}
