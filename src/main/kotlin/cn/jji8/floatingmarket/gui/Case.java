package cn.jji8.floatingmarket.gui;

import cn.jji8.floatingmarket.Main;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

/**
 * 箱子类,主要负责箱子界面的处理
 * */
public class Case{
    public int 页数;
    Goods 物品[] = new Goods[45];
    Inventory 箱子;
    Case(int 页数,String name){
        this.页数 = 页数;
        if(name==null){
            箱子 = org.bukkit.Bukkit.createInventory(null,6*9, Main.getMain().getConfig().getString("箱子标题").replaceAll("%页数%",String.valueOf(页数)));
        }else {
            箱子 = org.bukkit.Bukkit.createInventory(null,6*9,name+String.valueOf(页数));
        }
    }
    /**
     * 保存商品全部数据，服务器关闭时调用
     * */
    public void baocun(){
        for(Goods i:物品){
            if(i!=null){
                i.baocun();
            }
        }
    }
    /**
     *
     * */
    /**
     * 搜索，用于搜索一件商品
     * 没有商品返回null
     * @param 商品 使用Material搜索商品
     * @return 返回对应的goods
     * */
    public Goods sousuo(Material 商品){
        return sousuo(new ItemStack(商品));
    }
    /**
     * 搜索，用于搜索一件商品
     * 没有商品返回null
     * @param 商品 使用ItemStack搜索商品
     * @return 返回对应的goods
     * */
    public Goods sousuo(ItemStack 商品){
        for(Goods goods:物品){
            if(goods!=null&商品!=null){
                if(商品.equals(goods.getshangping())){
                    return goods;
                }
            }else if(goods!=null&商品==null){
                if(goods.getshangping()==null){
                    return goods;
                }
            }
        }
        return null;
    }
    /**
     * 返回一个 物品 数组空位，没有返回-1
     * */
    int kongw(){
        for(int i=0;i<物品.length;i++){
            if(物品[i]==null){
                return i;
            }
        }
        return -1;
    }
    String 上一页按钮名字 = Main.getMain().getConfig().getString("上一页按钮名字");
    String 下一页按钮名字 = Main.getMain().getConfig().getString("下一页按钮名字");
    String 无商品名字 = Main.getMain().getConfig().getString("无商品名字");
    String 上一页物品按钮 = Main.getMain().getConfig().getString("上一页物品按钮");
    String 下一页物品按钮 = Main.getMain().getConfig().getString("下一页物品按钮");
    String 无商品显示物品 = Main.getMain().getConfig().getString("无商品显示物品");
    /**
     * 用于加载或刷新商店页面
     * */
    public void shuaxin(){
        ItemStack ItemStack;
        try {
            ItemStack = new ItemStack(Material.getMaterial(无商品显示物品));
        }catch (Throwable a){
            Main.getMain().getLogger().warning("config.yml文件中“无商品显示物品”错误，请检查配置文件");
            return;
        }
        ItemMeta ItemMeta = ItemStack.getItemMeta();
        ItemMeta.setDisplayName(无商品名字);
        ItemStack.setItemMeta(ItemMeta);
        for(int i=0;i<54;i++){
            箱子.setItem(i,ItemStack);
        }
        for(int i=0;i<物品.length;i++){
            if(物品[i]!=null){
                箱子.setItem(i,物品[i].getxianshiwupin());
            }
        }
        //上一页物品
        try {
            ItemStack = new ItemStack(Material.getMaterial(上一页物品按钮));
        }catch (Throwable a){
            Main.getMain().getLogger().warning("config.yml文件中“上一页物品按钮”错误，请检查配置文件");
            return;
        }
        ItemMeta = ItemStack.getItemMeta();
        ItemMeta.setDisplayName(上一页按钮名字);
        ItemStack.setItemMeta(ItemMeta);
        箱子.setItem(45,ItemStack);
        //下一页物品
        try {
            ItemStack = new ItemStack(Material.getMaterial(下一页物品按钮));
        }catch (Throwable a){
            Main.getMain().getLogger().warning("config.yml文件中“下一页物品按钮”错误，请检查配置文件");
            return;
        }
        ItemMeta = ItemStack.getItemMeta();
        ItemMeta.setDisplayName(下一页按钮名字);
        ItemStack.setItemMeta(ItemMeta);
        箱子.setItem(53,ItemStack);
    }
    /**
     * 玩家点击格子时触发
     * @param a 代表玩家点击事件
     * */
    public void dianji(InventoryClickEvent a){
        //判断点击的是不是gui
        if(!箱子.equals(a.getClickedInventory())){
            return;
        }
        a.setCancelled(true);
        int 格子 = a.getRawSlot();
        //判断点击的是不是需要的格子
        if(格子>=45|格子<0){
            return;
        }
        //判断点击的格子是不是没有物品
        if(物品[格子]==null){
            return;
        }
        if(ClickType.SHIFT_LEFT.equals(a.getClick())){//Shift+鼠标左键. 购买一组
            物品[格子].goumaiyizu((Player) a.getWhoClicked());
        }else if(ClickType.SHIFT_RIGHT.equals(a.getClick())){//Shift+鼠标右键. 出售一组
            物品[格子].chushouyizu((Player) a.getWhoClicked());
        }else if(ClickType.LEFT.equals(a.getClick())){//鼠标左键. 购买一
            物品[格子].goumai((Player) a.getWhoClicked(),1);
        }else if(ClickType.RIGHT.equals(a.getClick())){//鼠标右键. 出售一
            物品[格子].chushou((Player) a.getWhoClicked(),1);
        }
        箱子.setItem(格子,物品[格子].getxianshiwupin());
    }
    /**
     * 用于给玩家打开gui
     * @param a 需要打开gui的玩家
     * */
    public void dakai(Player a){
        a.openInventory(箱子);
    }
    /**
     * 用于添加特殊物品
     * true成功 fales满了
     * ItemStack可以null
     * @param a
     * @return
     * */
    public boolean add(GoodSpecial a){
        for(int i=0;i<this.物品.length;i++){
            if(this.物品[i]==null){
                this.物品[i]=a;
                return true;
            }
        }
        return false;
    }
    /**
     * 添加一个goods箱子中
     * true成功 fales满了
     * */
    public boolean add(Goods 商品){
        for(int i=0;i<this.物品.length;i++){
            if(this.物品[i]==null){
                this.物品[i]=商品;
                return true;
            }
        }
        return false;
    }
}
