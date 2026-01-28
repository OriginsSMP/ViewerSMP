package org.shingas.viewerSMP.commands;

import io.papermc.paper.command.brigadier.BasicCommand;
import io.papermc.paper.command.brigadier.CommandSourceStack;
import net.kyori.adventure.text.minimessage.tag.resolver.Placeholder;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.Registry;
import org.bukkit.Sound;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.shingas.viewerSMP.ViewerSMP;
import org.shingas.viewerSMP.utils.MMParser;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class FeelingCMD implements BasicCommand {

    private String feeling;

    public FeelingCMD(String feeling) {
        this.feeling = feeling;
    }

    @Override
    public void execute(CommandSourceStack ctx, String[] args) {
        CommandSender sender = ctx.getSender();
        FileConfiguration feelingsConfig = ViewerSMP.getConfigManager().feelings();

        String prefix = feelingsConfig.getString("Prefix");
        TagResolver tags = TagResolver.resolver(
                Placeholder.parsed("prefix", prefix)
        );

        if (args.length == 0) {
            sender.sendMessage(MMParser.getMsgTags("<prefix> <yellow>Usage <gray>- <green>/" + feeling + " <player>", tags));
            return;

        }
        if (args[0].equals(sender.getName())) {
            sender.sendMessage(MMParser.getMsgTags("<prefix> <green>You show feelings to other people, not yourself.", tags));
            return;
        }

        Player target = Bukkit.getPlayer(args[0]);
        if (target == null) {
            sender.sendMessage(MMParser.getMsgTags("<prefix> <green>Are you sure this player exists?", tags));
            return;
        }

        String senderMsg = feelingsConfig.getString("Feelings." + feeling + ".Sender");
        String targetMsg = feelingsConfig.getString("Feelings." + feeling + ".Target");

        tags = TagResolver.resolver(
                Placeholder.parsed("prefix", prefix),
                Placeholder.parsed("sender", sender.getName()),
                Placeholder.parsed("target", target.getName())
        );

        ConfigurationSection soundSection = feelingsConfig.getConfigurationSection("Feelings."+ feeling +".Sound");
        String soundName = feelingsConfig.getString("Name");
        double volume = soundSection.getDouble("Volume");
        double pitch = soundSection.getDouble("Pitch");

        Sound sound = getSoundFromConfig(soundName);
        target.playSound(target.getLocation(), sound, (float) volume, (float) pitch);

        sender.sendMessage(MMParser.getMsgTags(senderMsg, tags));
        target.sendMessage(MMParser.getMsgTags(targetMsg, tags));

    }

    @Override
    public Collection<String> suggest(CommandSourceStack ctx, String[] args) {
        List<String> players = new ArrayList<>();
        for (Player p : Bukkit.getOnlinePlayers()) {
            players.add(p.getName());
        }
        return players;
    }

    public Sound getSoundFromConfig(String soundName) {
        // Convert config name to NamespacedKey
        NamespacedKey key = NamespacedKey.fromString(soundName, null);

        if (key == null) {
            Bukkit.getLogger().warning("Invalid sound key: " + soundName);
            return null;
        }

        // Look up the sound
        Sound sound = Registry.SOUNDS.get(key);

        if (sound == null) {
            Bukkit.getLogger().warning("Sound not found in registry: " + soundName);
        }

        return sound;
    }

}
