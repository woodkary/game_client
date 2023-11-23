package com.kary.karyplugin;

import com.kary.karyplugin.service.RecordService;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * @author:123
 */
public class DatabaseTester implements CommandExecutor {
    private RecordService recordService;

    public DatabaseTester(RecordService recordService) {
        this.recordService = recordService;
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        try{
            if(strings.length==0){
                Player player= (Player) commandSender;
                Bukkit.getServer().broadcastMessage(recordService.selectUserByName(player.getName()).toString());
            }else if("name".equals(strings[0])){
                Player player= (Player) commandSender;
                Bukkit.getServer().broadcastMessage(player.getName());
            }

            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }
}
