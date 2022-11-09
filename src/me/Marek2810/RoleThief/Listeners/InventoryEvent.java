package me.Marek2810.RoleThief.Listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.Inventory;

import me.Marek2810.RoleThief.Commands.Thief;
import net.md_5.bungee.api.ChatColor;

public class InventoryEvent implements Listener {

    //Check if inventories are synced 
    @EventHandler
    public void onInventoryClose (InventoryCloseEvent event) {
        if (Thief.thiefedInvs.containsValue(event.getInventory())) {
            Player player = (Player) event.getPlayer();
            Inventory thiefPinv = Thief.thiefs.get(player).getInventory();
            for (int i = 0; i < 36; i++) {
                if ( event.getInventory().getItem(i) == null && thiefPinv.getItem(i) == null ) continue;
                if (event.getInventory().getItem(i) == null) {
                    player.sendMessage(ChatColor.RED + String.valueOf(i) + " Chyba..");
                    break;
                }
                if (thiefPinv.getItem(i) == null) {
                    player.sendMessage(ChatColor.RED + String.valueOf(i) + " Chyba..");
                    break;
                }
                if ( ! (event.getInventory().getItem(i).equals(thiefPinv.getItem(i))) ) player.sendMessage(ChatColor.RED + String.valueOf(i) + " Chyba..");
            }
            Thief.thiefedInvs.remove(player);
            Thief.thiefs.remove(player);
        }
    }

    @EventHandler
    public void onPlayerQuit (PlayerQuitEvent event) {
        Player player = (Player) event.getPlayer();
        Thief.thiefedInvs.remove(player);
        Thief.thiefs.remove(player);
    }

}