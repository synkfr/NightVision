package org.ayosynk.nightVision;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class PlayerManager {
    private final Set<UUID> enabledPlayers = new HashSet<>();

    public boolean togglePlayer(UUID uuid) {
        if (enabledPlayers.contains(uuid)) {
            enabledPlayers.remove(uuid);
            return false;
        } else {
            enabledPlayers.add(uuid);
            return true;
        }
    }

    public boolean isEnabled(UUID uuid) {
        return enabledPlayers.contains(uuid);
    }

    public void addPlayer(UUID uuid) {
        enabledPlayers.add(uuid);
    }

    public void removePlayer(UUID uuid) {
        enabledPlayers.remove(uuid);
    }
}