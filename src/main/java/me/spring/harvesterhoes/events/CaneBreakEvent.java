package me.spring.harvesterhoes.events;

import de.tr7zw.nbtapi.NBTItem;
import me.spring.harvesterhoes.Harvesterhoes;
import me.spring.harvesterhoes.Util;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.economy.EconomyResponse;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.Collections;

public class CaneBreakEvent implements Listener {

    // Autosell NBTI
    // Get enchants and apply fortune multiplier, auto? or manual levels? && keyfinder
    // Multiply integers round up or down?

    private final Harvesterhoes plugin;
    private Economy econ = Harvesterhoes.getEconomy();
    final int PRICE_OF_CANE;

    public CaneBreakEvent(Harvesterhoes plg){
        this.plugin = plg;
        PRICE_OF_CANE = plugin.getConfig().getInt("cane-sell-price");
    }

    @EventHandler
    public void onSugarCane(BlockBreakEvent e) {
        Block block = e.getBlock();
        if(e.getPlayer().getItemInHand() == null){return;}
        NBTItem nbti = new NBTItem(e.getPlayer().getItemInHand());
        if(nbti.getBoolean("isHarvHoe")) {
            if (block.getType() == Material.SUGAR_CANE_BLOCK) {

                int level = nbti.getInteger("HASTE_LEVEL");
                if(level > 0){
                    Util.addEffect(e.getPlayer(), PotionEffectType.FAST_DIGGING, level-1);
                }

                int speedLevel = nbti.getInteger("SPEED_LEVEL");
                if(speedLevel > 0){Util.addEffect(e.getPlayer(), PotionEffectType.SPEED, 10);}

                int caneToGive = 0;
                ArrayList<Block> canesBroken = new ArrayList<>();
                while (block.getType() == Material.SUGAR_CANE_BLOCK) {
                    canesBroken.add(block);
                    block = block.getLocation().add(0, 1, 0).getBlock();
                }

                Collections.reverse(canesBroken);
                for (int i = 0; i < canesBroken.size(); i++) {
                    if (canesBroken.get(i).getRelative(BlockFace.DOWN).getType() != Material.SUGAR_CANE_BLOCK) {break;
                    } else {
                        canesBroken.get(i).setType(Material.AIR);
                        caneToGive += 1;
                    }
                }

                if(caneToGive > 0) {

                    if (nbti.getBoolean("autoSell")) {
                        Economy econ = Harvesterhoes.getEconomy();
                        EconomyResponse r = econ.depositPlayer(e.getPlayer(), caneToGive * PRICE_OF_CANE * nbti.getInteger("FORTUNE_LEVEL"));
                        if (!r.transactionSuccess()) {
                            e.getPlayer().sendMessage(r.errorMessage);
                            Util.tellConsole(String.format("%s encountered: ", r.errorMessage));
                        }
                    } else {
                        e.getPlayer().getInventory().addItem(new ItemStack(Material.SUGAR_CANE, caneToGive));
                    }
                }



                e.setCancelled(true);
            } else{e.setCancelled(true);}
        }
    }

}

