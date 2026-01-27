package org.shingas.viewerSMP.utils;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;

public class MMParser {

    private static final MiniMessage mm = MiniMessage.miniMessage();

    public static Component getMsg(String msg) {
        return mm.deserialize(msg);
    }

    public static Component getMsgTags(String msg, TagResolver tags) {
        return mm.deserialize(msg, tags);
    }

}
