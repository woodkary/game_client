package com.kary.karyplugin.pojo;

import java.util.Date;
import java.util.Objects;

/**
 * @author:123
 */

public class User {
    private String username;
    private String pwd;
    private int scoreTotal1v1;
    private int scoreTotalBrawl;
    private String email;
    private Date regdate;
    private int gamesCount;

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

    public int getScoreTotal1v1() {
        return scoreTotal1v1;
    }

    public void setScoreTotal1v1(int scoreTotal1v1) {
        this.scoreTotal1v1 = scoreTotal1v1;
    }

    public int getScoreTotalBrawl() {
        return scoreTotalBrawl;
    }

    public void setScoreTotalBrawl(int scoreTotalBrawl) {
        this.scoreTotalBrawl = scoreTotalBrawl;
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

    public User(String username, String pwd, int scoreTotal1v1, int scoreTotalBrawl, String email, Date regdate, int gamesCount) {
        this.username = username;
        this.pwd = pwd;
        this.scoreTotal1v1 = scoreTotal1v1;
        this.scoreTotalBrawl = scoreTotalBrawl;
        this.email = email;
        this.regdate = regdate;
        this.gamesCount = gamesCount;
    }

    public User() {
    }
}
