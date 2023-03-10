package cn.jji8.floatingmarket.gui;

import cn.jji8.floatingmarket.Main;
import cn.jji8.floatingmarket.logger.Logger;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 事件,主要负责事件的处理
 * */
public class Event {
    public FileConfiguration Configcommodity;//配置文件
    public File 配置路径;
    public File 数据路径;
    ArrayList<Case> biao;
    List<String> 商品列表;
    String 商品前缀 = "#";
    String name = null;
    public Event(){
        数据路径 = new File(Main.getMain().getDataFolder(),"special");
        Configcommodity = YamlConfiguration.loadConfiguration(配置路径 = new File(Main.getMain().getDataFolder(),"commodity.yml"));
    }
    public Event(String name){
        数据路径 = new File(Main.getMain().getDataFolder(),"stop/"+name);
        Configcommodity = YamlConfiguration.loadConfiguration(配置路径 = new File(Main.getMain().getDataFolder(),"stop/"+name+".yml"));
        商品前缀 = "stop"+name;
        this.name = name;
    }
    public boolean delete(String 商品){
        return 商品列表.remove(商品);
    }
    /**
     * get方法
     * @return
     * @param
     * */
    public List<String> get商品列表(){
        return 商品列表;
    }
    /**
     * 保存商品全部数据，在服务器关闭时调用
     * */
    public void baocunshuju(){
        for(Case i:biao){
            i.baocun();
        }
    }
    /**
     * 通过ItemStack添加商品
     * a=null 会添加“空”
     * @param a
     * */
    public void add(ItemStack a){
        if(a==null){
            int i = 0;
            while (商品列表.contains("空"+i)){
                i++;
            }
            商品列表.add("空"+i);
            NullGood NullGood = new NullGood(数据路径,"空"+i);
            NullGood.jiazai();
            tianjia(NullGood);
            shuaxin();
            baocun();
            return;
        }
        a = new ItemStack(a);
        a.setAmount(1);
        int i = 0;
        while (商品列表.contains(商品前缀+i)){
            i++;
        }
        商品列表.add(商品前缀+i);
        GoodSpecial GoodSpecial = new GoodSpecial(数据路径,商品前缀+i);
        GoodSpecial.物品 = a;
        tianjia(GoodSpecial);
        shuaxin();
        baocun();
    }
     /**
      * 保存
      * */
     public void baocun(){
         Configcommodity.set("商品",商品列表);
         try {
             Configcommodity.save(配置路径);
         } catch (IOException e) {
             e.printStackTrace();
             Logger.putWarning("保存commodity.yml失败");
         }
     }
     /**
      * 搜索一个商品,没有返回null
      * @param 商品
      * @return
      * */
     public Goods shousuo(Material 商品){
         for(Case a:biao){
             Goods goods = a.sousuo(商品);
             if(goods!=null){
                 return goods;
             }
         }
         return null;
     }
    /**
     * 搜索一个商品,没有返回null
     * */
    public Goods shousuo(ItemStack 商品){
        if(商品!=null){
            商品 = new ItemStack(商品);
            商品.setAmount(1);
        }
        for(Case a:biao){
            Goods goods = a.sousuo(商品);
            if(goods!=null){
                return goods;
            }
        }
        return null;
    }
    /**
     * 用于刷新全部物品
     * */
    public void shuaxin(){
        for(Case a:biao){
            a.shuaxin();
        }
    }
    /**
     * 玩家点击时被调用
     * */
    public void dianji(InventoryClickEvent a){
        int 点击页数 = ifdianjiyemian(a.getClickedInventory());
        if(点击页数==-1){
            return;
        }
        if(a.getRawSlot()==45){
            dakai((Player) a.getWhoClicked(),点击页数);
        }else if(a.getRawSlot()==53){
            dakai((Player) a.getWhoClicked(), 点击页数 + 2);
        }
        biao.get(点击页数).dianji(a);
    }
    /**
     * 判断玩家点击的哪一页
     * 没有点击bug返回-1
     * */
    public int ifdianjiyemian(Inventory 点击界面){
        for(int i=0;i<biao.size();i++){
            if(biao.get(i).箱子.equals(点击界面)){
                return i;
            }
        }
        return -1;
    }
    /**
     * 用于给玩家打开指定gui
     * */
    String 没有页数消息 = Main.getMain().getConfig().getString("没有页数消息");
    public void dakai(Player 玩家,int 页数){
        if(biao.size()<页数|页数<1){
            玩家.sendMessage(没有页数消息.replaceAll("%页数%", Integer.toString(页数)));
            if(页数==1){
                Logger.putPlayerChat(玩家,"没有第一页是因为没有添加任何商品哦，快点使用add命令添加商品吧！");
            }
            return;
        }
        页数--;
        biao.get(页数).dakai(玩家);
    }
    /**
     * 加载方法，用于加载全部商品
     * */
    public void jiazai(){
        biao = new ArrayList<Case>();
        商品列表 = Configcommodity.getStringList("商品");
        for(int sss = 0;sss<商品列表.size();sss++){
            String s = 商品列表.get(sss);
            if("空".equals(s.substring(0,1))) {
                NullGood NullGood = new NullGood(数据路径,s);
                NullGood.jiazai();
                tianjia(NullGood);
                continue;
            }
            GoodSpecial GoodSpecial = new GoodSpecial(数据路径,s);
            GoodSpecial.jiazai();
            tianjia(GoodSpecial);
        }
        for(Case i:biao){
            i.shuaxin();
        }
        baocun();
    }
     int 页数 = 1;
    /**
     * 用于添加商品
     * ItemStack可以null
     * */
    void tianjia(GoodSpecial a){
        if(biao.size()==0){
            biao.add(new Case(页数,name));
            页数++;
        }
        while (true){
            if(!biaotianjia(a)){
                biao.add(new Case(页数,name));
                页数++;
            }else {
                return;
            }
        }
    }

    /**
     * 向现有case中添加商品
     * 添加成功返回true，已满返回false
     * ItemStack可以null
     * */
    boolean biaotianjia(GoodSpecial a){
        for(Case 商品:biao){
            if(商品.add(a)){
                return true;
            }
        }
        return false;
    }
    /**
     * 用于添加Goods
     * */
    public void tianjia(Goods 商品){
        if(biao.size()==0){
            biao.add(new Case(页数,name));
            页数++;
        }
        while (true){
            if(!biaotianjia(商品)){
                biao.add(new Case(页数,name));
                页数++;
            }else {
                return;
            }
        }
    }
    /**
     * 向现有case中添加Goods
     * 添加成功返回true，已满返回false
     * */
    boolean biaotianjia(Goods 添加商品){
        for(Case 商品:biao){
            if(商品.add(添加商品)){
                return true;
            }
        }
        return false;
    }
}
