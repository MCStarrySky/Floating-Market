package cn.jji8.floatingmarket.command;

import cn.jji8.floatingmarket.logger.Language;
import cn.jji8.floatingmarket.logger.Logger;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

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
            ArrayList.add("重新加载");

            ArrayList.add("add");
            ArrayList.add("添加");

            ArrayList.add("help");
            ArrayList.add("帮助");

            ArrayList.add("delete");
            ArrayList.add("删除");

            ArrayList.add("set");
            ArrayList.add("设置");

            ArrayList.add("exchange");
            ArrayList.add("交换");

            ArrayList.add("setservermoney");
            ArrayList.add("设置服务器余额");


            ArrayList.add("setformula");
            ArrayList.add("设置公式");

            ArrayList.add("setBuyOrSell");
            ArrayList.add("设置是否允许购买或出售");

            ArrayList.add("setPlayerMaxMoneyForTime");
            ArrayList.add("设置每个玩家最大单位时间获得钱数");

            ArrayList.add("setAllMaxMoneyForTime");
            ArrayList.add("设置全服玩家最大单位时间获得钱数");
            return ArrayList;
        }
        if(Implement.equalsOneIgnoreCase(strings[0],new String[]{"setBuyOrSell","设置是否允许购买或出售"})){
            ArrayList<String> ArrayList = null;
            if(strings.length<=3){
                if(strings.length==2){
                    if(commandSender instanceof Player){
                        Logger.putPlayerBelow((Player) commandSender,"出售{true(允许) false(不允许)}");
                    }
                }
                if(strings.length==3){
                    if(commandSender instanceof Player){
                        Logger.putPlayerBelow((Player) commandSender,"购买{true(允许) false(不允许)}");
                    }
                }
                ArrayList = new ArrayList<String>();
                ArrayList.add("true");
                ArrayList.add("false");
                Logger.putdebug("补全了的呜呜呜");
            }
            return ArrayList;
        }
        if(Implement.equalsOneIgnoreCase(strings[0],new String[]{"set","设置"})){
            ArrayList<String> ArrayList = new ArrayList<String>();
            if(strings.length==2){
                ArrayList.add("库存数量");
            }
            if(strings.length==3){
                ArrayList.add("最低价格");
            }
            if(strings.length==4){
                ArrayList.add("最高价格");
            }
            return ArrayList;
        }
        if(Implement.equalsOneIgnoreCase(strings[0],new String[]{"setformula","设置公式"})){
            ArrayList<String> ArrayList = new ArrayList<String>();
            if(strings.length==2){
                ArrayList.add("价格公式");
            }
            if(strings.length==3){
                ArrayList.add("库存显示公式");
            }
            return ArrayList;
        }
        if(Implement.equalsOneIgnoreCase(strings[0],new String[]{"setservermoney","设置服务器余额"})){
            ArrayList<String> ArrayList = new ArrayList<String>();
            if(strings.length==2){
                ArrayList.add("钱数");
            }
            return ArrayList;
        }
        if(Implement.equalsOneIgnoreCase(strings[0],new String[]{"setPlayerMaxMoneyForTime","设置每个玩家最大单位时间获得钱数","setAllMaxMoneyForTime","设置全服玩家最大单位时间获得钱数"})){
            ArrayList<String> ArrayList = new ArrayList<String>();
            if(strings.length==2){
                ArrayList.add("时间（单位：秒）");
            }
            if(strings.length==3){
                ArrayList.add("最大钱数");
            }
            return ArrayList;
        }
        return null;
    }
}
