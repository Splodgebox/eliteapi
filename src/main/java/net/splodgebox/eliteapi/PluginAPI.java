package net.splodgebox.eliteapi;

import net.splodgebox.eliteapi.chat.Chat;
import net.splodgebox.eliteapi.gui.menu.listeners.BukkitMenuListener;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class PluginAPI {

    public static void implementMenuListeners(JavaPlugin plugin) {
        Bukkit.getPluginManager().registerEvents(new BukkitMenuListener(), plugin);
    }

}