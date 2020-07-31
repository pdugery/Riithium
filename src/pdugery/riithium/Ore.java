package pdugery.riithium;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.lang.reflect.Field;
import java.util.UUID;

public class Ore {
    private ItemStack o;

    public Ore(){
        o = new ItemStack(Material.LEGACY_SKULL_ITEM, 1, (short)3);
        SkullMeta m = (SkullMeta)o.getItemMeta();
        GameProfile p = new GameProfile(UUID.randomUUID(), "Riithium");
        p.getProperties().put("textures", new Property("textures", "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNWJhMzM4MzM0ZWM0YzA0YTMwNGEwODNhMGEwNTY5NWQ1MDM4ZWVmNmE1ZmFkZjBmZGQ2YjZlMWQ5YzM0MDIzNSJ9fX0="));

        try {
            Field fi = m.getClass().getDeclaredField("profile");
            fi.setAccessible(true);
            fi.set(m, p);
        } catch (Exception var6) {
            var6.printStackTrace();
        }

        m.setDisplayName(ChatColor.RESET + "Riithium");
        m.addEnchant(Enchantment.DURABILITY, 1, true);
        m.addItemFlags(ItemFlag.HIDE_ENCHANTS);

        o.setItemMeta(m);
    }

    public ItemStack getOre(){
        return o;
    }

    public boolean isEqual(ItemStack item){
        if(item.getType() != o.getType())
            return false;
        if(item.getItemMeta().getDisplayName().equals(o.getItemMeta().getDisplayName()))
            return false;
        if(!item.getItemMeta().getItemFlags().equals(o.getItemMeta().getItemFlags()))
            return false;
        return true;
    }

}