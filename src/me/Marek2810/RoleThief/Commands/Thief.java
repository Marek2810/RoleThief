package me.Marek2810.RoleThief.Commands;

import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import net.md_5.bungee.api.ChatColor;

public class Thief implements CommandExecutor {

    public static HashMap<Player, Inventory> thiefedInvs = new HashMap<Player, Inventory>();
    public static HashMap<Player, Player> thiefs = new HashMap<Player, Player>();
    
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if ( ! (sender instanceof Player) ) {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&4NOP!"));
            return true;
        }
        else {
            Player player = (Player) sender;
            if (args.length == 0) {
                player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                        "&6Usage: &7/thief <player>"));
                return true;
            }
            else {
                Player thiefedPlayer = Bukkit.getServer().getPlayer(args[0]);
                if ( ! (thiefedPlayer instanceof Player) ) {
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&cNo player :("));
                    return true;
                }
                player.openInventory( createThiefPlayerInv(player, thiefedPlayer) );
                return true;
            }
        }
    }

    public Inventory createThiefPlayerInv (Player player, Player thiefedPlayer) {
        Inventory pInv = thiefedPlayer.getInventory();
        Inventory inv = Bukkit.createInventory(null, 36, ChatColor.DARK_GRAY + "" + ChatColor.BOLD + "Thief");
        for(int i = 0; i < 36; i ++) {
            ItemStack inventorySlot = pInv.getItem(i);
            if (inventorySlot == null) continue;
            inv.setItem(i, inventorySlot);
        }
        thiefedInvs.put(player, inv);
        thiefs.put(player, thiefedPlayer);
        return inv;
    }
}