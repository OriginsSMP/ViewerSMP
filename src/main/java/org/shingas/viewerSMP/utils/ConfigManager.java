package org.shingas.viewerSMP.utils;


import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import org.shingas.viewerSMP.ViewerSMP;
import org.shingas.viewerSMP.data.CustomConfig;

public class ConfigManager {

    private final JavaPlugin plugin;

    private CustomConfig mainConfig;
    private CustomConfig cosmetics;
    private CustomConfig feelings;

    public ConfigManager(ViewerSMP plugin) {
        this.plugin = plugin;
    }

    public void loadConfigs() {
        plugin.saveDefaultConfig(); // config.yml

        mainConfig = new CustomConfig(plugin, "config.yml");
        cosmetics = new CustomConfig(plugin, "cosmetics.yml");
        feelings = new CustomConfig(plugin, "feelings.yml");
    }

    public void reloadAll() {
        plugin.reloadConfig();
        mainConfig.reload();
        cosmetics.reload();
        feelings.reload();
    }

    public FileConfiguration config() {
        return mainConfig.get();
    }

    public FileConfiguration cosmetics() {
        return cosmetics.get();
    }

    public FileConfiguration feelings() {
        return feelings.get();
    }
}