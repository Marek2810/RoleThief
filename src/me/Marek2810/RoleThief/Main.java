package me.Marek2810.RoleThief;

import me.Marek2810.RoleThief.Commands.Thief;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    @Override
    public void onEnable() {
        this.getCommand("thief").setExecutor(new Thief());
    }

    @Override
    public void onDisable() {

    }

}
