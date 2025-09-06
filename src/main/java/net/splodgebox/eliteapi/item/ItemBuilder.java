package net.splodgebox.eliteapi.item;

import com.cryptomorin.xseries.XMaterial;
import de.tr7zw.nbtapi.NBT;
import net.splodgebox.eliteapi.chat.Chat;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class ItemBuilder {

    private final ItemStack itemStack;
    private String name;
    private List<String> lore;
    private int amount = 1;
    private int modelData = -1;

    public ItemBuilder(ItemStack itemStack) {
        this.itemStack = itemStack;
        this.lore = getLore(itemStack);
    }

    public ItemBuilder(Material material) {
        this.itemStack = new ItemStack(material);
        this.lore = new ArrayList<>();
    }

    public ItemBuilder(String name, Material material) {
        this.itemStack = new ItemStack(material);
        this.name = Chat.color(name);
        this.lore = new ArrayList<>();
    }

    public ItemBuilder(XMaterial xMaterial) {
        this.itemStack = new ItemStack(Objects.requireNonNull(xMaterial.parseItem()));
        this.lore = new ArrayList<>();
    }

    public ItemBuilder amount(int amount) {
        this.amount = amount;
        return this;
    }

    public ItemBuilder modelData(int modelData) {
        this.modelData = modelData;
        return this;
    }

    public ItemBuilder addLore(String message) {
        if (message.contains("\n")) {
            Arrays.stream(message.split("\n")).forEach(line -> this.lore.add(Chat.color(line)));
        } else {
            this.lore.add(Chat.color(message));
        }
        return this;
    }

    public ItemBuilder addLore(String message, String... placeholders) {
        this.lore.add(Chat.color(Chat.replace(message, placeholders)));
        return this;
    }

    public ItemBuilder lore(List<String> lore) {
        this.lore = new ArrayList<>();
        lore.forEach(line -> this.lore.add(Chat.color(line)));
        return this;
    }

    public ItemBuilder lore(List<String> lore, String... placeholders) {
        this.lore = new ArrayList<>();
        lore.forEach(line -> this.lore.add(Chat.color(Chat.replace(line, placeholders))));
        return this;
    }

    public ItemBuilder name(String name) {
        this.name = Chat.color(name);
        return this;
    }

    public ItemBuilder name(String name, String... placeholders) {
        this.name = Chat.color(Chat.replace(name, placeholders));
        return this;
    }

    public ItemBuilder setNbtStr(String key, String value) {
        NBT.modify(itemStack, nbt -> {
            nbt.setString(key, value);
        });
        return this;
    }

    public ItemBuilder setNbtInt(String key, int value) {
        NBT.modify(itemStack, nbt -> {
            nbt.setInteger(key, value);
        });
        return this;
    }

    public ItemBuilder setNbtBool(String key, boolean value) {
        NBT.modify(itemStack, nbt -> {
            nbt.setBoolean(key, value);
        });
        return this;
    }

    public ItemStack build() {
        ItemMeta itemMeta = itemStack.getItemMeta();
        if (itemMeta != null) {
            if (this.name != null) {
                itemMeta.setDisplayName(this.name);
            }
            if (!this.lore.isEmpty()) {
                itemMeta.setLore(this.lore);
            }
            if (this.modelData != -1) {
                itemMeta.setCustomModelData(this.modelData);
            }
            itemStack.setItemMeta(itemMeta);
        }
        itemStack.setAmount(this.amount);
        return itemStack;
    }

    public List<String> getLore(ItemStack itemStack) {
        if (itemStack.getItemMeta() == null) {
            return new ArrayList<>();
        }

        if (itemStack.getItemMeta().getLore() == null) {
            return new ArrayList<>();
        }

        return itemStack.getItemMeta().getLore();
    }
}
