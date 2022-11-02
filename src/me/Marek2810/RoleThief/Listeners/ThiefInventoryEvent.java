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

import me.Marek2810.RoleThief.Commands.Thief;
import net.md_5.bungee.api.ChatColor;

public class ThiefInventoryEvent implements Listener {

    @EventHandler
    public void onInventoryClick (InventoryClickEvent event) {
        if (!(Thief.thiefedInvs.containsValue(event.getInventory()))) return;
        if (event.getClickedInventory() == null) return;
        if ( !(event.getClickedInventory().getType().equals(InventoryType.CHEST)) ) return;        
        if (event.getAction().equals(InventoryAction.NOTHING)) return;

        Player player = (Player) event.getWhoClicked();
        player.sendMessage(ChatColor.GOLD + "YEP");
        player.sendMessage("Inventory action: " + event.getAction());
        player.sendMessage("Clicked slot: " + event.getCurrentItem() );
        player.sendMessage("Cursor: " + event.getCursor());        
        
        if (event.getAction().equals(InventoryAction.PICKUP_ALL)
        		|| event.getAction().equals(InventoryAction.DROP_ALL_SLOT)) {
        	Thief.thiefs.get(player).getInventory().setItem(event.getSlot(), null);        	
        }
        else if (event.getAction().equals(InventoryAction.PICKUP_HALF)) {
        	ItemStack item = new ItemStack(event.getCurrentItem());
        	int amount = event.getCurrentItem().getAmount();
        	item.setAmount(amount/2);
        	Thief.thiefs.get(player).getInventory().setItem(event.getSlot(), item); 
        }
        else if (event.getAction().equals(InventoryAction.SWAP_WITH_CURSOR)) {
        	Thief.thiefs.get(player).getInventory().setItem(event.getSlot(), event.getCursor()); 
        }
//        else {
//        	event.setCancelled(true);
//        	player.sendMessage(ChatColor.RED + "Problém šéfe..." + ChatColor.GOLD 
//        			+ "Inventory action: " + event.getAction()  );
//        }
    }
    
    @EventHandler
    public void onInventoryDrag (InventoryDragEvent event) {
        Player player = (Player) event.getWhoClicked();
        player.sendMessage(ChatColor.GOLD + "Drag event");
    }

    public void updateInventory (Player player, Inventory inv) {
        Inventory thiefPlayerInv = Thief.thiefs.get(player).getInventory();
        for(int i = 0; i < 36; i++) {
//            player.sendMessage("Slot: " + i);
//                player.sendMessage("GUI: " + event.getInventory().getItem(i));
//                player.sendMessage("Player: " +  thiefPlayerInv.getItem(i));
            if ( !(InventoryEvent.prevThiefedInvs.get(player).getItem(i).equals(thiefPlayerInv.getItem(i)) ) ) {
                player.sendMessage(ChatColor.GREEN + "Zmena u playera");
            }
            if ( !InventoryEvent.prevThiefGUI.get(player).getItem(i).equals(inv.getItem(i)) ) {
                player.sendMessage(ChatColor.RED + "Zmena v GUI");
            }
        }
    }

}
