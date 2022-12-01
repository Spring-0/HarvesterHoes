package me.spring.harvesterhoes.commands;

import me.spring.harvesterhoes.Harvesterhoes;
import me.spring.harvesterhoes.Util;
import de.tr7zw.nbtapi.NBTItem;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class PluginGiveHoe implements CommandExecutor {

    private final Harvesterhoes plugin;

    public PluginGiveHoe(Harvesterhoes plg) {
        plugin = plg;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

        if (cmd.getName().equalsIgnoreCase("hh")) {

            Player pSender = (Player) sender;
            Player pTarget;

            List<String> lores = new ArrayList<>();

            ItemStack hoe = new ItemStack(Material.WOOD_HOE, 1);
            ItemMeta meta = hoe.getItemMeta();

            Bukkit.broadcastMessage(plugin.getConfig().getString("hoe-name"));
            meta.setDisplayName(Util.msgColorCode(plugin.getConfig().getString("hoe-name")));

            for(String s: plugin.getConfig().getStringList("hoe-lore")){

                System.out.println(s);

                s = s.replaceAll("%HASTE_LEVEL%", "0");
                s = s.replaceAll("%FORTUNE_LEVEL%", "0");
                s = s.replaceAll("%TOKEN_LEVEL%", "0");
                s = s.replaceAll("%KEYFINDER_LEVEL%", "0");
                s = s.replaceAll("%AUTOSELL%", "True");

                lores.add(Util.msgColorCode(s));
            }

            if(plugin.getConfig().getBoolean("hoe-tool-enchant-effect")){
                meta.addEnchant(Enchantment.DURABILITY, 1, false);
                meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            }

            meta.setLore(lores);
            hoe.setItemMeta(meta);

            NBTItem nbti = new NBTItem(hoe);
            nbti.setInteger("HASTE_LEVEL", 0);
            nbti.setInteger("FORTUNE_LEVEL", 0);
            nbti.setInteger("TOKEN_LEVEL", 0);
            nbti.setInteger("KEYFINDER_LEVEL", 0);
            nbti.setBoolean("isHarvHoe", Boolean.TRUE);
            nbti.setBoolean("autoSell", Boolean.TRUE);

            if (args.length == 0) {pSender.getInventory().addItem(new ItemStack[]{nbti.getItem()});
            } else if (args.length == 1) {
                pTarget = Bukkit.getServer().getPlayer(args[1]);
                pTarget.getInventory().addItem(new ItemStack[]{nbti.getItem()});
            }
        }
        return true;
    }
}
