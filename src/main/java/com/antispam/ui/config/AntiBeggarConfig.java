package com.antispam.ui.config;

import net.runelite.client.config.*;

@ConfigGroup("antibeggar")
public interface AntiBeggarConfig extends Config {

    @ConfigSection(
        name = "Filter Settings",
        description = "Configure what to filter",
        position = 0
    )
    String filterSection = "filterSection";

    @ConfigSection(
        name = "Whitelist",
        description = "Configure who to exclude from filtering",
        position = 1
    )
    String whitelistSection = "whitelistSection";

    @ConfigSection(
        name = "Blacklist",
        description = "Block specific usernames",
        position = 2
    )
    String blacklistSection = "blacklistSection";

    @ConfigSection(
        name = "Detectors",
        description = "Enable/disable specific spam detection methods",
        position = 3,
        closedByDefault = true
    )
    String detectorsSection = "detectorsSection";

    @ConfigSection(
        name = "Advanced",
        description = "Advanced filtering options",
        position = 4
    )
    String advancedSection = "advancedSection";

    @ConfigSection(
        name = "Support",
        description = "Support the developer",
        position = 5,
        closedByDefault = true
    )
    String supportSection = "supportSection";

    @ConfigItem(
        keyName = "filterPreset",
        name = "Filter preset",
        description = "Lenient: Blocks scams and advertisements. Moderate: Adds begging and spam keywords. Strict: Blocks all spam, begging, and advertisements. Custom: Use only your own keywords",
        position = 0,
        section = filterSection
    )
    default FilterPreset filterPreset() {
        return FilterPreset.MODERATE;
    }

    @ConfigItem(
        keyName = "filterPublicChat",
        name = "Filter public chat",
        description = "Filter spam and begging in public chat",
        position = 1,
        section = filterSection
    )
    default boolean filterPublicChat() {
        return true;
    }

    @ConfigItem(
        keyName = "filterOverheadText",
        name = "Filter overhead text",
        description = "Filter spam and begging in overhead text",
        position = 2,
        section = filterSection
    )
    default boolean filterOverheadText() {
        return true;
    }

    @ConfigItem(
        keyName = "filterPrivateMessages",
        name = "Filter private messages",
        description = "Filter spam and begging in private messages",
        position = 3,
        section = filterSection
    )
    default boolean filterPrivateMessages() {
        return false;
    }

    @ConfigItem(
        keyName = "whitelistFriends",
        name = "Whitelist friends",
        description = "Don't filter messages from friends",
        position = 0,
        section = whitelistSection
    )
    default boolean whitelistFriends() {
        return true;
    }

    @ConfigItem(
        keyName = "whitelistClan",
        name = "Whitelist clan members",
        description = "Don't filter messages from clan members",
        position = 1,
        section = whitelistSection
    )
    default boolean whitelistClan() {
        return true;
    }

    @ConfigItem(
        keyName = "customWhitelist",
        name = "Custom whitelist",
        description = "Additional players to exclude from filtering (comma separated). Example: Player1, Player2",
        position = 2,
        section = whitelistSection
    )
    default String customWhitelist() {
        return "";
    }

    @ConfigItem(
        keyName = "usernameBlacklist",
        name = "Username blacklist",
        description = "Block messages from specific usernames (comma or newline separated). Supports normalization. Prefix with 'regex:' for regex patterns. Example: Sp4mmer, B0t123, regex:.*[0-9]{3}.*",
        position = 0,
        section = blacklistSection
    )
    default String usernameBlacklist() {
        return "";
    }

    @ConfigItem(
        keyName = "enableSpacedTextDetector",
        name = "Spaced text detector",
        description = "Detects suspicious spacing patterns\nExample: 's e l l i n g  g o l d'",
        position = 0,
        section = detectorsSection
    )
    default boolean enableSpacedTextDetector() {
        return true;
    }

    @ConfigItem(
        keyName = "enableAlertSpamDetector",
        name = "Alert spam detector",
        description = "Detects warning/scammer alert messages\nExample: '[Beware] Player123 is a scammer'",
        position = 1,
        section = detectorsSection
    )
    default boolean enableAlertSpamDetector() {
        return true;
    }

    @ConfigItem(
        keyName = "enableUrlSpamDetector",
        name = "URL spam detector",
        description = "Detects URLs and website spam\nExample: 'check out rsgold.com', 'discord.gg/scam'",
        position = 2,
        section = detectorsSection
    )
    default boolean enableUrlSpamDetector() {
        return true;
    }

