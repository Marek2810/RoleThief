package me.Marek2810.RoleThief.Listeners;

import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import me.Marek2810.RoleThief.Main;
import me.Marek2810.RoleThief.Commands.Thief;
import me.Marek2810.RoleThief.Utils.InventoryUtils;
import net.md_5.bungee.api.ChatColor;

public class ThiefedPlayerEvent implements Listener {
	

    @EventHandler
    public void onInventoryClick (InventoryClickEvent event) {
    	if (event.getClickedInventory() == null) return;
    	if ( event.getAction().equals(InventoryAction.NOTHING)) return;
    	Player player = (Player) event.getWhoClicked();
    	if ( !(Thief.thiefedPlayers.containsValue(player)) ) return;
    	if ( InventoryUtils.processingCheck.containsKey(player) 
    			&&  InventoryUtils.processingCheck.get(player) ) {
    		event.setCancelled(true);
    		return;
    	} 
    	
        if ( !(InventoryUtils.syncCheck(player.getInventory(), Thief.thiefedInvs.get( Thief.thiefPlayers.get(player) ) )) ) {  
        	player.sendMessage(ChatColor.RED + "Desync!");
        	Main.console.sendMessage(ChatColor.RED + "Desync!");
        	Main.console.sendMessage("Player: " + event.getWhoClicked());
        	Main.console.sendMessage("Click: " + event.getAction());
        	Main.console.sendMessage("Slot: " + event.getSlot() + " Item: " + event.getCurrentItem());
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
        
        InventoryUtils.processingCheck.put(player, true);
        InventoryUtils.processingCheck.put(Thief.thiefPlayers.get(player), true);
        
        new BukkitRunnable() {
			public void run() {
				InventoryUtils.update(player.getInventory(),
						Thief.thiefedInvs.get( Thief.thiefPlayers.get(player)));
				InventoryUtils.processingCheck.remove(player);
		        InventoryUtils.processingCheck.remove(Thief.thiefPlayers.get(player));
				cancel();
			} 
		}.runTaskLater(Main.getPlugin(Main.class), 1); 			
    } 
    
    @EventHandler
    public void onInventoryDrag (InventoryDragEvent event) {
    	if (event.getInventory() == null) return;    	
    	Player player = (Player) event.getWhoClicked();
    	if ( !(Thief.thiefedPlayers.containsValue(player)) ) return;
    	if ( InventoryUtils.processingCheck.containsKey(player) 
    			&&  InventoryUtils.processingCheck.get(player) ) {
    		event.setCancelled(true);
    		return;
    	}
        
        if ( !(InventoryUtils.syncCheck(player.getInventory(), Thief.thiefedInvs.get( Thief.thiefPlayers.get(player) ) )) ) {  
        	player.sendMessage(ChatColor.RED + "Desync!");
        	Main.console.sendMessage(ChatColor.RED + "Desync!");
        	Main.console.sendMessage("Player: " + event.getWhoClicked());
        	Main.console.sendMessage("Click: drag");
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

        InventoryUtils.processingCheck.put(player, true);
        InventoryUtils.processingCheck.put(Thief.thiefPlayers.get(player), true);
        
        new BukkitRunnable() {
			public void run() {
				InventoryUtils.update(player.getInventory(),
						Thief.thiefedInvs.get( Thief.thiefPlayers.get(player)));
				InventoryUtils.processingCheck.remove(player);
		        InventoryUtils.processingCheck.remove(Thief.thiefPlayers.get(player));
				cancel();
			} 
		}.runTaskLater(Main.getPlugin(Main.class), 1); 	
    }
    
    @EventHandler
    public void  onEntityPickupItemEvent(EntityPickupItemEvent event) {
    	if ( !(event.getEntityType().equals(EntityType.PLAYER)) ) return;
    	Player player = (Player) event.getEntity();
    	if ( !(Thief.thiefedPlayers.containsValue(player)) ) return;
    	if ( InventoryUtils.processingCheck.containsKey(player) 
    			&&  InventoryUtils.processingCheck.get(player) ) {
    		event.setCancelled(true);
    		return;
    	}
    	
        if ( !(InventoryUtils.syncCheck(player.getInventory(), Thief.thiefedInvs.get( Thief.thiefPlayers.get(player) ) )) ) {  
        	player.sendMessage(ChatColor.RED + "Desync!");
        	Main.console.sendMessage(ChatColor.RED + "Desync!");
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
        
        InventoryUtils.processingCheck.put(player, true);
        InventoryUtils.processingCheck.put(Thief.thiefPlayers.get(player), true);

        new BukkitRunnable() {
			public void run() {
				InventoryUtils.update(player.getInventory(),
						Thief.thiefedInvs.get( Thief.thiefPlayers.get(player)));
				InventoryUtils.processingCheck.remove(player);
		        InventoryUtils.processingCheck.remove(Thief.thiefPlayers.get(player));
				cancel();
			} 
		}.runTaskLater(Main.getPlugin(Main.class), 1); 
    }
    
    @EventHandler
    public void onPlayerDropItemEvent(PlayerDropItemEvent event) {
    	if (event.getItemDrop() == null ) return;
    	Player player = (Player) event.getPlayer();
    	if ( !(Thief.thiefedPlayers.containsValue(player)) ) return;   
    	if ( InventoryUtils.processingCheck.containsKey(player) 
    			&&  InventoryUtils.processingCheck.get(player) ) {
    		event.setCancelled(true);
    		return;
    	}
       
        InventoryUtils.processingCheck.put(player, true);
        InventoryUtils.processingCheck.put(Thief.thiefPlayers.get(player), true);

        new BukkitRunnable() {
			public void run() {
				InventoryUtils.update(player.getInventory(),
						Thief.thiefedInvs.get( Thief.thiefPlayers.get(player)));
				InventoryUtils.processingCheck.remove(player);
		        InventoryUtils.processingCheck.remove(Thief.thiefPlayers.get(player));
				cancel();
			} 
		}.runTaskLater(Main.getPlugin(Main.class), 1); 
    }
}