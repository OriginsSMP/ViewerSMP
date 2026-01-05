package com.shingaspt.viewerSMP.commands

import com.shingaspt.viewerSMP.utils.MMParser
import io.papermc.paper.command.brigadier.BasicCommand
import io.papermc.paper.command.brigadier.CommandSourceStack
import org.bukkit.entity.Player

class IssueTicketCMD : BasicCommand {

    override fun execute(
        commandSourceStack: CommandSourceStack,
        args: Array<out String>
    ) {
        val sender = commandSourceStack.sender
        if (sender !is Player) {
            sender.sendMessage("This command can only be run by a player.")
            return
        }

        if (args.isEmpty()) {
            sender.sendMessage(MMParser.getMsg("<dark_red>Correct usage:<red> /ᴛɪᴄᴋᴇᴛ <ᴅɪꜱᴄᴏʀᴅ> <ɪɴꜰᴏ>"))
            return
        }

        val info = StringBuilder()
        if (args.size > 1){
            for (i in 1..<args.size) {
                info.append(args[i] + " ")
            }
        } else { info.append(args[0]) }

        val dcName = "dcname"
        val roles = StringBuilder()
        roles.append("<@$dcName>")

//        val ticket = IssueTicket()
//        for (role in ticket.getRoles()) {
//            val add = ConfigManager.getString("StaffRoles.$role")
//            roles.append("<@$add> ")
//        }
//
//        ViewerSMPBot.createTicket(
//            "issue",
//            dcName,
//            ticket.get(
//                dcName,
//                sender.name,
//                info.toString()
//            ),
//            roles.toString()
//        )

        sender.sendMessage(MMParser.getMsg("<dark_red>Ticket Created! <red>ᴏɴᴇ ᴏғ ᴏᴜʀ sᴛᴀғғ ᴡɪʟʟ ʙᴇ ᴡɪᴛʜ ʏᴏᴜ ᴀs sᴏᴏɴ ᴀs ᴘᴏssɪʙʟᴇ ᴛʜʀᴏᴜɢʜ ᴏᴜʀ ᴅɪsᴄᴏʀᴅ!"))
    }

}