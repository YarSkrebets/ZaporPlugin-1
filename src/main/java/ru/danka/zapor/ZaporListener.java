package ru.danka.zapor;

import net.minecraft.server.v1_12_R1.NBTTagCompound;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftItem;
import org.bukkit.craftbukkit.v1_12_R1.inventory.CraftItemStack;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Sheep;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public class ZaporListener implements Listener {
    private JavaPlugin plugin;

    public ZaporListener(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void sheepShearedSpawner(BlockPlaceEvent e){
        Location location = e.getBlockPlaced().getLocation();
        location.add(0,1,0);
        Sheep sheep = location.getWorld().spawn(location, Sheep.class);
        sheep.setSheared(true);
        sheep.setCustomName("Пажилая овца");
        sheep.setCustomNameVisible(true);
    }
    @EventHandler
    public void sheepPinkSpawner (BlockBreakEvent e){
        Player player = e.getPlayer();
        Location location = e.getBlock().getLocation();
        Sheep sheep = location.getWorld().spawn(location, Sheep.class);
        sheep.setColor(DyeColor.PINK);
        sheep.setAI(false);
        sheep.setCustomName( ChatColor.LIGHT_PURPLE +"Розовая овца");
        sheep.setCustomNameVisible(true);

    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        e.getPlayer().sendMessage("Ты хуесос");
    }

    @EventHandler
    public void handleBlockPlace(BlockPlaceEvent e) {
        ItemStack item = e.getItemInHand();
        EntityType entityType = getType(item);
        if (entityType == null) {
            return;
        }
        Block blockPlaced = e.getBlockPlaced();
        Bukkit.getScheduler().runTaskLater(plugin, () -> {
            CreatureSpawner spawner = (CreatureSpawner) blockPlaced.getState();
            spawner.setSpawnedType(entityType);
            spawner.update();
            e.getPlayer().sendMessage("Ты хуйло");
        }, 10L);
    }

    private EntityType getType(ItemStack item) {
        if (item.getType() == Material.MOB_SPAWNER) {
            NBTTagCompound tag = CraftItemStack.asNMSCopy(item).getTag();
            if (tag == null) {
                return null;
            }
            if (tag.hasKey("crispawner")) {
                return EntityType.valueOf(tag.getString("crispawner"));
            } else {
                return null;
            }
        } else return null;
    }
}
