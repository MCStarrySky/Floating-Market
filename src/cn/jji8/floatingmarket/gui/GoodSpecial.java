package cn.jji8.floatingmarket.gui;

import cn.jji8.floatingmarket.account.Variable;
import cn.jji8.floatingmarket.Main;
import cn.jji8.floatingmarket.money.Money;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import javax.annotation.Nonnull;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 负责特殊物品处理
 * */
public class GoodSpecial implements Goods {
    Case 箱子 = null;

    /**
     * 保存方法,用于保存数据
     */
    public void baocunyibu() {
        //完全异步保存
        Thread T = new Thread(){
            @Override
            public void run() {
                baocun();
            }
        };
        T.setName("数据保存线程->"+文件);
        T.start();
    }
    /**
     * 保存方法,用于保存数据
     */
    @Override
    public void baocun(){
        取消自动保存 = true;
        wenjian.set("购买数量",购买数量);
        wenjian.set("单独最高价格",单独最高价格);
        wenjian.set("单独最低价格",单独最低价格);
        wenjian.set("物品",物品);
        wenjian.set("库存公式",库存公式);
        try {
            wenjian.save(文件);
        } catch (Throwable e) {
            e.printStackTrace();
            Main.getMain().getLogger().warning("数据文件保存失败->"+文件);
        }
    }


    /**
     * 删除时调用的方法
     */
    @Override
    public void delete() {
        文件.delete();
    }

    /**
     * 加载方法,用于加载数据
     * false没有相关数据使用默认值 true加载成功
     */
    String 文件名字;
    File 文件;
    YamlConfiguration wenjian;
    @Override
    public void jiazai() {
        文件 = new File(Main.getMain().getDataFolder(),"special/"+getname());
        wenjian = YamlConfiguration.loadConfiguration(文件);
        if(wenjian.contains("购买数量")){
            购买数量 = wenjian.getLong("购买数量");
        }else {
            购买数量 = 0;
        }
        if(wenjian.contains("单独最高价格")){
            单独最高价格 = wenjian.getDouble("单独最高价格");
        }
        if(wenjian.contains("单独最低价格")){
            单独最低价格 = wenjian.getDouble("单独最低价格");
        }
        if(wenjian.contains("物品")){
            物品 = wenjian.getItemStack("物品");
        }
        if(wenjian.contains("库存公式")){
            库存公式 = wenjian.getString("库存公式");
        }
        baocun();
    }
    /**
     * 用于获取原商品
     */
    @Override
    public ItemStack getshangping() {
        物品.setAmount(1);
        return 物品;
    }
    /**
     * 获取物品的名字
     */
    @Override
    public String getname() {
        return 文件名字;
    }

    /**
     *构造器，需要一个Case，在刷新物品的时候会调用Case的shuaxin方法
     * 需要可以继承Case重写shuaxin即可。
     * */
    public GoodSpecial(@Nonnull Case Case,String 文件名字,ItemStack a){
        箱子 = Case;
        this.文件名字 = 文件名字;
        jiazai();
        if(a==null){
            return;
        }
        物品=a;
        baocun();
    }
    long 购买数量 = 0;
    double 涨跌幅度 = Main.getMain().getConfig().getDouble("涨跌价格");
    double 涨跌指数 = Main.getMain().getConfig().getDouble("涨跌指数");
    public double 单独最高价格 = -1;
    public double 单独最低价格 = -1;
    public ItemStack 物品;
    double 手续费 = Main.getMain().getConfig().getDouble("手续费");

    String 价格公式 = "Price";
    String 库存公式 = "stock";
    /**
     * 设置物品的公式名字
     * */
    public void setSetformula(String 价格公式,String 库存公式){
        if(价格公式!=null){
            this.价格公式 = 价格公式;
        }
        if(库存公式!=null){
            this.库存公式 = 库存公式;
        }
    }
    /**
     * 获取当前库存字符
     * */
    public String getstock(){
        Object jiage = Main.getMain().getFunction().function(库存公式,
                new Variable()
                        .setNumberOfItems(购买数量)
        );
        if(jiage==null){
            return "公式错误";
        }
        return jiage.toString();
    }
    /**
     * 获取当前价格
     * */
    public double getPrice(){
        double jiage = Main.getMain().getFunction().Doublefunction(价格公式,
                new Variable()
                        .setNumberOfItems(购买数量)
        );
        if(单独最高价格>0){
            if(jiage>单独最高价格){
                return 单独最高价格;
            }
        }
        if(单独最低价格>0){
            if(jiage>=0&&jiage<单独最低价格){
                return 单独最低价格;
            }
        }
        return jiage;
    }
    /**
     * 获取变量map
     * */
    public Map getMapvariable(){
        Map<String,Double> sss = new HashMap<>();
        sss.put("%购买数量%", (double) 购买数量);
        return sss;
    }
    /**
     * 调用此方法代表玩家出售此物品一组
     */
    public void chushouyizu(Player P){
        chushou(P,物品.getMaxStackSize());
    }

