package com.kary.hahaha3.controller.pagesAndIndexControllers;

import com.kary.hahaha3.pojo.JsonResult;
import com.kary.hahaha3.pojo.User;
import com.kary.hahaha3.pojo.vo.RecordVO;
import com.kary.hahaha3.service.RecordVOService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
    @GetMapping("/")
    @Operation(summary = "返回首页",description = "return to index")
    public JsonResult index0(HttpSession session){
        User myAccount= (User) session.getAttribute("myAccount");
        /*model.addAttribute("records",recordVOService.getGamesByIds(myAccount.getUsername(),1,1));*/
        return JsonResult.ok(recordVOService.getGamesByIds(myAccount.getUsername(), 1,1),"这是战绩");
    }
    @GetMapping("/index")
    @Operation(summary = "返回首页，加入比赛信息",description = "return to index")
    public JsonResult index(HttpSession session){
        User myAccount= (User) session.getAttribute("myAccount");
        /*model.addAttribute("records",recordVOService.getGamesByIds(myAccount.getUsername(),1,1));*/
        return JsonResult.ok(recordVOService.getGamesByIds(myAccount.getUsername(), 1,1),"这是战绩");
    }
}
