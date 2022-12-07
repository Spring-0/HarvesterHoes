package me.spring.harvesterhoes;

import me.spring.harvesterhoes.commands.PluginGiveHoe;
import me.spring.harvesterhoes.events.AutoSellToggle;
import me.spring.harvesterhoes.events.GUIEvents;
import me.spring.harvesterhoes.events.CaneBreakEvent;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

public final class Harvesterhoes extends JavaPlugin {
    private static Economy econ = null;
    @Override
    public void onEnable() {
        Util.tellConsole("Enabling Plugin...");
        this.saveDefaultConfig();
        getCommand("hh").setExecutor(new PluginGiveHoe(this));
        getServer().getPluginManager().registerEvents(new CaneBreakEvent(this), this);
        getServer().getPluginManager().registerEvents(new GUIEvents(this), this);
        getServer().getPluginManager().registerEvents(new AutoSellToggle(), this);

        if (!setupEconomy() ) {
            Util.tellConsole(String.format("[%s] - Disabled due to no Vault dependency found!", getDescription().getName()));
            getServer().getPluginManager().disablePlugin(this);
        }

    }

    private boolean setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        econ = rsp.getProvider();
        return econ != null;
    }

    public static Economy getEconomy() {
        return econ;
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

}
