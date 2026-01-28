package org.shingas.viewerSMP.events;

import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.components.CustomModelDataComponent;
import org.shingas.viewerSMP.ViewerSMP;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CosmeticApply implements Listener {

    private final FileConfiguration config = ViewerSMP.getConfigManager().cosmetics();

    public boolean isItemApplicable(String mat, int token) {
        String path = "CosmeticTokens.Token" + token + ".type";
        List<String> types = config.getStringList(path);
        for (String type : types) {
            if (mat.contains(type)) return true;
        }
        return false;
    }

    public int isCosmeticToken(ItemStack item) {
        if (item.getType() == Material.PAPER) {
            ItemMeta itemMeta = item.getItemMeta();
            if (itemMeta.hasCustomModelDataComponent()) {
                int modelData = itemMeta.getCustomModelDataComponent().getFloats().getFirst().intValue();
                String path = "CosmeticTokens.Token" + modelData + ".modelData";
                if(config.isSet(path)) {
                    return config.getInt(path);
                }
            }
        }
        return 0;
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        Player player = (Player) event.getWhoClicked();
        ItemStack item = event.getCurrentItem();
        ItemStack tokenItem = event.getCursor();

        if (item != null) {
            int token = isCosmeticToken(tokenItem);
            if (token != 0) {

                if (!isItemApplicable(item.getType().toString().toLowerCase(), token+1)) return;

                //Create the Item/ItemMetas
                ItemMeta oldMeta = item.getItemMeta();
                ItemStack newItem = new ItemStack(item.getType(), 1);
                ItemMeta newMeta = newItem.getItemMeta();

                //Get Name and Enchants of old Sword
                Component oldName = oldMeta.displayName();
                Map<Enchantment, Integer> oldEnchants = item.getEnchantments();


                //Get Damage and Apply to new Item
                if (oldMeta instanceof Damageable oldDamageable && newMeta instanceof Damageable newDamageable) {
                    int damage = oldDamageable.getDamage();
                    newDamageable.setDamage(damage);
                    newItem.setItemMeta(newMeta);
                }

                //Apply the custom model data to the new sword

                CustomModelDataComponent cmd = newMeta.getCustomModelDataComponent();
                List<Float> floats = new ArrayList<>(cmd.getFloats());
                floats.add((float) token);
                cmd.setFloats(floats);
                newMeta.setCustomModelDataComponent(cmd);

                //Apply the enchants to new sword
                for (Map.Entry<Enchantment, Integer> entry : oldEnchants.entrySet()) {
                    Enchantment enchant = entry.getKey();
                    int level = entry.getValue();
                    newMeta.addEnchant(enchant, level, true);
                }

                //Apply the name to new sword
                newMeta.displayName(oldName);


                //Apply the meta with all the changes and give item to player
                newItem.setItemMeta(newMeta);

                event.setCancelled(true);
                event.setCurrentItem(new ItemStack(Material.AIR));
                event.getView().getCursor().setAmount(0);
                player.getInventory().addItem(newItem);
            }
        }

    }

}
