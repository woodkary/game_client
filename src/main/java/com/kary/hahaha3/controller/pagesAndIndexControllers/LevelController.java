package com.kary.hahaha3.controller.pagesAndIndexControllers;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author:123
 */
@Controller
public class LevelController {
    @RequestMapping("/level{m:[1-3]}/{n:[1-3]}")
    public String level123(HttpServletRequest request) {
        String value=request.getRequestURI();
        return "/views"+value;
    }
}
