package me.Marek2810.RoleThief.Listeners;

import me.Marek2810.RoleThief.Commands.Thief;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.Inventory;

import java.util.HashMap;

public class InventoryEvent implements Listener {

    public static HashMap<Player, Inventory> prevThiefedInvs = new HashMap<Player, Inventory>();
    public static HashMap<Player, Inventory> prevThiefGUI = new HashMap<Player, Inventory>();

    @EventHandler
    public void onInventoryOpen (InventoryOpenEvent event) {
        if (Thief.thiefedInvs.containsValue(event.getInventory())) {
            Player player = (Player) event.getPlayer();
            Inventory thiefPinv = Thief.thiefs.get(player).getInventory();
            Inventory thiefGUI = Thief.thiefedInvs.get(player);
            Inventory pthiefPinv = Bukkit.createInventory(null, 36);
            Inventory pthiefGUI = Bukkit.createInventory(null, 36);
            for (int i = 0; i < 36; i++) {
                pthiefPinv.setItem(i, thiefPinv.getItem(i));
                pthiefGUI.setItem(i, thiefGUI.getItem(i));
            }
            prevThiefedInvs.put(player, pthiefPinv);
            prevThiefGUI.put(player, pthiefGUI);
        }
    }

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
            prevThiefedInvs.remove(player);
            prevThiefGUI.remove(player);
            Thief.thiefedInvs.remove(player);
            Thief.thiefs.remove(player);
        }
    }

    @EventHandler
    public void onPlayerQuit (PlayerQuitEvent event) {
        Player player = (Player) event.getPlayer();
        prevThiefedInvs.remove(player);
        prevThiefGUI.remove(player);
        Thief.thiefedInvs.remove(player);
        Thief.thiefs.remove(player);
    }

}