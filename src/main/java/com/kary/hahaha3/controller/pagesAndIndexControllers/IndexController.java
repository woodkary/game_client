package com.kary.hahaha3.controller.pagesAndIndexControllers;

import com.kary.hahaha3.pojo.User;
import com.kary.hahaha3.service.RecordVOService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author:123
 */
@Controller
public class IndexController {
    @Autowired
    @Qualifier("RecordVOService")
    private RecordVOService recordVOService;
    @Autowired
    @Qualifier("myAccount")
    private User myAccount;
    @GetMapping("/")
    @Operation(summary = "返回首页",description = "return to index")
    public String index0(Model model){
        model.addAttribute("records",recordVOService.getGamesByIds(myAccount.getUsername(), myAccount.getGameId()));
        return "/index";
    }
    @GetMapping("/index")
    @Operation(summary = "返回首页，加入比赛信息",description = "return to index")
    public String index(Model model){
        model.addAttribute("records",recordVOService.getGamesByIds(myAccount.getUsername(), myAccount.getGameId()));
        return "/index";
    }
}
