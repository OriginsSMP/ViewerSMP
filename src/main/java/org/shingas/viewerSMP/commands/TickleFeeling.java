package org.shingas.viewerSMP.commands;

import io.papermc.paper.command.brigadier.BasicCommand;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.shingas.viewerSMP.ViewerSMP;
import org.shingas.viewerSMP.utils.MMParser;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class TickleFeeling implements BasicCommand {


    @Override
    public void execute(CommandSourceStack ctx, String[] args) {
        CommandSender sender = ctx.getSender();
        String prefix = ViewerSMP.getConfigManager().getString("Prefix");
        String msg = ViewerSMP.getConfigManager().getString("Messages.tickleFeeling");
        TagResolver tags = TagResolver.resolver(
                Placeholder.parsed("prefix", prefix),
                Placeholder.parsed("msg", msg.replace("<player>", sender.getName()))
        );

        if (args.length == 0) {
            sender.sendMessage(MMParser.getMsgTags("<prefix> <yellow>Usage <gray>- <green>/tickle <player>", tags));
        } else {
            if (args[0].equals(sender.getName())) {
                sender.sendMessage(MMParser.getMsgTags("<prefix> <green>You are not allowed to tickle yourself!", tags));
            }
            sender.sendMessage(MMParser.getMsgTags("<prefix> <msg>", tags));
        }

    }

    @Override
    public Collection<String> suggest(CommandSourceStack ctx, String[] args) {
        if (args.length == 1) {
            List<String> players = new ArrayList<>();
            for (Player p : Bukkit.getOnlinePlayers()) {
                players.add(p.getName());
            }
            return players;
        }
        return BasicCommand.super.suggest(ctx, args);
    }
}
