package ru.danka.zapor;


import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;
import java.util.Collections;

public class ZaporInventory{

    public void createInventory(Player player){
        ZaporInventoryHolder holder = new ZaporInventoryHolder();
        Inventory inventory = Bukkit.createInventory(holder, 27,"репер");
        holder.setInventory(inventory);
        ItemStack itemStack = new ItemStack(Material.DIAMOND);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.addEnchant(Enchantment.ARROW_DAMAGE, 54,true);
        itemMeta.setDisplayName("Запористый алмазик");
        itemMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        itemMeta.setLore(Collections.singletonList("Запористый алмаз лично из Запористых запасов"));
        itemStack.setItemMeta(itemMeta);
        inventory.setItem(13, itemStack);



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
