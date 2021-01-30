/*
 * Author: Yaroslav Skrebets
 * Copyright (c) 2019.
 */

package ru.danka.zapor;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ItemStackWrapper extends ItemStack {

    public ItemStackWrapper() {
    }

    public ItemStackWrapper(Material type) {
        super(type);
    }

    public ItemStackWrapper(Material type, int amount) {
        super(type, amount);
    }

    public ItemStackWrapper(Material type, int amount, short damage) {
        super(type, amount, damage);
    }

    public ItemStackWrapper(ItemStack stack) throws IllegalArgumentException {
        super(stack);
    }

    public ItemStackWrapper setDisplayName(String name) {
        ItemMeta itemMeta = getItemMeta();
        itemMeta.setDisplayName(name);
        setItemMeta(itemMeta);
        return this;
    }

    public ItemStackWrapper setLore(List<String> lore) {
        if (lore == null) lore = new ArrayList<>();
        else lore = new ArrayList<>(lore);
        ItemMeta itemMeta = getItemMeta();
        itemMeta.setLore(lore);
        setItemMeta(itemMeta);
        return this;
    }

    public ItemStackWrapper setLore(String... lore) {
        return setLore(Arrays.asList(lore));
    }

    public ItemStackWrapper addItemFlags(ItemFlag... itemFlags) {
        ItemMeta itemMeta = getItemMeta();
        itemMeta.addItemFlags(itemFlags);
        setItemMeta(itemMeta);
        return this;
    }

    public ItemStackWrapper addEnchant(Enchantment ench, int level) {
        ItemMeta itemMeta = getItemMeta();
        if (level != 0) {
            itemMeta.addEnchant(ench, level, true);
        }
        setItemMeta(itemMeta);
        return this;
    }

    public ItemStackEditSession<ItemStackWrapper> editSession() {
        return ItemStackEditSession.warp(this);
    }
}
