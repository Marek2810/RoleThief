package me.Marek2810.RoleThief;

import me.Marek2810.RoleThief.Commands.Thief;
import me.Marek2810.RoleThief.Listeners.InventoryEvent;
import me.Marek2810.RoleThief.Listeners.ThiefInventoryEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    public static InventoryEvent inventoryEvent;
    public static ThiefInventoryEvent thiefInventoryEvent;

    @Override
    public void onEnable() {
        this.getCommand("thief").setExecutor(new Thief());
        inventoryEvent = new InventoryEvent();
        thiefInventoryEvent = new ThiefInventoryEvent();
        this.getServer().getPluginManager().registerEvents(inventoryEvent, this);
        this.getServer().getPluginManager().registerEvents(thiefInventoryEvent, this);
    }

    @Override
    public void onDisable() {

    }

}
