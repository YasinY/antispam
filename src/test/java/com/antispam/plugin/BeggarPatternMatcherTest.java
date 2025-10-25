package com.antispam.plugin;

import com.antispam.ui.config.AntiBeggarConfig;
import com.antispam.ui.config.FilterPreset;
import org.junit.Test;

import static org.junit.Assert.*;

public class BeggarPatternMatcherTest {

    private AntiBeggarConfig createMockConfig(final boolean... detectorStates) {
        return new AntiBeggarConfig() {
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
                return detectorStates.length > 0 ? detectorStates[0] : true;
            }

            @Override
            public boolean enableAlertSpamDetector() {
                return detectorStates.length > 1 ? detectorStates[1] : true;
            }

            @Override
            public boolean enableUrlSpamDetector() {
                return detectorStates.length > 2 ? detectorStates[2] : true;
            }

            @Override
            public boolean enableRWTDetector() {
                return detectorStates.length > 3 ? detectorStates[3] : true;
            }

            @Override
            public boolean enableGamblingDetector() {
                return detectorStates.length > 4 ? detectorStates[4] : true;
            }

            @Override
            public boolean enableShowTradingScamDetector() {
                return detectorStates.length > 5 ? detectorStates[5] : true;
            }

            @Override
            public boolean enableCCSpamDetector() {
                return detectorStates.length > 6 ? detectorStates[6] : true;
            }

            @Override
            public boolean enableSocialMediaDetector() {
                return detectorStates.length > 7 ? detectorStates[7] : true;
            }

            @Override
            public boolean enableSuspiciousPatternDetector() {
                return detectorStates.length > 8 ? detectorStates[8] : true;
            }

            @Override
            public boolean enableKeywordComboDetector() {
                return detectorStates.length > 9 ? detectorStates[9] : true;
            }

            @Override
            public boolean enableKeywordDetector() {
                return detectorStates.length > 10 ? detectorStates[10] : true;
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
    }

    @Test
    public void testAllDetectorsEnabled() {
        BeggarPatternMatcher matcher = new BeggarPatternMatcher(createMockConfig());
        matcher.updatePreset(FilterPreset.MODERATE);

        assertTrue(matcher.matches("b-u-y-i-n-g gold")); // SpacedText
        assertTrue(matcher.matches("Player123 is a scammer")); // AlertSpam
        assertTrue(matcher.matches("visit website.com")); // UrlSpam
        assertTrue(matcher.matches("selling 50b gold")); // RWT
        assertTrue(matcher.matches("doubling money come trade")); // Gambling
        assertTrue(matcher.matches("free items trade me")); // ShowTrading
        assertTrue(matcher.matches("join my cc now")); // CCSpam
        assertTrue(matcher.matches("join my discord gg")); // SocialMedia
    }

    @Test
    public void testSpacedTextDetectorDisabled() {
        BeggarPatternMatcher matcher = new BeggarPatternMatcher(createMockConfig(false, true, true, true, true, true, true, true, true, true, true));
        matcher.updatePreset(FilterPreset.MODERATE);

        assertFalse(matcher.matches("b-u-y-i-n-g")); // Should not detect spaced text
    }

    @Test
    public void testAlertSpamDetectorDisabled() {
        BeggarPatternMatcher matcher = new BeggarPatternMatcher(createMockConfig(true, false, true, true, true, true, true, true, true, true, true));
        matcher.updatePreset(FilterPreset.MODERATE);

        // Scammer alert might still be caught by other patterns if present
        String alertOnly = "WARNING scammer alert";
        assertFalse(matcher.matches(alertOnly));
    }

    @Test
    public void testUrlSpamDetectorDisabled() {
        BeggarPatternMatcher matcher = new BeggarPatternMatcher(createMockConfig(true, true, false, true, true, true, true, true, true, true, true));
        matcher.updatePreset(FilterPreset.MODERATE);

        String urlOnly = "visit example.com now";
        // May still be detected by other detectors
        matcher.matches(urlOnly);
    }

    @Test
    public void testRWTDetectorDisabled() {
        BeggarPatternMatcher matcher = new BeggarPatternMatcher(createMockConfig(true, true, true, false, true, true, true, true, true, true, true));
        matcher.updatePreset(FilterPreset.MODERATE);

        String rwtOnly = "selling 50b gold";
        // Should not be detected by RWT detector
        matcher.matches(rwtOnly);
    }

    @Test
    public void testGamblingDetectorDisabled() {
        BeggarPatternMatcher matcher = new BeggarPatternMatcher(createMockConfig(true, true, true, true, false, true, true, true, true, true, true));
        matcher.updatePreset(FilterPreset.MODERATE);

        String gamblingOnly = "55x2 dicing hot cc";
        // Should not be detected
        matcher.matches(gamblingOnly);
    }

    @Test
    public void testShowTradingDetectorDisabled() {
        BeggarPatternMatcher matcher = new BeggarPatternMatcher(createMockConfig(true, true, true, true, true, false, true, true, true, true, true));
        matcher.updatePreset(FilterPreset.MODERATE);

        String showTradingOnly = "free items trade me";
        matcher.matches(showTradingOnly);
    }

    @Test
    public void testCCSpamDetectorDisabled() {
        BeggarPatternMatcher matcher = new BeggarPatternMatcher(createMockConfig(true, true, true, true, true, true, false, true, true, true, true));
        matcher.updatePreset(FilterPreset.MODERATE);

        String ccOnly = "join my cc for drops";
        matcher.matches(ccOnly);
    }

    @Test
    public void testSocialMediaDetectorDisabled() {
        BeggarPatternMatcher matcher = new BeggarPatternMatcher(createMockConfig(true, true, true, true, true, true, true, false, true, true, true));
        matcher.updatePreset(FilterPreset.MODERATE);

        String socialOnly = "add my discord";
        matcher.matches(socialOnly);
    }

    @Test
    public void testSuspiciousPatternDetectorDisabled() {
        BeggarPatternMatcher matcher = new BeggarPatternMatcher(createMockConfig(true, true, true, true, true, true, true, true, false, true, true));
        matcher.updatePreset(FilterPreset.MODERATE);

        String suspiciousOnly = "click here now";
        matcher.matches(suspiciousOnly);
    }

    @Test
    public void testKeywordComboDetectorDisabled() {
        BeggarPatternMatcher matcher = new BeggarPatternMatcher(createMockConfig(true, true, true, true, true, true, true, true, true, false, true));
        matcher.updatePreset(FilterPreset.MODERATE);

        matcher.matches("test message");
    }

    @Test
    public void testKeywordDetectorDisabled() {
        BeggarPatternMatcher matcher = new BeggarPatternMatcher(createMockConfig(true, true, true, true, true, true, true, true, true, true, false));
        matcher.updatePreset(FilterPreset.MODERATE);

        matcher.matches("test message");
    }

    @Test
    public void testAllDetectorsDisabled() {
        BeggarPatternMatcher matcher = new BeggarPatternMatcher(createMockConfig(false, false, false, false, false, false, false, false, false, false, false));
        matcher.updatePreset(FilterPreset.MODERATE);

        assertFalse(matcher.matches("selling 50b gold"));
        assertFalse(matcher.matches("b-u-y-i-n-g"));
        assertFalse(matcher.matches("visit website.com"));
    }

    @Test
    public void testCustomKeywords() {
        BeggarPatternMatcher matcher = new BeggarPatternMatcher(createMockConfig());
        matcher.updatePreset(FilterPreset.MODERATE);
        matcher.updateCustomKeywords("testword,spamword");

        assertTrue(matcher.matches("this is a testword message"));
        assertTrue(matcher.matches("this is a spamword message"));
        assertFalse(matcher.matches("this is normal"));
    }

    @Test
    public void testCustomKeywordsEmpty() {
        BeggarPatternMatcher matcher = new BeggarPatternMatcher(createMockConfig());
        matcher.updateCustomKeywords("");

        assertFalse(matcher.matches("any message"));
    }

    @Test
    public void testCustomKeywordsNull() {
        BeggarPatternMatcher matcher = new BeggarPatternMatcher(createMockConfig());
        matcher.updateCustomKeywords(null);

        assertFalse(matcher.matches("any message"));
    }

    @Test
    public void testCustomRegex() {
        BeggarPatternMatcher matcher = new BeggarPatternMatcher(createMockConfig());
        matcher.updateCustomRegex("spam.*\\d+");

        assertTrue(matcher.matches("spam123"));
        assertTrue(matcher.matches("spam999"));
        assertFalse(matcher.matches("legitimate message"));
    }

    @Test
    public void testCustomRegexMultiline() {
        BeggarPatternMatcher matcher = new BeggarPatternMatcher(createMockConfig());
        matcher.updateCustomRegex("spam.*\nbot.*");

        assertTrue(matcher.matches("spam message"));
        assertTrue(matcher.matches("bot seller"));
    }

    @Test
    public void testCustomRegexEmpty() {
        BeggarPatternMatcher matcher = new BeggarPatternMatcher(createMockConfig());
        matcher.updateCustomRegex("");

        assertFalse(matcher.matches("any message"));
    }

    @Test
    public void testCustomRegexNull() {
        BeggarPatternMatcher matcher = new BeggarPatternMatcher(createMockConfig());
        matcher.updateCustomRegex(null);

        assertFalse(matcher.matches("any message"));
    }

    @Test
    public void testCustomRegexInvalid() {
        BeggarPatternMatcher matcher = new BeggarPatternMatcher(createMockConfig());
        matcher.updateCustomRegex("[invalid(regex");

        assertFalse(matcher.matches("any message"));
    }

    @Test
    public void testMatchesNull() {
        BeggarPatternMatcher matcher = new BeggarPatternMatcher(createMockConfig());
        assertFalse(matcher.matches(null));
    }

    @Test
    public void testMatchesEmpty() {
        BeggarPatternMatcher matcher = new BeggarPatternMatcher(createMockConfig());
        assertFalse(matcher.matches(""));
    }

    @Test
    public void testGetMatchedKeywordNull() {
        BeggarPatternMatcher matcher = new BeggarPatternMatcher(createMockConfig());
        assertNull(matcher.getMatchedKeyword(null));
    }

    @Test
    public void testGetMatchedKeywordEmpty() {
        BeggarPatternMatcher matcher = new BeggarPatternMatcher(createMockConfig());
        assertNull(matcher.getMatchedKeyword(""));
    }

    @Test
    public void testGetMatchedKeyword() {
        BeggarPatternMatcher matcher = new BeggarPatternMatcher(createMockConfig());
        matcher.updatePreset(FilterPreset.MODERATE);

        String keyword = matcher.getMatchedKeyword("selling 50b gold");
        assertNotNull(keyword);
        assertTrue(keyword.contains("rwt"));
    }

    @Test
    public void testGetMatchedKeywordNoMatch() {
        BeggarPatternMatcher matcher = new BeggarPatternMatcher(createMockConfig());
        matcher.updatePreset(FilterPreset.MODERATE);

        String keyword = matcher.getMatchedKeyword("hello friend");
        assertNull(keyword);
    }

    @Test
    public void testUpdatePreset() {
        BeggarPatternMatcher matcher = new BeggarPatternMatcher(createMockConfig());
        matcher.updatePreset(FilterPreset.LENIENT);

        // Lenient preset should have different keywords
        matcher.matches("test message");
    }

    @Test
    public void testNormalization() {
        BeggarPatternMatcher matcher = new BeggarPatternMatcher(createMockConfig());
        matcher.updateCustomKeywords("spam");

        assertTrue(matcher.matches("sp4m")); // 4=a
        assertTrue(matcher.matches("5pam")); // 5=s
        assertTrue(matcher.matches("spàm")); // accented
    }
}
