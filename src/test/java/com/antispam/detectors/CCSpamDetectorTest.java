package com.antispam.detectors;

import com.antispam.detectors.impl.CCSpamDetector;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class CCSpamDetectorTest {

    private CCSpamDetector detector;

    @Before
    public void setUp() {
        detector = new CCSpamDetector();
    }

    @Test
    public void testJoinCC() {
        DetectionResult result = detector.detect("join our cc for trades", "");
        assertTrue(result.isDetected());
        assertEquals("cc-spam: join", result.getKeyword());
    }

    @Test
    public void testJoinChat() {
        DetectionResult result = detector.detect("join the chat for rewards", "");
        assertTrue(result.isDetected());
        assertEquals("cc-spam: join", result.getKeyword());
    }

    @Test
    public void testJoinClan() {
        DetectionResult result = detector.detect("join clan now", "");
        assertTrue(result.isDetected());
        assertEquals("cc-spam: join", result.getKeyword());
    }

    @Test
    public void testSwapCC() {
        DetectionResult result = detector.detect("swap rs3 to osrs best rates cc", "");
        assertTrue(result.isDetected());
        assertEquals("cc-spam: swap", result.getKeyword());
    }

    @Test
    public void testSwappingGold() {
        DetectionResult result = detector.detect("swapping 07 gp to rs3", "");
        assertTrue(result.isDetected());
        assertEquals("cc-spam: swap", result.getKeyword());
    }

    @Test
    public void testCCBraces() {
        DetectionResult result = detector.detect("join <gt>SwapCC<lt> for trades", "");
        assertTrue(result.isDetected());
        assertEquals("cc-spam: braces", result.getKeyword());
    }

    @Test
    public void testCCBrackets() {
        DetectionResult result = detector.detect("join [SwapCC] now", "");
        assertTrue(result.isDetected());
        assertEquals("cc-spam: braces", result.getKeyword());
    }

    @Test
    public void testCCParenthesis() {
        DetectionResult result = detector.detect("join (GoldCC) for best rates", "");
        assertTrue(result.isDetected());
        assertEquals("cc-spam: braces", result.getKeyword());
    }

    @Test
    public void testMostTrusted() {
        DetectionResult result = detector.detect("most trusted swap cc", "");
        assertTrue(result.isDetected());
        assertEquals("cc-spam: trusted", result.getKeyword());
    }

    @Test
    public void testBestSwap() {
        DetectionResult result = detector.detect("best swap clan in game", "");
        assertTrue(result.isDetected());
        assertEquals("cc-spam: trusted", result.getKeyword());
    }

    @Test
    public void testDropPartyCC() {
        DetectionResult result = detector.detect("drop party tonight in cc", "");
        assertTrue(result.isDetected());
        assertEquals("cc-spam: drop-party", result.getKeyword());
    }

    @Test
    public void testGiveawayCC() {
        DetectionResult result = detector.detect("giveaway at 8pm in chat", "");
        assertTrue(result.isDetected());
        assertEquals("cc-spam: drop-party", result.getKeyword());
    }

    @Test
    public void testSpecificCCWorkless() {
        DetectionResult result = detector.detect("join workless for swaps", "");
        assertTrue(result.isDetected());
        assertEquals("cc-spam: specific-cc", result.getKeyword());
    }

    @Test
    public void testSpecificCCWKSwap() {
        DetectionResult result = detector.detect("wk swap best rates", "");
        assertTrue(result.isDetected());
        assertEquals("cc-spam: specific-cc", result.getKeyword());
    }

    @Test
    public void testSpecificCCRCSwap() {
        DetectionResult result = detector.detect("rc swap has best prices", "");
        assertTrue(result.isDetected());
        assertEquals("cc-spam: specific-cc", result.getKeyword());
    }

    @Test
    public void testNormalClanInvite() {
        DetectionResult result = detector.detect("want to join our bossing team?", "");
        assertFalse(result.isDetected());
    }

    @Test
    public void testNormalConversation() {
        DetectionResult result = detector.detect("I'm in a clan doing raids", "");
        assertFalse(result.isDetected());
    }

    @Test
    public void testNullText() {
        DetectionResult result = detector.detect(null, "");
        assertFalse(result.isDetected());
    }

    @Test
    public void testEmptyText() {
        DetectionResult result = detector.detect("", "");
        assertFalse(result.isDetected());
    }

    @Test
    public void testCaseInsensitive() {
        DetectionResult result = detector.detect("JOIN OUR CC NOW", "");
        assertTrue(result.isDetected());
    }

    @Test
    public void testDMMSwap() {
        DetectionResult result = detector.detect("swap dmm to 07 gp", "");
        assertTrue(result.isDetected());
        assertEquals("cc-spam: swap", result.getKeyword());
    }
}
