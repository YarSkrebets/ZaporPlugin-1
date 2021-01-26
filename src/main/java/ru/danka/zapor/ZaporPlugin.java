package ru.danka.zapor;

import net.minecraft.server.v1_12_R1.Block;
import net.minecraft.server.v1_12_R1.NBTTagCompound;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_12_R1.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.entity.Skeleton;
import org.bukkit.entity.Zombie;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public final class ZaporPlugin extends JavaPlugin {

    @Override
    public void onEnable() {
        getCommand("запор").setExecutor((sender, command, label, args) -> {
            for (int i = 0; i < 10; i++) {
                sender.sendMessage("ТЫ ПИДОРАС");
            }
            if (!(sender instanceof Player)) {
                sender.sendMessage("Ты блять не только пидорас ты еще и хуесос!");
                return true;
            }
            if (args.length > 0) {
                String message = String.join(" ", args);
                sender.sendMessage("ХУЙЛО СКАЗАЛ: " + message);
            }
            sender.sendMessage(ChatColor.RED + "Я узнал кто ты по ориентации");
            sender.sendMessage("ТЫ " + createRainbowMessage("ГОМОСЕК ЕБАННЫЙ УЕЗЖАЙ ИЗ ") + "РОССИИ");

            Player player = (Player) sender;
            Location location = player.getLocation();
            Zombie zombie = location.getWorld().spawn(location, Zombie.class);
            zombie.setCustomName(ChatColor.RED + "LEATHER MAN");
            zombie.setCustomNameVisible(true);

            zombie.getEquipment().setLeggings(new ItemStack(Material.LEATHER_LEGGINGS));

            ItemStack item = new ItemStack(Material.MOB_SPAWNER);
            net.minecraft.server.v1_12_R1.ItemStack nmsItem = CraftItemStack.asNMSCopy(item);
            NBTTagCompound nbtTagCompound = new NBTTagCompound();
            nbtTagCompound.setString("crispawner", "VILLAGER");
            nmsItem.setTag(nbtTagCompound);
            item = CraftItemStack.asBukkitCopy(nmsItem);

            player.getInventory().addItem(item);
            
            return true;
        });

        getCommand("габрираЗапор").setExecutor((sender, command, label, args)->{
            Player player = (Player) sender;
            //по запористому
//            Location location = player.getLocation();
//            location.add(5,5,5);
            //заебись
//            Location location = player.getLocation();
//            location.add(location.getDirection().multiply(5));
//            location.add(0,2,0);
            Location target = player.getTargetBlock(null, 30).getLocation();
            target.add(0,1,0);
            Skeleton skeleton = target.getWorld().spawn(target, Skeleton.class);
            skeleton.getEquipment().setItemInMainHand(new ItemStack(Material.GOLD_SWORD));
            skeleton.setCustomName(ChatColor.GOLD + "ДОЛБАЕБ С ЗОЛОТЫМ МЕЧОМ");
            skeleton.setCustomNameVisible(true);
            return true;
        });

        getCommand("лехаблять").setExecutor((sender, command, label, args) ->{
            Player player = (Player) sender;
            player.getEquipment().setBoots(new ItemStack(Material.DIAMOND_BOOTS));
            player.getEquipment().setChestplate(new ItemStack(Material.DIAMOND_CHESTPLATE));
            player.getEquipment().setHelmet(new ItemStack(Material.DIAMOND_HELMET));
            player.getEquipment().setLeggings(new ItemStack(Material.DIAMOND_LEGGINGS));
            player.getEquipment().setItemInMainHand(new ItemStack(Material.DIAMOND_SWORD));
            //player.setFlying(true); не работает да и хуй с ним

            return true;
        });
        Bukkit.getPluginManager().registerEvents(new ZaporListener(this), this);

    }

    public static String createRainbowMessage(String message) {
        StringBuilder builder = new StringBuilder();
        ChatColor[] chatColors = ChatColor.values();
        int i = 0;
        for (char c : message.toCharArray()) {
            builder.append(chatColors[i++]).append(c);
            if (i == chatColors.length) i = 0;
        }
        return builder.toString();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
