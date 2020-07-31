package pdugery.riithium;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import static org.bukkit.Bukkit.getLogger;

public class Commands implements CommandExecutor {

    Ore ore;
    DropOre dropOre;
    String prefix = ChatColor.RED + "RIIS" + ChatColor.DARK_GRAY + ChatColor.BOLD + " » " + ChatColor.GRAY;
    String helpMessage = prefix + "Use " + ChatColor.GOLD + "/riithium help " + ChatColor.GRAY + "to view available commands";
    String unknownCommand = prefix + "Command not recognized, use " + ChatColor.GOLD + "/riithium help " + ChatColor.GRAY + "to view available commands";

    public Commands(Ore ore, DropOre dropOre) {
        this.ore = ore;
        this.dropOre = dropOre;
    }

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (sender instanceof Player && !(((Player) sender).hasPermission("riis.admin")) && args.length != 0 && !args[0].equalsIgnoreCase("trade")) {
            sender.sendMessage(prefix + "You do not have permission to use that command");
            return true;
        }

        if (args.length == 0) {
            sender.sendMessage(helpMessage);
            return true;
        }
        if (args.length == 1) {
            if (args[0].equalsIgnoreCase("help")) {
                sender.sendMessage("" + ChatColor.DARK_GRAY + ChatColor.BOLD + "» " + ChatColor.BLUE + "Riithium " + ChatColor.DARK_GRAY + ChatColor.BOLD + "«");
                sender.sendMessage(ChatColor.DARK_GRAY + "> " + ChatColor.GRAY + "/riithium give (player) <amount>");
                sender.sendMessage(ChatColor.DARK_GRAY + "> " + ChatColor.GRAY + "/riithium chance (set) <percentage>");
                return true;
            }
            if (args[0].equalsIgnoreCase("chance")) {
                sender.sendMessage(prefix + "There is currently a " + ChatColor.GOLD + dropOre.getChance() + "%" + ChatColor.GRAY + " chance of Riithium dropping");
                return true;
            }
            if(args[0].equalsIgnoreCase("trade")){
                if(!(sender instanceof Player)){
                    sender.sendMessage("That command may only be used in-game");
                    return true;
                }
                Player p = (Player)sender;
                TraderInv traderInv = new TraderInv();
                p.openInventory(traderInv.getInv());
                return true;
            }
            sender.sendMessage(unknownCommand);
            return true;
        }
        if (args.length == 2) {
            if (args[0].equalsIgnoreCase("chance") && args[1].equalsIgnoreCase("set")) {
                sender.sendMessage(prefix + "You must specify a percentage");
                return true;
            }
            if (args[0].equalsIgnoreCase("give") && sender instanceof Player) {
                int amount;
                try {
                    amount = Integer.parseInt(args[1]);
                } catch (Exception e) {
                    sender.sendMessage(prefix + "You did not properly specify an amount");
                    return true;
                }
                if (invSpace(((Player) sender).getInventory()) < amount) {
                    sender.sendMessage(prefix + "You cannot fit " + ChatColor.GOLD + amount + " Riithium " + ChatColor.GRAY + "in your inventory");
                    return true;
                }
                try {
                    ItemStack items = ore.getOre();
                    items.setAmount(amount);
                    ((Player) sender).getInventory().addItem(items);
                    return true;
                } catch (Exception e) {
                    sender.sendMessage(prefix + "If you see this message, please inform an admin (Internal Error)");
                    getLogger().info("Error while executing /riithium give (amount) - " + e);
                    return true;
                }
            }
        }
        if (args.length == 3) {
            if (args[0].equalsIgnoreCase("chance") && args[1].equalsIgnoreCase("set")) {
                try {
                    int chance = Integer.parseInt(args[2]);
                    dropOre.setChance(chance);
                    sender.sendMessage(prefix + "There is now a " + ChatColor.GOLD + chance + "% " + ChatColor.GRAY + "chance of Riithium dropping");
                    return true;
                } catch (Exception e) {
                    sender.sendMessage(prefix + "You did not properly specify a percentage");
                    return true;
                }
            }
            if (args[0].equalsIgnoreCase("give")) {
                Player recipient = Bukkit.getPlayer(args[1]);
                if(recipient == null){
                    sender.sendMessage(prefix + "Player not found");
                    return true;
                }
                int amount;
                try {
                    amount = Integer.parseInt(args[2]);
                } catch (Exception e) {
                    sender.sendMessage(prefix + "You did not properly specify an amount");
                    return true;
                }
                if (invSpace(recipient.getInventory()) < amount) {
                    sender.sendMessage(prefix + ChatColor.GOLD + recipient + ChatColor.GRAY + " cannot fit " + ChatColor.GOLD + amount + " Riithium " + ChatColor.GRAY + "in their inventory");
                    return true;
                }
                try {
                    ItemStack items = ore.getOre();
                    items.setAmount(amount);
                    ((Player) sender).getInventory().addItem(items);
                    return true;
                } catch (Exception e) {
                    sender.sendMessage(prefix + "If you see this message, please contact an admin (Internal Error)");
                    getLogger().info("Error while executing /riithium give (amount) - " + e);
                    return true;
                }
            }
        }
        sender.sendMessage(helpMessage);
        return true;
    }

    public int invSpace(PlayerInventory inv) {
        int count = 0;
        for (int slot = 0; slot < 36; slot++) {
            ItemStack is = inv.getItem(slot);
            if (is == null) {
                count += ore.getOre().getMaxStackSize();
            }
            if (is != null) {
                if (is.getType().equals(Material.LEGACY_SKULL_ITEM) && is.getItemMeta().getDisplayName().equals(ChatColor.RESET + "Riithium") && is.getItemMeta().hasItemFlag(ItemFlag.HIDE_ENCHANTS)) {
                    count += (ore.getOre().getMaxStackSize() - is.getAmount());
                }
            }
        }
        return count;
    }

}
