package com.kary.karyplugin.pojo;

import java.util.Date;
import java.util.Objects;

/**
 * @author:123
 */

public class User {
    private String username;
    private String pwd;
    private int scoreTotal;
    private String email;
    private Date regdate;
    private int gamesCount;
    private String gamesId;


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public int getScoreTotal() {
        return scoreTotal;
    }

    public void setScoreTotal(int scoreTotal) {
        this.scoreTotal = scoreTotal;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getRegdate() {
        return regdate;
    }

    public void setRegdate(Date regdate) {
        this.regdate = regdate;
    }

    public int getGamesCount() {
        return gamesCount;
    }

    public void setGamesCount(int gamesCount) {
        this.gamesCount = gamesCount;
    }

    public String getGamesId() {
        return gamesId;
    }

    public void setGamesId(String gamesId) {
        this.gamesId = gamesId;
    }

    public User() {
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", pwd='" + pwd + '\'' +
                ", scoreTotal=" + scoreTotal +
                ", email='" + email + '\'' +
                ", regdate=" + regdate +
                ", gamesCount=" + gamesCount +
                ", gamesId='" + gamesId + '\'' +
                '}';
    }

    public User(String username, String pwd, int scoreTotal, String email, Date regdate, int gamesCount, String gamesId) {
        this.username = username;
        this.pwd = pwd;
        this.scoreTotal = scoreTotal;
        this.email = email;
        this.regdate = regdate;
        this.gamesCount = gamesCount;
        this.gamesId = gamesId;
    }


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
