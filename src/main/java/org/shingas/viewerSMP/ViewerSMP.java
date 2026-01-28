package org.shingas.viewerSMP;

import io.papermc.paper.plugin.lifecycle.event.types.LifecycleEvents;
import org.bukkit.plugin.java.JavaPlugin;
import org.shingas.viewerSMP.commands.ConfigReloadCMD;
import org.shingas.viewerSMP.commands.TickleFeeling;
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

        getLogger().info("Loading configs...");
        configManager = new ConfigManager(this);
        configManager.loadConfigs();
        getLogger().info("Configs loaded!");

        getServer().getPluginManager().registerEvents(new CosmeticApply(), this);


        getLogger().info("Registering commands...");
        getLifecycleManager().registerEventHandler(LifecycleEvents.COMMANDS, event -> {
            event.registrar().register("vsmpreload", new ConfigReloadCMD());
            event.registrar().register("tickle", new TickleFeeling());
        });
        getLogger().info("Registered 1 commands:");
        getLogger().info("- /vsmpreload <on/off>");
        getLogger().info("- /tickle <player>");

        getLogger().info("ViewerSMP has started.");

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic

        getLogger().info("ViewerSMP is shutting down...");
        getLogger().info("Saving configs...");
        configManager.reloadAll();
        getLogger().info("Configs saved!");
        getLogger().info("ViewerSMP is offline.");
    }

    public static ViewerSMP getInstance() {
        return instance;
    }

    public static ConfigManager getConfigManager() {
        return configManager;
    }

}
