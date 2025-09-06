package net.splodgebox.eliteapi;

import net.splodgebox.eliteapi.gui.Menu;
import net.splodgebox.eliteapi.gui.listeners.BukkitMenuListener;
import net.splodgebox.eliteapi.gui.types.UpdatingMenu;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class PluginAPI {

    public static void implementMenuTasks(JavaPlugin plugin, int start, int interval, boolean async) {
        Runnable runnable = () -> Menu.getMenus().forEach((uuid, menu) -> {
            if (!(menu instanceof UpdatingMenu updatingMenu))
                return;

            Player player = Bukkit.getPlayer(uuid);
            if (player == null || !player.isOnline())
                return;

            updatingMenu.onUpdate(player, 0);
            updatingMenu.update(player);
        });

        if (async) Bukkit.getScheduler().runTaskTimerAsynchronously(plugin, runnable, start, interval);
        else Bukkit.getScheduler().runTaskTimer(plugin, runnable, start, interval);
    }

    public static void implementMenuListeners(JavaPlugin plugin) {
        Bukkit.getPluginManager().registerEvents(new BukkitMenuListener(), plugin);
    }


}