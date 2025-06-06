package org.ayosynk.nightVision;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;

public class EffectApplier {
    private final ConfigManager configManager;

    public EffectApplier(ConfigManager configManager) {
        this.configManager = configManager;
    }

    public void applyNightVision(Player player) {
        int duration = configManager.getEffectDuration() > 0 ?
                configManager.getEffectDuration() * 20 : // Convert seconds to ticks
                Integer.MAX_VALUE;

        PotionEffect effect = new PotionEffect(
                PotionEffectType.NIGHT_VISION,
                duration,
                1,
                true,
                configManager.showParticles()
        );

        player.addPotionEffect(effect);
    }

    public void removeNightVision(Player player) {
        player.removePotionEffect(PotionEffectType.NIGHT_VISION);
    }

    public void sendNotification(Player player, boolean enabled) {
        if (configManager.areMessagesEnabled()) {
            String message = enabled ?
                    configManager.getEnabledMessage() :
                    configManager.getDisabledMessage();
            sendMessage(player, message);
        }

        if (configManager.areTitlesEnabled()) {
            String title = enabled ?
                    configManager.getEnabledTitle() :
                    configManager.getDisabledTitle();
            sendTitle(player, title);
        }
    }

    private void sendMessage(Player player, String message) {
        if (message == null || message.isEmpty()) return;
        player.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
    }

    private void sendTitle(Player player, String message) {
        if (message == null || message.isEmpty()) return;
        player.spigot().sendMessage(
                ChatMessageType.ACTION_BAR,
                new TextComponent(ChatColor.translateAlternateColorCodes('&', message))
        );
    }
}