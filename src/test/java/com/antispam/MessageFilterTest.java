package com.antispam;

import com.antispam.keywords.FilterPresetManager;
import com.antispam.keywords.Keywords;
import com.antispam.plugin.BeggarPatternMatcher;
import com.antispam.ui.config.FilterPreset;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class MessageFilterTest {

    private BeggarPatternMatcher matcher;

    @Before
    public void setUp() {
        matcher = new BeggarPatternMatcher();
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
