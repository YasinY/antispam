package com.antispam.plugin;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class UsernameFilterTest {

    private UsernameFilter filter;

    @Before
    public void setUp() {
        filter = new UsernameFilter();
    }

    @Test
    public void testEmptyBlacklist() {
        filter.updateBlacklist("");
        assertFalse(filter.isBlacklisted("testuser"));
    }

    @Test
    public void testNullBlacklist() {
        filter.updateBlacklist(null);
        assertFalse(filter.isBlacklisted("testuser"));
    }

    @Test
    public void testSingleName() {
        filter.updateBlacklist("spammer123");
        assertTrue(filter.isBlacklisted("spammer123"));
        assertFalse(filter.isBlacklisted("legituser"));
    }

    @Test
    public void testMultipleNamesCommaSeparated() {
        filter.updateBlacklist("spammer1,spammer2,spammer3");
        assertTrue(filter.isBlacklisted("spammer1"));
        assertTrue(filter.isBlacklisted("spammer2"));
        assertTrue(filter.isBlacklisted("spammer3"));
        assertFalse(filter.isBlacklisted("legituser"));
    }

    @Test
    public void testMultipleNamesNewlineSeparated() {
        filter.updateBlacklist("spammer1\nspammer2\nspammer3");
        assertTrue(filter.isBlacklisted("spammer1"));
        assertTrue(filter.isBlacklisted("spammer2"));
        assertTrue(filter.isBlacklisted("spammer3"));
    }

    @Test
    public void testCaseInsensitive() {
        filter.updateBlacklist("SpAmMeR");
        assertTrue(filter.isBlacklisted("spammer"));
        assertTrue(filter.isBlacklisted("SPAMMER"));
        assertTrue(filter.isBlacklisted("SpAmMeR"));
    }

    @Test
    public void testLeetSpeakNumbers() {
        filter.updateBlacklist("test");
        assertTrue(filter.isBlacklisted("t3st")); // 3=e
        assertTrue(filter.isBlacklisted("t35t")); // 3=e, 5=s
    }

    @Test
    public void testLeetSpeakSymbols() {
        filter.updateBlacklist("test");
        assertTrue(filter.isBlacklisted("te$t")); // $=s
    }

    @Test
    public void testRepeatedCharacters() {
        filter.updateBlacklist("spaam"); // Already has double 'a'
        assertTrue(filter.isBlacklisted("spaaaam")); // 'aaaa' -> 'aa'
        assertTrue(filter.isBlacklisted("spaaaaaam")); // excessive 'a' -> 'aa'
    }

    @Test
    public void testUnicodeCharacters() {
        filter.updateBlacklist("spammer");
        assertTrue(filter.isBlacklisted("spàmmér")); // accented characters
        assertTrue(filter.isBlacklisted("spąmmęr")); // unicode variants
    }

    @Test
    public void testCyrillicCharacters() {
        filter.updateBlacklist("spammer");
        assertTrue(filter.isBlacklisted("spаmmеr")); // а=a (cyrillic), е=e (cyrillic)
    }

    @Test
    public void testRegexPattern() {
        filter.updateBlacklist("regex:spam.*");
        assertTrue(filter.isBlacklisted("spammer123"));
        assertTrue(filter.isBlacklisted("spam_bot"));
        assertFalse(filter.isBlacklisted("legituser"));
    }

    @Test
    public void testMultipleRegexPatterns() {
        filter.updateBlacklist("regex:spam.*,regex:bot.*");
        assertTrue(filter.isBlacklisted("spammer123"));
        assertTrue(filter.isBlacklisted("bot_seller"));
        assertFalse(filter.isBlacklisted("legituser"));
    }

    @Test
    public void testRegexAndNormalMixed() {
        filter.updateBlacklist("spammer,regex:bot.*,scammer");
        assertTrue(filter.isBlacklisted("spammer"));
        assertTrue(filter.isBlacklisted("scammer"));
        assertTrue(filter.isBlacklisted("bot123"));
        assertTrue(filter.isBlacklisted("bot_gold"));
        assertFalse(filter.isBlacklisted("legituser"));
    }

    @Test
    public void testInvalidRegexIgnored() {
        filter.updateBlacklist("regex:[invalid(regex,normalname");
        assertTrue(filter.isBlacklisted("normalname"));
        assertFalse(filter.isBlacklisted("[invalid(regex"));
    }

    @Test
    public void testNullUsername() {
        filter.updateBlacklist("spammer");
        assertFalse(filter.isBlacklisted(null));
    }

    @Test
    public void testEmptyUsername() {
        filter.updateBlacklist("spammer");
        assertFalse(filter.isBlacklisted(""));
    }

    @Test
    public void testWhitespaceHandling() {
        filter.updateBlacklist("  spammer  ,  scammer  ");
        assertTrue(filter.isBlacklisted("spammer"));
        assertTrue(filter.isBlacklisted("scammer"));
    }

    @Test
    public void testEmptyEntriesIgnored() {
        filter.updateBlacklist("spammer,,,,scammer");
        assertTrue(filter.isBlacklisted("spammer"));
        assertTrue(filter.isBlacklisted("scammer"));
    }

    @Test
    public void testUpdateBlacklistClearsPrevious() {
        filter.updateBlacklist("spammer1");
        assertTrue(filter.isBlacklisted("spammer1"));

        filter.updateBlacklist("spammer2");
        assertFalse(filter.isBlacklisted("spammer1"));
        assertTrue(filter.isBlacklisted("spammer2"));
    }

    @Test
    public void testUpdateToEmptyClears() {
        filter.updateBlacklist("spammer1,spammer2");
        assertTrue(filter.isBlacklisted("spammer1"));

        filter.updateBlacklist("");
        assertFalse(filter.isBlacklisted("spammer1"));
        assertFalse(filter.isBlacklisted("spammer2"));
    }

    @Test
    public void testRegexCaseInsensitive() {
        filter.updateBlacklist("regex:spam");
        assertTrue(filter.isBlacklisted("SPAMMER"));
        assertTrue(filter.isBlacklisted("Spammer"));
        assertTrue(filter.isBlacklisted("spammer"));
    }

    @Test
    public void testAccentedCharactersNormalization() {
        filter.updateBlacklist("cafe");
        assertTrue(filter.isBlacklisted("café")); // with accent
        assertTrue(filter.isBlacklisted("cafe")); // without accent
    }

    @Test
    public void testGreekLetters() {
        filter.updateBlacklist("spamer"); // Note: single 'm'
        assertTrue(filter.isBlacklisted("spαmer")); // α→a
        assertTrue(filter.isBlacklisted("spamer")); // exact match
    }

    @Test
    public void testComplexNormalization() {
        filter.updateBlacklist("spamer"); // single m
        assertTrue(filter.isBlacklisted("5p@m3r")); // 5→s, @→a, 3→e
        assertTrue(filter.isBlacklisted("SPAMER")); // case insensitive
        assertTrue(filter.isBlacklisted("spàmęr")); // unicode variants
    }
}
