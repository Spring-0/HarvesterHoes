package me.spring.harvesterhoes.events;

import de.tr7zw.nbtapi.NBTItem;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class AutoSellToggle implements Listener {

    @EventHandler
    public void onShiftRightClick(PlayerInteractEvent e){
        if(e.getPlayer().isSneaking() && (e.getAction() == Action.RIGHT_CLICK_BLOCK || e.getAction() == Action.RIGHT_CLICK_AIR)){
            if(e.getPlayer().getItemInHand() == null || !(new NBTItem(e.getPlayer().getItemInHand()).getBoolean("isHarvHoe")) || e.getPlayer().getItemInHand().getType() == Material.AIR){return;}
            NBTItem nbti = new NBTItem(e.getPlayer().getItemInHand());
            nbti.setBoolean("autoSell", !nbti.getBoolean("autoSell"));
            e.getPlayer().sendMessage("Autosell: " + nbti.getBoolean("autoSell"));
            e.getPlayer().setItemInHand(nbti.getItem());
        }
    }

}
