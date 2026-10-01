![Night Vision Title Image](https://cdn.modrinth.com/data/cached_images/53bc8fe7c0c7944f4446f187d5068b7e3e9a249b.png)

# 🌙 Night Vision — Seamless Vision Control Plugin

**An ultra-lightweight, zero-setup plugin to toggle Night Vision at will — built for performance, Folia threading, and simplicity.**

---

## ✨ Features

* ✅ **Toggle with `/nv`** — one command, no fuss
* ⚡ **Folia & Paper Native** — fully regionalized and thread-safe for Folia, Paper, Purpur, and modern forks
* 🔓 **Zero Setup Required** — works out of the box with zero database or permission configuration
* 🪦 **Survives death** — no reactivation needed
* 🥛 **Immune to milk** — effect automatically re-applied
* 🔁 **True Persistence** — saved natively in player data via `PersistentDataContainer` (survives server reboots, deaths, and reconnects)
* ⚙️ **Okaeri Configs** — automatic config updates, comments, and automatic `.bk` backups when updating
* 🔔 **Modrinth Update Checker** — non-blocking asynchronous notifications when new releases are available
* ❌ **No particles** — completely clean visuals

---

## 🚀 Installation

1. Download the latest `NightVision.jar`
2. Place it into your server's `plugins/` folder
3. Start or restart your Minecraft server

> 💡 *Supports Folia, Paper, Purpur, Gale, Spigot, and compatible forks on Minecraft 1.21 through 26.3+.*

---

## 🔎 Preview

[![YouTube Preview](https://img.youtube.com/vi/n35jMuxTI28/hqdefault.jpg)](https://www.youtube-nocookie.com/embed/n35jMuxTI28)

Click to watch the demo on YouTube.

---

## ⚙️ Configuration (`config.yml`)

```yaml
#    ███╗░░██╗██╗░██████╗░██╗░░██╗████████╗        ██╗░░░██╗██╗░██████╗██╗░█████╗░███╗░░██╗
#    ████╗░██║██║██╔════╝░██║░░██║╚══██╔══╝        ██║░░░██║██║██╔════╝██║██╔══██╗████╗░██║
#    ██╔██╗██║██║██║░░██╗░███████║░░░██║░░░        ╚██╗░██╔╝██║╚█████╗░██║██║░░██║██╔██╗██║
#    ██║╚████║██║██║░░╚██╗██╔══██║░░░██║░░░        ░╚████╔╝░██║░╚═══██╗██║██║░░██║██║╚████║
#    ██║░╚███║██║╚██████╔╝██║░░██║░░░██║░░░        ░░╚██╔╝░░██║██████╔╝██║╚█████╔╝██║░╚███║
#    ╚═╝░░╚══╝╚═╝░╚═════╝░╚═╝░░╚═╝░░░╚═╝░░░        ░░░╚═╝░░░╚═╝╚═════╝░╚═╝░╚════╝░╚═╝░░╚══╝
#
# NightVision configuration file - powered by Okaeri Configs
# Changes are automatically loaded and formatted.

# Configuration schema version. Used for automatic backups and migrations. Do not modify manually.
config-version: 2

# Core Settings
# If true, players require the 'nightvision.use' permission. If false, everyone can use /nv.
use-permissions: false
# Duration of the night vision effect in seconds (-1 = infinite).
effect-duration: -1
# Whether night vision should automatically be re-applied when an enabled player joins the server.
apply-on-join: true
# Whether to show potion swirl particles around the player.
show-particles: false

# Chat message notifications sent upon toggling /nv.
messages:
  # If set to false, chat messages will not appear.
  enabled: true
  # Message displayed in chat when Night Vision is toggled ON.
  enabled-text: "&a&lNight Vision Enabled"
  # Message displayed in chat when Night Vision is toggled OFF.
  disabled-text: "&c&lNight Vision Disabled"

# Action bar title notifications sent upon toggling /nv.
titles:
  # If set to false, action bar titles will not appear.
  enabled: true
  # Action bar text shown when Night Vision is toggled ON.
  enabled-text: "&7Night Vision &aON"
  # Action bar text shown when Night Vision is toggled OFF.
  disabled-text: "&7Night Vision &cOFF"

# Modrinth Update Checker settings (https://modrinth.com/plugin/nvplugin).
update-checker:
  # Check for updates on Modrinth.
  enabled: true
  # Send in-game notification to administrators/OPs when they join if an update is available.
  notify-admins-on-join: true
```

---

## 💬 Support

Need help or want to suggest a feature?

[![Join Discord](https://img.shields.io/discord/1378591879393710110?label=Join%20Our%20Discord\&logo=discord\&style=for-the-badge)](https://discord.gg/pAPPvSmWRK)
[![Open Issues](https://img.shields.io/github/issues/synkfr/NightVision?label=Report%20an%20Issue\&logo=github\&style=for-the-badge)](https://github.com/synkfr/NightVision/issues)

---

## 📦 Downloads

* [Modrinth Page](https://modrinth.com/plugin/nvplugin)
* Latest `.jar` available on [GitHub Releases](https://github.com/synkfr/NightVision/releases)

---

**Night Vision** — *Stay sharp, stay visible, stay in control.*
