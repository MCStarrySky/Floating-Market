package cn.jji8.floatingmarket;

import cn.jji8.floatingmarket.account.Function;
import cn.jji8.floatingmarket.money.Money;
import cn.jji8.floatingmarket.money.ServerMoney;
import cn.jji8.floatingmarket.command.Completion;
import cn.jji8.floatingmarket.command.Implement;
import cn.jji8.floatingmarket.gui.Event;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

/**
 * 一个主类
 * */
public class Main extends JavaPlugin {
    static Main main;
    public Event event;
    public EventListEners EventListEners;
    Metrics Metrics;
    ServerMoney servermoney;
    Function function;
    public Function getFunction() {
        return function;
    }

    /**
     * 插件启动时会被调用
     * */
    @Override
    public void onEnable() {
        Metrics = new Metrics(this,8676);
        main = this;
        event = new Event();
        servermoney = new ServerMoney();
        getLogger().info("开始加载...");
        Money.setupEconomy();//加载经济
        saveDefaultConfig();
        saveResource("function.js",false);
        Bukkit.getPluginCommand("Floatingmarket").setExecutor(new Implement());
        Bukkit.getPluginCommand("Floatingmarket").setTabCompleter(new Completion());
        Bukkit.getPluginManager().registerEvents(new EventListEners(),this);
        function = new Function(new File(getDataFolder(),"function.js"));
        EventListEners = new EventListEners();
        event.jiazai();
        getLogger().info("加载完成。");
    }
    @Override
    public void onDisable(){
        getLogger().info("正在保存数据..");
        servermoney.baocun();
        event.baocunshuju();
        getLogger().info("保存完毕。");
    }

    public ServerMoney getServermoney() {
        return servermoney;
    }
    /**
     * 获取插件main
     * */
    public static Main getMain() {
        return main;
    }
    /**
     * 重新加载全部插件配置
     * */
    public void reload(){
        servermoney.baocun();
        function = new Function(new File(getDataFolder(),"function.js"));
        Money.setupEconomy();
        servermoney = new ServerMoney();
        event.baocunshuju();
        event = new Event();
        event.jiazai();
    }
}
