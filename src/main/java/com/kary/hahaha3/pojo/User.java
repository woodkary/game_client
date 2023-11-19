package com.kary.hahaha3.pojo;

import io.swagger.v3.oas.annotations.tags.Tag;
import jdk.jfr.DataAmount;
import lombok.*;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Objects;

/**
 * @author:123
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Tag(name = "User")
public class User {
    private String username;
    private String pwd;
    private int scoreTotal;
    private String email;
    private Date regdate;
    private int gamesCount;
    private String gamesId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(username, user.username) && Objects.equals(pwd, user.pwd);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, pwd);
    }

    public User(String username, String pwd) {
        this.username = username;
        this.pwd = pwd;
    }
}
