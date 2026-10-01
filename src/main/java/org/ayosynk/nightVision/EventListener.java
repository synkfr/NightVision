package org.ayosynk.nightVision;

import org.ayosynk.nightVision.scheduler.TaskScheduler;
import org.ayosynk.nightVision.updater.ModrinthUpdateChecker;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class EventListener implements Listener {

    private final JavaPlugin plugin;
    private final PlayerManager playerManager;
    private final ConfigManager configManager;
    private final EffectApplier effectApplier;
    private final ModrinthUpdateChecker updateChecker;

    public EventListener(JavaPlugin plugin, PlayerManager playerManager, ConfigManager configManager, ModrinthUpdateChecker updateChecker) {
        this.plugin = plugin;
        this.playerManager = playerManager;
        this.configManager = configManager;
        this.effectApplier = new EffectApplier(configManager);
        this.updateChecker = updateChecker;
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        boolean enabled = playerManager.loadPlayer(player);

        if (enabled && configManager.applyOnJoin()) {
            TaskScheduler.runEntityLater(plugin, player, () -> effectApplier.applyNightVision(player), 20L);
        }

        if (updateChecker != null) {
            TaskScheduler.runEntityLater(plugin, player, () -> updateChecker.notifyAdmin(player), 30L);
        }
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onPlayerQuit(PlayerQuitEvent event) {

        playerManager.unloadPlayer(event.getPlayer());
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onPlayerRespawn(PlayerRespawnEvent event) {
        Player player = event.getPlayer();

        if (playerManager.isEnabled(player)) {
            TaskScheduler.runEntityLater(plugin, player, () -> effectApplier.applyNightVision(player), 20L);
        }
    }

    @EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
    public void onMilkDrink(PlayerItemConsumeEvent event) {
        if (event.getItem().getType() != Material.MILK_BUCKET) return;

        Player player = event.getPlayer();
        if (playerManager.isEnabled(player)) {
            TaskScheduler.runEntityLater(plugin, player, () -> effectApplier.applyNightVision(player), 5L);
        }
    }
}
