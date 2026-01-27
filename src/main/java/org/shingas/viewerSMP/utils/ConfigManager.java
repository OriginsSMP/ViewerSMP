package org.shingas.viewerSMP.utils;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.shingas.viewerSMP.ViewerSMP;

import java.util.List;

public class ConfigManager {

    private FileConfiguration config;
    private ViewerSMP plugin;

    public ConfigManager() {
        plugin = ViewerSMP.getInstance();
        plugin.saveDefaultConfig();
        config = plugin.getConfig();
    }

    public void reload() {
        plugin.reloadConfig();
        config = plugin.getConfig();
        Bukkit.getLogger().info("Ticket's config was reloaded.");
    }


    public String getString(String path)  {
        return config.getString(path);
    }

    public int getInt(String path)  {
        return config.getInt(path);
    }

    public List<String> getList(String path) {
        return config.getStringList(path);
    }

    public boolean pathExists(String path) {
        return config.contains(path);
    }

    public void save() {
        plugin.saveConfig();
        Bukkit.getLogger().info("Ticket's config was saved.");
    }

}
