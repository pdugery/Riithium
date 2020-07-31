package pdugery.riithium;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class DropOre implements Listener {

    private int chance = 5;
    Ore ore;

    public DropOre(Ore ore) {
        this.ore = ore;
    }

    public void setChance(int chance){
        this.chance = chance;
    }
    public int getChance(){
        return chance;
    }

    @EventHandler
    public void blockBreakEvent(BlockBreakEvent e){
        if(!(e.getBlock().getType().equals(Material.DIAMOND_ORE)))
            return;
        if(e.getPlayer().getInventory().getItemInMainHand().containsEnchantment(Enchantment.SILK_TOUCH))
            return;
        if(((int)(Math.random()*100+1)) > chance)
            return;
        e.getBlock().getLocation().getWorld().dropItemNaturally(e.getBlock().getLocation(), ore.getOre());
    }

}
