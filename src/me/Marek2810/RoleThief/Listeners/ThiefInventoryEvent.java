package me.Marek2810.RoleThief.Listeners;

import java.util.HashMap;
import java.util.Objects;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.scheduler.BukkitRunnable;

import me.Marek2810.RoleThief.Main;
import me.Marek2810.RoleThief.Commands.Thief;

public class ThiefInventoryEvent implements Listener {
	
	static HashMap<Player, Long> cd = new HashMap<Player, Long>();

    @EventHandler
    public void onInventoryClick (InventoryClickEvent event) {
    	if (event.getClickedInventory() == null) return;
    	if ( !(Thief.thiefedInvs.containsValue(event.getInventory())) ) return;
        if ( event.getAction().equals(InventoryAction.NOTHING)) return;
        Player player = (Player) event.getWhoClicked();
        if (cd.get(player) != null && cd.get(player) > System.currentTimeMillis() ) {
            event.setCancelled(true);
            return;
        }
        
        cd.put(player, System.currentTimeMillis()+250);
        
        if ( !(areInventoriesSync(player, event.getInventory())) ) {
        	for (int i = 0; i < 36; i++) {
        		event.getInventory().setItem(i, Thief.thiefedPlayers.get(player).getInventory().getItem(i));
        	}        	
        	event.setCancelled(true);
        	return;
        }

        new BukkitRunnable() {
			public void run() {
				updateInventroy(player, event.getInventory());
				cancel();
			} 
		}.runTaskLater(Main.getPlugin(Main.class), 1); 			
    } 
    
    public boolean areInventoriesSync(Player player, Inventory inv) {
    	 for(int i = 0; i < 36; i++) {
             if(!(Objects.equals(inv.getItem(i), Thief.thiefedPlayers.get(player).getInventory().getItem(i)))){
                 inv.setItem(i, Thief.thiefedPlayers.get(player).getInventory().getItem(i));
                 player.updateInventory();
                 return false;
             }
         } 
    	return true;
    }
    
    public void updateInventroy(Player player, Inventory inv) {
    	  for(int i = 0; i < 36; i++){
    	        if(!(Objects.equals(inv.getItem(i), Thief.thiefedPlayers.get(player).getInventory().getItem(i)))){
    	            Thief.thiefedPlayers.get(player).getInventory().setItem(i, inv.getItem(i));
    	            player.updateInventory();
    	        }
    	    }
    }
  
    @EventHandler
    public void onInventoryDrag (InventoryDragEvent event) {
    	if ( !(Thief.thiefedInvs.containsValue(event.getInventory())) ) return;
    	if ( !(Thief.thiefedPlayers.containsValue(event.getWhoClicked())) ) return;
    	if ( event.getInventory().getType().equals(InventoryType.PLAYER) ) return; 
    	Player player = (Player) event.getWhoClicked();
    	if (cd.get(player) != null && cd.get(player) > System.currentTimeMillis() ) {
            event.setCancelled(true);
            return;
        }
        
        cd.put(player, System.currentTimeMillis()+250);
        
        if ( !(areInventoriesSync(player, event.getInventory())) ) {
        	for (int i = 0; i < 36; i++) {
        		event.getInventory().setItem(i, Thief.thiefedPlayers.get(player).getInventory().getItem(i));
        	}        	
        	event.setCancelled(true);
        	return;
        }

        new BukkitRunnable() {
			public void run() {
				updateInventroy(player, event.getInventory());
				cancel();
			} 
		}.runTaskLater(Main.getPlugin(Main.class), 1); 		

    }
}