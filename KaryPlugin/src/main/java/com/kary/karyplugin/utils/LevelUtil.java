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
    public static final int MASTER=6;
    public static final int KING=7;
    public static int getLevel(int score){
        return (Math.max(score, 0))/300+1;
    }
}
