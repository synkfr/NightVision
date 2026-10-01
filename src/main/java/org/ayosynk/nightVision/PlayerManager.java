package org.ayosynk.nightVision;

import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Collections;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class PlayerManager {

    private final JavaPlugin plugin;
    private final NamespacedKey nightVisionKey;
    private final Set<UUID> enabledCache = Collections.newSetFromMap(new ConcurrentHashMap<>());

    public PlayerManager(JavaPlugin plugin) {
        this.plugin = plugin;
        this.nightVisionKey = new NamespacedKey(plugin, "night_vision_enabled");
    }

    public boolean loadPlayer(Player player) {
        if (player == null) return false;
        PersistentDataContainer pdc = player.getPersistentDataContainer();
        Byte state = pdc.get(nightVisionKey, PersistentDataType.BYTE);
        boolean enabled = state != null && state == (byte) 1;

        if (enabled) {
            enabledCache.add(player.getUniqueId());
        } else {
            enabledCache.remove(player.getUniqueId());
        }
        return enabled;
    }

    public void unloadPlayer(Player player) {
        if (player != null) {
            enabledCache.remove(player.getUniqueId());
        }
    }

    public boolean togglePlayer(Player player) {
        boolean currentState = isEnabled(player);
        boolean newState = !currentState;
        setNightVision(player, newState);
        return newState;
    }

    public boolean isEnabled(Player player) {
        if (player == null) return false;
        if (enabledCache.contains(player.getUniqueId())) {
            return true;
        }

        PersistentDataContainer pdc = player.getPersistentDataContainer();
        Byte state = pdc.get(nightVisionKey, PersistentDataType.BYTE);
        boolean enabled = state != null && state == (byte) 1;
        if (enabled) {
            enabledCache.add(player.getUniqueId());
        }
        return enabled;
    }

    public boolean isEnabled(UUID uuid) {
        return enabledCache.contains(uuid);
    }

    public void setNightVision(Player player, boolean enabled) {
        if (player == null) return;
        UUID uuid = player.getUniqueId();
        PersistentDataContainer pdc = player.getPersistentDataContainer();

        if (enabled) {
            enabledCache.add(uuid);
            pdc.set(nightVisionKey, PersistentDataType.BYTE, (byte) 1);
        } else {
            enabledCache.remove(uuid);
            pdc.set(nightVisionKey, PersistentDataType.BYTE, (byte) 0);
        }
    }
}
