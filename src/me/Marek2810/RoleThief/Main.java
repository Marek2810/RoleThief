package me.Marek2810.RoleThief;

import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import me.Marek2810.RoleThief.Commands.Thief;
import me.Marek2810.RoleThief.Listeners.InventoryEvent;
import me.Marek2810.RoleThief.Listeners.ThiefInventoryEvent;

public class Main extends JavaPlugin {
    
	public static Main inst;
	public static InventoryEvent inventoryEvent;
    public static ThiefInventoryEvent thiefInventoryEvent;
	public static ConsoleCommandSender console;    

    @Override
    public void onEnable() {
    	inst = this;
    	console = getServer().getConsoleSender();
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