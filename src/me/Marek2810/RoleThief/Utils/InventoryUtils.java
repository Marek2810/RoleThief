package me.Marek2810.RoleThief.Utils;

import java.util.Objects;

import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class InventoryUtils {

	public static void update(Player player, Inventory baseInv, Inventory updateInv) {
	  	  for(int i = 0; i < 36; i++){
	  	        if(!(Objects.equals(baseInv.getItem(i), updateInv.getItem(i)))){
	  	        	updateInv.setItem(i, baseInv.getItem(i));
	  	            player.updateInventory();
	  	        }
	  	    }
		}
	
	public static boolean syncCheck(Player player, Inventory inv, Inventory inv2) {
		for(int i = 0; i < 36; i++) {
            if(!(Objects.equals(inv.getItem(i), inv2.getItem(i)))){
                inv.setItem(i, inv2.getItem(i));
                player.updateInventory();
                return false;
            }
        } 
   	return true;
	}
}