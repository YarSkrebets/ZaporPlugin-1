package ru.sonicxd2.essent1al.devutils;

import com.google.common.collect.Sets;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.NumberConversions;
import org.bukkit.util.Vector;
import ru.sonicxd2.essent1al.addon.AddonPlugin;

import java.util.Set;

public class TntRunAddon implements AddonPlugin, Listener {
    private static final Set<Material> fallingBlocks = Sets.newHashSet(Material.SAND, Material.GRAVEL);
    private boolean enabled = false;
    private JavaPlugin plugin;


    @Override
    public void onEnable(JavaPlugin plugin) {
        this.plugin = plugin;
        plugin.getCommand("tntrun").setExecutor((commandSender, command, s, strings) -> {
            if (!commandSender.hasPermission("devutils.tntrun")) {
                return true;
            }
            enabled = !enabled;
            commandSender.sendMessage(ChatColor.GREEN + "TntRun теперь " + (enabled ? "включен" : "выключен"));
            return true;
        });
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onMove(PlayerMoveEvent e) {
        if (!enabled) {
            return;
        }
        e.getFrom().add(0, -1, 0);
        if (fallingBlocks.contains(e.getFrom().getBlock().getType())) {
            Bukkit.getScheduler().runTaskLater(plugin, () -> {
                Block block = getPlayerBlock(e.getFrom());
                if (block != null) {
                    setAir(block.getLocation());
                }
            }, 6);

        }
    }

    private static final double MODIFIER = 0.2;

    private Block getPlayerBlock(Location loc) {
        Block b = getBlock(loc, MODIFIER, -MODIFIER);
        if (b.getType() != Material.AIR) {
            return b;
        }
        b = getBlock(loc, -MODIFIER, MODIFIER);
        if (b.getType() != Material.AIR) {
            return b;
        }
        b = getBlock(loc, MODIFIER, MODIFIER);
        if (b.getType() != Material.AIR) {
            return b;
        }
        b = getBlock(loc, -MODIFIER, -MODIFIER);
        if (b.getType() != Material.AIR) {
            return b;
        }
        return null;
    }

    public Block getBlock(Location loc, double dX, double dZ) {
        return loc.getWorld().getBlockAt(NumberConversions.floor(loc.getX() + dX), loc.getBlockY(), NumberConversions.floor(loc.getZ() + dZ));
    }

    private void setAir(Location from) {
        from.getBlock().setType(Material.AIR);
        from.add(0, -1, 0).getBlock().setType(Material.AIR);
        from.add(0, 1, 0);
    }

    @Override
    public void onDisable(JavaPlugin plugin) {

    }
}
