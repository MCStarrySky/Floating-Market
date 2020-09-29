package cn.jji8.floatingmarket.gui;

import cn.jji8.floatingmarket.Main;
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
    ArrayList<Case> biao;
    List<String> 商品列表;
    String 商品前缀 = "#";
    public Event(){
        Configcommodity = YamlConfiguration.loadConfiguration(new File(Main.getMain().getDataFolder(),"commodity.yml"));
    }
    public Event(String 商店名字){
        Configcommodity = YamlConfiguration.loadConfiguration(new File(Main.getMain().getDataFolder(),"stop/商店名字.yml"));
        商品前缀 = "stop"+商店名字;
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
     * 通过ItemStack添加商品
     * @param a
     * */
    public void add(ItemStack a){
        a = new ItemStack(a);
        a.setAmount(1);
        int i = 0;
        while (商品列表.contains(商品前缀+i)){
            i++;
        }
        商品列表.add(商品前缀+i);
        tianjia(商品前缀+i,a);
        shuaxin();
        baocun();
    }
     /**
      * 保存
      * */
     public void baocun(){
         Configcommodity.set("商品",商品列表);
         try {
             Configcommodity.save(new File(Main.getMain().getDataFolder(),"commodity.yml"));
         } catch (IOException e) {
             e.printStackTrace();
             Main.getMain().getLogger().warning("保存commodity.yml失败");
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
        商品 = new ItemStack(商品);
        商品.setAmount(1);
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
            tianjia(商品列表.get(sss),null);
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
    void tianjia(String 商品,ItemStack a){
        if(biao.size()==0){
            biao.add(new Case(页数));
            页数++;
        }
        while (true){
            if(!biaotianjia(商品,a)){
                biao.add(new Case(页数));
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
    boolean biaotianjia(String 添加商品,ItemStack a){
        for(Case 商品:biao){
            if(商品.add(添加商品,a)){
                return true;
            }
        }
        return false;
    }
}
