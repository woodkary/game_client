package com.kary.hahaha3.controller.PersonalInformation;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author:123
 */
@Controller
public class AvatarController {
    @PostMapping("/user/changeAvatar")
    public String changeAvatar(@RequestParam("file")MultipartFile file, HttpSession session){
        return "views/changeAvatar";
    }
}
