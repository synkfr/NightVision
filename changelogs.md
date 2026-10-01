# 📜 Changelogs

All notable changes to the **NightVision** plugin will be documented in this file.

---

## [1.4.0] — 2026-10-01

### 🚀 Major Highlights
* **Universal 1.21 to 26.3+ Support**: Native support across all modern Minecraft versions (1.21, 1.21.1–1.21.4, through 26.3+).
* **Native Folia Support**: Added full compatibility for Folia's multi-threaded regional scheduling, plus Paper, Purpur, Gale, and legacy Spigot.
* **Okaeri Configs Integration**: Complete rewrite of the configuration system using Okaeri Configs with automatic schema updates, formatted comments, and automated `.bk` backup generation on update.
* **Zero-Setup PersistentDataContainer (PDC)**: Toggle preferences are now stored directly in player NBT data, surviving server restarts and player deaths with zero external database configuration.
* **Modrinth Update Checker**: Added an asynchronous, non-blocking update checker communicating with the Modrinth API (`https://modrinth.com/plugin/nvplugin`).

---

### 🌟 New Features & Enhancements
* **Folia Regional Threading**:
  * Implemented `TaskScheduler` with an isolated `PaperFoliaBridge` that uses `entity.getScheduler().runDelayed()` for regional player operations and `Bukkit.getAsyncScheduler()` for asynchronous tasks.
  * Added `folia-supported: true` to `plugin.yml`.
  * Safe graceful fallbacks for standard Paper and Spigot.
* **Automated Config Backups & Schema Migration**:
  * Detects legacy configurations (`config-version < 2`) and automatically generates a `config.yml.bk` (or timestamped backup) before generating the new Okaeri schema.
  * Okaeri preserves custom edits while automatically inserting any newly introduced configuration keys and comments.
* **Player State Persistence**:
  * Replaced transient in-memory UUID set with Minecraft's native `PersistentDataContainer` (`night_vision_enabled`).
  * Player states persist across server restarts, reboots, crashes, world transfers, and player deaths.
  * Added memory-leak-free caching that automatically cleans up when players disconnect.
* **Modrinth Update Checker**:
  * Non-blocking REST client query to `https://api.modrinth.com/v2/project/nvplugin/version`.
  * Logs a notification in the console on server startup if an update is found.
  * Sends an in-game notification to operators or players with `nightvision.admin` upon login.
  * Configurable in `config.yml` under `update-checker`.
* **Visuals & Color Formatting**:
  * Added `ColorUtil` supporting both standard legacy color codes (`&a`, `&l`) and modern Hex color formatting (`&#RRGGBB`).
  * Clean potion effects: ambient particles disabled by default and potion icon hidden from the HUD.
* **Clean Codebase**:
  * Removed all inline and block comment clutter across all Java source files.
  * Replaced reflection-heavy scheduler wrappers with direct native calls in isolated class bridges.
  * Shaded and relocated all Okaeri dependencies to `org.ayosynk.nightVision.libs.okaeri.configs` to prevent classpath conflicts.

---

## [1.3.0] — 2025-06-06

* Dedicated config manager with type-safe access.
* Automatic config validation.
* Hot-reload capability via `/nv reload`.

---

## [1.1.0] — 2025-06-02

* Updated `config.yml` settings.
* Improved night vision persistence after milk consumption.

---

## [1.0.0] — 2025-05-29

* Initial release of NightVision plugin.
* Toggle night vision effect via `/nv` and `/nightvision`.
* Basic join, respawn, and milk drink event listeners.
