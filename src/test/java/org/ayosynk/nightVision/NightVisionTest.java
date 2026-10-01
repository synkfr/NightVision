package org.ayosynk.nightVision;

import org.ayosynk.nightVision.updater.ModrinthUpdateChecker;
import org.ayosynk.nightVision.util.ColorUtil;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class NightVisionTest {

    @Test
    public void testColorUtil() {
        String colored = ColorUtil.colorize("&a&lHello");
        assertNotNull(colored);
        assertTrue(colored.contains("Hello"));

        String hexColored = ColorUtil.colorize("&#123456Hex Text");
        assertNotNull(hexColored);
        assertTrue(hexColored.contains("Hex Text"));
    }

    @Test
    public void testVersionComparison() {
        assertTrue(ModrinthUpdateChecker.isNewerVersion("1.4", "1.3"));
        assertTrue(ModrinthUpdateChecker.isNewerVersion("2.0.0", "1.9.9"));
        assertFalse(ModrinthUpdateChecker.isNewerVersion("1.3", "1.4"));
        assertFalse(ModrinthUpdateChecker.isNewerVersion("1.4", "1.4"));
        assertFalse(ModrinthUpdateChecker.isNewerVersion("1.0", "1.4"));
    }
}
