package me.Marek2810.RoleThief.Listeners;

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

    @EventHandler
    public void onInventoryClick (InventoryClickEvent event) {
    	if (event.getClickedInventory() == null) return;
    	if ( !(Thief.thiefedInvs.containsValue(event.getInventory())) ) return;
        if ( event.getAction().equals(InventoryAction.NOTHING)) return;
        Player player = (Player) event.getWhoClicked();
    	if ( !(Thief.thiefPlayers.containsValue(player)) ) return;
    	if ( InventoryUtils.processingCheck.containsKey(player) 
    			&&  InventoryUtils.processingCheck.get(player) ) {
    		event.setCancelled(true);
    		return;
    	}
        
        if ( !(InventoryUtils.syncCheck(event.getInventory(), Thief.thiefedPlayers.get(player).getInventory())) ) {       	
        	player.sendMessage(ChatColor.RED + "Desync!");
        	Main.console.sendMessage(ChatColor.RED + "Desync!");
        	Main.console.sendMessage("Player: " + event.getWhoClicked());
        	Main.console.sendMessage("Click: " + event.getAction());
        	Main.console.sendMessage("Slot: " + event.getSlot() + " Item: " + event.getCurrentItem());
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

        InventoryUtils.processingCheck.put(player, true);
        InventoryUtils.processingCheck.put(Thief.thiefedPlayers.get(player), true);
        
        new BukkitRunnable() {
			public void run() {				
				InventoryUtils.update(event.getInventory(),
						Thief.thiefedPlayers.get(player).getInventory());
				InventoryUtils.processingCheck.remove(player);
		        InventoryUtils.processingCheck.remove(Thief.thiefedPlayers.get(player));
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
    	if ( InventoryUtils.processingCheck.containsKey(player) 
    			&&  InventoryUtils.processingCheck.get(player) ) {
    		event.setCancelled(true);
    		return;
    	}
        
        if ( !(InventoryUtils.syncCheck(event.getInventory(), Thief.thiefedPlayers.get(player).getInventory())) ) {       	
        	player.sendMessage(ChatColor.RED + "Desync!");
        	Main.console.sendMessage(ChatColor.RED + "Desync!");
        	Main.console.sendMessage("Player: " + event.getWhoClicked());
        	Main.console.sendMessage("Click: drag");
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

        InventoryUtils.processingCheck.put(player, true);
        InventoryUtils.processingCheck.put(Thief.thiefedPlayers.get(player), true);
        
        new BukkitRunnable() {
			public void run() {					
				InventoryUtils.update(event.getInventory(),
						Thief.thiefedPlayers.get(player).getInventory());
				InventoryUtils.processingCheck.remove(player);
		        InventoryUtils.processingCheck.remove(Thief.thiefedPlayers.get(player));
				cancel();
			} 
		}.runTaskLater(Main.getPlugin(Main.class), 1);	
    }   
    
}