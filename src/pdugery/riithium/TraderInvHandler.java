package pdugery.riithium;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class TraderInvHandler implements Listener {

    Ore ore;
    TraderInv traderInv;

    public TraderInvHandler(Ore ore, TraderInv traderInv){
        this.ore = ore;
        this.traderInv = traderInv;
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e){
        Inventory inv = e.getView().getTopInventory();
        if(!e.getView().getTitle().equals(traderInv.getTitle()))
            return;
        int slot = e.getRawSlot();
        if(slot == 2 || slot == 3){
            e.setCancelled(true);
            return;
        }
        InventoryAction ie = e.getAction();
        if((slot == 4|| slot == 5 || slot == 6 || slot == 7 || slot == 8) && !(ie == InventoryAction.PICKUP_ALL || ie == InventoryAction.PICKUP_HALF || ie == InventoryAction.PICKUP_SOME || ie == InventoryAction.PICKUP_ONE)){
            e.setCancelled(true);
            return;
        }
        if(inv.getItem(0) != null)
            System.out.println(inv.getItem(0).isSimilar(ore.getOre()));
        /*if(inv.getItem(0) == null && inv.getItem(1) == null)
            return;

        if(!inv.getItem(0).equals(ore.getOre()) && !inv.getItem(1).equals(ore.getOre())){
            return;
        }

        List<ItemStack> trades = new ArrayList<ItemStack>();

        if(inv.getItem(0).equals(ore.getOre()) && inv.getItem(1) != null){
            trades = getTrades(inv.getItem(1));
        }
        if(inv.getItem(1).equals(ore.getOre()) && inv.getItem(0) != null){
            trades = getTrades(inv.getItem(0));
        }

        for(int i = 0; i < trades.size(); i++){
            inv.setItem(3 + i, trades.get(i));
        }*/


    }
    public List<ItemStack> getTrades(ItemStack i){
        List<ItemStack> trades = new ArrayList<ItemStack>();
        Map<Enchantment, Integer> enchants = i.getEnchantments();
        for(Map.Entry<Enchantment, Integer> entry : enchants.entrySet()){
            Enchantment ench = entry.getKey();
            Integer level = entry.getValue();
            if(level == ench.getMaxLevel()){
                ItemStack is = i;
                is.removeEnchantment(ench);
                is.addUnsafeEnchantment(ench, level + 1);
                trades.add(is);
            }
        }
        return trades;
    }

}
