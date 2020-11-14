package cn.jji8.floatingmarket.command;

import cn.jji8.floatingmarket.gui.GoodSpecial;
import cn.jji8.floatingmarket.gui.Goods;
import cn.jji8.floatingmarket.Main;
import cn.jji8.floatingmarket.gui.NullGood;
import cn.jji8.floatingmarket.logger.Logger;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;

/**
 * 一个命令执行器,专用于执行命令
 * */
public class Implement implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] 参数) {
        if(参数.length==1){
            if(equalsOneIgnoreCase(参数[0],new String[]{"reload","重新加载"})){
                if(!commandSender.hasPermission("Floatingmarket.reload")){
                    commandSender.sendMessage("你没有执行此命令的权限,需要：Floatingmarket.reload");
                    return true;
                }
                Logger.putPlayerChat(commandSender,"开始重新加载");
                reload();
                Logger.putPlayerChat(commandSender,"重新加载完成");
                return true;
            }
        }
        //下方命令必须玩家操作
        if(!(commandSender instanceof Player)){
            Logger.putPlayerChat(commandSender,"操作失败");
            return true;
        }
        Player Player = (Player) commandSender;
        if(参数.length==0){
            if(!commandSender.hasPermission("Floatingmarket.open")){
                Logger.putPlayerChat(commandSender,"没有打开商店的权限，需要：Floatingmarket.open");
                return true;
            }
            Main.getMain().event.dakai(Player,1);
            return true;
        }
        if(参数.length>=1){
            if(equalsOneIgnoreCase(参数[0],new String[]{"help","帮助"})){
                if(!commandSender.hasPermission("Floatingmarket.help")){
                    Logger.putPlayerChat(commandSender,"你没有执行此命令的权限,需要：Floatingmarket.help");
                    return true;
                }
                Logger.putPlayerChat(commandSender,"§6-------------------------帮助-----------------------");
                Logger.putPlayerChat(commandSender,"§e/Floatingmarket help");
                Logger.putPlayerChat(commandSender,"§7//获取帮助");
                Logger.putPlayerChat(commandSender,"§e/Floatingmarket add");
                Logger.putPlayerChat(commandSender,"§7//添加物品，将手上物品添加到商店中");
                Logger.putPlayerChat(commandSender,"§e/Floatingmarket set 库存数量");
                Logger.putPlayerChat(commandSender,"§e/Floatingmarket set 库存数量 最低价格 最高价格 ");
                Logger.putPlayerChat(commandSender,"§7//设置手上物品的库存数量");
                Logger.putPlayerChat(commandSender,"§e/Floatingmarket delete");
                Logger.putPlayerChat(commandSender,"§7//删除手上物品商品");
                Logger.putPlayerChat(commandSender,"§e/Floatingmarket reload");
                Logger.putPlayerChat(commandSender,"§7//重新加载插件配置文件");
                Logger.putPlayerChat(commandSender,"§e/Floatingmarket exchange 数字 数字");
                Logger.putPlayerChat(commandSender,"§7//交换两个商品的位置");
                Logger.putPlayerChat(commandSender,"§e/Floatingmarket setservermoney 钱");
                Logger.putPlayerChat(commandSender,"§7//设置服务器钱数");
                Logger.putPlayerChat(commandSender,"§e/Floatingmarket setBuyOrSell 出售{true,false} 购买{true,false}");
                Logger.putPlayerChat(commandSender,"§7//设置物品是否允许出售或收购");
                Logger.putPlayerChat(commandSender,"§e/Floatingmarket setformula 价格公式");
                Logger.putPlayerChat(commandSender,"§e/Floatingmarket setformula 价格公式 库存显示公式");
                Logger.putPlayerChat(commandSender,"§7//设置物品使用的公式");
                Logger.putPlayerChat(commandSender,"§e/Floatingmarket setPlayerMaxMoneyForTime 时间（单位：秒） 最大钱数");
                Logger.putPlayerChat(commandSender,"§7//设置每个玩家最大单位时间获得钱数");
                Logger.putPlayerChat(commandSender,"§e/Floatingmarket setAllMaxMoneyForTime 时间（单位：秒） 最大钱数");
                Logger.putPlayerChat(commandSender,"§7//设置全服玩家最大单位时间获得钱数");
                Logger.putPlayerChat(commandSender,"§6---------------------------------------------------");
                return true;
            }
            if(equalsOneIgnoreCase(参数[0],new String[]{"setBuyOrSell","设置是否允许购买或出售"})){
                if(!commandSender.hasPermission("Floatingmarket.setBuyOrSell")){
                    Logger.putPlayerChat(commandSender,"你没有执行此命令的权限,需要：Floatingmarket.setBuyOrSell");
                    return true;
                }
                if(参数.length!=3){
                    Logger.putPlayerChat(commandSender,参数[0]+" 出售{true,false} 购买{true,false}");
                    return true;
                }
                if(("true".equalsIgnoreCase(参数[1])|"false".equalsIgnoreCase(参数[1]))
                        &("true".equalsIgnoreCase(参数[2])|"false".equalsIgnoreCase(参数[2]))
                ){
                    setBuyOrSell(Player,"true".equalsIgnoreCase(参数[1]),"true".equalsIgnoreCase(参数[2]));
                }else {
                    Logger.putPlayerChat(commandSender,参数[0]+" 出售{true,false} 购买{true,false}");
                    Logger.putPlayerChat(commandSender,"§e{true(允许),false(不允许)}");
                }
                return true;
            }
            if(equalsOneIgnoreCase(参数[0],new String[]{"setPlayerMaxMoneyForTime","设置每个玩家最大单位时间获得钱数"})){
                if(!commandSender.hasPermission("Floatingmarket.setPlayerMaxMoneyForTime")){
                    Logger.putPlayerChat(commandSender,"你没有执行此命令的权限,需要：Floatingmarket.setPlayerMaxMoneyForTime");
                    return true;
                }
                if(参数.length<3){
                    Logger.putPlayerChat(commandSender,参数[0]+" 时间（单位：秒） 最大钱数");
                    return true;
                }
                if(!(commandSender instanceof Player)){
                    Logger.putPlayerChat(commandSender,"只可以玩家执行此命令");
                    return true;
                }
                Goods goods = Main.getMain().event.shousuo(((Player)commandSender).getInventory().getItemInMainHand());
                if(goods==null){
                    Logger.putPlayerChat(commandSender,"你手中的物品还没有上架，无法设置此选项。");
                    return true;
                }
                if(!(goods instanceof GoodSpecial)){
                    Logger.putPlayerChat(commandSender,"你手上的没有拿物品哦，我怎么知道你要设置哪个商品呢？");
                    return true;
                }
                GoodSpecial goodSpecial = (GoodSpecial)goods;
                int time;
                double maxMoney;
                try{
                    time = Integer.valueOf(参数[1]);
                }catch(Throwable a){
                    Logger.putPlayerChat(commandSender,参数[1]+"不是一个有效数字。");
                    return true;
                }
                try{
                    maxMoney = Double.valueOf(参数[2]);
                }catch(Throwable a){
                    Logger.putPlayerChat(commandSender,参数[2]+"不是一个有效数字。");
                    return true;
                }
                goodSpecial.getShop().setPlayerMaxMoneyForTime(time*1000,maxMoney);
                Logger.putPlayerChat(commandSender,"设置成功");
                return true;
            }
            if(equalsOneIgnoreCase(参数[0],new String[]{"setAllMaxMoneyForTime","设置全服玩家最大单位时间获得钱数"})){
                if(!commandSender.hasPermission("Floatingmarket.setAllMaxMoneyForTime")){
                    Logger.putPlayerChat(commandSender,"你没有执行此命令的权限,需要：Floatingmarket.setAllMaxMoneyForTime");
                    return true;
                }
                if(参数.length<3){
                    Logger.putPlayerChat(commandSender,参数[0]+" 时间（单位：秒） 最大钱数");
                    return true;
                }
                if(!(commandSender instanceof Player)){
                    Logger.putPlayerChat(commandSender,"只可以玩家执行此命令");
                    return true;
                }
                Goods goods = Main.getMain().event.shousuo(((Player)commandSender).getInventory().getItemInMainHand());
                if(goods==null){
                    Logger.putPlayerChat(commandSender,"你手中的物品还没有上架，无法设置此选项。");
                    return true;
                }
                if(!(goods instanceof GoodSpecial)){
                    Logger.putPlayerChat(commandSender,"你手上的没有拿物品哦，我怎么知道你要设置哪个商品呢？");
                    return true;
                }
                GoodSpecial goodSpecial = (GoodSpecial)goods;
                int time;
                double maxMoney;
                try{
                    time = Integer.valueOf(参数[1]);
                }catch(Throwable a){
                    Logger.putPlayerChat(commandSender,参数[1]+"不是一个有效数字。");
                    return true;
                }
                try{
                    maxMoney = Double.valueOf(参数[2]);
                }catch(Throwable a){
                    Logger.putPlayerChat(commandSender,参数[2]+"不是一个有效数字。");
                    return true;
                }
                goodSpecial.getShop().setAllMaxMoneyForTime(time*1000,maxMoney);
                Logger.putPlayerChat(commandSender,"设置成功");
                return true;
            }
            if(equalsOneIgnoreCase(参数[0],new String[]{"setformula","设置公式"})){
                if(!commandSender.hasPermission("Floatingmarket.setformula")){
                    Logger.putPlayerChat(commandSender,"你没有执行此命令的权限,需要：Floatingmarket.setformula");
                    return true;
                }
                if(参数.length==2){
                    setSetformula(Player,参数[1],null);
                    return true;
                }
                if(参数.length==3){
                    setSetformula(Player,参数[1],参数[2]);
                    return true;
                }
                Logger.putPlayerChat(commandSender,参数[0]+" 价格公式");
                Logger.putPlayerChat(commandSender,参数[0]+" 价格公式 库存显示公式");
                return true;
            }
            if(equalsOneIgnoreCase(参数[0],new String[]{"add","添加"})){
                if(!commandSender.hasPermission("Floatingmarket.add")){
                    Logger.putPlayerChat(commandSender,"你没有执行此命令的权限,需要：Floatingmarket.add");
                    return true;
                }
                addspecial(Player);
                return true;
            }
            if(equalsOneIgnoreCase(参数[0],new String[]{"setservermoney","设置服务器余额"})){
                if(!commandSender.hasPermission("Floatingmarket.setservermoney")){
                    Logger.putPlayerChat(commandSender,"你没有执行此命令的权限,需要：Floatingmarket.setservermoney");
                    return true;
                }
                if(参数.length!=2){
                    Logger.putPlayerChat(commandSender,参数[0]+" 钱数");
                    return true;
                }
                try {
                    setServermoney(Double.valueOf(参数[1]));
                    Logger.putPlayerChat(commandSender,"设置成功");
                }catch (NumberFormatException a){
                    Logger.putPlayerChat(commandSender,"你输入的数字不是一个有效数字");
                }
                return true;
            }
            if(equalsOneIgnoreCase(参数[0],new String[]{"set","设置"})){
                if(!commandSender.hasPermission("Floatingmarket.set")){
                    Logger.putPlayerChat(commandSender,"你没有执行此命令的权限,需要：Floatingmarket.set");
                    return true;
                }
                if(参数.length!=4&参数.length!=2){
                    Logger.putPlayerChat(commandSender,参数[0]+" 库存数量");
                    Logger.putPlayerChat(commandSender,参数[0]+" 库存数量 最低价格 最高价格");
                    return true;
                }
                if(参数.length==4){
                    try {
                        tianjia(Player,Integer.valueOf(参数[1]),Double.valueOf(参数[2]),Double.valueOf(参数[3]));
                    }catch (NumberFormatException a){
                        Logger.putPlayerChat(commandSender,"你输入的数字不是一个有效数字");
                    }
                    return true;
                }
                if(参数.length==2){
                    try {
                        tianjia(Player,Integer.valueOf(参数[1]));
                    }catch (NumberFormatException a){
                        Logger.putPlayerChat(commandSender,"你输入的数字不是一个有效数字");
                    }
                    return true;
                }

            }
            if(equalsOneIgnoreCase(参数[0],new String[]{"delete","删除"})){
                if(!commandSender.hasPermission("Floatingmarket.delete")){
                    Logger.putPlayerChat(commandSender,"你没有执行此命令的权限,需要：Floatingmarket.delete");
                    return true;
                }
                delete(Player);
                return true;
            }
            if(equalsOneIgnoreCase(参数[0],new String[]{"exchange","交换"})){
                if(!commandSender.hasPermission("Floatingmarket.exchange")){
                    Logger.putPlayerChat(commandSender,"你没有执行此命令的权限,需要：Floatingmarket.exchange");
                    return true;
                }
                if(参数.length!=3){
                    Logger.putPlayerChat(commandSender,参数[0]+" 数字 数字");
                    return true;
                }
                try {
                    if(exchange(Integer.valueOf(参数[1]),Integer.valueOf(参数[2]))){
                        Logger.putPlayerChat(commandSender,"交换成功");
                        return true;
                    }else {
                        Logger.putPlayerChat(commandSender,"你输入的数字没有对应的商品");
                    }
                }catch (NumberFormatException a){
                    Logger.putPlayerChat(commandSender,"你输入的数字不是一个有效数字");
                    return true;
                }
                return true;
            }
        }
        Logger.putPlayerChat(commandSender,"命令参数错误");
        return true;
    }
    /**
     * 创新加载全部配置文件的方法
     * */
    private static void reload() {
        Main.getMain().reloadConfig();
        for(Player P: Bukkit.getOnlinePlayers()){
            P.closeInventory();
        }
        Main.getMain().reload();
    }

    /**
     * 交互方法，用于交换两个商品的位置
     * */
    public static boolean exchange(int 位置1,int 位置2){
        List<String> List = Main.getMain().event.get商品列表();
        if(位置1<0|位置2<0|位置1>=List.size()|位置2>=List.size()){
            return false;
        }
        String a = List.get(位置1);
        String b = List.get(位置2);
        List.set(位置1,b);
        List.set(位置2,a);
        Main.getMain().event.baocun();
        reload();
        return true;
    }
    /**
     * 添加方法
     * 添加玩家手上的物品
     * */
    public static void addspecial(Player 命令执行者){
        ItemStack ItemStack = 命令执行者.getInventory().getItemInMainHand();
        if(Material.AIR.equals(ItemStack.getType())){
            Logger.putPlayerChat(命令执行者,"你空手执行添加商品命令，为你添加空商品！");
            ItemStack=null;
            Main.getMain().event.add(ItemStack);
            Goods goods = Main.getMain().event.shousuo(ItemStack);
            if(goods!=null){
                goods.baocun();
            }
            Logger.putPlayerChat(命令执行者,"添加成功");
            return;
        }
        Goods goods = Main.getMain().event.shousuo(ItemStack);
        if(goods!=null){
            String 价格字符 =  String.valueOf(goods.getjiage());
            int 小数点位置 = 价格字符.indexOf(".");
            String 价格字符舍;
            if(价格字符.length()>小数点位置+5){
                价格字符舍 = 价格字符.substring(0,小数点位置+5);
            }else {
                价格字符舍 = 价格字符;
            }
            if(goods.getjiage()<=0.0001){
                价格字符舍 = "0.0001";
            }
            Logger.putPlayerChat(命令执行者,"本商品已经在买了，现在价格是："+价格字符舍);
            return;
        }
        Main.getMain().event.add(ItemStack);
        goods = Main.getMain().event.shousuo(ItemStack);
        if(goods!=null){
            goods.baocun();
        }
        Logger.putPlayerChat(命令执行者,"添加成功");
    }
    /**
     * 修改方法，修改物品的价格
     * */
    public static void tianjia(Player Player, int 价格, double 最低价格, double 最高价格){
        ItemStack 物品堆 = Player.getInventory().getItemInMainHand();
        if(Material.AIR.equals(物品堆.getType())){
            Logger.putPlayerChat(Player,"你不可以空手");
            return;
        }
        Goods goods = Main.getMain().event.shousuo(物品堆);
        if(goods==null){
            Logger.putPlayerChat(Player,"此商品没有被上架");
            return;
        }
        goods.setjiage(价格);
        goods.setdijiage(最低价格);
        goods.setgaojiage(最高价格);
        Main.getMain().event.shuaxin();
        goods.baocun();
        Logger.putPlayerChat(Player,"设置成功");
    }
    /**
     * 设置玩家手上物品是否允许出售或购买
     * */
    public static void setBuyOrSell(Player Player,boolean 出售, boolean 购买){
        ItemStack 物品堆 = Player.getInventory().getItemInMainHand();
        if(Material.AIR.equals(物品堆.getType())){
            Logger.putPlayerChat(Player,"你不可以空手");
            return;
        }
        Goods goods = Main.getMain().event.shousuo(物品堆);
        if(goods==null){
            Logger.putPlayerChat(Player,"此商品没有被上架");
            return;
        }
        goods.set售或收购(出售,购买);
        Logger.putPlayerChat(Player,"设置成功");
    }
    /**
     * 修改方法，修改物品的价格
     * */
    public static void tianjia(Player Player, int 价格){
        tianjia(Player,价格,-1,-1);
    }
    /**
     * 删除方法，用于删除商品
     * 删除成功返回true 失败返回false
     * */
    public static boolean delete(Player Player){
        ItemStack 物品堆 = Player.getInventory().getItemInMainHand();
        if(Material.AIR.equals(物品堆.getType())){
            Logger.putPlayerChat(Player,"删除空商品");
            物品堆=null;
        }
        Goods goods = Main.getMain().event.shousuo(物品堆);
        if(goods==null){
            Logger.putPlayerChat(Player,"此商品没有被上架");
            return false;
        }
        if(Main.getMain().event.delete(goods.getname())){
            Logger.putPlayerChat(Player,"删除成功");
            Main.getMain().event.baocun();
            for(Player P: Bukkit.getOnlinePlayers()){
                P.closeInventory();
            }
            Main.getMain().reload();
            goods.delete();
            return true;
        }else {
            Logger.putPlayerChat(Player,"错误？");
            return false;
        }

    }

    public void setServermoney(Double servermoney) {
        Main.getMain().getServermoney().set余额(servermoney);
        Main.getMain().event.shuaxin();
    }

    public void setSetformula(Player Player,String 价格公式,String 库存公式) {
        ItemStack 物品堆 = Player.getInventory().getItemInMainHand();
        if(Material.AIR.equals(物品堆.getType())){
            Logger.putPlayerChat(Player,"你不可以空手");
            return;
        }
        Goods goods = Main.getMain().event.shousuo(物品堆);
        if(goods==null){
            Logger.putPlayerChat(Player,"此商品没有被上架");
            return;
        }
        goods.setSetformula(价格公式,库存公式);
        Logger.putPlayerChat(Player,"设置成功");
    }




    /**
     * 比较器，只要字符与字符列表的其中一个相同就返回true
     * */
    static boolean equalsOneIgnoreCase(String s,String[] strings){
        for(String s1:strings){
            if(s.equalsIgnoreCase(s1)){
                return true;
            }
        }
        return false;
    }
}
