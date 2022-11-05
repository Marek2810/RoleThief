package me.Marek2810.RoleThief.Listeners;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryAction;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import me.Marek2810.RoleThief.Main;
import me.Marek2810.RoleThief.Commands.Thief;

public class ThiefInventoryEvent implements Listener {

    @EventHandler
    public void onInventoryClick (InventoryClickEvent event) {
    	if (event.getClickedInventory() == null) return;
    	if ( !(Thief.thiefedInvs.containsValue(event.getInventory())) ) return;
    	if ( event.getClickedInventory().getType().equals(InventoryType.PLAYER) ) return; 
        if ( event.getAction().equals(InventoryAction.NOTHING)) return;

        Player player = (Player) event.getWhoClicked();
            
        new BukkitRunnable() {
        	public void run() {
        		checkThiefGUI(player, event.getInventory());
    		}
    	}.runTaskLater(Main.getPlugin(Main.class), 1);    	   	
    }
    
    @EventHandler
    public void onInventoryDrag (InventoryDragEvent event) {
    	if (!(Thief.thiefedInvs.containsValue(event.getInventory())) ) return;
    	
    	Player player = (Player) event.getWhoClicked();
    	new BukkitRunnable() {
			public void run() {
				if ( ! (checkThiefGUI(player, event.getInventory())) ) {
					event.setCancelled(true);
					player.sendMessage("Chyba.");
					return;
				}
				checkThiefGUI(player, event.getInventory());		
			}
		}.runTaskLater(Main.getPlugin(Main.class), 1);
    }

    public boolean checkThiefGUI (Player player, Inventory inv) {
    	boolean output = false;
        for(int i = 0; i < 36; i++) {
        	if ( InventoryEvent.prevThiefGUI.get(player).getItem(i) == null 
        			&& inv.getItem(i) == null ) continue;        	
        	if ( InventoryEvent.prevThiefGUI.get(player).getItem(i) == null) {
        		updateThiefedPinv(player, i, inv.getItem(i));
//        		player.sendMessage("--- " + i + " --- (pGUI)");
//        		player.sendMessage( ChatColor.GREEN + "Zmena v gui");
//        		player.sendMessage("p GUI: " + InventoryEvent.prevThiefGUI.get(player).getItem(i));
//        		player.sendMessage("GUI: " + inv.getItem(i));
        		output = true;
        		continue;
        	}
        	else if ( inv.getItem(i) == null ) {
        		updateThiefedPinv(player, i, null);
        		output = true;
        		continue;
        	}
        	else if ( !( InventoryEvent.prevThiefGUI.get(player).getItem(i).equals(inv.getItem(i) ) )) {
        		updateThiefedPinv(player, i, inv.getItem(i));
        		output = true;
        		continue;
        	}
        	if ( InventoryEvent.prevThiefGUI.get(player).getItem(i).equals(inv.getItem(i))) continue;
        	return false;
        }
        return output;
    }
    
    public void updateThiefedPinv (Player player, int slot, ItemStack item) {
    	Thief.thiefs.get(player).getInventory().setItem(slot, item);
    	InventoryEvent.prevThiefGUI.get(player).setItem(slot, item);
    	//InventoryEvent.prevThiefGUI.get(player).setItem(slot, item);
    }
}