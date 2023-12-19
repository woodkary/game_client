package com.kary.hahaha3.controller.rankInformation;

import com.kary.hahaha3.pojo.JsonResult;
import com.kary.hahaha3.pojo.vo.NewGameRecordJSON;
import com.kary.hahaha3.service.GameRecordService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author:123
 */
@RestController
@Tag(name = "更新排位信息")
@RequestMapping("/games")
public class UpdateGameController {
    @Autowired
    @Qualifier("GameRecordService")
    GameRecordService gameRecordService;
    @PostMapping("/recordNewMatch")
    @Operation(summary = "记录新的一场比赛",description = "返回1表示更新成功，返回0表示更新失败。把游戏里updateDatabase的参数全部用json发进来")
    public JsonResult recordNewMatch(NewGameRecordJSON json){
        Integer res= gameRecordService.recordNewMatch(json.getMaxGameId(),json.getDuration(),json.getUsername(),json.getKill(),json.getDeath(),json.getScoreGain(),json.getAssist(),json.getTakeDamage(),json.getTakenDamage(),json.getMvpPlayer(),json.getGameMode());
        if(res!=1){
            return JsonResult.error("更新失败");
        }
        return JsonResult.ok(res,"更新成功");
    }
}
