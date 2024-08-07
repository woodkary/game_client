package com.kary.hahaha3.controller.loginAndRegister;

import com.kary.hahaha3.controller.BaseController;
import com.kary.hahaha3.exceptions.connection.DatabaseConnectionException;
import com.kary.hahaha3.exceptions.connection.VerificationCodeSendingException;
import com.kary.hahaha3.exceptions.emptyInput.EmailEmptyException;
import com.kary.hahaha3.exceptions.errorInput.EmailErrorException;
import com.kary.hahaha3.exceptions.errorInput.ErrorInputException;
import com.kary.hahaha3.exceptions.errorInput.VerificationCodeErrorException;
import com.kary.hahaha3.exceptions.expired.VerificationCodeExpireException;
import com.kary.hahaha3.mapper.UserMapper;
import com.kary.hahaha3.pojo.JsonResult;
import com.kary.hahaha3.pojo.User;
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

import javax.mail.MessagingException;
import java.util.List;


@RestController
@Tag(name = "邮箱验证")
public class EmailVerificationController extends BaseController {
    @Autowired
    @Qualifier("AESEncoder")
    private AESUtil aesEncoder;
    @Autowired
    private UserService userService;
    @Autowired
    private UserMapper userMapper;
    private class SendResult extends JsonResult{
        @Schema(name = "code",description = "状态码",example = "200")
        private int code;
        @Schema(name = "data",description = "数据",example = "445123")
        private String data;
        @Schema(name = "message",description = "消息",example = "等待输入验证码")
        private String message;
    }
    private class TypeResult extends JsonResult{
        @Schema(name = "code",description = "状态码",example = "200")
        private int code;
        @Schema(name = "data",description = "数据",example = "445123")
        private String data;
        @Schema(name = "message",description = "消息",example = "登陆成功")
        private String message;
    }
    //mode有username password email,现在要得到code
    //输入授权码
    //准备发送验证码
    @GetMapping("/sendVeriCode/{operation}")
    @Operation(summary = "发送验证码",description = "operation 1是注册,2是改密码,3是登录")
    @ApiResponses(
        value = {
            @ApiResponse(responseCode = "200",description = "等待输入验证码",
                    content = { @Content(mediaType = "application/json",
                    schema = @Schema(implementation = SendResult.class)) }
            ),
            @ApiResponse(responseCode = "400",description = "发送验证码错误",
                    content = { @Content(mediaType = "application/json",
                    schema = @Schema(implementation = JsonResult.class)) }
            ),
            @ApiResponse(responseCode = "401",description = "注册操作，该邮箱已注册",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = JsonResult.class)) }
            ),
            @ApiResponse(responseCode = "402",description = "登录、重置密码：该邮箱未注册",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = JsonResult.class)) }
            )
        }
    )
    public JsonResult sendVeriCode(@RequestParam("email") String email, @PathVariable Integer operation, HttpSession session) throws VerificationCodeSendingException, EmailEmptyException, EmailErrorException {
        if(userService.emailIsRegistered(email)&&operation==1){
            throw new EmailErrorException("该邮箱已注册");
        }

        List<User> users=userMapper.selectUserByEmail(email);
        if(!users.isEmpty()){
            User userGetByEmail=users.get(0);
            session.setAttribute("userGetByEmail",userGetByEmail);
        }else{
            if(operation==1) {//如果是注册，就要把邮箱存起来
                session.setAttribute("email",email);
            }else{
                throw new EmailErrorException("该邮箱未注册");
            }
        }
        //先生成验证码
        String verificationCode=MailUtil.getRandom6Digit();
        session.setAttribute("verificationCode",verificationCode);
        //发送验证码
        try {
            MailUtil.sendMail(email,verificationCode,"验证码");
        } catch (MessagingException e) {
            throw new VerificationCodeSendingException("发送验证码错误",e);
        }
        return JsonResult.ok(verificationCode,"等待输入验证码");
    }
    @PostMapping("/typeVeriCode/{operation}")
    @Operation(summary = "验证发送的验证码",description = "operation 1是注册,2是改密码,3是登录"+"\r\n"+"登录本来应该是GET，但懒得改了")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200",description = "成功",
                            content = { @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = TypeResult.class)) }
                    ),
                    @ApiResponse(responseCode = "401",description = "验证码过期",
                            content = { @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = JsonResult.class)) }
                    ),
                    @ApiResponse(responseCode = "402",description = "验证码错误",
                            content = { @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = JsonResult.class)) }
                    ),
                    @ApiResponse(responseCode = "400",description = "其他错误",
                            content = { @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = JsonResult.class)) }
                    )
            }
    )
    public JsonResult typeVeriCodeToRegister(@RequestBody String veriCode, @PathVariable Integer operation, HttpSession session) throws Exception {
        String verificationCode= (String) session.getAttribute("verificationCode");
        if(verificationCode==null){
            throw new VerificationCodeExpireException("验证码过期，请重新发送，或者考虑下载最新版本的Node.js");
        }
        if(verificationCode.equals(veriCode)){
            Integer flag=2;
            String successMessage="操作不当";
            switch (operation){
                case 1:{//1是注册
                    flag= userService.insertUser((String) session.getAttribute("username"), (String) session.getAttribute("password"), (String) session.getAttribute("email"));
                    successMessage="注册成功";
                    break;
                }
                case 2:{//2是改密码
                    flag=1;
                    successMessage="发送邮箱成功，请准备修改密码";
                    break;
                }
                case 3:{
                    User userGetByEmail= (User) session.getAttribute("userGetByEmail");
                    String passwordRaw=userGetByEmail.getPwd();

                    session.setAttribute("myAccount",userGetByEmail);
                    return JsonResult.ok(userGetByEmail,"登录成功");
                }
                default:{
                    throw new ErrorInputException("操作不当，请重试");
                }
            }
            if(flag!=1){
                throw new DatabaseConnectionException("更新数据库出错");
            }else {
                return JsonResult.ok(1,successMessage);
            }
        }else{
            throw new VerificationCodeErrorException("验证码错误，请重试");
        }
    }
}
