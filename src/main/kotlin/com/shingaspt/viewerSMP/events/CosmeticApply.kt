package com.shingaspt.viewerSMP.events

import com.shingaspt.viewerSMP.ViewerSMP
import io.papermc.paper.registry.data.dialog.body.DialogBody.item
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.components.CustomModelDataComponent


class CosmeticApply : Listener {

    private val swords = listOf(
        Material.WOODEN_SWORD,
        Material.STONE_SWORD,
        Material.COPPER_SWORD,
        Material.IRON_SWORD,
        Material.GOLDEN_SWORD,
        Material.DIAMOND_SWORD,
        Material.NETHERITE_SWORD,
    )

    private val config = ViewerSMP.instance.configManager

    fun isSword(type: Material): Boolean {
        return swords.contains(type)
    }

    fun isCosmeticToken(item: ItemStack) : Int {
        if (item.type == Material.PAPER) {
            val itemMeta = item.itemMeta
            if (itemMeta.hasCustomModelDataComponent()) {
                val modelData = itemMeta.customModelDataComponent.floats.first().toInt()
                val path = "CosmeticTokens.Token$modelData.ModelData"
                if(config.pathExists(path)) {
                    return config.getInt(path)
                }
            }
        }
        return 0
    }

    @EventHandler
    fun onInventoryClick(event: InventoryClickEvent) {
        val player = event.whoClicked as Player
        val swordItem = event.currentItem
        val tokenItem = event.cursor

        if (swordItem != null) {
            if (isSword(swordItem.type)) {
                val token = isCosmeticToken(tokenItem)
                if (token != 0) {
                    //Get Name and Enchants of old Sword
                    val swordName = swordItem.itemMeta.displayName()
                    val swordEnchants = swordItem.enchantments

                    //Create item stack of new sword
                    val newSword = ItemStack(swordItem.type, 1)
                    val swordItemMeta = newSword.itemMeta

                    //Apply the custom model data to the new sword
                    val cmd = swordItemMeta.customModelDataComponent
                    cmd.floats = listOf(token.toFloat())
                    swordItemMeta.setCustomModelDataComponent(cmd)

                    //Apply the enchants to new sword
                    for ((enchant, level) in swordEnchants) {
                        swordItemMeta.addEnchant(enchant, level, true) // true = ignore level cap
                    }

                    //Apply the name to new sword
                    swordItemMeta.displayName(swordName)

                    //Apply the meta with all the changes and give item to player
                    newSword.itemMeta = swordItemMeta

                    event.isCancelled = true
                    event.currentItem = null
                    event.view.cursor.let { event.view.setCursor(null) }

                    player.inventory.addItem(newSword)
                }
            }
        }
    }

}