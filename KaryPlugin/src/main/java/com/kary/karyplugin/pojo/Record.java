package com.kary.karyplugin.pojo;

/**
 * @author:123
 */
public class Record {
    private int kill;
    private int death;
    private int assist;
    private int scoreGain;
    private double takeDamage;
    private double takenDamage;

    public double getTakeDamage() {
        return takeDamage;
    }

    public void setTakeDamage(double takeDamage) {
        this.takeDamage = takeDamage;
    }

    public double getTakenDamage() {
        return takenDamage;
    }

    public void setTakenDamage(double takenDamage) {
        this.takenDamage = takenDamage;
    }

    public synchronized void addOneKill(){
        kill+=1;
    }
    public synchronized void addOneDeath(){
        death+=1;
    }
    public synchronized void addOneAssist(){
        assist+=1;
    }
    public synchronized void addScore(int add){
        scoreGain+=add;
    }
    public synchronized void addTakenDamage(double takenDamage){
        this.takenDamage+=takenDamage;
    }
    public synchronized void addTakeDamage(double takeDamage){
        this.takeDamage+=takeDamage;
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

}
