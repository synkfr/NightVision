package org.ayosynk.nightVision.updater;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.ayosynk.nightVision.ConfigManager;
import org.ayosynk.nightVision.scheduler.TaskScheduler;
import org.ayosynk.nightVision.util.ColorUtil;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class ModrinthUpdateChecker {

    private static final String API_URL = "https://api.modrinth.com/v2/project/nvplugin/version";
    private static final String PROJECT_URL = "https://modrinth.com/plugin/nvplugin";

    private final JavaPlugin plugin;
    private final ConfigManager configManager;

    private boolean updateAvailable = false;
    private String latestVersion = null;

    public ModrinthUpdateChecker(JavaPlugin plugin, ConfigManager configManager) {
        this.plugin = plugin;
        this.configManager = configManager;
    }

    public void checkForUpdates() {
        if (!configManager.isUpdateCheckerEnabled()) {
            return;
        }

        TaskScheduler.runAsync(plugin, () -> {
            try {
                URL url = URI.create(API_URL).toURL();
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");
                connection.setConnectTimeout(6000);
                connection.setReadTimeout(6000);
                connection.setRequestProperty("User-Agent", "AyoSynk-NightVision/" + plugin.getDescription().getVersion() + " (https://modrinth.com/plugin/nvplugin)");
                connection.setRequestProperty("Accept", "application/json");

                int responseCode = connection.getResponseCode();
                if (responseCode != HttpURLConnection.HTTP_OK) {
                    return;
                }

                StringBuilder response = new StringBuilder();
                try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8))) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        response.append(line);
                    }
                }

                JsonElement parsed = JsonParser.parseString(response.toString());
                if (!parsed.isJsonArray()) {
                    return;
                }

                JsonArray versions = parsed.getAsJsonArray();
                if (versions.isEmpty()) {
                    return;
                }

                JsonObject latestObj = versions.get(0).getAsJsonObject();
                if (!latestObj.has("version_number")) {
                    return;
                }

                String remoteVersion = latestObj.get("version_number").getAsString();
                String currentVersion = plugin.getDescription().getVersion();

                if (isNewerVersion(remoteVersion, currentVersion)) {
                    this.updateAvailable = true;
                    this.latestVersion = remoteVersion;

                    plugin.getLogger().info("=================================================");
                    plugin.getLogger().info("A new version of NightVision is available: v" + remoteVersion + " (Current: v" + currentVersion + ")");
                    plugin.getLogger().info("Download from Modrinth: " + PROJECT_URL);
                    plugin.getLogger().info("=================================================");
                }
            } catch (Exception e) {

                plugin.getLogger().fine("Update check failed: " + e.getMessage());
            }
        });
    }

    public void notifyAdmin(Player player) {
        if (!updateAvailable || latestVersion == null) {
            return;
        }

        if (!configManager.isNotifyAdminsOnJoin()) {
            return;
        }

        if (player.hasPermission("nightvision.admin") || player.isOp()) {
            player.sendMessage(ColorUtil.colorize("&8[&bNightVision&8] &eA new update is available: &a&lv" + latestVersion + " &7(Current: &7v" + plugin.getDescription().getVersion() + "&7)"));
            player.sendMessage(ColorUtil.colorize("&8[&bNightVision&8] &eDownload at: &b&n" + PROJECT_URL));
        }
    }

    public boolean isUpdateAvailable() {
        return updateAvailable;
    }

    public String getLatestVersion() {
        return latestVersion;
    }

    public static boolean isNewerVersion(String remote, String current) {
        if (remote == null || current == null) return false;
        String cleanRemote = remote.replaceAll("[^0-9.]", "");
        String cleanCurrent = current.replaceAll("[^0-9.]", "");

        String[] remoteParts = cleanRemote.split("\\.");
        String[] currentParts = cleanCurrent.split("\\.");

        int length = Math.max(remoteParts.length, currentParts.length);
        for (int i = 0; i < length; i++) {
            int r = i < remoteParts.length && !remoteParts[i].isEmpty() ? Integer.parseInt(remoteParts[i]) : 0;
            int c = i < currentParts.length && !currentParts[i].isEmpty() ? Integer.parseInt(currentParts[i]) : 0;
            if (r > c) return true;
            if (r < c) return false;
        }
        return false;
    }
}
