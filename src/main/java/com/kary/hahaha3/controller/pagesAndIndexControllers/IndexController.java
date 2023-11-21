package com.kary.hahaha3.controller.pagesAndIndexControllers;

import com.kary.hahaha3.pojo.User;
import com.kary.hahaha3.pojo.vo.RecordVO;
import com.kary.hahaha3.service.RecordVOService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author:123
 */
//TODO css样式表太丑啦！求一个好看的样式。
@RestController
public class IndexController {
    @Autowired
    @Qualifier("RecordVOService")
    private RecordVOService recordVOService;
    @Autowired
    @Qualifier("myAccount")
    private User myAccount;
    @GetMapping("/")
    @Operation(summary = "返回首页",description = "return to index")
    public List<RecordVO> index0(/*Model model*/){
        /*model.addAttribute("records",recordVOService.getGamesByIds(myAccount.getUsername(), myAccount.getGamesId(),1));
        return "/index";*/
        return recordVOService.getGamesByIds(myAccount.getUsername(), myAccount.getGameId(),1);
    }
    @GetMapping("/index")
    @Operation(summary = "返回首页，加入比赛信息",description = "return to index")
    public List<RecordVO> index(/*Model model*/){
        /*model.addAttribute("records",recordVOService.getGamesByIds(myAccount.getUsername(), myAccount.getGamesId(),1));
        return "/index";*/
        return recordVOService.getGamesByIds(myAccount.getUsername(), myAccount.getGameId(),1);
    }
}
