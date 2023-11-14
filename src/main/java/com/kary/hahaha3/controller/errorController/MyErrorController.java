package com.kary.hahaha3.controller.errorController;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author:123
 */
@Controller
public class MyErrorController implements ErrorController {
    @RequestMapping("/error")
    public String errorMessage(HttpServletRequest request, Model model){
        Integer errorCode= (Integer) request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        model.addAttribute("errorMessage",errorCode+",菜就多练!");
        return "error";
    }
}
