package com.kary.hahaha3.controller.rankInformation;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author:123
 */
@Controller
public class UserRankInformationController {
    @GetMapping("/usr/getMyRank")
    @Operation(summary = "获取我自己的排位信息")
    public void myRankInformation(){}
    @GetMapping("/usr/getOthersRank")
    @Operation(summary = "获取别人的排位信息")
    public void othersRankInformation(){}
    @GetMapping("/usr/getMyHistoricalRecord")
    @Operation(summary = "获取我的比赛记录")
    public void myHistoricalRecord(){}
}
