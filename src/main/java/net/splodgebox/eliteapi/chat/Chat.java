package net.splodgebox.eliteapi.chat;

import de.tr7zw.nbtapi.utils.MinecraftVersion;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NonNull;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Represents a chat message with support for color codes and hex color translation.
 * <p>
 * This class allows sending colorized messages to players and retrieving the colorized message
 * as a string. It supports both legacy color codes (using '&') and hex color codes (e.g., '#FFFFFF')
 * based on the Minecraft version.
 * </p>
 *
 * <p><b>Note:</b> This class uses Lombok annotations for builder pattern and constructor generation.
 * Ensure that Lombok is properly set up in your project.</p>
 *
 * @author splodgebox
 */
@Builder
@AllArgsConstructor
public class Chat {

    /**
     * The message content to be sent or processed.
     */
    @NonNull
    private String message;

    /**
     * Sends the colorized message to the specified player.
     *
     * @param player the player to whom the message will be sent
     * @throws IllegalArgumentException if the player is null
     */
    public void sendMessage(@NonNull Player player) {
        player.sendMessage(color(message));
    }

    /**
     * Retrieves the colorized version of the message.
     *
     * @return the colorized message string, or null if the original message is null
     */
    public String getMessage() {
        return color(message);
    }


    /**
     * Sends a colorized message to the specified player. If the message contains multiple lines,
     * each line is colorized and sent individually.
     *
     * @param player the player to whom the message will be sent
     * @param message the message to be sent; can contain color codes and multiple lines
     * @throws IllegalArgumentException if the player is null
     */
    public static void send(Player player, String message) {
        if (message.contains("\n")) {
            for (String line : message.split("\n")) {
                player.sendMessage(color(line));
            }
            return;
        }

        player.sendMessage(color(message));
    }

    /**
     * Sends a formatted and colorized message to the specified player.
     *
     * @param player the player to whom the message will be sent; must not be null
     * @param message the message to be sent; supports placeholders and color codes
     * @param placeholders an optional array of placeholder-replacement pairs to format the message
     *                     before sending; must be provided in key-value pairs
     * @throws IllegalArgumentException if the player is null
     */
    public static void send(Player player, String message, String... placeholders) {
        player.sendMessage(color(replace(message, placeholders)));
    }

    /**
     * Logs a colorized message to the console.
     *
     * @param message the original message string to be logged; supports color codes
     */
    public static void log(String message) {
        Bukkit.getConsoleSender().sendMessage(color(message));
    }

    /**
     * Applies color processing to the given message.
     * <p>
     * This method checks if the message is non-null and non-empty. If valid, it proceeds to
     * translate color codes; otherwise, it returns the message as is.
     * </p>
     *
     * @param message the original message string to be colorized
     * @return the colorized message if applicable, or the original message if null or empty
     */
    public static String color(String message) {
        if (message == null || message.isEmpty()) {
            return message;
        }

        return translate(message);
    }

    /**
     * Translates color codes within the message.
     * <p>
     * This method supports both legacy color codes (using '&') and hex color codes (e.g., '#FFFFFF')
     * if the Minecraft version supports it (version ID >= 1161). Hex color codes are converted to
     * their corresponding {@link ChatColor} representations.
     * </p>
     *
     * @param message the message string containing color codes to be translated
     * @return the message with all color codes translated to their respective formats
     */
    public static String translate(String message) {
        // Check if the Minecraft version supports hex color codes
        if (MinecraftVersion.getVersion().getVersionId() >= 1161) {
            // Pattern to match hex color codes (e.g., #FFFFFF)
            Pattern pattern = Pattern.compile("#[a-fA-F0-9]{6}");
            Matcher matcher = pattern.matcher(message);

            // Iterate through all hex color codes and replace them with ChatColor equivalents
            while (matcher.find()) {
                String colorCode = message.substring(matcher.start(), matcher.end());
                String chatColor = net.md_5.bungee.api.ChatColor.of(colorCode) + "";
                message = message.replace(colorCode, chatColor);
                matcher = pattern.matcher(message); // Reset matcher after replacement
            }
        }

        // Translate legacy color codes using '&' to ChatColor codes
        return ChatColor.translateAlternateColorCodes('&', message);
    }

    public static String replace(String message, String regex, String replacement) {
    	return message.replaceAll(regex, replacement);
    }

    public static String replace(String message, String... placeholders) {
        for (int i = 0; i < placeholders.length; i += 2) {
            message = message.replace(placeholders[i], placeholders[i + 1]);
        }
        return message;
    }

}
