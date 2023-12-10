package com.kary.hahaha3.controller.loginAndRegister;

import com.kary.hahaha3.controller.BaseController;
import com.kary.hahaha3.exceptions.emptyInput.PasswordEmptyException;
import com.kary.hahaha3.exceptions.emptyInput.UsernameEmptyException;
import com.kary.hahaha3.exceptions.errorInput.EmailErrorException;
import com.kary.hahaha3.exceptions.errorInput.PasswordErrorException;
import com.kary.hahaha3.exceptions.errorInput.UsernameErrorException;
import com.kary.hahaha3.pojo.JsonResult;
import com.kary.hahaha3.pojo.vo.RegisterJSON;
import com.kary.hahaha3.service.UserService;
import com.kary.hahaha3.utils.AESUtil;
import com.kary.hahaha3.utils.MailUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author:123
 */
@RestController
@Tag(name = "注册")
//注册邮箱仅支持qq邮箱
@CrossOrigin(origins = {"*"})
public class RegisterController extends BaseController {
    @Autowired
    @Qualifier("AESEncoder")
    private AESUtil aesEncoder;
    @Autowired
    private UserService userService;
    @PostMapping("/register")
    @Operation(summary = "用户注册",description = "user register")
    public JsonResult userRegister(@RequestBody RegisterJSON registerJSON,
                                   HttpSession session)throws Exception{
        String username=registerJSON.getUsername();
        String password=registerJSON.getPassword();

        if (userService.selectUserByName(username)!=null) {
            throw new UsernameErrorException("该用户已注册");
        }else{
            password=aesEncoder.encrypt(password);
            session.setAttribute("username",username);
            session.setAttribute("password",password);
            return JsonResult.ok("请准备发验证码");
        }
    }
}

