package me.spring.harvesterhoes.events;

import me.spring.harvesterhoes.GUI;
import me.spring.harvesterhoes.Harvesterhoes;
import me.spring.harvesterhoes.UpdateHoe;
import de.tr7zw.nbtapi.NBTItem;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.awt.*;
import java.util.List;

public class GUIEvents implements Listener {


    private final Harvesterhoes plugin;
    private final UpdateHoe updateHoe;

    public GUIEvents(Harvesterhoes plg) {
        plugin = plg;
        updateHoe = new UpdateHoe(plugin);
    }

    @EventHandler
    public void onRightClick(PlayerInteractEvent e){
        if(e.getAction() == Action.RIGHT_CLICK_BLOCK || e.getAction() == Action.RIGHT_CLICK_AIR) {
            if (e.getPlayer().getInventory().getItemInHand() == null || e.getPlayer().getInventory().getItemInHand().getType() == Material.AIR) {return;}
            NBTItem nbti = new NBTItem(e.getPlayer().getInventory().getItemInHand());
            if (nbti.hasKey("isHarvHoe") || nbti.getBoolean("isHarvHoe")) {
                e.getPlayer().openInventory(new GUI(plugin).getUpgradeGUI());
            }
        }
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e){
        int enchantIncrement = 1;
        if(e.getClickedInventory() == null) return;
        if(e.getCurrentItem().getType() == Material.AIR || e.getCurrentItem() == null) return;
        if(!(e.getClickedInventory().getTitle().equalsIgnoreCase(plugin.getConfig().getString("upgrade-menu-title")))){return;}
        Player p = (Player) e.getWhoClicked();
        if(!(new NBTItem(e.getWhoClicked().getInventory().getItemInHand()).getBoolean("isHarvHoe"))){ return;}
        if(e.getView().getTitle().equalsIgnoreCase(plugin.getConfig().getString("upgrade-menu-title"))){
            ItemStack itemClicked = e.getCurrentItem(); // Upgrade
            NBTItem nbtiBlock = new NBTItem(itemClicked);
            String enchantUpgrade = nbtiBlock.getString("enchant-type");
            updateHoe.updateHoe(e.getWhoClicked().getInventory().getItemInHand(), enchantUpgrade, enchantIncrement, (Player) e.getWhoClicked());
            e.getWhoClicked().sendMessage(String.format("You have purchased %d level(s) of %s!", enchantIncrement, enchantUpgrade));

        }
        e.setCancelled(true);
    }

}
