package me.spring.harvesterhoes;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

/*
    Class containing methods used to make life easier
 */
public class Util {



    public Util(){}

    /*
        Method used to output message to console
     */
    public static void tellConsole(String msg){
        final String PREFIX = ChatColor.DARK_PURPLE + "[Spring] ";
        Bukkit.getServer().getConsoleSender().sendMessage(PREFIX + msg);
    }

    public static void addEffect(Player p, PotionEffectType pet, int level){
        p.addPotionEffect(new PotionEffect(pet, 3*20, level));
    }

    public static String msgColorCode(String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }

}
