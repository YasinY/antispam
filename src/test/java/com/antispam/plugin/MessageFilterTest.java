package com.antispam.plugin;

import com.antispam.ui.config.AntiBeggarConfig;
import com.antispam.ui.config.FilterPreset;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class MessageFilterTest {

    private MessageFilter messageFilter;
    private BeggarPatternMatcher matcher;

    @Before
    public void setUp() {
        AntiBeggarConfig mockConfig = new AntiBeggarConfig() {
            @Override
            public FilterPreset filterPreset() {
                return FilterPreset.MODERATE;
            }

            @Override
            public boolean filterPublicChat() {
                return true;
            }

            @Override
            public boolean filterOverheadText() {
                return true;
            }

            @Override
            public boolean filterPrivateMessages() {
                return false;
            }

            @Override
            public boolean whitelistFriends() {
                return true;
            }

            @Override
            public boolean whitelistClan() {
                return true;
            }

            @Override
            public String customWhitelist() {
                return "";
            }

            @Override
            public String usernameBlacklist() {
                return "";
            }

            @Override
            public boolean enableSpacedTextDetector() {
                return true;
            }

            @Override
            public boolean enableAlertSpamDetector() {
                return true;
            }

            @Override
            public boolean enableUrlSpamDetector() {
                return true;
            }

            @Override
            public boolean enableRWTDetector() {
                return true;
            }

            @Override
            public boolean enableGamblingDetector() {
                return true;
            }

            @Override
            public boolean enableShowTradingScamDetector() {
                return true;
            }

            @Override
            public boolean enableCCSpamDetector() {
                return true;
            }

            @Override
            public boolean enableSocialMediaDetector() {
                return true;
            }

            @Override
            public boolean enableSuspiciousPatternDetector() {
                return true;
            }

            @Override
            public boolean enableKeywordComboDetector() {
                return true;
            }

            @Override
            public boolean enableKeywordDetector() {
                return true;
            }

            @Override
            public String customKeywords() {
                return "";
            }

            @Override
            public String customRegex() {
                return "";
            }

            @Override
            public boolean showBlockedOverhead() {
                return true;
            }

            @Override
            public boolean showBlockedChat() {
                return false;
            }

            @Override
            public boolean showNotifications() {
                return false;
            }
        };

        matcher = new BeggarPatternMatcher(mockConfig);
        matcher.updatePreset(FilterPreset.MODERATE);
        messageFilter = new MessageFilter(matcher);
    }

    @Test
    public void testShouldFilterSpam() {
        assertTrue(messageFilter.shouldFilter("selling 50b gold"));
        assertTrue(messageFilter.shouldFilter("join my cc for giveaways"));
        assertTrue(messageFilter.shouldFilter("visit website.com"));
    }

    @Test
    public void testShouldNotFilterLegit() {
        assertFalse(messageFilter.shouldFilter("hello how are you"));
        assertFalse(messageFilter.shouldFilter("nice drop mate"));
        assertFalse(messageFilter.shouldFilter(""));
    }

    @Test
    public void testShouldFilterWithMatcher() {
        matcher.updateCustomKeywords("testspam");
        assertTrue(messageFilter.shouldFilter("this is testspam message"));
        assertFalse(messageFilter.shouldFilter("this is normal message"));
    }
}
