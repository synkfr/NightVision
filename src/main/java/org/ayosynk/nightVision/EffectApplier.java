package org.ayosynk.nightVision;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.ayosynk.nightVision.util.ColorUtil;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class EffectApplier {

    private final ConfigManager configManager;

    public EffectApplier(ConfigManager configManager) {
        this.configManager = configManager;
    }

    public void applyNightVision(Player player) {
        if (player == null || !player.isOnline()) return;

        int duration = configManager.getEffectDuration() > 0
                ? configManager.getEffectDuration() * 20
                : PotionEffect.INFINITE_DURATION;

        PotionEffect effect = new PotionEffect(
                PotionEffectType.NIGHT_VISION,
                duration,
                0,
                true,
                configManager.showParticles(),
                false
        );

        player.addPotionEffect(effect);
    }

    public void removeNightVision(Player player) {
        if (player == null) return;
        player.removePotionEffect(PotionEffectType.NIGHT_VISION);
    }

    public void sendNotification(Player player, boolean enabled) {
        if (player == null) return;

        if (configManager.areMessagesEnabled()) {
            String message = enabled ? configManager.getEnabledMessage() : configManager.getDisabledMessage();
            sendMessage(player, message);
        }

        if (configManager.areTitlesEnabled()) {
            String title = enabled ? configManager.getEnabledTitle() : configManager.getDisabledTitle();
            sendActionBar(player, title);
        }
    }

    private void sendMessage(Player player, String message) {
        if (message == null || message.trim().isEmpty()) return;
        player.sendMessage(ColorUtil.colorize(message));
    }

    private void sendActionBar(Player player, String message) {
        if (message == null || message.trim().isEmpty()) return;
        String formatted = ColorUtil.colorize(message);

        try {

            player.spigot().sendMessage(ChatMessageType.ACTION_BAR, TextComponent.fromLegacyText(formatted));
        } catch (Exception ignored) {

            player.sendMessage(formatted);
        }
    }
}
