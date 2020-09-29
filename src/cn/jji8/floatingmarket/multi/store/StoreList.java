package cn.jji8.floatingmarket.multi.store;

import cn.jji8.floatingmarket.Main;
import cn.jji8.floatingmarket.gui.Event;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 多商店支持
 * */
public class StoreList {
    YamlConfiguration config = YamlConfiguration.loadConfiguration(new File(Main.getMain().getDataFolder(),"StoreList.yml"));
    Map<String, Event> stopList = new HashMap();
    /**
     * 加载一个商店列表
     * */
    public StoreList(){
        List<String> list = config.getStringList("list");
        for(String s:list){
            stopList.put(s,new Event(s));
        }
    }

}
