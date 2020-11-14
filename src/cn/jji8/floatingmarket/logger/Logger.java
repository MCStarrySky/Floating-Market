package cn.jji8.floatingmarket.logger;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;
/**
 * 专门用于输出日志的类
 * */
public class Logger {
    static java.util.logging.Logger logger;
    static File File;
    static YamlConfiguration yml;
    static boolean info = true,waring = true,debug = false;
    static int fadeIn,stay,fadeOut;
    static String pluginName,puginPrefix;
    /**
     * 设置信息提示器
     * */
    public static void load(Plugin main){
        System.out.println(main.getName()+"信息管理器开始加载..");
        Logger.logger = main.getLogger();
        File = new File(main.getDataFolder(),"LoggerSet.yml");
        yml = YamlConfiguration.loadConfiguration(File);
        info = getConfig("显示插件信息",true);
        waring = getConfig("显示插件警告",true);
        debug = getConfig("debug",false);
        fadeIn = getConfig("标题消息淡入时间", 20);
        stay = getConfig("标题消息持续时间",40);
        fadeOut = getConfig("标题消息淡出时间",20);
        puginPrefix = getConfig("聊天消息前缀","["+main.getName()+"]:");
        pluginName = main.getName();
        System.out.println(main.getName()+"信息管理器加载完成..");
    }
    /**
     * 给玩家标题信息
     * */
    public static void putPlayerH1(Player player,String H1,String H2){
        player.sendTitle(H1,H2,fadeIn,stay,fadeOut);
    }
    /**
     * 给玩家显示物品栏上方信息
     * */
    public static void putPlayerBelow(Player player,String Below){
        player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(Below));
    }
    /**
     * 给玩家发送聊天消息
     * */
    public static void putPlayerChat(Player player,String Below){
        player.spigot().sendMessage(ChatMessageType.CHAT, TextComponent.fromLegacyText(puginPrefix+Below));
    }
    public static void putPlayerChat(CommandSender player, String Below){
        player.sendMessage(puginPrefix+Below);
    }
    /**
     * 抛出debug信息
     * */
    public static void putdebug(String inof){
        if(debug){
            System.out.println("["+pluginName+"][debug]"+inof);
        }
    }
    /**
     * 抛出信息
     * */
    public static void putInfo(String inof){
        if(info){
            if(logger==null){
                System.out.println(inof);
                return;
            }
            logger.info(inof);
        }
    }
    /**
     * 抛出警告
     * */
    public static void putWarning(String inof){
        if(waring){
            if(logger==null){
                System.out.println(inof);
                return;
            }
            logger.warning(inof);
        }
    }
    /**
     * 抛出错误
     * */
    public static void putSevere(String inof){
        if(logger==null){
            System.out.println(inof);
            return;
        }
        logger.severe(inof);
    }
    /**
     * 简化配置的加载代码
     * */
    static boolean getConfig(String key,boolean Default){
        if(yml.contains(key)){
            return yml.getBoolean(key);
        }else {
            yml.set(key,Default);
            try {
                yml.save(File);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return Default;
        }
    }
    static double getConfig(String key,Double Default){
        if(yml.contains(key)){
            return yml.getDouble(key);
        }else {
            yml.set(key,Default);
            try {
                yml.save(File);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return Default;
        }
    }
    static int getConfig(String key,int Default){
        if(yml.contains(key)){
            return yml.getInt(key);
        }else {
            yml.set(key,Default);
            try {
                yml.save(File);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return Default;
        }
    }
    static String getConfig(String key,String Default){
        if(yml.contains(key)){
            return yml.getString(key);
        }else {
            yml.set(key,Default);
            try {
                yml.save(File);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return Default;
        }
    }
}
