package org.ayosynk.nightVision;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.UUID;

public class EventListener implements Listener {
    private final JavaPlugin plugin;
    private final PlayerManager playerManager;
    private final ConfigManager configManager;
    private final EffectApplier effectApplier;

    public EventListener(JavaPlugin plugin, PlayerManager playerManager, ConfigManager configManager) {
        this.plugin = plugin;
        this.playerManager = playerManager;
        this.configManager = configManager;
        this.effectApplier = new EffectApplier(configManager);
    }

    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent event) {
        Player player = event.getPlayer();
        UUID uuid = player.getUniqueId();

        if (playerManager.isEnabled(uuid)) {
            new BukkitRunnable() {
                @Override
                public void run() {
                    effectApplier.applyNightVision(player);
                }
            }.runTaskLater(plugin, 20L);
        }
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        if (!configManager.applyOnJoin()) return;

        Player player = event.getPlayer();
        UUID uuid = player.getUniqueId();

        if (playerManager.isEnabled(uuid)) {
            new BukkitRunnable() {
                @Override
                public void run() {
                    effectApplier.applyNightVision(player);
                }
            }.runTaskLater(plugin, 40L);
        }
    }

    @EventHandler
    public void onMilkDrink(PlayerItemConsumeEvent event) {
        if (event.getItem().getType() != Material.MILK_BUCKET) return;

        Player player = event.getPlayer();
        UUID uuid = player.getUniqueId();

        if (playerManager.isEnabled(uuid)) {
            new BukkitRunnable() {
                @Override
                public void run() {
                    effectApplier.applyNightVision(player);
                }
            }.runTaskLater(plugin, 5L);
        }
    }
}