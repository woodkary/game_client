package com.kary.hahaha3.utils;

/**
 * @author:123
 */
public class LevelUtil {
    public static final String[] levels=new String[]{"青铜","白银","黄金","白金","钻石","大师","王者"};

    public static String getLevel(int score){
        int i=score/300;
        return levels[i];
    }
}
