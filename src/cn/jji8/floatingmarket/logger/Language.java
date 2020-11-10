package cn.jji8.floatingmarket.logger;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 负责语言的类
 * */
public class Language {
    static File file;
    static YamlConfiguration yml;
    /**
     * 初始化语言，在main中调用
     * */
    public static void load(Plugin main){
        Logger.putInfo("语言管理器开始加载...");
        file = new File(main.getDataFolder(),"language.yml");
        yml = YamlConfiguration.loadConfiguration(file);
        Logger.putInfo("语言管理器加载完成。");
    }
    /**
     * 通过key获取玩家设置的语言，如果玩家没有设置则返回Default
     * */
    public String get(String key,String Default){
        if(yml.contains(key)){
            return yml.getString(key);
        }else {
            yml.set(key,Default);
            try {
                yml.save(file);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return Default;
        }
    }
    /**
     * 通过key获取玩家设置的语言，如果玩家没有设置则返回Default
     * 可将文字中的变量替换成map的V.toString。
     * */
    public String get(String key, String Default, Map<String,Object> map){
        String string;
        if(yml.contains(key)){
            string = yml.getString(key);
        }else {
            yml.set(key,Default);
            try {
                yml.save(file);
            } catch (IOException e) {
                e.printStackTrace();
            }
            string = Default;
        }
        Set<String> a = map.keySet();
        for(String s:a){
            string = string.replaceAll(s,map.get(s).toString());
        }
        return string;
    }
    /**
     * 通过key获取玩家设置的语言list，如果玩家没有设置则返回Default
     * */
    public List<String> get(String key,List<String> Default){
        if(yml.contains(key)){
            return yml.getStringList(key);
        }else {
            yml.set(key,Default);
            try {
                yml.save(file);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return Default;
        }
    }
    /**
     * 通过key获取玩家设置的语言，如果玩家没有设置则返回Default
     * 可将文字中的变量替换成map的V.toString。
     * */
    public List<String> get(String key,List<String> Default, Map<String,Object> map){
        List<String> string;
        if(yml.contains(key)){
            string = yml.getStringList(key);
        }else {
            yml.set(key,Default);
            try {
                yml.save(file);
            } catch (IOException e) {
                e.printStackTrace();
            }
            string = new ArrayList<String>(Default);
        }
        Set<String> a = map.keySet();
        for(int i=0;i<string.size();i++){
            for(String s:a){
                string.set(i,string.get(i).replaceAll(s,map.get(s).toString()));
            }
        }
        return string;
    }

}
