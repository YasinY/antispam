package com.antispam;

import com.antispam.keywords.FilterPresetManager;
import com.antispam.keywords.Keywords;
import com.antispam.plugin.BeggarPatternMatcher;
import com.antispam.ui.config.AntiBeggarConfig;
import com.antispam.ui.config.FilterPreset;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class MessageFilterTest {

    private BeggarPatternMatcher matcher;
    private AntiBeggarConfig mockConfig;

    @Before
    public void setUp() {
        mockConfig = new AntiBeggarConfig() {
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
    }

    @Test
    public void testGiveMeGold() {
        matcher.updatePreset(FilterPreset.MODERATE);

        String message = "give me 5 million gold";
        boolean matches = matcher.matches(message);
        String keyword = matcher.getMatchedKeyword(message);

        System.out.println("Message: " + message);
        System.out.println("Matches: " + matches);
        System.out.println("Keyword: " + keyword);

        assertTrue("Should detect 'give me' in: " + message, matches);
    }

    @Test
    public void testTryNowHigh() {
        matcher.updatePreset(FilterPreset.LENIENT);

        String message = "try now here +high 1 m min";
        boolean matches = matcher.matches(message);
        String keyword = matcher.getMatchedKeyword(message);

        System.out.println("Message: " + message);
        System.out.println("Matches: " + matches);
        System.out.println("Keyword: " + keyword);

        assertTrue("Should detect 'try now' in: " + message, matches);
    }

    @Test
    public void testKeywordsAvailable() {
        Keywords lenient = FilterPresetManager.getKeywordsForPreset(FilterPreset.LENIENT);
        Keywords moderate = FilterPresetManager.getKeywordsForPreset(FilterPreset.MODERATE);

        System.out.println("Lenient standalone keywords: " + lenient.getStandalone());
        System.out.println("Moderate standalone keywords: " + moderate.getStandalone());

        assertTrue("Lenient should contain 'try now'", lenient.getStandalone().contains("try now"));
        assertTrue("Moderate should contain 'give me'", moderate.getStandalone().contains("give me"));
    }
}
