package cn.jji8.floatingmarket.command;

import cn.jji8.floatingmarket.gui.Goods;
import cn.jji8.floatingmarket.Main;
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
            if("reload".equals(参数[0])){
                if(!commandSender.hasPermission("Floatingmarket.reload")){
                    commandSender.sendMessage("你没有执行此命令的权限");
                    return true;
                }
                commandSender.sendMessage("开始重新加载");
                reload();
                commandSender.sendMessage("重新加载完成");
                return true;
            }
        }
        //下方命令必须玩家操作
        if(!(commandSender instanceof Player)){
            commandSender.sendMessage("操作失败");
            return true;
        }
        Player Player = (Player) commandSender;
        if(参数.length==0){
            if(!commandSender.hasPermission("Floatingmarket.open")){
                commandSender.sendMessage("没有打开商店的权限，需要：Floatingmarket.open");
                return true;
            }
            Main.getMain().event.dakai(Player,1);
            return true;
        }
        if(参数.length>=1){
            if("help".equals(参数[0])){
                if(!commandSender.hasPermission("Floatingmarket.help")){
                    commandSender.sendMessage("你没有执行此命令的权限");
                    return true;
                }
                commandSender.sendMessage("§6-------------------------帮助-----------------------");
                commandSender.sendMessage("§e/Floatingmarket help");
                commandSender.sendMessage("§7//获取帮助");
                commandSender.sendMessage("§e/Floatingmarket add");
                commandSender.sendMessage("§7//添加物品，将手上物品添加到商店中");
                commandSender.sendMessage("§e/Floatingmarket set 库存数量");
                commandSender.sendMessage("§e/Floatingmarket set 库存数量 最低价格 最高价格 ");
                commandSender.sendMessage("§7//设置手上物品的库存数量");
                commandSender.sendMessage("§e/Floatingmarket delete");
                commandSender.sendMessage("§7//删除手上物品商品");
                commandSender.sendMessage("§e/Floatingmarket reload");
                commandSender.sendMessage("§7//重新加载插件配置文件");
                commandSender.sendMessage("§e/Floatingmarket exchange 数字 数字");
                commandSender.sendMessage("§7//交换两个商品的位置");
                commandSender.sendMessage("§e/Floatingmarket setservermoney 钱");
                commandSender.sendMessage("§7//设置服务器钱数");
                commandSender.sendMessage("§e/Floatingmarket setformula 价格公式");
                commandSender.sendMessage("§e/Floatingmarket setformula 价格公式 库存显示公式");
                commandSender.sendMessage("§7//设置物品使用的公式");
                commandSender.sendMessage("§6---------------------------------------------------");
                return true;
            }
            if("setformula".equals(参数[0])){
                if(!commandSender.hasPermission("Floatingmarket.setformula")){
                    commandSender.sendMessage("你没有执行此命令的权限");
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
                commandSender.sendMessage("setformula 价格公式");
                commandSender.sendMessage("setformula 价格公式 库存显示公式");
                return true;
            }
            if("add".equals(参数[0])){
                if(!commandSender.hasPermission("Floatingmarket.add")){
                    commandSender.sendMessage("你没有执行此命令的权限");
                    return true;
                }
                addspecial(Player);
                return true;
            }
            if("setservermoney".equals(参数[0])){
                if(!commandSender.hasPermission("Floatingmarket.setservermoney")){
                    commandSender.sendMessage("你没有执行此命令的权限");
                    return true;
                }
                if(参数.length!=2){
                    commandSender.sendMessage("setservermoney 钱数");
                    return true;
                }
                try {
                    setServermoney(Double.valueOf(参数[1]));
                    commandSender.sendMessage("设置成功");
                }catch (NumberFormatException a){
                    commandSender.sendMessage("你输入的数字不是一个有效数字");
                }
                return true;
            }
            if("set".equals(参数[0])){
                if(!commandSender.hasPermission("Floatingmarket.set")){
                    commandSender.sendMessage("你没有执行此命令的权限");
                    return true;
                }
                if(参数.length!=4&参数.length!=2){
                    commandSender.sendMessage("set 库存数量");
                    commandSender.sendMessage("set 库存数量 最低价格 最高价格");
                    return true;
                }
                if(参数.length==4){
                    try {
                        tianjia(Player,Integer.valueOf(参数[1]),Double.valueOf(参数[2]),Double.valueOf(参数[3]));
                    }catch (NumberFormatException a){
                        commandSender.sendMessage("你输入的数字不是一个有效数字");
                    }
                    return true;
                }
                if(参数.length==2){
                    try {
                        tianjia(Player,Integer.valueOf(参数[1]));
                    }catch (NumberFormatException a){
                        commandSender.sendMessage("你输入的数字不是一个有效数字");
                    }
                    return true;
                }

            }
            if("delete".equals(参数[0])){
                if(!commandSender.hasPermission("Floatingmarket.delete")){
                    commandSender.sendMessage("你没有执行此命令的权限");
                    return true;
                }
                delete(Player);
                return true;
            }
            if("exchange".equals(参数[0])){
                if(!commandSender.hasPermission("Floatingmarket.exchange")){
                    commandSender.sendMessage("你没有执行此命令的权限");
                    return true;
                }
                if(参数.length!=3){
                    commandSender.sendMessage("§e/Floatingmarket exchange 数字 数字");
                    return true;
                }
                try {
                    if(exchange(Integer.valueOf(参数[1]),Integer.valueOf(参数[2]))){
                        commandSender.sendMessage("交换成功");
                        return true;
                    }else {
                        commandSender.sendMessage("你输入的数字没有对应的商品");
                    }
                }catch (NumberFormatException a){
                    commandSender.sendMessage("你输入的数字不是一个有效数字");
                    return true;
                }
                return true;
            }
        }
        commandSender.sendMessage("命令参数错误");
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
            命令执行者.sendMessage("你不可以空手");
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
            命令执行者.sendMessage("本商品已经在买了，现在价格是："+价格字符舍);
            return;
        }
        Main.getMain().event.add(ItemStack);
        goods = Main.getMain().event.shousuo(ItemStack);
        if(goods!=null){
            goods.baocun();
        }
        命令执行者.sendMessage("添加成功");
    }
    /**
     * 修改方法，修改物品的价格
     * */
    public static void tianjia(Player Player, int 价格, double 最低价格, double 最高价格){
        ItemStack 物品堆 = Player.getInventory().getItemInMainHand();
        if(Material.AIR.equals(物品堆.getType())){
            Player.sendMessage("你不可以空手");
            return;
        }
        Goods goods = Main.getMain().event.shousuo(物品堆);
        if(goods==null){
            Player.sendMessage("此商品没有被上架");
            return;
        }
        goods.setjiage(价格);
        goods.setdijiage(最低价格);
        goods.setgaojiage(最高价格);
        Main.getMain().event.shuaxin();
        goods.baocun();
        Player.sendMessage("设置成功");
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
            Player.sendMessage("你不可以空手");
            return false;
        }
        Goods goods = Main.getMain().event.shousuo(物品堆);
        if(goods==null){
            Player.sendMessage("此商品没有被上架");
            return false;
        }
        if(Main.getMain().event.delete(goods.getname())){
            Player.sendMessage("删除成功");
            Main.getMain().event.baocun();
            for(Player P: Bukkit.getOnlinePlayers()){
                P.closeInventory();
            }
            Main.getMain().reload();
            goods.delete();
            return true;
        }else {
            Player.sendMessage("错误？");
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
            Player.sendMessage("你不可以空手");
            return;
        }
        Goods goods = Main.getMain().event.shousuo(物品堆);
        if(goods==null){
            Player.sendMessage("此商品没有被上架");
            return;
        }
        goods.setSetformula(价格公式,库存公式);
        Player.sendMessage("设置成功");
    }
}
