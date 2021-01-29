/*
 * Author: Yaroslav Skrebets
 * Copyright (c) 2019.
 */

package ru.sonicxd2.nemida.item;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public class ItemStacks {
    public static ItemStackWrapper create() {
        return new ItemStackWrapper();
    }

    public static ItemStackWrapper create(ItemStack itemStack) {
        return new ItemStackWrapper(itemStack);
    }

    public static ItemStackWrapper create(Material material) {
        return new ItemStackWrapper(material, 1);
    }

    public static ItemStackWrapper create(Material material, int amount) {
        return new ItemStackWrapper(material, amount);
    }

    public static ItemStackWrapper create(Material material, int amount, short damage) {
        return new ItemStackWrapper(material, amount, damage);
    }

    public static ItemStackWrapper create(Material material, int amount, int damage) {
        return create(material, amount, (short) damage);
    }
}
