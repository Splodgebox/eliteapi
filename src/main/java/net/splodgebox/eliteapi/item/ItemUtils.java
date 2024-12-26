package net.splodgebox.eliteapi.item;

import de.tr7zw.nbtapi.NBT;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.function.Consumer;

/**
 * Utility class providing methods for handling ItemStacks and their associated NBT data.
 */
public class ItemUtils {

    /**
     * Checks if the given ItemStack has a specific NBT tag.
     *
     * @param itemStack The item stack to check.
     * @param key       The key of the NBT tag.
     * @return true if the tag exists and the item stack is valid; false otherwise.
     */
    public static boolean hasTag(ItemStack itemStack, String key) {
        if (!isValid(itemStack)) return false;

        return NBT.get(itemStack, nbt -> (boolean) nbt.hasTag(key));
    }

    /**
     * Retrieves the value of a specific NBT tag from the given ItemStack.
     *
     * @param itemStack The item stack from which to retrieve the tag.
     * @param key       The key of the NBT tag.
     * @return The value of the tag, or null if the tag doesn't exist or the item stack is invalid.
     */
    public static String getString(ItemStack itemStack, String key) {
        if (!hasTag(itemStack, key)) return null;

        return NBT.get(itemStack, nbt -> (String) nbt.getString(key));
    }

    /**
     * Retrieves the value of a specific NBT tag from the given ItemStack.
     *
     * @param itemStack The item stack from which to retrieve the tag.
     * @param key       The key of the NBT tag.
     * @return The value of the tag, or null if the tag doesn't exist or the item stack is invalid.
     */
    public static int getInteger(ItemStack itemStack, String key) {
        if (!hasTag(itemStack, key)) return 0;

        return NBT.get(itemStack, nbt -> (int) nbt.getInteger(key));
    }

    /**
     * Retrieves the value of a specific NBT tag from the given ItemStack.
     *
     * @param itemStack The item stack from which to retrieve the tag.
     * @param key       The key of the NBT tag.
     * @return The value of the tag, or null if the tag doesn't exist or the item stack is invalid.
     */
    public static boolean getBoolean(ItemStack itemStack, String key) {
        if (!hasTag(itemStack, key)) return false;

        return NBT.get(itemStack, nbt -> (boolean) nbt.getBoolean(key));
    }

    /**
     * Checks if given ItemStack is valid
     *
     * @param itemStack The item stack to check
     * @return true if ItemStack is valid
     */
    public static boolean isValid(ItemStack itemStack) {
        return itemStack != null && itemStack.getType() != Material.AIR;
    }

    /**
     * Reduces the amount of an active item in the player's inventory for the specified hand by the given amount.
     * If the remaining amount of the item becomes zero or less, it removes the item from the player's inventory.
     *
     * @param player The player whose inventory will be modified.
     * @param hand   The hand from which the item will be taken, either MAIN_HAND or OFF_HAND.
     * @param amount The amount to reduce from the item in the specified hand.
     */
    public static void takeActiveItem(Player player, CompatibleHand hand, int amount) {
        if (hand == CompatibleHand.MAIN_HAND) {
            ItemStack item = player.getInventory().getItemInMainHand();
            updateItemStack(player::setItemInHand, item, amount);
        } else {
            ItemStack item = player.getInventory().getItemInOffHand();
            updateItemStack(player.getEquipment()::setItemInOffHand, item, amount);
        }
    }

    /**
     * Updates the given ItemStack, reducing its amount by the specified value.
     * If the remaining amount drops to zero or below, sets the stack to `null`.
     *
     * @param updater A consumer function to update the item in the inventory.
     * @param item    The ItemStack being updated.
     * @param amount  The amount to decrease the stack by.
     */
    private static void updateItemStack(Consumer<ItemStack> updater, ItemStack item, int amount) {
        if (!isValid(item)) return;

        int remainingAmount = item.getAmount() - amount;
        if (remainingAmount > 0) {
            item.setAmount(remainingAmount);
            updater.accept(item);
        } else {
            updater.accept(null);
        }
    }

}
