package com.kary.karyplugin.pojo;

/**
 * @author:123
 */

public class UserGame {
    private String username;
    private int scoreTotal1v1;
    private int scoreTotalBrawl;
    private int gamesCount;
    private int gamesCount1v1;
    private int gamesCountBrawl;
    private int maxScore1v1;
    private int maxScoreBrawl;
    private int portrait;
    private int onMatch;

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

    public int getGamesCount1v1() {
        return gamesCount1v1;
    }

    public void setGamesCount1v1(int gamesCount1v1) {
        this.gamesCount1v1 = gamesCount1v1;
    }

    public int getGamesCountBrawl() {
        return gamesCountBrawl;
    }

    public void setGamesCountBrawl(int gamesCountBrawl) {
        this.gamesCountBrawl = gamesCountBrawl;
    }

    public int getMaxScore1v1() {
        return maxScore1v1;
    }

    public void setMaxScore1v1(int maxScore1v1) {
        this.maxScore1v1 = maxScore1v1;
    }

    public int getMaxScoreBrawl() {
        return maxScoreBrawl;
    }

    public void setMaxScoreBrawl(int maxScoreBrawl) {
        this.maxScoreBrawl = maxScoreBrawl;
    }

    public int getPortrait() {
        return portrait;
    }

    public void setPortrait(int portrait) {
        this.portrait = portrait;
    }

    public int getOnMatch() {
        return onMatch;
    }

    public void setOnMatch(int onMatch) {
        this.onMatch = onMatch;
    }

    public UserGame(String username, int scoreTotal1v1, int scoreTotalBrawl, int gamesCount, int gamesCount1v1, int gamesCountBrawl, int maxScore1v1, int maxScoreBrawl, int portrait, int onMatch) {
        this.username = username;
        this.scoreTotal1v1 = scoreTotal1v1;
        this.scoreTotalBrawl = scoreTotalBrawl;
        this.gamesCount = gamesCount;
        this.gamesCount1v1 = gamesCount1v1;
        this.gamesCountBrawl = gamesCountBrawl;
        this.maxScore1v1 = maxScore1v1;
        this.maxScoreBrawl = maxScoreBrawl;
        this.portrait = portrait;
        this.onMatch = onMatch;
    }

    public UserGame() {
    }
}
