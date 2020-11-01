package cn.jji8.floatingmarket.gui;

import cn.jji8.floatingmarket.Main;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.io.File;

public class NullGood implements Goods {
    /**
     * 通过数据路径，和名字构造一个NullGood
     * */
    public NullGood(File file,String name){
        this.name = name;
        文件 = new File(file,name);
        wenjian = YamlConfiguration.loadConfiguration(文件);
    }
    String name;
    File 文件;
    YamlConfiguration wenjian;
    /**
     * 获取用于显示的物品堆
     */
    @Override
    public ItemStack getxianshiwupin() {
        if (物品==null){
            Main.getMain().getLogger().warning("config.yml文件中“空物品”错误，请检查配置文件");
        }
        return 物品;
    }

    /**
     * 调用此方法代表玩家购买了此商品
     *
     * @param P
     * @param 数量
     */
    @Override
    public void goumai(Player P, int 数量) {
    }

    /**
     * 调用此方法代表玩家出售此物品
     *
     * @param P
     * @param 数量
     */
    @Override
    public void chushou(Player P, int 数量) {
    }

    /**
     * 保存方法,用于保存数据
     */
    @Override
    public void baocun() {
        wenjian.set("物品",物品);
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
    String 空物品 = Main.getMain().getConfig().getString("空物品");
    String 空物品名字 = Main.getMain().getConfig().getString("空物品名字");
    ItemStack 物品;
    /**
     * 加载方法,用于加载数据
     * false没有相关数据使用默认值 true加载成功
     */
    @Override
    public void jiazai() {
        try {
            物品 = new ItemStack(Material.getMaterial(空物品));
        }catch (Throwable a){
            Main.getMain().getLogger().warning("config.yml文件中“空物品”错误，请检查配置文件");
            return;
        }
        ItemMeta ItemMeta = 物品.getItemMeta();
        ItemMeta.setDisplayName(空物品名字);
        物品.setItemMeta(ItemMeta);
        if(wenjian.contains("物品")){
            物品 = wenjian.getItemStack("物品");
        }
    }

    /**
     * 用于获取原商品
     */
    @Override
    public ItemStack getshangping() {
        return null;
    }

    /**
     * 用于获取物品价格
     */
    @Override
    public double getjiage() {
        return -1;
    }

    /**
     * 用于设置物品库存
     *
     * @param 价格
     */
    @Override
    public void setjiage(int 价格) {

    }

    /**
     * 用于设置物品最高价格
     *
     * @param 价格
     */
    @Override
    public void setgaojiage(double 价格) {

    }

    /**
     * 用于设置物品最低价格
     *
     * @param 价格
     */
    @Override
    public void setdijiage(double 价格) {

    }

    /**
     * 获取物品的名字
     */
    @Override
    public String getname() {
        return name;
    }

    /**
     * 调用此方法代表玩家出售此物品一组
     *
     * @param P
     */
    @Override
    public void chushouyizu(Player P) {
    }

    /**
     * 调用此方法代表玩家购买一组物品
     *
     * @param P
     */
    @Override
    public void goumaiyizu(Player P) {
        if(!P.hasPermission("Floatingmarket.setornament")){
            P.sendMessage("没有打开商店的权限，需要：Floatingmarket.setornament");
            return;
        }
        ItemStack ItemStack = P.getInventory().getItem(0);//获取玩家背包的第一个物品
        if(ItemStack==null){
            try {
                物品 = new ItemStack(Material.getMaterial(空物品));
                ItemMeta ItemMeta = 物品.getItemMeta();
                ItemMeta.setDisplayName(空物品名字);
                物品.setItemMeta(ItemMeta);
            }catch (Throwable a){
                Main.getMain().getLogger().warning("config.yml文件中“空物品”错误，请检查配置文件");
                return;
            }
        }else {
            物品 = new ItemStack(ItemStack);
        }
        baocun();
        P.sendMessage("设置成功！");
        return;
    }

    /**
     * 设置物品的公式名字
     *
     * @param 价格公式
     * @param 库存公式
     */
    @Override
    public void setSetformula(String 价格公式, String 库存公式) {

    }

    /**
     * 设置物品是否允许出售或购买
     *
     * @param 出售
     * @param 购买
     */
    @Override
    public void set售或收购(boolean 出售, boolean 购买) {

    }
}
