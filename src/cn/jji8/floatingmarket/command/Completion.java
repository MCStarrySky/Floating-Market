package cn.jji8.floatingmarket.command;

import cn.jji8.floatingmarket.logger.Logger;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.ArrayList;
import java.util.List;

/**
 * 一个命令补全器,转用于命令补全
 * */
public class Completion implements TabCompleter {
    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] strings) {
        if(strings.length==1){
            ArrayList<String> ArrayList = new ArrayList<String>();
            ArrayList.add("reload");
            ArrayList.add("add");
            ArrayList.add("help");
            ArrayList.add("delete");
            ArrayList.add("set");
            ArrayList.add("exchange");
            ArrayList.add("setservermoney");
            ArrayList.add("setformula");
            ArrayList.add("setBuyOrSell");
            return ArrayList;
        }
        if("setBuyOrSell".equalsIgnoreCase(strings[0])){
            ArrayList<String> ArrayList = new ArrayList<String>();
            ArrayList.add("true");
            ArrayList.add("false");
            Logger.putdebug("补全了的呜呜呜");
            return ArrayList;
        }
        return null;
    }
}
