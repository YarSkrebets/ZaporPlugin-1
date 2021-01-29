/*
 * Author: Yaroslav Skrebets
 * Copyright (c) 2019.
 */

package ru.sonicxd2.nemida.item;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.UnaryOperator;

public class ItemStackEditSession<T extends ItemStack> {
    private T item;
    private ItemMeta meta;
    private String name;
    private List<String> lore;


    private ItemStackEditSession(T itemStack) {
        item = itemStack;
        meta = item.getItemMeta();
    }

    List<String> getLore() {
        if (lore == null) {
            return meta.getLore() == null ? new ArrayList<>() : meta.getLore();
        }
        return lore;
    }

    String getName() {
        return name == null ? meta.getDisplayName() : name;
    }

    /**
     * Apply all changing.
     */
    private void apply() {
        if (name != null) {
            meta.setDisplayName(name);
            name = null;
        }
        if (lore != null) {
            meta.setLore(lore);
            lore = null;
        }
        item.setItemMeta(meta);
        meta = null;
    }


    public ItemStackEditSession setLore(List<String> lore) {
        this.lore = lore instanceof ArrayList ? lore : new ArrayList<>(lore);
        return this;
    }

    public ItemStackEditSession setName(String name) {
        this.name = name;
        return this;
    }

    public ItemStackEditSession addLore(String... strings) {
        if (lore == null) {
            setLore(new ArrayList<>());
        }
        Collections.addAll(lore, strings);
        return this;
    }

    public ItemStackEditSession change(Consumer<ItemStack> func) {
        apply();
        func.accept(item);
        this.meta = item.getItemMeta();
        return this;
    }

    public ItemStackEditSession fullChange(UnaryOperator<T> func) {
        apply();
        item = func.apply(item);
        this.meta = item.getItemMeta();
        return this;
    }

    public static <T extends ItemStack> ItemStackEditSession<T> clone(T item) {
        return new ItemStackEditSession(item.clone());
    }

    public static <T extends ItemStack> ItemStackEditSession<T> warp(T item) {
        return new ItemStackEditSession(item);
    }

    public static ItemStackEditSession<ItemStack> create(Material material, int amount) {
        return warp(new ItemStack(material, amount));
    }

}
