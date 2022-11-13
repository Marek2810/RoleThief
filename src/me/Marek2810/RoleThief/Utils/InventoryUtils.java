package me.Marek2810.RoleThief.Utils;

import java.util.HashMap;
import java.util.Objects;

import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class InventoryUtils {
	
	public static HashMap<Player, Boolean> processingCheck = new HashMap<Player, Boolean>();

	public static void update(Inventory baseInv, Inventory updateInv) {
	  	  for(int i = 0; i < 36; i++){
	  	        if(!(Objects.equals(baseInv.getItem(i), updateInv.getItem(i)))){
	  	        	updateInv.setItem(i, baseInv.getItem(i));
	  	        }
	  	    }
		}
	
	public static boolean syncCheck(Inventory inv, Inventory inv2) {
		for(int i = 0; i < 36; i++) {
            if(!(Objects.equals(inv.getItem(i), inv2.getItem(i)))) return false;            
        } 
   	return true;
	}
}