    String 没物品消息 = Main.getMain().getConfig().getString("没物品消息");
    String 操作失败消息 = Main.getMain().getConfig().getString("操作失败消息");
    String 手续费不足 = Main.getMain().getConfig().getString("手续费不足");
    String 扣除手续费 = Main.getMain().getConfig().getString("扣除手续费");
    /**
     * 调用此方法代表玩家出售此物品
     */
    public void chushou(Player P, int 数量) {
        if(!kouwupin(P,数量,物品)){
            P.sendMessage(没物品消息);
            return;
        }
        if(手续费>0){
            if(!Money.kouqian(P,手续费,false)){
                P.sendMessage(手续费不足.replaceAll("%钱%",Double.toString(手续费)));
                return;
            }else {
                P.sendMessage(扣除手续费.replaceAll("%钱%",Double.toString(手续费)));
            }
        }
        double 钱 = 0;
        int 数量a = 数量;
        for(int i = 0;i<数量;i++){
            购买数量--;
            double qian = getPrice();
            if(qian>=0){
                钱 += qian;
            }else {
                购买数量++;
                数量a--;
                P.getInventory().addItem(物品);
            }
        }
        if(钱==0){
            return;
        }
        if(!Money.jiaqian(P,钱)){
            for(int i = 0;i<数量a;i++){
                购买数量++;
                P.getInventory().addItem(物品);
            }
            return;
        }
        suanxinxianshi();
        baocun(10);
    }
    long 执行时间 = -1;
    public boolean 取消自动保存 = false;
    /**
     * 保存数据，但不会频繁重复保存
     * 在指定时间多次调用此方法，前面的调用无效
     * 时间/秒；
     * */
    public void baocun(int 时间秒){
        取消自动保存 = false;
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
                        if(取消自动保存){
                            return;
                        }
                        if(System.currentTimeMillis()>执行时间){
                            baocun();
                            执行时间 = -1;
                            return;
                        }
                    }
                }
            };
            执行时间 = System.currentTimeMillis()+时间秒*1000;
            T.start();
        }else {
            执行时间 = System.currentTimeMillis()+时间秒*1000;
        }
    }
    /**
     * 立即保存
     * */
    String 错误物品 = Main.getMain().getConfig().getString("错误物品");
    List<String> 价格显示 = Main.getMain().getConfig().getStringList("价格显示");
    /**
     * 获取用于显示的物品堆
     */
    public ItemStack getxianshiwupin() {
        if(显示物品==null){
            shuaxinxianshiwupin();
        }
        return 显示物品;
    }
    ItemStack 显示物品 = null;
    /**
     * 用于刷新显示物品
     * 直接修改物品属性，所有的这个物品都会被改变
     * */
    public ItemStack shuaxinxianshiwupin(){
        boolean 错误 = false;
        if(显示物品==null){
            if(物品==null){
                try {
                    显示物品 = new ItemStack(Material.getMaterial(错误物品));
                }catch (Throwable a){
                    Main.getMain().getLogger().warning("config.yml文件中“错误物品”错误，请检查配置文件");
                    return new ItemStack(Material.BEDROCK);
                }
                错误 = true;
            }else {
                物品.setAmount(1);
                显示物品 = new ItemStack(物品);
            }
        }
        String 价格字符舍 = Money.XianShiZiFu(getPrice());
        ItemMeta ItemMeta = 显示物品.getItemMeta();
        if(错误){
            ItemMeta.setDisplayName("§7§l此物品配置错误");
        }
        ItemMeta ItemMeta1 = 物品.getItemMeta();
        List<String> ArrayList;
        if(ItemMeta1==null){
            ArrayList = new ArrayList();
        }else {
            ArrayList = ItemMeta1.getLore();// new ArrayList();
        }
        if(ArrayList==null){
            ArrayList = new ArrayList();
        }
        String 服务器账户余额字符舍 = Money.XianShiZiFu(Main.getMain().getServermoney().getmoney());
        for(String S:价格显示){
            S = S.replaceAll("%价格%",价格字符舍)
                    .replaceAll("%购买数量%",getstock())
                    .replaceAll("%服务器账户余额%",服务器账户余额字符舍);
            ArrayList.add(S);
        }
        ItemMeta.setLore(ArrayList);
        显示物品.setItemMeta(ItemMeta);
        return 显示物品;
    }
    /**
     * 给玩家扣除指定数量得指定物品
     * */
    boolean kouwupin(Player P,int 数量,Material 物品){
        if(物品==null){
            P.sendMessage("执行此命令出错");
        }
        return kouwupin(P,数量,new ItemStack(物品));
    }
    /**
     * 给玩家扣除指定数量得指定物品
     * */
    boolean kouwupin(Player P,int 数量,ItemStack 物品){
        int 物品数量 = 0;
        ItemStack[] 全部物品 = P.getInventory().getContents();
        for (ItemStack a:全部物品){
            if(a!=null){
                if(a.isSimilar(物品)){
                    物品数量 += a.getAmount();
                    a.setAmount(0);
                }
            }
        }
        if(物品数量<数量){
            for(int i = 0;i<物品数量;i++){
                P.getInventory().addItem(物品);
            }
            return false;
        }
        for(int i = 0;i<物品数量-数量;i++){
            P.getInventory().addItem(物品);
        }
        return true;
    }

    /**
     * 调价
     * 用于调整物品价格
     * */
    /**public void tiaojia(){
     while (购买数量>涨跌指数){
     if(单独最高价格>0){
     最高价格 = 单独最高价格;
     }
     if(单独最低价格>0){
     最低价格 = 单独最低价格;
     }
     价格 += 涨跌幅度;
     购买数量-=涨跌指数;
     if(价格>=最高价格){
     购买数量-=涨跌指数;
     价格=最高价格;
     break;
     }
     }
     while(购买数量<-涨跌指数){
     价格 -= 涨跌幅度;
     购买数量+=涨跌指数;
     if(价格<=最低价格){
     购买数量-=涨跌指数;
     价格=最低价格;
     break;
     }
     }
     }*/

    /**
     * 用于获取物品价格
     */
    public double getjiage() {
        return getPrice();
    }

    /**
     * 用于设置物品库存
     */
    public void setjiage(int 价格) {
        this.购买数量 = 价格;
    }

    /**
     * 用于设置物品最高价格
     *
     * @param 价格
     */
    public void setgaojiage(double 价格) {
        this.单独最高价格 = 价格;
    }

    /**
     * 用于设置物品最低价格
     *
     * @param 价格
     */
    public void setdijiage(double 价格) {
        this.单独最低价格 = 价格;
    }
    /**
     * 调价刷新
     * */
    public void suanxinxianshi(){
        shuaxinxianshiwupin();
        //刷新在每次点击自动刷新物品了，不会每次刷新全部物品了。
        //箱子.shuaxin();
    }
    /**
     * 调用此方法代表玩家购买一组物品
     * */
    public void goumaiyizu(Player P){
        goumai(P,物品.getMaxStackSize());
    }
    String 背包满消息 = Main.getMain().getConfig().getString("背包满消息");
    String 增加手续费 = Main.getMain().getConfig().getString("增加手续费");
    /**
     * 调用此方法代表玩家购买了此商品
     */
    public void goumai(Player P, int 数量q) {
        long 前购买数量 = 购买数量;
        double 钱数 = 0;
        int 数量 = 数量q;
        for(int i = 0;i<数量q;i++){
            购买数量++;
            double sss = getPrice();
            if(sss>=0){
                钱数 += sss;
            }else {
                数量--;
                购买数量--;
            }
        }
        double 总价格 = 0;
        if(手续费>0){
            P.sendMessage(增加手续费.replaceAll("%钱%",Double.toString(手续费)));
            总价格 = 钱数+手续费;
        }else {
            总价格 = 钱数;
        }
        if(总价格==0){
            return;
        }
        if(!Money.kouqian(P,总价格)){
            购买数量 = 前购买数量;
            return;
        }
        int 退回数量 = 0;
        for(;数量>0;数量--){
            HashMap HashMap = P.getInventory().addItem(物品);
            for(Object a:HashMap.values()){
                if(a instanceof ItemStack){
                    退回数量++;
                }
            }
        }
        if(退回数量!=0){
            double 钱 = 0;
            for(int i = 0;i<退回数量;i++){
                购买数量--;
                钱 += getPrice();
            }
            Money.jiaqian(P,钱,true,false);
            P.sendMessage(背包满消息);
        }
        suanxinxianshi();
        baocun(10);
    }
}
