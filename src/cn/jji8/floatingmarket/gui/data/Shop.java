package cn.jji8.floatingmarket.gui.data;

import cn.jji8.floatingmarket.logger.Logger;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;

/**
 *
 * */
public class Shop {
    File dataFile;
    YamlConfiguration yml;
    public final static String PlayerTimeKey="everytime",PlayerMaxMoneyKey="Maxmoney",allTimeKey="alleverytime",allMaxMoneyKey="allMaxMoney";
    /**
     * 构造一个Shop
     * 给定一个yml数据文件
     * */
    public Shop(File datafile){
        dataFile = datafile;
        yml = YamlConfiguration.loadConfiguration(datafile);
    }
    /**
     * 判断一个玩家是否可以继续出售此商店的物品
     * 如果可以出售将自动设置玩家已出售的钱数
     * */
    public boolean ifPlayerContinue(Player player,double money){
        Logger.putdebug(player+"判断玩家是否可以出售物品  交易金格："+money);
        if(ifPlayerSell(player,money)&ifAllSell(money,player)){
            setAllMoney(getAllMoney()+money);
            setMoney(player,getMoney(player)+money);
            Logger.putdebug(player+"可以出售物品");
            return true;
        }
        Logger.putdebug(player+"玩家不可以出售物品");
        player.sendMessage("已拒绝交易");
        return false;
    }
    /**
     * 判断这个玩家是否还可以向此商店出售物品
     * 如果可以出售将自动设置玩家已出售的钱数
     * */
    public boolean ifPlayerSell(Player player,double money){
        Logger.putdebug(player+"判断玩家是否超过交易限制");
        //判断单个玩家
        long playerMaxTime = yml.getLong(PlayerTimeKey);
        double playerMaxMoney = yml.getDouble(PlayerMaxMoneyKey);
        if(yml.contains(PlayerTimeKey)&yml.contains(PlayerMaxMoneyKey)) {
            if (getTime(player) + playerMaxTime < System.currentTimeMillis()) {
                setMoney(player, 0);
                setTime(player, System.currentTimeMillis());
            }
            double PlayerMoney = getMoney(player) + money;
            if (PlayerMoney > playerMaxMoney) {
                Logger.putdebug(player+"超过限制");
                player.sendMessage("你在本商店"+(getTime(player)+playerMaxTime-System.currentTimeMillis())/1000+"秒前，赚到的钱"+getMoney(player)+"+本次交易"+money+"超出了最大限制"+playerMaxMoney+"你还需要等"+(getTime(player)+playerMaxTime-System.currentTimeMillis())/1000+"秒才可继续出售");
                return false;
            }
            player.sendMessage("你在本商店"+(getTime(player)+playerMaxTime-System.currentTimeMillis())/1000+"秒前，最多还可以赚"+(playerMaxMoney-getMoney(player))+"元");
        }
        Logger.putdebug(player+"没有超过限制");
        return true;
    }
    /**
     * 判断全部玩家是否可以出售此商店的物品
     * 如果可以出售将自动设置玩家已出售的钱数
     * */
    public boolean ifAllSell(double money,Player player){
        //判断全局
        Logger.putdebug("判断判断全局是否超过交易限制");
        if(yml.contains(allTimeKey)&yml.contains(allMaxMoneyKey)){
            long allTime = yml.getLong(allTimeKey);
            double allMaxMoney = yml.getDouble(allMaxMoneyKey);
            if(getAllTime()+allTime<System.currentTimeMillis()){
                setAllMoney(0);
                setAllTime(System.currentTimeMillis());
            }
            double allmoney = getAllMoney()+money;
            if(allmoney > allMaxMoney){
                Logger.putdebug("全局超过限制");
                player.sendMessage("全部玩家在本商店"+(getAllTime()+allTime-System.currentTimeMillis())/1000+"秒前，赚到的钱"+getAllMoney()+"+本次交易"+money+"超出了最大限制"+allMaxMoney+"你还需要等"+(getAllTime()+allTime-System.currentTimeMillis())/1000+"秒才可继续出售");
                return false;
            }
            player.sendMessage("全部玩家在本商店"+(getAllTime()+allTime-System.currentTimeMillis())/1000+"秒前，最多还可以赚"+(allMaxMoney-getAllMoney())+"元");
        }
        Logger.putdebug("全局没有超过限制");
        return true;

    }
    /**
     * 设置玩家在指定时间内最大出售物品可获得的钱数
     * */
    public void setPlayerMaxMoneyForTime(long time,double money){
        yml.set(PlayerMaxMoneyKey,money);
        yml.set(PlayerTimeKey,time);
        saveAsynchronous();
    }
    /**
     * 设置所有玩家在指定时间内最大出售物品可获得的总钱数
     * */
    public void setAllMaxMoneyForTime(long time,double money){
        yml.set(allMaxMoneyKey,money);
        yml.set(allTimeKey,time);
        saveAsynchronous();
    }
    /**
     * 设置全部玩家上次出售的时间
     * */
    public void setAllTime(long money){
        yml.set("all.time",money);
        saveAsynchronous();
    }

    /**
     * 设置所有玩家已出售的钱数
     * */
    public void setAllMoney(double money){
        yml.set("all.money",money);
        saveAsynchronous();
    }
    /**
     * 设置玩家已经出售的钱数
     * */
    public void setMoney(Player player,double money){
        yml.set("Data."+player.getName()+".money",money);
        saveAsynchronous();
    }
    /**
     * 设置玩家上次出售物品的时间
     * */
    public void setTime(Player player,long time){
        yml.set("Data."+player.getName()+".time",time);
        saveAsynchronous();
    }


    /**
     * 获得全部玩家上次出售的时间
     * */
    public double getAllTime(){
        return yml.getLong("all.time");
    }
    /**
     * 获取所有玩家已经出售的钱数
     * */
    public double getAllMoney(){
        return yml.getDouble("all.money");
    }
    /**
     * 获得玩家上次出售此商店物品的时间
     * */
    public long getTime(Player player){
        return yml.getLong("Data."+player.getName()+".time");
    }
    /**
     * 获得玩家已经出售的钱数
     * */
    public double getMoney(Player player){
        return yml.getDouble("Data."+player.getName()+".money");
    }
    /**
     * 保存数据
     * */
    public void save(){
        try {
            yml.save(dataFile);
        } catch (IOException e) {
            e.printStackTrace();
            Logger.putWarning(dataFile+"    ------>文件保存失败，请检查磁盘空间，和文件权限。");
        }
    }
    /**
     * 一步保存数据
     * */
    static long 执行时间 = -1;
    /**
     * 保存数据，但不会频繁重复保存
     * 在指定时间多次调用此方法，前面的调用无效
     * 时间/秒；
     * */
    public void saveAsynchronous(){
        if(执行时间==-1){
            Thread T = new Thread(){
                @Override
                public void run() {
                    while (true){
                        try {
                            sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        if(System.currentTimeMillis()>执行时间){
                            save();
                            执行时间 = -1;
                            return;
                        }
                    }
                }
            };
            执行时间 = System.currentTimeMillis()+10*1000;
            T.start();
        }else {
            执行时间 = System.currentTimeMillis()+10*1000;
        }
    }
}
