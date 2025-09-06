# EliteAPI

EliteAPI is a comprehensive utility library for Bukkit/Spigot plugin development, providing a range of tools to simplify common tasks such as GUI creation, chat formatting, and item handling.

## Features

### Menu System
- Easy-to-use menu creation and management
- Support for updating/dynamic menus
- Automatic listener registration
- Configurable menu update scheduling (sync/async)
- Button system for interactive GUIs

### Chat Utilities
- Rich text formatting with color support
- Legacy color code support (using '&' symbol)
- Hex color support for modern Minecraft versions (1.16.1+)
- Multi-line message handling
- Placeholder replacement system
- Console logging utilities

### Item Utilities
- NBT data manipulation for ItemStacks
- Item validity checking
- Convenient methods for handling player items

### Message System
- Annotation-based message management
- Centralized language configuration
- Default message fallbacks
- YAML-based message storage and retrieval

## Installation

### Gradle
```groovy
repositories {
    maven { url 'https://jitpack.io' }
}

dependencies {
    implementation 'com.github.splodgebox:eliteapi:VERSION'
}
```

### Maven
```xml
<repositories>
    <repository>
        <id>jitpack.io</id>
        <url>https://jitpack.io</url>
    </repository>
</repositories>

<dependencies>
    <dependency>
        <groupId>com.github.splodgebox</groupId>
        <artifactId>eliteapi</artifactId>
        <version>VERSION</version>
    </dependency>
</dependencies>
```

## Usage

### Setting Up

Initialize the API in your plugin's `onEnable()` method:

```java
@Override
public void onEnable() {
    // Register menu listeners
    PluginAPI.implementMenuListeners(this);

    // Optional: Setup automatic menu updates
    // Parameters: plugin, startDelay, updateInterval, async
    PluginAPI.implementMenuTasks(this, 0, 20, false);
}
```

### Creating Menus

```java
// Create a basic menu
Menu menu = new Menu("My Menu", 3); // 3 rows

// Add items with click actions
menu.setButton(13, new Button(
    new ItemStack(Material.DIAMOND),
    (player, clickType) -> {
        player.sendMessage("You clicked a diamond!");
        return true; // true to close the menu, false to keep it open
    }
));

// Open the menu for a player
menu.open(player);
```

### Creating Dynamic Menus

```java
public class MyUpdatingMenu extends UpdatingMenu {
    public MyUpdatingMenu() {
        super("Dynamic Menu", 3);
    }

    @Override
    public void make(Player player) {
        // Initial menu setup
        setButton(13, new Button(new ItemStack(Material.CLOCK)));
    }

    @Override
    public void onUpdate(Player player, long tick) {
        // Update something in the menu periodically
        ItemStack clock = new ItemStack(Material.CLOCK);
        ItemMeta meta = clock.getItemMeta();
        meta.setDisplayName("Time: " + System.currentTimeMillis());
        clock.setItemMeta(meta);

        updateButton(13, clock);
    }
}
```

### Chat Formatting

```java
// Simple colored message
Chat.send(player, "&aHello &bWorld!");

// With placeholders
Chat.send(player, "&eWelcome, %player%!", "%player%", player.getName());

// Multi-line messages
Chat.send(player, "&aLine 1\n&bLine 2\n&cLine 3");

// Using hex colors (1.16.1+)
Chat.send(player, "#ff0000This is red! #00ff00This is green!");

// Log to console
Chat.log("&aPlugin enabled successfully!");
```

### Item Utilities

```java
// Check if an item has a specific NBT tag
boolean hasTag = ItemUtils.hasTag(item, "customKey");

// Get values from NBT
String value = ItemUtils.getString(item, "customKey");
int number = ItemUtils.getInteger(item, "numberValue");
boolean flag = ItemUtils.getBoolean(item, "flagValue");

// Check if an ItemStack is valid
boolean valid = ItemUtils.isValid(item);

// Take items from player's hand
ItemUtils.takeActiveItem(player, CompatibleHand.MAIN_HAND, 1);
```

### Item Builder

```java
// Create an item with the builder pattern
ItemStack diamond = new ItemBuilder(Material.DIAMOND)
    .name("&bShiny Diamond")
    .amount(5)
    .addLore("&7This is a special diamond")
    .addLore("&eOwner: %player%", "%player%", player.getName())
    .modelData(1001)
    .setNbtStr("custom-tag", "special-value")
    .build();

// Or start from an existing item
ItemStack enhanced = new ItemBuilder(existingItem)
    .addLore("&cAdditional lore line")
    .setNbtBool("modified", true)
    .build();
```

### Message System

```java
// 1. Define messages in your class with annotations
public class Messages {
    @Message(path = "messages.welcome", defaultMessage = "&aWelcome to the server!")
    public static String WELCOME;

    @Message(path = "messages.goodbye", defaultMessage = "&eSee you later!")
    public static String GOODBYE;
}

// 2. Load the messages in your plugin's onEnable
@Override
public void onEnable() {
    MessageManager messageManager = new MessageManager(this);
    messageManager.loadMessages(Messages.class);

    // Now you can use the messages
    // They'll be saved to lang.yml with default values if not present
    Chat.send(player, Messages.WELCOME);
}
```

## Requirements

- Java 17 or higher
- Bukkit/Spigot server 1.16+ recommended (for hex color support)
- Dependencies (included automatically):
  - NBT API
  - XSeries
  - ACF Commands framework

## License

This project is licensed under the MIT License - see the LICENSE file for details.

## Contributing

Contributions are welcome! Feel free to submit pull requests or create issues for bugs and feature requests.

## Support

If you need help or have questions, please create an issue on the GitHub repository.

---
Created by splodgebox
