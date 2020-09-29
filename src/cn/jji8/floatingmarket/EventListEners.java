package cn.jji8.floatingmarket;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class EventListEners implements Listener {
    @EventHandler
    public void wanjia(InventoryClickEvent a) {//玩家点击物品栏格子时触发
        Main.getMain().event.dianji(a);
    }
}
