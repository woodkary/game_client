package com.kary.hahaha3.controller.rankInformation;

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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author:123
 */
@Controller
public class UserRankInformationController {
    @Autowired
    @Qualifier("RecordVOService")
    private RecordVOService recordVOService;
    @GetMapping("/ranks/getMyRank/{page}")
    @Operation(summary = "获取我自己的比赛记录信息")
    public String myRankInformation(@PathVariable int page, Model model, HttpSession session){
        User myAccount= (User) session.getAttribute("myAccount");
        model.addAttribute("records",recordVOService.getGamesByIds(myAccount.getUsername(), myAccount.getGamesId(),page));
        return "views/records";
    }
    @GetMapping("/ranks/getOthersRank")
    @Operation(summary = "获取别人的比赛记录信息")
    public void othersRankInformation(){}
}
