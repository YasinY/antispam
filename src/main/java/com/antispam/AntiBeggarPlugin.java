package com.antispam;

import com.antispam.plugin.BeggarPatternMatcher;
import com.antispam.plugin.ChatType;
import com.antispam.plugin.MessageFilter;
import com.antispam.plugin.UsernameFilter;
import com.antispam.ui.AntiBeggarPanel;
import com.antispam.ui.config.AntiBeggarConfig;
import com.antispam.ui.config.WhitelistManager;
import com.antispam.ui.panel.statistics.FilterStatistics;
import com.google.inject.Provides;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.ChatMessageType;
import net.runelite.api.Client;
import net.runelite.api.MessageNode;
import net.runelite.api.Player;
import net.runelite.api.events.OverheadTextChanged;
import net.runelite.api.events.ScriptCallbackEvent;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.events.ConfigChanged;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.ui.ClientToolbar;
import net.runelite.client.ui.NavigationButton;
import net.runelite.client.util.ImageUtil;
import net.runelite.client.util.LinkBrowser;

import javax.inject.Inject;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

@Slf4j
@PluginDescriptor(
        name = "Anti-Spam",
        description = "Filters spam, begging, and advertisements from chat"
)
public class AntiBeggarPlugin extends Plugin {

    private static final String CONFIG_GROUP = "antibeggar";

    @Inject
    private Client client;

    @Inject
    private AntiBeggarConfig config;

    @Inject
    private ClientToolbar clientToolbar;

    @Inject
    private ScheduledExecutorService executorService;

    private BeggarPatternMatcher matcher;
    private MessageFilter messageFilter;
    private WhitelistManager whitelistManager;
    private UsernameFilter usernameFilter;
    private AntiBeggarPanel panel;
    private NavigationButton navButton;
    private ScheduledFuture<?> updateTask;

    @Getter
    private FilterStatistics statistics;

    private final Set<Integer> processedMessages = ConcurrentHashMap.newKeySet();

    @Override
    protected void startUp() {
        statistics = new FilterStatistics();
        matcher = new BeggarPatternMatcher(config);
        whitelistManager = new WhitelistManager(client, config);
        usernameFilter = new UsernameFilter();
        messageFilter = new MessageFilter(matcher);

        panel = new AntiBeggarPanel(this);

        final BufferedImage icon = ImageUtil.loadImageResource(getClass(), "/icon.png");
        navButton = NavigationButton.builder()
                .tooltip("Anti-Spam")
                .icon(icon)
                .priority(5)
                .panel(panel)
                .build();

        clientToolbar.addNavigation(navButton);

        updateTask = executorService.scheduleAtFixedRate(() ->
                        SwingUtilities.invokeLater(() -> panel.updateStatistics()),
                5, 5, TimeUnit.SECONDS
        );

        updateAllSettings();
    }

    @Override
    protected void shutDown() {
        if (updateTask != null) {
            updateTask.cancel(true);
        }
        clientToolbar.removeNavigation(navButton);
        processedMessages.clear();
    }

    @Provides
    AntiBeggarConfig provideConfig(ConfigManager configManager) {
        return configManager.getConfig(AntiBeggarConfig.class);
    }

    @Subscribe
    public void onConfigChanged(ConfigChanged event) {
        if (!CONFIG_GROUP.equals(event.getGroup())) {
            return;
        }

        switch (event.getKey()) {
            case "kofiButton":
                LinkBrowser.browse("https://ko-fi.com/alwaysonosrs");
                break;
            case "filterPreset":
                matcher.updatePreset(config.filterPreset());
                break;
            case "customKeywords":
                matcher.updateCustomKeywords(config.customKeywords());
                break;
            case "customRegex":
                matcher.updateCustomRegex(config.customRegex());
                break;
            case "customWhitelist":
                whitelistManager.updateCustomWhitelist(config.customWhitelist());
                break;
            case "usernameBlacklist":
                usernameFilter.updateBlacklist(config.usernameBlacklist());
                break;
            case "enableSpacedTextDetector":
            case "enableAlertSpamDetector":
            case "enableUrlSpamDetector":
            case "enableRWTDetector":
            case "enableGamblingDetector":
            case "enableShowTradingScamDetector":
            case "enableCCSpamDetector":
            case "enableSocialMediaDetector":
            case "enableSuspiciousPatternDetector":
            case "enableKeywordComboDetector":
            case "enableKeywordDetector":
                matcher.updatePreset(config.filterPreset());
                break;
        }
    }

