package com.shingaspt.viewerSMP.utils

import com.shingaspt.viewerSMP.ViewerSMP
import org.bukkit.Bukkit
import org.bukkit.configuration.file.FileConfiguration

class ConfigManager {

    private lateinit var config: FileConfiguration
    private var plugin = ViewerSMP.instance

    init {
        setupConfig()
    }

    fun setupConfig() {
        // Only saves default config if it doesn't exist yet
        plugin.saveDefaultConfig()
        config = plugin.config
    }

    fun reload() {
        plugin.reloadConfig()
        config = plugin.config
        Bukkit.getLogger().info("Ticket's config was reloaded.")
    }

    fun getString(path: String): String {
        return config.getString(path).toString()
    }

    fun getInt(path: String): Int {
        return config.getInt(path)
    }

    fun pathExists(path: String): Boolean {
        return config.contains(path)
    }

    fun save() {
        plugin.saveConfig()
        Bukkit.getLogger().info("Ticket's config was saved.")
    }

}