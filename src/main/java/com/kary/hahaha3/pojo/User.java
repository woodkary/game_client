package com.kary.hahaha3.pojo;

import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.*;

import java.io.Serializable;
import java.util.Date;

/**
 * @author:123
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@Tag(name = "User")
//   /picture/leyun.jpg
public class User implements Serializable {
    @Schema(name = "username",description = "用户名",example = "kary")
    private String username;
    @Schema(name = "pwd",description = "密码",example = "ertjwrgbr3hwrh")
    private String pwd;
    @Schema(name = "email",description = "邮箱",example = "834479572@qq.com")
    private String email;
    @Schema(name = "regdate",description = "注册日期",example = "2021/12/24")
    private Date regdate;
    @Schema(name = "personalQuote",description = "个性签名",example = "null")
    private String personalQuote;
}
