package com.kary.karyplugin.pojo;

/**
 * @author:123
 */

public class UserGame {
    private String username;
    private int scoreTotal1v1;
    private int scoreTotalBrawl;
    private int gamesCount;

    @Override
    public String toString() {
        return "UserGame{" +
                "username='" + username + '\'' +
                ", scoreTotal1v1=" + scoreTotal1v1 +
                ", scoreTotalBrawl=" + scoreTotalBrawl +
                ", gamesCount=" + gamesCount +
                '}';
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    public int getGamesCount() {
        return gamesCount;
    }

    public void setGamesCount(int gamesCount) {
        this.gamesCount = gamesCount;
    }

    public UserGame(String username, int scoreTotal1v1, int scoreTotalBrawl, int gamesCount) {
        this.username = username;
        this.scoreTotal1v1 = scoreTotal1v1;
        this.scoreTotalBrawl = scoreTotalBrawl;
        this.gamesCount = gamesCount;
    }

    public UserGame() {
    }
}
