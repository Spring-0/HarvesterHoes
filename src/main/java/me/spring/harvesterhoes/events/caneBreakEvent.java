package me.spring.harvesterhoes.events;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Collections;

public class caneBreakEvent implements Listener {

    @EventHandler
    public void onSugarCane(BlockBreakEvent e) {
        Block block = e.getBlock();
        if(ChatColor.stripColor(e.getPlayer().getInventory().getItemInHand().getItemMeta().getDisplayName()).equalsIgnoreCase("Water Hoe")) {
            if (block.getType() == Material.SUGAR_CANE_BLOCK) {
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
                if (caneToGive > 0) {e.getPlayer().getInventory().addItem(new ItemStack(Material.DIAMOND, caneToGive));}
                e.setCancelled(true);
            } else{e.setCancelled(true);}
        }
    }

}
