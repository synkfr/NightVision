package org.ayosynk.nightVision;

import eu.okaeri.configs.yaml.bukkit.YamlBukkitConfigurer;
import eu.okaeri.configs.yaml.bukkit.serdes.SerdesBukkit;
import org.ayosynk.nightVision.config.PluginConfig;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ConfigManager {

    private final JavaPlugin plugin;
    private PluginConfig config;

    public ConfigManager(JavaPlugin plugin) {
        this.plugin = plugin;
        reload();
    }

    public synchronized void reload() {
        File dataFolder = plugin.getDataFolder();
        if (!dataFolder.exists()) {
            dataFolder.mkdirs();
        }

        File configFile = new File(dataFolder, "config.yml");

        if (configFile.exists()) {
            checkAndBackupOldConfig(configFile);
        }

        try {
            this.config = eu.okaeri.configs.ConfigManager.create(PluginConfig.class, it -> {
                it.withConfigurer(new YamlBukkitConfigurer(), new SerdesBukkit());
                it.withBindFile(configFile);
                it.saveDefaults();
                it.load(true);
            });
        } catch (Exception e) {
            plugin.getLogger().severe("Failed to load configuration with Okaeri: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void checkAndBackupOldConfig(File configFile) {
        try {
            YamlConfiguration check = YamlConfiguration.loadConfiguration(configFile);
            int version = check.getInt("config-version", 0);

            if (version < 2) {
                File backupFile = new File(plugin.getDataFolder(), "config.yml.bk");
                if (backupFile.exists()) {
                    String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
                    backupFile = new File(plugin.getDataFolder(), "config.yml.bk_" + timestamp);
                }

                Files.copy(configFile.toPath(), backupFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
                plugin.getLogger().warning("Outdated config format detected! Created backup: " + backupFile.getName() + " and upgraded configuration.");
            }
        } catch (Exception e) {
            plugin.getLogger().warning("Could not verify config version for backup check: " + e.getMessage());
        }
    }

    public PluginConfig getRawConfig() {
        return config;
    }

    public boolean usePermissions() {
        return config != null && config.isUsePermissions();
    }

    public int getEffectDuration() {
        return config != null ? config.getEffectDuration() : -1;
    }

    public boolean applyOnJoin() {
        return config != null && config.isApplyOnJoin();
    }

    public boolean showParticles() {
        return config != null && config.isShowParticles();
    }

    public boolean areMessagesEnabled() {
        return config != null && config.getMessages().isEnabled();
    }

    public String getEnabledMessage() {
        return config != null ? config.getMessages().getEnabledText() : "&a&lNight Vision Enabled";
    }

    public String getDisabledMessage() {
        return config != null ? config.getMessages().getDisabledText() : "&c&lNight Vision Disabled";
    }

    public boolean areTitlesEnabled() {
        return config != null && config.getTitles().isEnabled();
    }

    public String getEnabledTitle() {
        return config != null ? config.getTitles().getEnabledText() : "&7Night Vision &aON";
    }

    public String getDisabledTitle() {
        return config != null ? config.getTitles().getDisabledText() : "&7Night Vision &cOFF";
    }

    public boolean isUpdateCheckerEnabled() {
        return config != null && config.getUpdateChecker().isEnabled();
    }

    public boolean isNotifyAdminsOnJoin() {
        return config != null && config.getUpdateChecker().isNotifyAdminsOnJoin();
    }
}
