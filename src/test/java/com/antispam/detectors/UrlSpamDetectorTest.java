package com.antispam.detectors;

import com.antispam.detectors.impl.UrlSpamDetector;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class UrlSpamDetectorTest {

    private UrlSpamDetector detector;

    @Before
    public void setUp() {
        detector = new UrlSpamDetector();
    }

    @Test
    public void testStandardHttpUrl() {
        DetectionResult result = detector.detect("check out http://rsgold.com", "");
        assertTrue(result.isDetected());
        assertEquals("url", result.getKeyword());
    }

    @Test
    public void testStandardHttpsUrl() {
        DetectionResult result = detector.detect("visit https://buygold.net", "");
        assertTrue(result.isDetected());
        assertEquals("url", result.getKeyword());
    }

    @Test
    public void testWwwUrl() {
        DetectionResult result = detector.detect("go to www.rsgold.com", "");
        assertTrue(result.isDetected());
        assertEquals("url", result.getKeyword());
    }

    @Test
    public void testSpacedDomain() {
        DetectionResult result = detector.detect("visit rsgold .com for gold", "");
        assertTrue(result.isDetected());
        assertEquals("spaced-url", result.getKeyword());
    }

    @Test
    public void testDottedDomain() {
        DetectionResult result = detector.detect("rsgold . c . o . m", "");
        assertTrue(result.isDetected());
        assertEquals("dotted-url", result.getKeyword());
    }

    @Test
    public void testWordDot() {
        DetectionResult result = detector.detect("rsgold dot com", "");
        assertTrue(result.isDetected());
        assertEquals("word-dot", result.getKeyword());
    }

    @Test
    public void testWordDotWithNumbers() {
        DetectionResult result = detector.detect("rsg0ld d0t com", "");
        assertTrue(result.isDetected());
        assertEquals("word-dot", result.getKeyword());
    }

    @Test
    public void testParenthesisDot() {
        DetectionResult result = detector.detect("rsgold(dot)com", "");
        assertTrue(result.isDetected());
        assertEquals("parenthesis-dot", result.getKeyword());
    }

    @Test
    public void testDiscordLink() {
        DetectionResult result = detector.detect("join discord.gg/goldtrades", "");
        assertTrue(result.isDetected());
        assertTrue(result.getKeyword().contains("url") || result.getKeyword().contains("discord"));
    }

    @Test
    public void testDiscordLinkObfuscated() {
        DetectionResult result = detector.detect("disc0rd d0t gg", "");
        assertTrue(result.isDetected());
        assertTrue(result.getKeyword().contains("word-dot") || result.getKeyword().contains("discord"));
    }

    @Test
    public void testDiscordLinkExact() {
        DetectionResult result = detector.detect("join discord . gg now", "");
        assertTrue(result.isDetected());
    }

    @Test
    public void testDiscordDotGgWord() {
        DetectionResult result = detector.detect("discord d0t gg giveaway", "");
        assertTrue(result.isDetected());
    }

    @Test
    public void testSubstitutionDomainCom() {
        DetectionResult result = detector.detect("rsgold.c0m for gold", "");
        assertTrue(result.isDetected());
        assertEquals("substitution-url", result.getKeyword());
    }

    @Test
    public void testSubstitutionDomainNet() {
        DetectionResult result = detector.detect("buy at rsgold,n3t", "");
        assertTrue(result.isDetected());
        assertEquals("substitution-url", result.getKeyword());
    }

    @Test
    public void testSlashSeparator() {
        DetectionResult result = detector.detect("rsgold / com", "");
        assertTrue(result.isDetected());
        assertEquals("slash-url", result.getKeyword());
    }

    @Test
    public void testAtDomain() {
        DetectionResult result = detector.detect("contact us @ rsgold.com", "");
        assertTrue(result.isDetected());
        assertTrue(result.getKeyword() != null && result.getKeyword().contains("url"));
    }

    @Test
    public void testAtDomainVariations() {
        DetectionResult result = detector.detect("msg @goldsite . gg", "");
        assertTrue(result.isDetected());
    }

    @Test
    public void testAtDomainSpaced() {
        DetectionResult result = detector.detect("find me @ site . net", "");
        assertTrue(result.isDetected());
    }

    @Test
    public void testSpecialCharsDomain() {
        DetectionResult result = detector.detect("rsgold<>com", "");
        assertTrue(result.isDetected());
        assertEquals("special-chars-url", result.getKeyword());
    }

    @Test
    public void testDomainWithDifferentTlds() {
        DetectionResult result = detector.detect("visit rsgold.net", "");
        assertTrue(result.isDetected());
        assertEquals("spaced-url", result.getKeyword());
    }

    @Test
    public void testDomainGg() {
        DetectionResult result = detector.detect("visit goldsite.gg", "");
        assertTrue(result.isDetected());
        assertEquals("spaced-url", result.getKeyword());
    }

    @Test
    public void testDomainIo() {
        DetectionResult result = detector.detect("check goldtrade.io", "");
        assertTrue(result.isDetected());
        assertEquals("spaced-url", result.getKeyword());
    }

    @Test
    public void testNormalMessage() {
        DetectionResult result = detector.detect("I need help with a quest", "");
        assertFalse(result.isDetected());
    }

    @Test
    public void testNormalRedditMention() {
        DetectionResult result = detector.detect("I saw this on reddit", "");
        assertFalse(result.isDetected());
    }

    @Test
    public void testEmptyText() {
        DetectionResult result = detector.detect("", "");
        assertFalse(result.isDetected());
    }

    @Test
    public void testCaseInsensitive() {
        DetectionResult result = detector.detect("VISIT WWW.RSGOLD.COM", "");
        assertTrue(result.isDetected());
    }

    @Test
    public void testMultipleUrlPatterns() {
        DetectionResult result = detector.detect("visit http://rsgold.com or discord.gg/trade", "");
        assertTrue(result.isDetected());
    }

    @Test
    public void testLeetspeakUrl() {
        DetectionResult result = detector.detect("v1s1t rsg0ld.c0m", "");
        assertTrue(result.isDetected());
    }

    @Test
    public void testNoUrlInNormalChat() {
        DetectionResult result = detector.detect("I like to play this game", "");
        assertFalse(result.isDetected());
    }

    @Test
    public void testNoUrlWithDotInSentence() {
        DetectionResult result = detector.detect("Yes. I agree with that.", "");
        assertFalse(result.isDetected());
    }

    @Test
    public void testNoUrlDiscordMention() {
        DetectionResult result = detector.detect("I use discord but not for spam", "");
        assertFalse(result.isDetected());
    }

    @Test
    public void testSpecialCharsWithoutUrl() {
        DetectionResult result = detector.detect("This is <cool> stuff!", "");
        assertFalse(result.isDetected());
    }

    @Test
    public void testAtSymbolWithoutDomain() {
        DetectionResult result = detector.detect("meet @ ge", "");
        assertFalse(result.isDetected());
    }

    @Test
    public void testSpecialCharsBrackets() {
        DetectionResult result = detector.detect("rsgold[]com for trades", "");
        assertTrue(result.isDetected());
    }

    @Test
    public void testSpecialCharsBraces() {
        DetectionResult result = detector.detect("visit goldsite{}gg now", "");
        assertTrue(result.isDetected());
    }
}
