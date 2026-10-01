package org.ayosynk.nightVision.config;

import eu.okaeri.configs.OkaeriConfig;
import eu.okaeri.configs.annotation.Comment;
import eu.okaeri.configs.annotation.CustomKey;
import eu.okaeri.configs.annotation.Header;

@Header("   ███╗░░██╗██╗░██████╗░██╗░░██╗████████╗        ██╗░░░██╗██╗░██████╗██╗░█████╗░███╗░░██╗")
@Header("   ████╗░██║██║██╔════╝░██║░░██║╚══██╔══╝        ██║░░░██║██║██╔════╝██║██╔══██╗████╗░██║")
@Header("   ██╔██╗██║██║██║░░██╗░███████║░░░██║░░░        ╚██╗░██╔╝██║╚█████╗░██║██║░░██║██╔██╗██║")
@Header("   ██║╚████║██║██║░░╚██╗██╔══██║░░░██║░░░        ░╚████╔╝░██║░╚═══██╗██║██║░░██║██║╚████║")
@Header("   ██║░╚███║██║╚██████╔╝██║░░██║░░░██║░░░        ░░╚██╔╝░░██║██████╔╝██║╚█████╔╝██║░╚███║")
@Header("   ╚═╝░░╚══╝╚═╝░╚═════╝░╚═╝░░╚═╝░░░╚═╝░░░        ░░░╚═╝░░░╚═╝╚═════╝░╚═╝░╚════╝░╚═╝░░╚══╝")
@Header("")
@Header("NightVision configuration file - powered by Okaeri Configs")
@Header("Changes are automatically loaded and formatted.")
public class PluginConfig extends OkaeriConfig {

    @Comment("Configuration schema version. Used for automatic backups and migrations. Do not modify manually.")
    @CustomKey("config-version")
    private int configVersion = 2;

    @Comment({"Core Settings", "If true, players require the 'nightvision.use' permission. If false, everyone can use /nv."})
    @CustomKey("use-permissions")
    private boolean usePermissions = false;

    @Comment("Duration of the night vision effect in seconds (-1 = infinite).")
    @CustomKey("effect-duration")
    private int effectDuration = -1;

    @Comment("Whether night vision should automatically be re-applied when an enabled player joins the server.")
    @CustomKey("apply-on-join")
    private boolean applyOnJoin = true;

    @Comment("Whether to show potion swirl particles around the player.")
    @CustomKey("show-particles")
    private boolean showParticles = false;

    @Comment("Chat message notifications sent upon toggling /nv.")
    private MessagesSection messages = new MessagesSection();

    @Comment("Action bar title notifications sent upon toggling /nv.")
    private TitlesSection titles = new TitlesSection();

    @Comment("Modrinth Update Checker settings (https://modrinth.com/plugin/nvplugin).")
    @CustomKey("update-checker")
    private UpdateCheckerSection updateChecker = new UpdateCheckerSection();

    public int getConfigVersion() {
        return configVersion;
    }

    public void setConfigVersion(int configVersion) {
        this.configVersion = configVersion;
    }

    public boolean isUsePermissions() {
        return usePermissions;
    }

    public void setUsePermissions(boolean usePermissions) {
        this.usePermissions = usePermissions;
    }

    public int getEffectDuration() {
        return effectDuration;
    }

    public void setEffectDuration(int effectDuration) {
        this.effectDuration = effectDuration;
    }

    public boolean isApplyOnJoin() {
        return applyOnJoin;
    }

    public void setApplyOnJoin(boolean applyOnJoin) {
        this.applyOnJoin = applyOnJoin;
    }

    public boolean isShowParticles() {
        return showParticles;
    }

    public void setShowParticles(boolean showParticles) {
        this.showParticles = showParticles;
    }

    public MessagesSection getMessages() {
        return messages;
    }

    public TitlesSection getTitles() {
        return titles;
    }

    public UpdateCheckerSection getUpdateChecker() {
        return updateChecker;
    }

    public static class MessagesSection extends OkaeriConfig {
        @Comment("If set to false, chat messages will not appear.")
        private boolean enabled = true;

        @Comment("Message displayed in chat when Night Vision is toggled ON.")
        @CustomKey("enabled-text")
        private String enabledText = "&a&lNight Vision Enabled";

        @Comment("Message displayed in chat when Night Vision is toggled OFF.")
        @CustomKey("disabled-text")
        private String disabledText = "&c&lNight Vision Disabled";

        public boolean isEnabled() {
            return enabled;
        }

        public void setEnabled(boolean enabled) {
            this.enabled = enabled;
        }

        public String getEnabledText() {
            return enabledText;
        }

        public void setEnabledText(String enabledText) {
            this.enabledText = enabledText;
        }

        public String getDisabledText() {
            return disabledText;
        }

        public void setDisabledText(String disabledText) {
            this.disabledText = disabledText;
        }
    }

    public static class TitlesSection extends OkaeriConfig {
        @Comment("If set to false, action bar titles will not appear.")
        private boolean enabled = true;

        @Comment("Action bar text shown when Night Vision is toggled ON.")
        @CustomKey("enabled-text")
        private String enabledText = "&7Night Vision &aON";

        @Comment("Action bar text shown when Night Vision is toggled OFF.")
        @CustomKey("disabled-text")
        private String disabledText = "&7Night Vision &cOFF";

        public boolean isEnabled() {
            return enabled;
        }

        public void setEnabled(boolean enabled) {
            this.enabled = enabled;
        }

        public String getEnabledText() {
            return enabledText;
        }

        public void setEnabledText(String enabledText) {
            this.enabledText = enabledText;
        }

        public String getDisabledText() {
            return disabledText;
        }

        public void setDisabledText(String disabledText) {
            this.disabledText = disabledText;
        }
    }

    public static class UpdateCheckerSection extends OkaeriConfig {
        @Comment("Check for updates on Modrinth.")
        private boolean enabled = true;

        @Comment("Send in-game notification to administrators/OPs when they join if an update is available.")
        @CustomKey("notify-admins-on-join")
        private boolean notifyAdminsOnJoin = true;

        public boolean isEnabled() {
            return enabled;
        }

        public void setEnabled(boolean enabled) {
            this.enabled = enabled;
        }

        public boolean isNotifyAdminsOnJoin() {
            return notifyAdminsOnJoin;
        }

        public void setNotifyAdminsOnJoin(boolean notifyAdminsOnJoin) {
            this.notifyAdminsOnJoin = notifyAdminsOnJoin;
        }
    }
}
