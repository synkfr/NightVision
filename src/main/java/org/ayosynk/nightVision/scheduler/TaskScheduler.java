package org.ayosynk.nightVision.scheduler;

import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.plugin.Plugin;

public final class TaskScheduler {

    private static final boolean IS_FOLIA;
    private static final boolean HAS_ENTITY_SCHEDULER;

    static {
        boolean folia = false;
        try {
            Class.forName("io.papermc.paper.threadedregions.RegionizedServer");
            folia = true;
        } catch (Throwable ignored) {
        }
        IS_FOLIA = folia;

        boolean entitySched = false;
        try {
            Entity.class.getMethod("getScheduler");
            entitySched = true;
        } catch (Throwable ignored) {
        }
        HAS_ENTITY_SCHEDULER = entitySched;
    }

    private TaskScheduler() {
    }

    public static boolean isFolia() {
        return IS_FOLIA;
    }

    public static void runEntityLater(Plugin plugin, Entity entity, Runnable task, long delayTicks) {
        if (entity == null || !entity.isValid()) {
            return;
        }

        if (HAS_ENTITY_SCHEDULER) {
            PaperFoliaBridge.runEntityDelayed(plugin, entity, task, delayTicks);
            return;
        }

        Bukkit.getScheduler().runTaskLater(plugin, task, delayTicks);
    }

    public static void runAsync(Plugin plugin, Runnable task) {
        if (IS_FOLIA) {
            PaperFoliaBridge.runAsync(plugin, task);
            return;
        }

        Bukkit.getScheduler().runTaskAsynchronously(plugin, task);
    }

    private static final class PaperFoliaBridge {
        static void runEntityDelayed(Plugin plugin, Entity entity, Runnable task, long delayTicks) {
            entity.getScheduler().runDelayed(plugin, scheduledTask -> {
                if (entity.isValid()) {
                    task.run();
                }
            }, null, Math.max(1L, delayTicks));
        }

        static void runAsync(Plugin plugin, Runnable task) {
            Bukkit.getAsyncScheduler().runNow(plugin, scheduledTask -> task.run());
        }
    }
}
