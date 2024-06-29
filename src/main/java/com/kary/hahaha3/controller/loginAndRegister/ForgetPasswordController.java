package com.kary.hahaha3.controller.loginAndRegister;

import com.kary.hahaha3.controller.BaseController;
import com.kary.hahaha3.exceptions.emptyInput.PasswordEmptyException;
import com.kary.hahaha3.exceptions.emptyInput.UsernameEmptyException;
import com.kary.hahaha3.exceptions.errorInput.EmailErrorException;
import com.kary.hahaha3.exceptions.errorInput.PasswordErrorException;
import com.kary.hahaha3.exceptions.errorInput.UsernameErrorException;
import com.kary.hahaha3.mapper.UserMapper;
import com.kary.hahaha3.pojo.JsonResult;
import com.kary.hahaha3.pojo.User;
import com.kary.hahaha3.pojo.vo.RegisterJSON;
import com.kary.hahaha3.pojo.vo.ResetPasswordJSON;
import com.kary.hahaha3.service.UserService;
import com.kary.hahaha3.utils.AESUtil;
import com.kary.hahaha3.utils.MailUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.*;

/**
 * @author:123
 */
@RestController
@Tag(name = "忘记密码")
public class ForgetPasswordController extends BaseController {
    @Autowired
    @Qualifier("AESEncoder")
    AESUtil aesEncoder;
    @Autowired
    private UserService userService;
    private class ForgetPasswordResult extends JsonResult{
        @Schema(name = "code",description = "状态码",example = "200")
        private int code;
        @Schema(name = "data",description = "数据",example = "")
        private String data;
        @Schema(name = "message",description = "消息",example = "修改密码成功")
        private String message;
    }

    @PostMapping("/resetPassword")
    @Operation(summary = "重置密码",description = "仅需要输入密码")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200",description = "修改密码成功",
                            content = { @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ForgetPasswordResult.class)) }
                    ),
                    @ApiResponse(responseCode = "400",description = "用户不存在",
                            content = { @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = JsonResult.class)) }
                    ),
                    @ApiResponse(responseCode = "401",description = "输入邮箱不合法",
                            content = { @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = JsonResult.class)) }
                    )
            })
    public JsonResult resetPassword(@RequestBody String password,
                                    HttpSession session) throws Exception {
        User userGetByEmail= (User) session.getAttribute("userGetByEmail");
        String username=userGetByEmail.getUsername();
        User userInDatabase= userService.selectUserByName(username);
        if(userInDatabase==null){
            throw new UsernameErrorException("该用户不存在");
        }else{
            String email=userInDatabase.getEmail();
            if(!MailUtil.legalQQMail(email)){
                throw new EmailErrorException("请输入合法的邮箱");
            }else {
                password=aesEncoder.encrypt(password);
                userService.updateUserPassword(username,password);
                return JsonResult.ok("修改密码成功");
            }
        }

    }
}
