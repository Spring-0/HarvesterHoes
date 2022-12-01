package me.spring.harvesterhoes;

import de.tr7zw.nbtapi.NBTItem;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class UpdateHoe {


    private final Harvesterhoes plugin;

    public UpdateHoe(Harvesterhoes plg) {
        plugin = plg;
    }
    public void updateHoe(ItemStack hoe, String enchantUpgrade, int enchantIncrement, Player p){

        NBTItem nbti = new NBTItem(hoe);

        int hasteLevel = nbti.getInteger("HASTE_LEVEL");
        int fortuneLevel = nbti.getInteger("FORTUNE_LEVEL");
        int tokenLevel = nbti.getInteger("TOKEN_LEVEL");
        int keyFinderLevel = nbti.getInteger("KEYFINDER_LEVEL");

        switch(enchantUpgrade) {
            case "haste":
                hasteLevel += enchantIncrement;
                break;
            case "fortune":
                fortuneLevel += enchantIncrement;
                break;
            case "token-finder":
                tokenLevel += enchantIncrement;
                break;
            case "key-finder":
                keyFinderLevel += enchantIncrement;
                break;

        }


        ItemMeta meta = hoe.getItemMeta();

        List<String> lores = new ArrayList<>();

        for(String s: plugin.getConfig().getStringList("hoe-lore")){

            s = s.replaceAll("%HASTE_LEVEL%", String.valueOf(hasteLevel));
            s = s.replaceAll("%FORTUNE_LEVEL%", String.valueOf(fortuneLevel));
            s = s.replaceAll("%TOKEN_LEVEL%", String.valueOf(tokenLevel));
            s = s.replaceAll("%KEYFINDER_LEVEL%", String.valueOf(keyFinderLevel));

            lores.add(Util.msgColorCode(s));
        }

        meta.setLore(lores);
        hoe.setItemMeta(meta);

        nbti = new NBTItem(hoe);

        nbti.setInteger("HASTE_LEVEL", hasteLevel);
        nbti.setInteger("FORTUNE_LEVEL", fortuneLevel);
        nbti.setInteger("TOKEN_LEVEL", tokenLevel);
        nbti.setInteger("KEYFINDER_LEVEL", keyFinderLevel);

        p.setItemInHand(nbti.getItem());

    }

}
