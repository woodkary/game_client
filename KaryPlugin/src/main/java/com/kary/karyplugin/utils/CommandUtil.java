package com.kary.karyplugin.utils;

/**
 * @author:123
 */
public class CommandUtil {
    public static final String COMMAND_SOLO_PVP="tellraw @p {\"text\":\"单挑\",\"color\":\"red\",\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/solopvp\"}}";
    public static final String COMMAND_BRAWL="tellraw @p {\"text\":\"乱斗\",\"color\":\"green\",\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/brawl\"}}";
    public static final String COMMAND_QUIT_MATCHING="tellraw @p {\"text\":\"退出匹配\",\"color\":\"green\",\"clickEvent\":{\"action\":\"run_command\",\"value\":\"/quitmatching\"}}";
}
