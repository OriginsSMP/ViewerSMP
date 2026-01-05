package com.shingaspt.viewerSMP.utils

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.minimessage.MiniMessage
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver

class MMParser {

    companion object {

        private val mm: MiniMessage = MiniMessage.miniMessage()

        fun getMsg(msg: String) : Component {
            return mm.deserialize(msg)
        }

        fun getMsgTags(msg: String, tags: TagResolver): Component {
            return mm.deserialize(msg, tags)
        }
    }

}