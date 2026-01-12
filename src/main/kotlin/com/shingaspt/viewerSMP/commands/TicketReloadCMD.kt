package com.shingaspt.viewerSMP.commands

import com.shingaspt.viewerSMP.ViewerSMP
import com.shingaspt.viewerSMP.utils.ConfigManager
import com.shingaspt.viewerSMP.utils.MMParser
import io.papermc.paper.command.brigadier.BasicCommand
import io.papermc.paper.command.brigadier.CommandSourceStack

class TicketReloadCMD : BasicCommand {

    override fun execute(
        commandSourceStack: CommandSourceStack,
        args: Array<out String>
    ) {
        val sender = commandSourceStack.sender
        val config = ViewerSMP.configManager
        config.reload()
        sender.sendMessage(MMParser.getMsg("<dark_red>Ticket Config - <red>ᴄᴏɴғɪɢ ғɪʟᴇ ʜᴀs ʙᴇᴇɴ ʀᴇʟᴏᴀᴅᴇᴅ!"))
    }

    override fun permission(): String {
        return "viewersmp.config.reload"
    }

}