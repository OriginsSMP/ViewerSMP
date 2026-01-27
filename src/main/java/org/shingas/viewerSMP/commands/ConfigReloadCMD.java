package org.shingas.viewerSMP.commands;

import com.mojang.brigadier.tree.LiteralCommandNode;
import io.papermc.paper.command.brigadier.BasicCommand;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import io.papermc.paper.command.brigadier.Commands;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import org.bukkit.command.CommandSender;
import org.jspecify.annotations.Nullable;
import org.shingas.viewerSMP.ViewerSMP;
import org.shingas.viewerSMP.utils.ConfigManager;
import org.shingas.viewerSMP.utils.MMParser;

public class ConfigReloadCMD implements BasicCommand {


    @Override
    public void execute(CommandSourceStack ctx, String[] args) {
        CommandSender sender = ctx.getSender();
        ConfigManager config = ViewerSMP.getConfigManager();
        config.reload();
        TagResolver tags = TagResolver.resolver(
                Placeholder.parsed("prefix", config.getString("Prefix")),
                Placeholder.parsed("message", config.getString("Messages.configReload"))
        );
        sender.sendMessage(MMParser.getMsgTags("<prefix> <message>", tags));
    }

    @Override
    public @Nullable String permission() {
        return "viewersmp.config.reload";
    }
}
