package me.spring.harvesterhoes.enchantments;

import me.spring.harvesterhoes.Harvesterhoes;

public class Haste {

    private static Harvesterhoes plugin;

    public Haste(Harvesterhoes plg){
        plugin = plg;
    }

    private static final int MAX_LEVEL = plugin.getConfig().getInt("enchants.haste.max-level");

    public static int getMaxLevel(){
        return MAX_LEVEL;
    }

}
