package me.spring.harvesterhoes;

import me.spring.harvesterhoes.commands.PluginGiveHoe;
import me.spring.harvesterhoes.events.GUIEvents;
import org.bukkit.plugin.java.JavaPlugin;

public final class Harvesterhoes extends JavaPlugin {

    @Override
    public void onEnable() {
        Util.tellConsole("Enabling Plugin...");
        this.saveDefaultConfig();
        getCommand("hh").setExecutor(new PluginGiveHoe(this));
        getServer().getPluginManager().registerEvents(new GUIEvents(this), this);

    }
    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

}
