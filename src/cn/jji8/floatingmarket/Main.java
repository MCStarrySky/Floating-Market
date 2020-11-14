package cn.jji8.floatingmarket;

import cn.jji8.floatingmarket.account.Function;
import cn.jji8.floatingmarket.logger.Language;
import cn.jji8.floatingmarket.logger.Logger;
import cn.jji8.floatingmarket.money.Money;
import cn.jji8.floatingmarket.money.ServerMoney;
import cn.jji8.floatingmarket.command.Completion;
import cn.jji8.floatingmarket.command.Implement;
import cn.jji8.floatingmarket.gui.Event;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.*;
import java.util.logging.Level;

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
        Logger.load(this);
        Language.load(this);
        Logger.putInfo("开始加载...");
        Money.setupEconomy();//加载经济
        saveDefaultConfig();
        saveResource("function.js",false);
        saveResource("language.yml",false);
        Bukkit.getPluginCommand("Floatingmarket").setExecutor(new Implement());
        Bukkit.getPluginCommand("Floatingmarket").setTabCompleter(new Completion());
        Bukkit.getPluginManager().registerEvents(new EventListEners(),this);
        function = new Function(new File(getDataFolder(),"function.js"));
        EventListEners = new EventListEners();
        event.jiazai();
        Logger.putInfo("加载完成。");
    }
    @Override
    public void onDisable(){
        Logger.putInfo("正在保存数据..");
        servermoney.baocun();
        event.baocunshuju();
        Logger.putInfo("保存完毕。");
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
        Language.load(this);
        servermoney = new ServerMoney();
        event.baocunshuju();
        event = new Event();
        event.jiazai();
    }
    /**
     * 用于释放配置文件的方法
     * */
    public void saveResource(String resourcePath, boolean replace) {
        if (resourcePath != null && !resourcePath.equals("")) {
            resourcePath = resourcePath.replace('\\', '/');
            InputStream in = this.getResource(resourcePath);
            if (in == null) {
                throw new IllegalArgumentException("The embedded resource '" + resourcePath + "' cannot be found in " + super.getFile());
            } else {
                File outFile = new File(super.getDataFolder(), resourcePath);
                int lastIndex = resourcePath.lastIndexOf(47);
                File outDir = new File(super.getDataFolder(), resourcePath.substring(0, lastIndex >= 0 ? lastIndex : 0));
                if (!outDir.exists()) {
                    outDir.mkdirs();
                }

                try {
                    if (outFile.exists() && !replace) {
                        //super.getLogger().log(Level.WARNING, "Could not save " + outFile.getName() + " to " + outFile + " because " + outFile.getName() + " already exists.");
                    } else {
                        OutputStream out = new FileOutputStream(outFile);
                        byte[] buf = new byte[1024];

                        int len;
                        while((len = in.read(buf)) > 0) {
                            out.write(buf, 0, len);
                        }

                        out.close();
                        in.close();
                    }
                } catch (IOException var10) {
                    super.getLogger().log(Level.SEVERE, "Could not save " + outFile.getName() + " to " + outFile, var10);
                }

            }
        } else {
            throw new IllegalArgumentException("ResourcePath cannot be null or empty");
        }
    }
}
