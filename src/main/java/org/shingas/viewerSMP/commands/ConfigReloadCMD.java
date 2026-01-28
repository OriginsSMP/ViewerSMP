package org.shingas.viewerSMP.commands;

import io.papermc.paper.command.brigadier.BasicCommand;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import org.bukkit.command.CommandSender;
import org.jspecify.annotations.Nullable;
import org.shingas.viewerSMP.ViewerSMP;
import org.shingas.viewerSMP.utils.ConfigManager;
import org.shingas.viewerSMP.utils.MMParser;

import java.util.ArrayList;
import java.util.List;

public class ConfigReloadCMD implements BasicCommand {


    @Override
    public void execute(CommandSourceStack ctx, String[] args) {
        CommandSender sender = ctx.getSender();
        ConfigManager config = ViewerSMP.getConfigManager();
        config.reloadAll();

        TagResolver tags = TagResolver.resolver(
                Placeholder.parsed("prefix", config.config().getString("Prefix"))
        );

        String main = config.config().getString("ConfigReload");
        String cosmetics = config.config().getString("CosmeticsReload");
        String feelings = config.config().getString("FeelingsReload");

        sender.sendMessage(MMParser.getMsgTags("<prefix> " + main, tags));
        sender.sendMessage(MMParser.getMsgTags("<prefix> " + cosmetics, tags));
        sender.sendMessage(MMParser.getMsgTags("<prefix> " + feelings, tags));
    }

    @Override
    public @Nullable String permission() {
        return "viewersmp.config.reload";
    }
}