    @Subscribe
    public void onScriptCallbackEvent(ScriptCallbackEvent event) {
        if (!"chatFilterCheck".equals(event.getEventName())) {
            return;
        }

        long startTime = System.nanoTime();

        int[] intStack = client.getIntStack();
        int intStackSize = client.getIntStackSize();

        final int messageType = intStack[intStackSize - 2];
        final int messageId = intStack[intStackSize - 1];

        ChatMessageType chatMessageType = ChatMessageType.of(messageType);
        final MessageNode messageNode = client.getMessages().get(messageId);

        if (messageNode == null) {
            return;
        }

        final String name = messageNode.getName();
        final String message = messageNode.getValue();

        ChatType chatType = getChatType(chatMessageType);
        if (chatType == null) {
            return;
        }

        if (whitelistManager.isWhitelisted(name)) {
            return;
        }

        if (usernameFilter.isBlacklisted(name)) {
            handleFilteredMessage(messageId, chatType, name, message, "username-blacklist", startTime, messageNode, intStack, intStackSize);
            return;
        }

        if (messageFilter.shouldFilter(message)) {
            String keyword = matcher.getMatchedKeyword(message);
            handleFilteredMessage(messageId, chatType, name, message, keyword, startTime, messageNode, intStack, intStackSize);
        }
    }

    private ChatType getChatType(ChatMessageType chatMessageType) {
        switch (chatMessageType) {
            case PUBLICCHAT:
            case MODCHAT:
                return config.filterPublicChat() ? ChatType.PUBLIC : null;
            case PRIVATECHAT:
            case MODPRIVATECHAT:
                return config.filterPrivateMessages() ? ChatType.PRIVATE : null;
            default:
                return null;
        }
    }

    private void handleFilteredMessage(int messageId, ChatType chatType, String name, String message,
                                        String keyword, long startTime, MessageNode messageNode,
                                        int[] intStack, int intStackSize) {
        boolean alreadyProcessed = !processedMessages.add(messageId);

        if (!alreadyProcessed) {
            double filterTimeMs = (System.nanoTime() - startTime) / 1_000_000.0;
            statistics.recordFilter(chatType, keyword);

            if (config.showNotifications()) {
                sendFilterNotification(name, message, keyword, filterTimeMs);
            }
        }

        hideMessage(messageNode, intStack, intStackSize);
    }

    private void hideMessage(MessageNode messageNode, int[] intStack, int intStackSize) {
        if (config.showBlockedChat()) {
            messageNode.setValue("[ - ]");
            messageNode.setRuneLiteFormatMessage("[ - ]");
        } else {
            intStack[intStackSize - 3] = 0;
        }
    }

    @Subscribe
    public void onOverheadTextChanged(OverheadTextChanged event) {
        if (!config.filterOverheadText()) {
            return;
        }

        if (!(event.getActor() instanceof Player)) {
            return;
        }

        Player player = (Player) event.getActor();
        String playerName = player.getName();

        if (whitelistManager.isWhitelisted(playerName)) {
            return;
        }

        if (usernameFilter.isBlacklisted(playerName)) {
            statistics.recordFilter(ChatType.OVERHEAD, "username-blacklist");

            if (config.showBlockedOverhead()) {
                event.getActor().setOverheadText("[ - ]");
            } else {
                event.getActor().setOverheadText(" ");
            }
            return;
        }

        if (messageFilter.shouldFilter(event.getOverheadText())) {
            String originalText = event.getOverheadText();
            String keyword = matcher.getMatchedKeyword(originalText);
            statistics.recordFilter(ChatType.OVERHEAD, keyword);

            if (config.showBlockedOverhead()) {
                event.getActor().setOverheadText("[ - ]");
            } else {
                event.getActor().setOverheadText(" ");
            }
        }
    }

    private void sendFilterNotification(String playerName, String message, String keyword, double filterTimeMs) {
        String notification = String.format(
                "[Anti-Spam] Blocked from %s (keyword: %s, %.3fms): %s",
                playerName,
                keyword != null ? keyword : "unknown",
                filterTimeMs,
                message
        );

        client.addChatMessage(
                ChatMessageType.GAMEMESSAGE,
                "",
                notification,
                ""
        );
    }

    private void updateAllSettings() {
        matcher.updatePreset(config.filterPreset());
        matcher.updateCustomKeywords(config.customKeywords());
        matcher.updateCustomRegex(config.customRegex());
        whitelistManager.updateCustomWhitelist(config.customWhitelist());
        usernameFilter.updateBlacklist(config.usernameBlacklist());
    }
}
