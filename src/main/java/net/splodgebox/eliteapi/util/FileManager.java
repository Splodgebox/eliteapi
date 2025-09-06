package net.splodgebox.eliteapi.util;

import kotlin.text.Charsets;
import lombok.Getter;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

/**
 * FileManager is a utility class for managing configuration files within a plugin.
 * It supports saving, reloading, and copying files, as well as managing file paths and YAML configurations.
 */
@Getter
public class FileManager {

    private final JavaPlugin plugin;
    private final String name;
    private final String dir;
    private final File file;
    private YamlConfiguration config;

    public FileManager(JavaPlugin plugin, String name, String dir) {
        this.plugin = plugin;
        this.name = name;
        this.dir = dir;

        this.file = new File(dir, name);
        if (!file.exists())
            plugin.saveResource(name, false);

        this.config = YamlConfiguration.loadConfiguration(this.file);
    }

    public void save() {
        try {
            this.config.save(this.file);
        } catch (Exception e) {
            throw new RuntimeException("Failed to save file: ", e);
        }
    }

    public void reload() {
        try {
            this.config = YamlConfiguration.loadConfiguration(this.file);

            InputStream stream = plugin.getResource(this.name);
            if (stream != null)
                this.config.setDefaults(YamlConfiguration.loadConfiguration(new InputStreamReader(stream, Charsets.UTF_8)));
        } catch (Exception e) {
            throw new RuntimeException("Failed to reload file: ", e);
        }
    }

    public boolean copy(File newFile) {
        try {
            Files.copy(this.file.toPath(), newFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
            return true;
        } catch (Exception e) {
            throw new RuntimeException("Failed to copy file: ", e);
        }
    }

}
