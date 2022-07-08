package me.sub.hcfactions.Events.Enchant;

import me.sub.hcfactions.Files.Locale.Locale;
import me.sub.hcfactions.Main.Main;
import me.sub.hcfactions.Utils.Color.C;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.AnvilInventory;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class AnvilEnchantEvent implements Listener {

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e){
        if(!e.isCancelled()){
            HumanEntity ent = e.getWhoClicked();
            if(ent instanceof Player){
                Player player = (Player)ent;
                Inventory inv = e.getInventory();
                if(inv instanceof AnvilInventory){
                    InventoryView view = e.getView();
                    int rawSlot = e.getRawSlot();
                    if(rawSlot == view.convertSlot(rawSlot)){
                        if(rawSlot == 2){
                            ItemStack item = e.getCurrentItem();
                            if(item != null){
                                ItemMeta meta = item.getItemMeta();
                                if(meta != null){
                                    if(meta.hasEnchants()) {
                                        for (String s : Main.getInstance().getConfig().getConfigurationSection("settings.limits.enchantments").getKeys(false)) {
                                            if (Enchantment.getByName(s) != null) {
                                                Enchantment enchantment = Enchantment.getByName(s);
                                                if (meta.getEnchants().containsKey(enchantment)) {
                                                    int enchantmentLevel = meta.getEnchants().get(enchantment);
                                                    if (enchantmentLevel >= Main.getInstance().getConfig().getInt("settings.limits.enchantments." + s)) {
                                                        e.setCancelled(true);
                                                        player.sendMessage(C.chat(Locale.get().getString("events.anvil.cancelled")));
                                                        return;
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