    @ConfigItem(
        keyName = "enableRWTDetector",
        name = "RWT detector",
        description = "Detects real-world trading (buying/selling items)\nExample: 'Buying tbow 1200m', 'Sell me your bank for 10% extra'",
        position = 3,
        section = detectorsSection
    )
    default boolean enableRWTDetector() {
        return true;
    }

    @ConfigItem(
        keyName = "enableGamblingDetector",
        name = "Gambling detector",
        description = "Detects gambling/doubling money scams\nExample: 'Doubling money 100m max', '2x your items'",
        position = 4,
        section = detectorsSection
    )
    default boolean enableGamblingDetector() {
        return true;
    }

    @ConfigItem(
        keyName = "enableShowTradingScamDetector",
        name = "Show trading scam detector",
        description = "Detects 'show me bank' type scams\nExample: 'Show me 900m bank and I give you 1800m'",
        position = 5,
        section = detectorsSection
    )
    default boolean enableShowTradingScamDetector() {
        return true;
    }

    @ConfigItem(
        keyName = "enableCCSpamDetector",
        name = "CC spam detector",
        description = "Detects clan chat spam\nExample: 'Join cc SWAP for best rates', 'Most trusted cc'",
        position = 6,
        section = detectorsSection
    )
    default boolean enableCCSpamDetector() {
        return true;
    }

    @ConfigItem(
        keyName = "enableSocialMediaDetector",
        name = "Social media detector",
        description = "Detects social media spam\nExample: 'Follow my twitch', 'Check out my youtube'",
        position = 7,
        section = detectorsSection
    )
    default boolean enableSocialMediaDetector() {
        return true;
    }

    @ConfigItem(
        keyName = "enableSuspiciousPatternDetector",
        name = "Suspicious pattern detector",
        description = "Detects suspicious text patterns\nExample: 'b u y i n g', '!!!!!!!'",
        position = 8,
        section = detectorsSection
    )
    default boolean enableSuspiciousPatternDetector() {
        return true;
    }

    @ConfigItem(
        keyName = "enableKeywordComboDetector",
        name = "Keyword combo detector",
        description = "Detects keyword combinations\nExample: 'pls some items', 'free gold', 'need gp'",
        position = 9,
        section = detectorsSection
    )
    default boolean enableKeywordComboDetector() {
        return true;
    }

    @ConfigItem(
        keyName = "enableKeywordDetector",
        name = "Keyword detector",
        description = "Detects preset and custom keywords\nExample: 'selling', 'buying', 'burnt food', 'help'",
        position = 10,
        section = detectorsSection
    )
    default boolean enableKeywordDetector() {
        return true;
    }

    @ConfigItem(
        keyName = "customKeywords",
        name = "Custom keywords",
        description = "Add your own keywords to filter. Separate multiple keywords with commas. Example: noob, scam, buying, selling",
        position = 0,
        section = advancedSection
    )
    default String customKeywords() {
        return "selling, buying";
    }

    @ConfigItem(
        keyName = "customRegex",
        name = "Custom regex patterns",
        description = "Advanced: Add custom regex patterns (one per line)",
        position = 1,
        section = advancedSection
    )
    default String customRegex() {
        return "";
    }

    @ConfigItem(
        keyName = "showBlockedOverhead",
        name = "Show [ - ] above heads",
        description = "Show [ - ] indicator above player heads for blocked overhead text",
        position = 2,
        section = advancedSection
    )
    default boolean showBlockedOverhead() {
        return true;
    }

    @ConfigItem(
        keyName = "showBlockedChat",
        name = "Show [ - ] in chatbox",
        description = "Show [ - ] indicator in chatbox for blocked messages",
        position = 3,
        section = advancedSection
    )
    default boolean showBlockedChat() {
        return false;
    }

    @ConfigItem(
        keyName = "showNotifications",
        name = "Show block notifications",
        description = "Show [Anti-Spam] notification for each blocked message",
        position = 4,
        section = advancedSection
    )
    default boolean showNotifications() {
        return false;
    }

    @ConfigItem(
        keyName = "kofiButton",
        name = "Open Ko-fi Link",
        description = "Click to open my Ko-fi page and support the plugin!",
        position = 0,
        section = supportSection
    )
    default boolean kofiButton() {
        return false;
    }
}
