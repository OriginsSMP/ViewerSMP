package com.shingaspt.viewerSMP

import com.shingaspt.viewerSMP.utils.ConfigManager
import github.scarsz.discordsrv.DiscordSRV
import net.dv8tion.jda.api.EmbedBuilder
import net.dv8tion.jda.api.JDA
import net.dv8tion.jda.api.JDABuilder
import net.dv8tion.jda.api.entities.Guild
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel
import net.dv8tion.jda.api.entities.channel.concrete.ThreadChannel
import org.bukkit.Bukkit
import java.util.UUID
import java.util.function.Consumer

class ViewerSMPBot {

    companion object {
        private lateinit var bot: JDA
        private lateinit var guild: Guild
        private lateinit var ticketChannel: TextChannel
        private lateinit var applicationChannel: TextChannel

        fun start() {

            val config = ViewerSMP.configManager

            try {
                val token = config.getString("Bot.token")
                val guildID: String = config.getString("Bot.guildID")
                val ticketChannelID: String = config.getString("Bot.ticketChannelID")
                val applicationChannelID: String = config.getString("Bot.applicationChannelID")

                try {
                    val jda = JDABuilder.createDefault(token)
                        .build()
                    jda.awaitReady()

                    bot = jda
                    guild = bot.getGuildById(guildID)!!
                    ticketChannel = guild.getTextChannelById(ticketChannelID)!!
                    applicationChannel = guild.getTextChannelById(applicationChannelID)!!
                } catch (e: Exception) {
                    Bukkit.getLogger().info(e.message)
                }

                Bukkit.getLogger().info("Candii Bot is now reporting to duty!")
                Bukkit.getLogger().info("Guild: $guild")
                Bukkit.getLogger().info("Ticket Channel: $ticketChannel")
                Bukkit.getLogger().info("Application Channel: $applicationChannel")

            } catch (e: Exception) {
                Bukkit.getLogger().severe("Cannot start bot, confirm if your config is ready to go!" + e.message)
            }
        }



    }

}