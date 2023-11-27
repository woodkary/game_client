package com.kary.hahaha3.controller.PersonalInformation;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

import com.kary.hahaha3.mapper.UserMapper;
import com.kary.hahaha3.pojo.User;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * @author:123
 */
@Controller
public class AvatarController {
    @Autowired
    private UserMapper userMapper;
    @Value("${upload.dir}")
    private String uploadDir;
    @Value("${upload.relativeDir}")
    private String relativeDir;
    @GetMapping("/user/toChangeAvatar")
    public String temp(HttpSession session, Model model) {
        User myAccount= (User) session.getAttribute("myAccount");
        model.addAttribute("myAccountAvatar",myAccount.getAvatar());
        return "views/changeAvatar";
    }

    @PostMapping("/user/changeAvatar")
    public String changeAvatar(@RequestParam("file")MultipartFile file, RedirectAttributes redirectAttributes, HttpSession session, Model model) {
        if (file.isEmpty()) {
            redirectAttributes.addFlashAttribute("message", "请选择要上传的头像.");
            User myAccount= (User) session.getAttribute("myAccount");
            model.addAttribute("myAccountAvatar",myAccount.getAvatar());
            return "views/changeAvatar";
        }
        try{
            User myAccount= (User) session.getAttribute("myAccount");
            //TODO 改数据库，增加头像列
            String fileName = UUID.randomUUID() + "_" + StringUtils.cleanPath(file.getOriginalFilename());

            Path targetLocation = Paths.get(uploadDir).resolve(fileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            userMapper.updateUserAvatar(myAccount.getUsername(), relativeDir+"/"+fileName);
            redirectAttributes.addFlashAttribute("message", "头像 " + fileName + " 上传成功!");

            myAccount.setAvatar(relativeDir+"/"+fileName);
            session.setAttribute("myAccount",myAccount);

            model.addAttribute("myAccountAvatar",relativeDir+"/"+fileName);
            return "views/changeAvatar";
        }
        catch (Exception e){
            e.printStackTrace();
            User myAccount= (User) session.getAttribute("myAccount");
            model.addAttribute("myAccountAvatar",myAccount.getAvatar());
            redirectAttributes.addFlashAttribute("message", "错误，请重新上传");
            return "views/changeAvatar";
        }
    }

}
