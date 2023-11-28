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
import com.kary.hahaha3.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@RequestMapping("/ranks/{page}")
public class UserRankInformationController extends BaseController {
    @Autowired
    @Qualifier("RecordVOService")
    private RecordVOService recordVOService;
    @Autowired
    private UserService userService;
    @GetMapping("")
    @Operation(summary = "获取自己或别人的比赛记录信息")
    public JsonResult othersRankInformation(@RequestParam("username")String username, @PathVariable int page) throws Exception {
        User account= userService.selectUserByName(username);
        if(account==null){
            throw new UsernameErrorException("找不到该用户");
        }
        List<RecordVO> recordVO=recordVOService.getGamesByIds(account.getUsername(),page);
        return JsonResult.ok(recordVO,"这是战绩");
    }
}
