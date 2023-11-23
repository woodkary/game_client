package com.kary.karyplugin.pojo;

/**
 * @author:123
 */
public class Record {
    private int kill;
    private int death;
    private int assist;
    private int scoreGain;
    public void addOneKill(){
        kill+=1;
    }
    public void addOneDeath(){
        death+=1;
    }
    public void addOneAssist(){
        assist+=1;
    }
    private void addScore(int add){
        scoreGain+=add;
    }

    public int getKill() {
        return kill;
    }

    public void setKill(int kill) {
        this.kill = kill;
    }

    public int getDeath() {
        return death;
    }

    public void setDeath(int death) {
        this.death = death;
    }

    public int getAssist() {
        return assist;
    }

    public void setAssist(int assist) {
        this.assist = assist;
    }

    public int getScoreGain() {
        return scoreGain;
    }

    public void setScoreGain(int scoreGain) {
        this.scoreGain = scoreGain;
    }

    public Record() {
    }

    public Record(int kill, int death, int assist, int scoreGain) {
        this.kill = kill;
        this.death = death;
        this.assist = assist;
        this.scoreGain = scoreGain;
    }
}
