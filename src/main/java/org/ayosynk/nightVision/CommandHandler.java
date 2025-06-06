package org.ayosynk.nightVision;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.UUID;

public class CommandHandler implements CommandExecutor {
    private final JavaPlugin plugin;
    private final PlayerManager playerManager;
    private final ConfigManager configManager;
    private final EffectApplier effectApplier;

    public CommandHandler(JavaPlugin plugin, PlayerManager playerManager, ConfigManager configManager) {
        this.plugin = plugin;
        this.playerManager = playerManager;
        this.configManager = configManager;
        this.effectApplier = new EffectApplier(configManager);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        // Reload command
        if (args.length > 0 && args[0].equalsIgnoreCase("reload")) {
            if (!sender.hasPermission("nightvision.reload")) {
                sender.sendMessage(ChatColor.RED + "You don't have permission to reload the config.");
                return true;
            }
            configManager.reload();
            sender.sendMessage(ChatColor.GREEN + "Configuration reloaded!");
            return true;
        }

        // Toggle command
        if (!(sender instanceof Player player)) {
            sender.sendMessage(ChatColor.RED + "Only players can use this command.");
            return true;
        }

        if (configManager.usePermissions() && !player.hasPermission("nightvision.use")) {
            player.sendMessage(ChatColor.RED + "You don't have permission to use this command.");
            return true;
        }

        UUID uuid = player.getUniqueId();
        boolean isNowEnabled = playerManager.togglePlayer(uuid);

        if (isNowEnabled) {
            effectApplier.applyNightVision(player);
        } else {
            effectApplier.removeNightVision(player);
        }

        effectApplier.sendNotification(player, isNowEnabled);
        return true;
    }
}