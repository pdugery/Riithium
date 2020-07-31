package pdugery.riithium;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class TraderInv {

    private Inventory inv;
    private String title;

    public TraderInv(){
        title = ChatColor.DARK_GRAY + "         > " + ChatColor.BLUE + "Riithium Trader" + ChatColor.DARK_GRAY + " <";
        inv = Bukkit.createInventory(null, 9, title);
        ItemStack decor = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);
        ItemMeta m = decor.getItemMeta();
        m.setDisplayName(" ");
        decor.setItemMeta(m);
        inv.setItem(2, decor);
        inv.setItem(3, decor);
    }

    public Inventory getInv(){
        return inv;
    }

    public String getTitle(){
        return title;
    }


}
