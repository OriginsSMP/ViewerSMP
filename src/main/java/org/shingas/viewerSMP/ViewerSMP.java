package org.shingas.viewerSMP;

import io.papermc.paper.plugin.lifecycle.event.types.LifecycleEvents;
import org.bukkit.plugin.java.JavaPlugin;
import org.shingas.viewerSMP.commands.ConfigReloadCMD;
import org.shingas.viewerSMP.events.CosmeticApply;
import org.shingas.viewerSMP.utils.ConfigManager;

public final class ViewerSMP extends JavaPlugin {

    private static ViewerSMP instance;
    private static ConfigManager configManager;

    @Override
    public void onEnable() {
        // Plugin startup logic

        getLogger().info("ViewerSMP is starting...");
        instance = this;

        getLogger().info("Loading config...");
        configManager = new ConfigManager();
        getLogger().info("Config loaded!");

        getServer().getPluginManager().registerEvents(new CosmeticApply(), this);


        getLogger().info("Registering commands...");
        getLifecycleManager().registerEventHandler(LifecycleEvents.COMMANDS, event -> {
            event.registrar().register("vsmpreload", new ConfigReloadCMD());
        });
        getLogger().info("Registered 1 commands:");
        getLogger().info("- /vsmpreload");

        getLogger().info("ViewerSMP has started.");

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic

        getLogger().info("ViewerSMP is shutting down...");
        getLogger().info("Saving config...");
        configManager.save();
        getLogger().info("Config saved!");
        getLogger().info("ViewerSMP is offline.");
    }

    public static ViewerSMP getInstance() {
        return instance;
    }

    public static ConfigManager getConfigManager() {
        return configManager;
    }

}
