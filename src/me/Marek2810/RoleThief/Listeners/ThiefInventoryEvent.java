package me.Marek2810.RoleThief.Listeners;

import java.util.HashMap;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import me.Marek2810.RoleThief.Main;
import me.Marek2810.RoleThief.Commands.Thief;
import me.Marek2810.RoleThief.Utils.InventoryUtils;
import net.md_5.bungee.api.ChatColor;

public class ThiefInventoryEvent implements Listener {
	
	static HashMap<Player, Long> cd = new HashMap<Player, Long>();

    @EventHandler
    public void onInventoryClick (InventoryClickEvent event) {
    	if (event.getClickedInventory() == null) return;
    	if ( !(Thief.thiefedInvs.containsValue(event.getInventory())) ) return;
        if ( event.getAction().equals(InventoryAction.NOTHING)) return;
        Player player = (Player) event.getWhoClicked();
    	if ( !(Thief.thiefPlayers.containsValue(player)) ) return;

        if (cd.get(player) != null && cd.get(player) > System.currentTimeMillis() ) {
            event.setCancelled(true);
            return;
        } else {
        	cd.remove(player);
        }
        
        cd.put(player, System.currentTimeMillis()+250);
        
        if ( !(InventoryUtils.syncCheck(event.getInventory(), Thief.thiefedPlayers.get(player).getInventory())) ) {       	
        	player.sendMessage(ChatColor.RED + "Desync!");
        	for (int i = 0; i < 36; i++) {
        		ItemStack item = new ItemStack(Material.AIR);
        		if (Thief.thiefedPlayers.get(player).getInventory().getItem(i) != null) {
        			item = Thief.thiefedPlayers.get(player).getInventory().getItem(i);
        		}
        		event.getInventory().setItem(i, item);
        	}        	
        	event.setCancelled(true);
        	return;
        }    

        new BukkitRunnable() {
			public void run() {				
				InventoryUtils.update(event.getInventory(),
						Thief.thiefedPlayers.get(player).getInventory());
				cancel();
			} 
		}.runTaskLater(Main.getPlugin(Main.class), 1); 			
    } 
    
    @EventHandler
    public void onInventoryDrag (InventoryDragEvent event) {
    	if (event.getInventory() == null) return;
    	if ( !(Thief.thiefedInvs.containsValue(event.getInventory())) ) return;
    	Player player = (Player) event.getWhoClicked();
    	if ( !(Thief.thiefPlayers.containsValue(player)) ) return;

        if (cd.get(player) != null && cd.get(player) > System.currentTimeMillis() ) {
            event.setCancelled(true);
            return;
        } else {
        	cd.remove(player);
        }
        
        cd.put(player, System.currentTimeMillis()+250);
        
        if ( !(InventoryUtils.syncCheck(event.getInventory(), Thief.thiefedPlayers.get(player).getInventory())) ) {       	
        	player.sendMessage(ChatColor.RED + "Desync!");
        	for (int i = 0; i < 36; i++) {
        		ItemStack item = new ItemStack(Material.AIR);
        		if (Thief.thiefedPlayers.get(player).getInventory().getItem(i) != null) {
        			item = Thief.thiefedPlayers.get(player).getInventory().getItem(i);
        		}
        		event.getInventory().setItem(i, item);
        	}        	
        	event.setCancelled(true);
        	return;
        }    

        new BukkitRunnable() {
			public void run() {	
				InventoryUtils.update(event.getInventory(),
						Thief.thiefedPlayers.get(player).getInventory());
				cancel();
			} 
		}.runTaskLater(Main.getPlugin(Main.class), 1); 	
    }
}