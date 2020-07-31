package pdugery.riithium;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemFlag;

public class StopPlaceOre implements Listener {

    Ore ore;
    public StopPlaceOre(Ore ore){
        this.ore = ore;
    }

    @EventHandler
    public void blockPlaceEvent(BlockPlaceEvent e){
        System.out.println(ore.isEqual(e.getItemInHand()));
        if(ore.isEqual(e.getItemInHand()))
            e.setCancelled(true);
    }
}
