package org.ayosynk.nightVision;

import org.ayosynk.nightVision.util.ColorUtil;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CommandHandler implements CommandExecutor, TabCompleter {

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

        if (args.length > 0 && args[0].equalsIgnoreCase("reload")) {
            if (!sender.hasPermission("nightvision.reload")) {
                sender.sendMessage(ColorUtil.colorize("&cYou do not have permission to reload the configuration."));
                return true;
            }
            configManager.reload();
            sender.sendMessage(ColorUtil.colorize("&a[NightVision] Configuration reloaded successfully!"));
            return true;
        }

        if (!(sender instanceof Player player)) {
            sender.sendMessage(ColorUtil.colorize("&cOnly players can toggle night vision for themselves."));
            return true;
        }

        if (configManager.usePermissions() && !player.hasPermission("nightvision.use")) {
            sender.sendMessage(ColorUtil.colorize("&cYou do not have permission to use this command."));
            return true;
        }

        boolean newState = playerManager.togglePlayer(player);

        if (newState) {
            effectApplier.applyNightVision(player);
        } else {
            effectApplier.removeNightVision(player);
        }

        effectApplier.sendNotification(player, newState);
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (args.length == 1) {
            if (sender.hasPermission("nightvision.reload") && "reload".startsWith(args[0].toLowerCase())) {
                return Collections.singletonList("reload");
            }
        }
        return Collections.emptyList();
    }
}
