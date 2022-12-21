package me.spring.harvesterhoes;

import de.tr7zw.nbtapi.NBTItem;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class GUI {

    private final Harvesterhoes plugin;
    private FileConfiguration config;
    private NBTItem nbti;

    public GUI(Harvesterhoes plg) {
        plugin = plg;
        config = plugin.getConfig();
    }

    public Inventory getUpgradeGUI() {
        Inventory inv = Bukkit.createInventory(null, plugin.getConfig().getInt("upgrade-menu-size"), plugin.getConfig().getString("upgrade-menu-title"));
        inv.clear();
        addItemsToGui(inv);

        return inv;
    }


    public void addItemsToGui(Inventory gui) {

        ConfigurationSection menuSection = config.getConfigurationSection("hoe-upgrade-menu");

        for (String key : menuSection.getKeys(false)) {
            ItemStack itemComp = new ItemStack(Material.matchMaterial(menuSection.getString(key + ".item")));

            NBTItem nbti = new NBTItem(itemComp);

            ItemMeta meta = itemComp.getItemMeta();

            meta.setDisplayName(Util.msgColorCode(menuSection.getString(key + ".name")));

            if (menuSection.getBoolean(key + ".glow-effect")) {
                meta.addEnchant(Enchantment.DURABILITY, 1, true);
                meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
            }

            List<String> lores = new ArrayList<>();

            for (String s : menuSection.getStringList(key + ".lore")) {

                s = s.replaceAll("%HASTE_LEVEL%", String.valueOf(nbti.getInteger("HASTE_LEVEL")));
                s = s.replaceAll("%FORTUNE_LEVEL%", String.valueOf(nbti.getInteger("FORTUNE_LEVEL")));
                s = s.replaceAll("%TOKEN_LEVEL%", String.valueOf(nbti.getInteger("TOKEN_LEVEL")));
                s = s.replaceAll("%KEYFINDER_LEVEL%", String.valueOf(nbti.getInteger("KEYFINDER_LEVEL")));
                s = s.replaceAll("%SPEED_LEVEL%", String.valueOf(nbti.getInteger("SPEED_LEVEL")));

                lores.add(Util.msgColorCode(s));
            }

            meta.setLore(lores);
            itemComp.setItemMeta(meta);

            nbti = new NBTItem(itemComp);
            String enchantTypeInput = menuSection.getString(key + ".enchant-type");
            String[] validEnchants = {"haste", "fortune", "token-finder", "key-finder", "speed"};
            if (!(Arrays.asList(validEnchants).contains(enchantTypeInput.toLowerCase()))) {
                Util.tellConsole(String.format("" +
                        "'%s' is not a valid input for enchant-type. Check documentation.", enchantTypeInput));
                return;
            }
            nbti.setString("enchant-type", enchantTypeInput);

            gui.addItem(new ItemStack[]{nbti.getItem()});
        }
    }

    public void updateGUI(ItemStack hoe, ItemStack itemClicked){

        ConfigurationSection menuSection = config.getConfigurationSection("hoe-upgrade-menu");
        NBTItem nbti = new NBTItem(hoe);

        String hasteLevel = String.valueOf(nbti.getInteger("HASTE_LEVEL"));
        String fortuneLevel = String.valueOf(nbti.getInteger("FORTUNE_LEVEL"));
        String tokenLevel = String.valueOf(nbti.getInteger("TOKEN_LEVEL"));
        String keyFinderLevel = String.valueOf(nbti.getInteger("KEYFINDER_LEVEL"));
        String speedLevel = String.valueOf(nbti.getInteger("SPEED_LEVEL"));

        ItemMeta meta = itemClicked.getItemMeta();

        List<String> lores = new ArrayList<>();

        for(String s : menuSection.getStringList( ".1.lore")){
            s = s.replace("%HASTE_LEVEL%", hasteLevel);
            s = s.replace("%FORTUNE_LEVEL%", fortuneLevel);
            s = s.replace("%TOKEN_LEVEL%", tokenLevel);
            s = s.replace("%KEYFINDER_LEVEL%", keyFinderLevel);
            s = s.replace("%SPEED_LEVEL%", speedLevel);

            lores.add(s);
        }
        meta.setLore(lores);
        itemClicked.setItemMeta(meta);
        lores.clear();

    }
}
