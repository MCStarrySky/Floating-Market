package cn.jji8.floatingmarket.multi.store;

import cn.jji8.floatingmarket.Main;
import cn.jji8.floatingmarket.gui.Event;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 多商店支持
 * */
public class StoreList {
    File configFile = new File(Main.getMain().getDataFolder(),"StoreList.yml");
    YamlConfiguration config = YamlConfiguration.loadConfiguration(configFile);
    Map<String, Event> stopMap = new HashMap();
    List<String> list = config.getStringList("list");
    /**
     * 构造器，自动调用setStoreList
     * */
    public StoreList(){
        setStoreList();
    }
    /**
     * 加载一个商店列表
     * */
    public void setStoreList(){
        for(String s:list){
            stopMap.put(s,new Event(s));
        }
    }
    /**
     * 打开商店列表中的商店
     * @return true 已经给玩家打开 false 没有这个商店
     * */
    public boolean open(Player player, String stopName){
        Event Event = stopMap.get(stopName);
        if(Event==null){
            return false;
        }
        Event.dakai(player,1);
        return true;
    }
    /**
     * 保存商店列表数据
     * */
    public void baocun(){
        config.set("list",list);
        try {
            config.save(configFile);
        } catch (IOException e) {
            Main.getMain().getLogger().warning(configFile+"  保存失败");
            e.printStackTrace();
        }
    }
    /**
     * 添加商店
     * @return true 添加成功 false 已经有这个名字的商店了
     * */
    public boolean add(String name){
        if(list.contains(name)){
            return false;
        }
        list.add(name);
        stopMap.put(name,new Event(name));
        baocun();
        return true;
    }
    /**
     * 删除商店
     * @return true 删除成功 false 没有这个商店
     * */
    public boolean del(String name){
        if(list.remove(name)){
            stopMap = new HashMap<>();
            setStoreList();
            baocun();
        }
        return false;
    }
    /**
     * 获取对应名字的Event
     * @return null 没这个商店
     * */
    public Event getEvent(String name){
        return stopMap.get(name);
    }
}
