package org.ayosynk.nightVision;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class ConfigManager {
    private final JavaPlugin plugin;
    private FileConfiguration config;

    private boolean usePermissions;
    private int effectDuration;
    private boolean applyOnJoin;
    private boolean showParticles;

    public ConfigManager(JavaPlugin plugin) {
        this.plugin = plugin;
        reload();
    }

    public void reload() {
        plugin.saveDefaultConfig();
        plugin.reloadConfig();
        config = plugin.getConfig();

        // Load configuration values
        usePermissions = config.getBoolean("use-permissions", false);
        effectDuration = config.getInt("effect-duration", -1);
        applyOnJoin = config.getBoolean("apply-on-join", true);
        showParticles = config.getBoolean("show-particles", true);
    }

    public boolean usePermissions() {
        return usePermissions;
    }

    public int getEffectDuration() {
        return effectDuration;
    }

    public boolean applyOnJoin() {
        return applyOnJoin;
    }

    public boolean showParticles() {
        return showParticles;
    }

    public boolean areMessagesEnabled() {
        return config.getBoolean("messages.enabled", true);
    }

    public boolean areTitlesEnabled() {
        return config.getBoolean("titles.enabled", true);
    }

    public String getEnabledMessage() {
        return config.getString("messages.enabled-text", "");
    }

    public String getDisabledMessage() {
        return config.getString("messages.disabled-text", "");
    }

    public String getEnabledTitle() {
        return config.getString("titles.enabled-text", "");
    }

    public String getDisabledTitle() {
        return config.getString("titles.disabled-text", "");
    }
}