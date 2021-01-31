package ru.danka.zapor;

import net.minecraft.server.v1_12_R1.NBTTagCompound;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.craftbukkit.v1_12_R1.entity.CraftItem;
import org.bukkit.craftbukkit.v1_12_R1.inventory.CraftItemStack;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public class ZaporListener implements Listener {
    private final JavaPlugin plugin;

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

    @EventHandler
    public  void zaporDiamond(InventoryClickEvent e){
        if (e.getClickedInventory().getHolder() instanceof ZaporInventory.ZaporInventoryHolder) {
            e.setCancelled(true);
            if (e.getCurrentItem().getType() == Material.DIAMOND){
                e.getWhoClicked().getInventory().addItem( ItemStacks.create(Material.DIAMOND).setDisplayName(ChatColor.GOLD + "Хуй тебе а не алмаз от Запористого"));
            }
        }
        if (e.getWhoClicked().getOpenInventory().getTopInventory().getHolder() instanceof ZaporInventory.ZaporInventoryHolder) {
            e.setCancelled(true);
        }

    }

    @EventHandler
    public void riddleBook( InventoryClickEvent e){
        if (e.getClickedInventory().getHolder() instanceof ZaporRiddle.ZaporInventoryHolder){
            e.setCancelled(true);
        }
        if (e.getWhoClicked().getOpenInventory().getTopInventory().getHolder() instanceof ZaporRiddle.ZaporInventoryHolder){
            e.setCancelled(true);
        }
        if (e.getCurrentItem().getType() == Material.EMERALD_BLOCK){
            Location location = e.getWhoClicked().getLocation();
            location.add(0,30,0);
            location.getBlock().setType(Material.ANVIL);
            Player dolbaeb = (Player)e.getWhoClicked();
            dolbaeb.sendTitle(ChatColor.RED +"НЕПРАВИЛЬНО БЛЯТЬ", ChatColor.RED +"ТОБИ ПИЗДА", 20,20,20);
            e.getWhoClicked().closeInventory();
        }
        if (e.getCurrentItem().getType()== Material.DIAMOND_BLOCK){
            Player dolbaed = (Player)e.getWhoClicked();
            dolbaed.sendTitle(ChatColor.GREEN +"ПРАВИЛЬНО ЕБАТЬ", ChatColor.GREEN +"ХУЯ ТЫ УМНЫЙ",20,20,20);
            Location location = e.getWhoClicked().getLocation();
            Location location1 = e.getWhoClicked().getLocation();
            location.add (0,10,0);
            location1.add (0,2,0);
            e.getWhoClicked().closeInventory();
            for (int i = 0; i < 64; i++) {
                location.add(0,i,0);
                ExperienceOrb exp = location1.getWorld().spawn(location, ExperienceOrb.class);
                exp.setExperience(100);
                location.getWorld().dropItemNaturally(location, ItemStacks.create(Material.DIAMOND));

            }
        }

        if (e.getCurrentItem().getType() == Material.REDSTONE_BLOCK){
            Player dolbaeb = (Player)e.getWhoClicked();
            dolbaeb.sendTitle(ChatColor.MAGIC +"ШИЛО ТАРАКАНЫ", ChatColor.MAGIC +"АУЕ БРАТ", 20,20,20);
            e.getWhoClicked().closeInventory();
            Location location = e.getWhoClicked().getLocation();
            for (int i = 0; i < 20; i++) {
                Silverfish silverfish = location.getWorld().spawn(location, Silverfish.class);
                silverfish.setCustomName(ChatColor.DARK_GREEN +"ТАРАКАНЫ");
                silverfish.setCustomNameVisible(true);

            }


        }
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
