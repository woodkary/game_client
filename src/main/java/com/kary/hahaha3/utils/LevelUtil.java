package com.kary.hahaha3.utils;

/**
 * @author:123
 */
public class LevelUtil {
    public static final String[] levels=new String[]{"","青铜","白银","黄金","白金","钻石","王者"};

    public static String getLevel(int score){
        if(score>=1800){
            return levels[6];
        }else if(score>=1500){
            return levels[5];
        }
        int i=score/300;
        return levels[i+1];
    }
}
