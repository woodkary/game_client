package com.kary.karyplugin.utils;

/**
 * @author:123
 */
public class LevelUtil {
    public static final int COPPER=1;
    public static final int SILVER=2;
    public static final int GOLD=3;
    public static final int PLATINUM=4;
    public static final int DIAMOND=5;
    public static final int KING=6;
    public static int getLevel(int score){
        if(score>=1800){
            return KING;
        }else if(score>=1500){
            return DIAMOND;
        }
        return score/300+1;
    }
}
