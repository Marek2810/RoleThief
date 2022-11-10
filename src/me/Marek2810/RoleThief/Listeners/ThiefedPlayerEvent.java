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

public class ThiefedPlayerEvent implements Listener {
	
	static HashMap<Player, Long> cd = new HashMap<Player, Long>();

    @EventHandler
    public void onInventoryClick (InventoryClickEvent event) {
    	if (event.getClickedInventory() == null) return;
    	if ( event.getAction().equals(InventoryAction.NOTHING)) return;
    	Player player = (Player) event.getWhoClicked();
    	if ( !(Thief.thiefedPlayers.containsValue(player)) ) return;
       
        if (cd.get(player) != null && cd.get(player) > System.currentTimeMillis() ) {
            event.setCancelled(true);
            return;
        } else {
        	cd.remove(player);
        }
        
        cd.put(player, System.currentTimeMillis()+250);
        
        if ( !(InventoryUtils.syncCheck(player.getInventory(), Thief.thiefedInvs.get( Thief.thiefPlayers.get(player) ) )) ) {  
        	player.sendMessage(ChatColor.RED + "Desync!");
        	for (int i = 0; i < 36; i++) {
        		ItemStack item = new ItemStack(Material.AIR);
        		if (Thief.thiefedInvs.get( Thief.thiefPlayers.get(player)).getItem(i) != null) {
        			item = Thief.thiefedInvs.get( Thief.thiefPlayers.get(player)).getItem(i);
        		}
        		player.getInventory().setItem(i, item);
        	}        	
        	event.setCancelled(true);
        	return;
        }    

        new BukkitRunnable() {
			public void run() {
				InventoryUtils.update(player.getInventory(),
						Thief.thiefedInvs.get( Thief.thiefPlayers.get(player)));
				cancel();
			} 
		}.runTaskLater(Main.getPlugin(Main.class), 1); 			
    } 
    
    @EventHandler
    public void onInventoryDrag (InventoryDragEvent event) {
    	if (event.getInventory() == null) return;    	
    	Player player = (Player) event.getWhoClicked();
    	if ( !(Thief.thiefedPlayers.containsValue(player)) ) return;    	
        
        if (cd.get(player) != null && cd.get(player) > System.currentTimeMillis() ) {
            event.setCancelled(true);
            return;
        } else {
        	cd.remove(player);
        }
        
        cd.put(player, System.currentTimeMillis()+250);
        
        if ( !(InventoryUtils.syncCheck(player.getInventory(), Thief.thiefedInvs.get( Thief.thiefPlayers.get(player) ) )) ) {  
        	player.sendMessage(ChatColor.RED + "Desync!");
        	for (int i = 0; i < 36; i++) {
        		ItemStack item = new ItemStack(Material.AIR);
        		if (Thief.thiefedInvs.get( Thief.thiefPlayers.get(player)).getItem(i) != null) {
        			item = Thief.thiefedInvs.get( Thief.thiefPlayers.get(player)).getItem(i);
        		}
        		player.getInventory().setItem(i, item);
        	}        	
        	event.setCancelled(true);
        	return;
        }    

        new BukkitRunnable() {
			public void run() {
				InventoryUtils.update(player.getInventory(),
						Thief.thiefedInvs.get( Thief.thiefPlayers.get(player)));
				cancel();
			} 
		}.runTaskLater(Main.getPlugin(Main.class), 1); 	
    }
}