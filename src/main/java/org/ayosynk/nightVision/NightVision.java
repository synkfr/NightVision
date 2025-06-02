package org.ayosynk.nightVision;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemConsumeEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class NightVision extends JavaPlugin implements Listener {

    private final Set<UUID> enabledPlayers = new HashSet<>();
    private FileConfiguration config;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        config = getConfig();
        getServer().getPluginManager().registerEvents(this, this);
        getLogger().info("NightVision plugin loaded");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player player)) {
            sender.sendMessage("Only players can use this command.");
            return true;
        }

        UUID uuid = player.getUniqueId();

        if (enabledPlayers.contains(uuid)) {
            enabledPlayers.remove(uuid);
            player.removePotionEffect(PotionEffectType.NIGHT_VISION);
            notify(player, false);
        } else {
            enabledPlayers.add(uuid);
            applyNightVision(player);
            notify(player, true);
        }

        return true;
    }

    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent event) {
        Player player = event.getPlayer();
        if (enabledPlayers.contains(player.getUniqueId())) {
            getServer().getScheduler().runTaskLater(this, () -> applyNightVision(player), 20L); // 1s delay
        }
    }

    @EventHandler
    public void onMilkDrink(PlayerItemConsumeEvent event) {
        Player player = event.getPlayer();
        if (event.getItem().getType() == Material.MILK_BUCKET &&
                enabledPlayers.contains(player.getUniqueId())) {
            getServer().getScheduler().runTaskLater(this, () -> applyNightVision(player), 2L); // Reapply after milk
        }
    }

    private void applyNightVision(Player player) {
        PotionEffect effect = new PotionEffect(
                PotionEffectType.NIGHT_VISION,
                Integer.MAX_VALUE,
                1,
                true,
                false
        );
        player.addPotionEffect(effect);
    }

    private void notify(Player player, boolean enabled) {
        String msgKey = enabled ? "messages.enabled" : "messages.disabled";
        String titleKey = enabled ? "messages.title_enabled" : "messages.title_disabled";
        sendMessage(player, config.getString(msgKey));
        sendTitle(player, config.getString(titleKey));
    }

    private void sendMessage(Player player, String msg) {
        if (msg != null)
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', msg));
    }

    private void sendTitle(Player player, String message) {
        player.spigot().sendMessage(
                ChatMessageType.ACTION_BAR,
                new TextComponent(ChatColor.translateAlternateColorCodes('&', message))
        );
    }
}
