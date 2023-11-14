package com.kary.hahaha3.controller.pagesAndIndexControllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

/**
 * @author:123
 */
@Controller
public class IndexController {
    @GetMapping("/index")
    @Operation(summary = "返回首页，加入弹窗",description = "return to index")
    public String index(HttpServletRequest request, Model model){
        String showPopup = (String) request.getAttribute("showPopup");
        if (showPopup != null) {
            model.addAttribute("showPopup", showPopup);
        }
        return "/index";
    }
}
