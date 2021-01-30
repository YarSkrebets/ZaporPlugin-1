package ru.danka.zapor;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.meta.ItemMeta;

public class ZaporRiddle {
    public void createRiddleInventory(Player player){
        ZaporInventoryHolder holder = new ZaporInventoryHolder();
        Inventory inventory = Bukkit.createInventory(holder,45, "Загадка от Жака Фреско");
        holder.setInventory(inventory);
        inventory.setItem(13, ItemStacks.create(Material.BOOK).setDisplayName("Загадка от Жака Фреско").setLore("Было 2 козла.", "Сколько?").addEnchant(Enchantment.BINDING_CURSE, 1).addItemFlags(ItemFlag.HIDE_ENCHANTS));
        inventory.setItem(29, ItemStacks.create(Material.EMERALD_BLOCK).setDisplayName("Ответ #1").setLore("Два"));
        inventory.setItem(31, ItemStacks.create(Material.DIAMOND_BLOCK).setDisplayName("Ответ #2").setLore("Египет"));
        inventory.setItem(33, ItemStacks.create(Material.REDSTONE_BLOCK).setDisplayName("Ответ #3").setLore("Шило, тараканы"));
        player.openInventory(inventory);

    }


    public static class ZaporInventoryHolder implements InventoryHolder {
        private Inventory inventory;

        public void setInventory(Inventory inventory){
            this.inventory = inventory;
        }

        @Override
        public Inventory getInventory() {
            return inventory;
        }


    }
}
