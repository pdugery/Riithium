package pdugery.riithium;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.plugin.java.JavaPlugin;

@SuppressWarnings("deprecated")
public class Riithium extends JavaPlugin {

    Ore ore;
    DropOre dropOre;
    StopPlaceOre stopPlaceOre;
    Commands commands;
    TraderInv traderInv;
    TraderInvHandler traderInvHandler;
    PreventLostItems preventLostItems;

    @Override
    public void onEnable(){
        ore = new Ore();
        dropOre = new DropOre(ore);
        stopPlaceOre = new StopPlaceOre(ore);
        commands = new Commands(ore, dropOre);
        traderInv = new TraderInv();
        traderInvHandler = new TraderInvHandler(ore, traderInv);
        preventLostItems = new PreventLostItems(traderInv);
        getServer().getPluginManager().registerEvents(dropOre, this);
        getServer().getPluginManager().registerEvents(stopPlaceOre, this);
        getServer().getPluginManager().registerEvents(traderInvHandler, this);
        getServer().getPluginManager().registerEvents(preventLostItems, this);
        this.getCommand("riithium").setExecutor(commands);
    }
}
