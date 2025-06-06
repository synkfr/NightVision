package org.ayosynk.nightVision;

import org.bukkit.plugin.java.JavaPlugin;

public class NightVision extends JavaPlugin {

    private PlayerManager playerManager;
    private ConfigManager configManager;
    private EventListener eventListener;

    @Override
    public void onEnable() {
        // Initialize managers
        configManager = new ConfigManager(this);
        playerManager = new PlayerManager();
        eventListener = new EventListener(this, playerManager, configManager);

        // Register events
        getServer().getPluginManager().registerEvents(eventListener, this);

        // Set command executor
        getCommand("nightvision").setExecutor(new CommandHandler(this, playerManager, configManager));

        getLogger().info("NightVision v" + getDescription().getVersion() + " enabled!");
    }

    @Override
    public void onDisable() {
        getLogger().info("NightVision disabled");
    }

    public ConfigManager getConfigManager() {
        return configManager;
    }

    public PlayerManager getPlayerManager() {
        return playerManager;
    }
}