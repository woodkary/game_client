package com.kary.hahaha3.controller.rankInformation;

import com.kary.hahaha3.exceptions.errorInput.UsernameErrorException;
import com.kary.hahaha3.mapper.UserMapper;
import com.kary.hahaha3.pojo.JsonResult;
import com.kary.hahaha3.pojo.User;
import com.kary.hahaha3.pojo.vo.RecordVO;
import com.kary.hahaha3.service.RecordVOService;
import io.swagger.v3.oas.annotations.Operation;
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
@RequestMapping("/ranks")
public class UserRankInformationController {
    @Autowired
    @Qualifier("RecordVOService")
    private RecordVOService recordVOService;
    @Autowired
    private UserMapper userMapper;
    @GetMapping("/getRank/{page}")
    @Operation(summary = "获取自己或别人的比赛记录信息")
    public JsonResult othersRankInformation(@RequestParam("username")String username, @PathVariable int page, Model model) throws UsernameErrorException {
        User account= userMapper.selectUserByName(username);
        if(account==null){
            throw new UsernameErrorException("找不到该用户");
        }
        return JsonResult.ok(recordVOService.getGamesByIds(account.getUsername(), 1,1),"这是战绩");
    }
}
