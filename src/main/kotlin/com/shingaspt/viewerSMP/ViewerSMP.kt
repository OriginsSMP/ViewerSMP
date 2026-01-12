package com.shingaspt.viewerSMP

import com.shingaspt.viewerSMP.commands.IssueTicketCMD
import com.shingaspt.viewerSMP.commands.TicketReloadCMD
import com.shingaspt.viewerSMP.events.CosmeticApply
import com.shingaspt.viewerSMP.utils.ConfigManager
import github.scarsz.discordsrv.DiscordSRV
import io.papermc.paper.plugin.lifecycle.event.types.LifecycleEvents
import org.bukkit.plugin.java.JavaPlugin
import java.io.File

class ViewerSMP : JavaPlugin() {

    private lateinit var discordSRV : DiscordSRV

    companion object {
        lateinit var instance: ViewerSMP
            private set
        lateinit var configManager: ConfigManager
            private set
    }

    override fun onEnable() {
        // Plugin startup logic

        logger.info("ViewerSMP is starting...")
        instance = this

        logger.info("Loading config...")
        configManager = ConfigManager()
        logger.info("Config loaded!")

        server.pluginManager.registerEvents(CosmeticApply(), this)


        logger.info("Registering commands...")
        this.lifecycleManager.registerEventHandler(LifecycleEvents.COMMANDS) { event ->
            //event.registrar().register("ticket", IssueTicketCMD())
            event.registrar().register("ticketreload", TicketReloadCMD())
        }
        logger.info("Registered 2 commands:")
        logger.info("- /ticket <discord> <issue>")
        logger.info("- /ticketreload")



        /*
        logger.info("ViewerSMP Bot is starting...")
        ViewerSMPBot.start()
        logger.info("ViewerSMP Bot has started.")
        */

        logger.info("ViewerSMP has started.")


    }

    override fun onDisable() {
        logger.info("ViewerSMP is shutting down...")
        logger.info("Saving config...")
        configManager.save()
        logger.info("Config saved!")
        logger.info("ViewerSMP is offline.")
    }
}
