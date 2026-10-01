package org.ayosynk.nightVision;

import org.ayosynk.nightVision.scheduler.TaskScheduler;
import org.ayosynk.nightVision.updater.ModrinthUpdateChecker;
import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.java.JavaPlugin;

public class NightVision extends JavaPlugin {

    private PlayerManager playerManager;
    private ConfigManager configManager;
    private ModrinthUpdateChecker updateChecker;
    private EventListener eventListener;

    @Override
    public void onEnable() {

        this.configManager = new ConfigManager(this);
        this.playerManager = new PlayerManager(this);
        this.updateChecker = new ModrinthUpdateChecker(this, configManager);
        this.eventListener = new EventListener(this, playerManager, configManager, updateChecker);

        getServer().getPluginManager().registerEvents(eventListener, this);

        CommandHandler commandHandler = new CommandHandler(this, playerManager, configManager);
        PluginCommand command = getCommand("nightvision");
        if (command != null) {
            command.setExecutor(commandHandler);
            command.setTabCompleter(commandHandler);
        }

        updateChecker.checkForUpdates();

        String environment = TaskScheduler.isFolia() ? "Folia" : "Paper/Compatible";
        getLogger().info("NightVision v" + getDescription().getVersion() + " successfully enabled on " + environment + "!");
    }

    @Override
    public void onDisable() {
        getLogger().info("NightVision disabled.");
    }

    public ConfigManager getConfigManager() {
        return configManager;
    }

    public PlayerManager getPlayerManager() {
        return playerManager;
    }

    public ModrinthUpdateChecker getUpdateChecker() {
        return updateChecker;
    }
}
