package com.kary.hahaha3.controller.rankInformation;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kary.hahaha3.controller.BaseController;
import com.kary.hahaha3.exceptions.DatabaseUpdateException;
import com.kary.hahaha3.exceptions.JsonException;
import com.kary.hahaha3.exceptions.errorInput.GameNotFoundException;
import com.kary.hahaha3.exceptions.errorInput.MatchTypeErrorException;
import com.kary.hahaha3.exceptions.errorInput.UsernameErrorException;
import com.kary.hahaha3.exceptions.expired.SessionExpireException;
import com.kary.hahaha3.mapper.UserMapper;
import com.kary.hahaha3.pojo.JsonResult;
import com.kary.hahaha3.pojo.User;
import com.kary.hahaha3.pojo.vo.PersonalReport;
import com.kary.hahaha3.pojo.vo.RecordVO;
import com.kary.hahaha3.pojo.vo.Records;
import com.kary.hahaha3.pojo.vo.SetPortraitJSON;
import com.kary.hahaha3.service.PersonalReportService;
import com.kary.hahaha3.service.RecordVOService;
import com.kary.hahaha3.service.RecordsService;
import com.kary.hahaha3.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author:123
 */
@RestController
@Tag(name = "查询排位信息")
@RequestMapping("/ranks")
public class UserRankInformationController extends BaseController {
    @Autowired
    @Qualifier("RecordsService")
    private RecordsService recordsService;
    @Autowired
    @Qualifier("PersonalReportService")
    private PersonalReportService personalReportService;
    @Autowired
    @Qualifier("RecordVOService")
    private RecordVOService recordVOService;
    @Autowired
    @Qualifier("UserService")
    private UserService userService;
    @GetMapping("/othersAllRecords")
    @Operation(summary = "统计别人的战绩信息，即全部场次部分",description="仅返回一个Records对象。请看Records类")
    public JsonResult getOthersAllGame(@RequestParam("username")String username,HttpSession session) throws SessionExpireException {
        User account= userService.selectUserByName(username);
        if(account==null){
            throw new SessionExpireException("用户不存在");
        }
        Records records=recordsService.getAllGame(username);
        return JsonResult.ok(records,"你的所有战绩信息");
    }
    @GetMapping("/othersMonthRecords")
    @Operation(summary = "统计本月战绩信息，即本月场次部分，仅返回一个Records对象。请看Records类")
    public JsonResult getOthersMonthGame(@RequestParam("username")String username,HttpSession session) throws SessionExpireException {
        User account= userService.selectUserByName(username);
        if(account==null){
            throw new SessionExpireException("用户不存在");
        }
        Records records=recordsService.getAllGameThisMonth(username);
        return JsonResult.ok(records,"你的本月战绩信息");
    }
    @GetMapping("/othersReport/{type}")
    @Operation(summary = "统计战报",description="仅返回一个PersonalReport对象。个人主页中需要调用两次")
    public JsonResult getPersonalReport(@RequestParam("username")String username,@PathVariable int type,HttpSession session) throws SessionExpireException, UsernameErrorException, MatchTypeErrorException {
        User account= userService.selectUserByName(username);
        if(account==null){
            throw new SessionExpireException("用户不存在");
        }
        PersonalReport personalReport=personalReportService.getPersonalReport(username, type);
        //将PersonalReport对象转换为json字符串
        return JsonResult.ok(personalReport,"这是个人战报");
    }
    @GetMapping("/checkIfExist")
    @Operation(summary = "查用户是否存在")
    public Boolean checkIfExist(@RequestParam("username")String username) throws SessionExpireException {
        User account= userService.selectUserByName(username);
        if(account==null){
            throw new SessionExpireException("用户不存在");
        }
        return true;
    }
    @GetMapping("/getRanks/{page}")
    @Operation(summary = "获取我自己或别人的比赛记录信息，返回List<RecordVO>")
    public JsonResult myRankInformation(@RequestParam("username")String username,@PathVariable int page) throws SessionExpireException, MatchTypeErrorException {
        List<RecordVO> recordVOS=recordVOService.getGamesByUsername(username, null,page);
        return JsonResult.ok(recordVOS,"这是比赛");
    }
    @GetMapping("/getRanks")
    @Operation(summary = "获取我自己或别人的所有比赛记录信息，返回List<RecordVO>",description = "共64个RecordVO")
    public JsonResult myAllRankInformation(@RequestParam("username")String username) throws SessionExpireException, MatchTypeErrorException {
        List<RecordVO> recordVOS=recordVOService.getGamesByUsername(username, null,1);
        return JsonResult.ok(recordVOS,"这是比赛");
    }
    @GetMapping("/getGamesByGameId")
    @Operation(summary = "通过id获取比赛记录信息，返回List<RecordVO>")
    public JsonResult getGamesByGameId(@RequestParam("gameId")Integer gameId) throws GameNotFoundException {
        List<RecordVO> recordVOS=recordVOService.getGamesByGameId(gameId);
        return JsonResult.ok(recordVOS,"这是比赛");
    }
    @PostMapping("/portrait")
    @Operation(summary = "修改头像",description = "请输入用户名username和头像id portrait")
    public JsonResult updatePortrait(@RequestBody SetPortraitJSON setPortraitJSON) throws DatabaseUpdateException {
        Integer num=userService.updateUserPortrait(setPortraitJSON.getUsername(),setPortraitJSON.getPortrait());
        if(num==1){
            return JsonResult.ok(num,"修改成功");
        }else{
            throw new DatabaseUpdateException("修改失败，请重试");
        }
    }

}
