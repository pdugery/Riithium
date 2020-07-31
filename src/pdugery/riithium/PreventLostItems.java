package pdugery.riithium;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;

public class PreventLostItems implements Listener {

    TraderInv traderInv;

    public PreventLostItems(TraderInv traderInv){
        this.traderInv = traderInv;
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent e){
        if(!e.getView().getTitle().equals(traderInv.getTitle()))
            return;
        Inventory inv = e.getInventory();
        for(int i = 0; i < 9; i++){
            if(i != 2 && i != 3 && inv.getItem(i) != null)
                e.getPlayer().getInventory().addItem(inv.getItem(i));
        }
    }
}
