package cn.jji8.floatingmarket.gui;

import cn.jji8.floatingmarket.Main;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class NullGood implements Goods {
    /**
     * 获取用于显示的物品堆
     */
    @Override
    public ItemStack getxianshiwupin() {
        if (显示物品==null){
            Main.getMain().getLogger().warning("config.yml文件中“空物品”错误，请检查配置文件");
        }
        return 显示物品;
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

    }

    /**
     * 删除时调用的方法
     */
    @Override
    public void delete() {

    }
    String 空物品 = Main.getMain().getConfig().getString("空物品");
    String 空物品名字 = Main.getMain().getConfig().getString("空物品名字");
    ItemStack 显示物品 =null;
    /**
     * 加载方法,用于加载数据
     * false没有相关数据使用默认值 true加载成功
     */
    @Override
    public void jiazai() {
        try {
            显示物品 = new ItemStack(Material.getMaterial(空物品));
        }catch (Throwable a){
            Main.getMain().getLogger().warning("config.yml文件中“空物品”错误，请检查配置文件");
            return;
        }
        ItemMeta ItemMeta = 显示物品.getItemMeta();
        ItemMeta.setDisplayName(空物品名字);
        显示物品.setItemMeta(ItemMeta);
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
        return "空";
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
}
