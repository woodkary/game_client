package com.kary.hahaha3.controller.rankInformation;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kary.hahaha3.controller.BaseController;
import com.kary.hahaha3.exceptions.DatabaseUpdateException;
import com.kary.hahaha3.exceptions.JsonException;
import com.kary.hahaha3.exceptions.errorInput.ErrorInputException;
import com.kary.hahaha3.exceptions.errorInput.GameNotFoundException;
import com.kary.hahaha3.exceptions.errorInput.MatchTypeErrorException;
import com.kary.hahaha3.exceptions.errorInput.UsernameErrorException;
import com.kary.hahaha3.exceptions.expired.SessionExpireException;
import com.kary.hahaha3.mapper.UserMapper;
import com.kary.hahaha3.pojo.Games;
import com.kary.hahaha3.pojo.JsonResult;
import com.kary.hahaha3.pojo.User;
import com.kary.hahaha3.pojo.UserGame;
import com.kary.hahaha3.pojo.vo.PersonalReport;
import com.kary.hahaha3.pojo.vo.RecordVO;
import com.kary.hahaha3.pojo.vo.Records;
import com.kary.hahaha3.pojo.vo.SetPortraitJSON;
import com.kary.hahaha3.service.PersonalReportService;
import com.kary.hahaha3.service.RecordVOService;
import com.kary.hahaha3.service.RecordsService;
import com.kary.hahaha3.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Date;
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
    private class RecordsResult extends JsonResult{
        @Schema(name = "code",description = "状态码",example = "200")
        private int code;
        @Schema(name = "data",description = "总战绩信息",implementation = Records.class)
        private Records data;
        @Schema(name = "message",description = "战绩信息",example = "你的战绩信息")
        private String message;
    }
    private class PersonalReportResult extends JsonResult{
        @Schema(name = "code",description = "状态码",example = "200")
        private int code;
        @Schema(name = "data",description = "战报信息",implementation = PersonalReport.class)
        private PersonalReport data;
        @Schema(name = "message",description = "战报信息",example = "你的战报信息")
        private String message;
    }
    private class RecordVOResult extends JsonResult{
        @Schema(name = "code",description = "状态码",example = "200")
        private int code;
        @Schema(name = "data",description = "比赛信息",example = "[{\"gameId\":1,\"username\":\"kary\",\"gameTime\":\"2021/12/24\",\"portrait\":1,\"kills\":10,\"deaths\":15,\"assists\":20,\"scoreGain\":15,\"kda\":2.5,\"duration\":300000,\"isMVP\":true,\"type\":\"大乱斗\",\"takeDamage\":15.89,\"takenDamage\":21.89}]")
        private List<RecordVO> data;
        @Schema(name = "message",description = "提示消息",example = "这是比赛")
        private String message;
    }
    private class PortraitResult extends JsonResult{
        @Schema(name = "code",description = "状态码",example = "200")
        private int code;
        @Schema(name = "data",description = "头像id",example = "1")
        private Integer data;
        @Schema(name = "message",description = "提示消息",example = "修改成功")
        private String message;
    }
    private class UserGameResult extends JsonResult{
        @Schema(name = "code",description = "状态码",example = "200")
        private int code;
        @Schema(name = "data",description = "用户信息",example = "[{\"username\":\"kary\",\"scoreTotal1v1\":10,\"gamesCount\":14,\"gamesCount1v1\":15,\"gamesCountBrawl\":12,\"scoreTotalBrawl\":10,\"portrait\":3,\"maxScore1v1\":156,\"maxScoreBrawl\":255,\"onMatch\":2}]")
        private List<UserGame> data;
        @Schema(name = "message",description = "提示消息",example = "获取成功")
        private String message;
    }
    private class RankPositionResult extends JsonResult{
        @Schema(name = "code",description = "状态码",example = "200")
        private int code;
        @Schema(name = "data",description = "排名",example = "1")
        private Integer data;
        @Schema(name = "message",description = "提示消息",example = "获取成功")
        private String message;
    }
    private class GamesResult extends JsonResult{
        @Schema(name = "code",description = "状态码",example = "200")
        private int code;
        @Schema(name = "data",description = "比赛信息",example = "[{\"type\":1,\"gameId\":\"33\",\"gameTime\":\"2021/12/24\",\"duration\":7711,\"mvpPlayer\":\"kary\"}]")
        private List<Games> data;
        @Schema(name = "message",description = "提示消息",example = "这是比赛")
        private String message;
    }
    @GetMapping("/othersAllRecords")
    @Operation(summary = "统计别人的战绩信息，即全部场次部分",description="仅返回一个Records对象。请看Records类")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200",description = "你的所有战绩信息",
                            content = { @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = RecordsResult.class)) }
                    ),
                    @ApiResponse(responseCode = "400",description = "用户不存在",
                            content = { @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = JsonResult.class)) }
                    )
            })
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
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200",description = "你的本月战绩信息",
                            content = { @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = RecordsResult.class)) }
                    ),
                    @ApiResponse(responseCode = "400",description = "用户不存在",
                            content = { @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = JsonResult.class)) }
                    )
            })
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
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200",description = "你的本月战绩信息",
                            content = { @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = PersonalReportResult.class)) }
                    ),
                    @ApiResponse(responseCode = "400",description = "用户不存在",
                            content = { @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = JsonResult.class)) }
                    )
            })
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
    @Operation(summary = "根据页数获取我自己或别人的比赛记录信息")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200",description = "你的比赛记录信息",
                            content = { @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = RecordVOResult.class)) }
                    ),
                    @ApiResponse(responseCode = "400",description = "用户不存在",
                            content = { @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = JsonResult.class)) }
                    )
            }
    )
    public JsonResult myRankInformation(@RequestParam("username")String username,@PathVariable int page) throws SessionExpireException, MatchTypeErrorException {
        List<RecordVO> recordVOS=recordVOService.getGamesByUsername(username, null,page);
        return JsonResult.ok(recordVOS,"这是比赛");
    }
    @GetMapping("/getRanks")
    @Operation(summary = "获取我自己或别人的所有比赛记录信息",description = "请输入用户名username")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200",description = "你的比赛记录信息",
                            content = { @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = RecordVOResult.class)) }
                    ),
                    @ApiResponse(responseCode = "400",description = "用户不存在",
                            content = { @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = JsonResult.class)) }
                    )
            }
    )
    public JsonResult myAllRankInformation(@RequestParam("username")String username) throws SessionExpireException, MatchTypeErrorException {
        List<RecordVO> recordVOS=recordVOService.getGamesByUsername(username, null,1);
        return JsonResult.ok(recordVOS,"这是比赛");
    }
    @GetMapping("/getGamesByGameId")
    @Operation(summary = "通过id获取比赛记录信息，返回所有比赛记录信息",description = "请输入比赛id")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200",description = "你的比赛记录信息",
                            content = { @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = RecordVOResult.class)) }
                    ),
                    @ApiResponse(responseCode = "400",description = "这局游戏不存在",
                            content = { @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = JsonResult.class)) }
                    )
            }
    )
    public JsonResult getGamesByGameId(@RequestParam("gameId")Integer gameId) throws GameNotFoundException {
        List<RecordVO> recordVOS=recordVOService.getGamesByGameId(gameId);
        return JsonResult.ok(recordVOS,"这是比赛");
    }
    @GetMapping("/getGamesByDate")
    @Operation(summary = "通过日期获取比赛记录信息，格式为2021/12/24",description = "输入日期，返回所有游戏记录信息")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200",description = "你的比赛记录信息",
                            content = { @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = GamesResult.class)) }
                    ),
                    @ApiResponse(responseCode = "400",description = "日期格式错误",
                            content = { @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = JsonResult.class)) }
                    )
            }
    )
    public JsonResult getGamesByDate(@RequestParam("date")String dateStr) throws ErrorInputException {
        try {
            SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
            Date date = format.parse(dateStr);
            List<Games> games = recordVOService.getGamesByDate(date);
            return JsonResult.ok(games, "这是比赛");
        }catch (Exception e){
            throw new ErrorInputException("日期格式错误",e);
        }
    }
    @PostMapping("/portrait")
    @Operation(summary = "修改头像",description = "请输入用户名username和头像id portrait")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200",description = "修改成功",
                            content = { @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = PortraitResult.class)) }
                    ),
                    @ApiResponse(responseCode = "400",description = "修改失败",
                            content = { @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = JsonResult.class)) }
                    )
            }
    )
    public JsonResult updatePortrait(@RequestBody SetPortraitJSON setPortraitJSON) throws DatabaseUpdateException {
        Integer num=userService.updateUserPortrait(setPortraitJSON.getUsername(),setPortraitJSON.getPortrait());
        if(num==1){
            return JsonResult.ok(num,"修改成功");
        }else{
            throw new DatabaseUpdateException("修改失败，请重试");
        }
    }
    @GetMapping("/get1v1RankingOrder")
    @Operation(summary = "获取1v1排行榜")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200",description = "获取成功",
                            content = { @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = UserGameResult.class)) }
                    ),
                    @ApiResponse(responseCode = "400",description = "获取失败",
                            content = { @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = JsonResult.class)) }
                    )
            }
    )
    public JsonResult get1v1RankingOrder() throws JsonException {
        List<UserGame> users=userService.getAllUserOrder1v1();
        if(users==null){
            throw new JsonException("获取失败");
        }
        return JsonResult.ok(users,"获取成功");
    }
    @GetMapping("/getBrawlRankingOrder")
    @Operation(summary = "获取乱斗排行榜")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200",description = "获取成功",
                            content = { @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = UserGameResult.class)) }
                    ),
                    @ApiResponse(responseCode = "400",description = "获取失败",
                            content = { @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = JsonResult.class)) }
                    )
            }
    )
    public JsonResult getBrawlRankingOrder() throws JsonException {
        List<UserGame> users=userService.getAllUserOrderBrawl();
        if(users==null){
            throw new JsonException("获取失败");
        }
        return JsonResult.ok(users,"获取成功");
    }
    @GetMapping("/getTotalScoreRankingOrder")
    @Operation(summary = "获取总分排行榜")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200",description = "获取成功",
                            content = { @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = UserGameResult.class)) }
                    ),
                    @ApiResponse(responseCode = "400",description = "获取失败",
                            content = { @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = JsonResult.class)) }
                    )
            }
    )
    public JsonResult getTotalScoreRankingOrder() throws JsonException {
        List<UserGame> users=userService.getAllUserOrderTotalScore();
        if(users==null){
            throw new JsonException("获取失败");
        }
        return JsonResult.ok(users,"获取成功");
    }
    @GetMapping("/getRankPosition")
    @Operation(summary = "获取用户排名",description = "请输入用户名username")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200",description = "获取成功",
                            content = { @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = RankPositionResult.class)) }
                    ),
                    @ApiResponse(responseCode = "400",description = "获取失败",
                            content = { @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = JsonResult.class)) }
                    )
            }
    )
    public JsonResult getRankPosition(@RequestParam("username")String username) throws JsonException, SessionExpireException {
        List<UserGame> users=userService.getAllUserOrderTotalScore();
        if(users==null){
            throw new JsonException("获取失败");
        }
        int i=0;
        for(UserGame user:users){
            i++;
            if(user.getUsername().equals(username)){
                break;
            }
        }
        if(i>=users.size()){
            throw new SessionExpireException("用户不存在");
        }
        return JsonResult.ok(i,"获取成功");
    }

}
