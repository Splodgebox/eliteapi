package net.splodgebox.eliteapi.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RootCommandHandler implements CommandExecutor, TabCompleter {

    private final Map<String, CommandHandler> mainCommands = new HashMap<>();

    public void registerMainCommand(String mainCommand, CommandHandler handler) {
        mainCommands.put(mainCommand.toLowerCase(), handler);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 0) {
            sender.sendMessage("Available commands: " + String.join(", ", mainCommands.keySet()));
            return true;
        }

        CommandHandler handler = mainCommands.get(label.toLowerCase());
        if (handler != null) {
            return handler.onCommand(sender, command, label, args);
        }

        sender.sendMessage("Unknown command. Use /help for available commands.");
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        CommandHandler handler = mainCommands.get(command.getName().toLowerCase());
        if (handler != null) {
            return handler.onTabComplete(sender, command, alias, args);
        }

        return Collections.emptyList();
    }
}
