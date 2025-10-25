package com.antispam.detectors;

import com.antispam.detectors.impl.ShowTradingScamDetector;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ShowTradingScamDetectorTest {

    private ShowTradingScamDetector detector;

    @Before
    public void setUp() {
        detector = new ShowTradingScamDetector();
    }

    @Test
    public void testBankValuePercent() {
        DetectionResult result = detector.detect("show bank value and get 10%", "");
        assertTrue(result.isDetected());
        assertEquals("show-scam: bank-value-percent", result.getKeyword());
    }

    @Test
    public void testShowBankWithAmount() {
        DetectionResult result = detector.detect("show me 100m bank", "");
        assertTrue(result.isDetected());
        assertEquals("show-scam: show-bank-amount", result.getKeyword());
    }

    @Test
    public void testShowBankWithLargeAmount() {
        DetectionResult result = detector.detect("show 500m bank and win", "");
        assertTrue(result.isDetected());
        assertEquals("show-scam: show-bank-amount", result.getKeyword());
    }

    @Test
    public void testDoublingWhatYouShow() {
        DetectionResult result = detector.detect("doubling what you show", "");
        assertTrue(result.isDetected());
        assertEquals("show-scam: doubling-shown", result.getKeyword());
    }

    @Test
    public void testTradeAndShow() {
        DetectionResult result = detector.detect("trade me and show items", "");
        assertTrue(result.isDetected());
        assertEquals("show-scam: trade-and-show", result.getKeyword());
    }

    @Test
    public void testShowItemGetItem() {
        DetectionResult result = detector.detect("show me items and you will get double", "");
        assertTrue(result.isDetected());
        assertEquals("show-scam: show-item-get-item", result.getKeyword());
    }

    @Test
    public void testShowItemReceive() {
        DetectionResult result = detector.detect("show bank you'll receive reward", "");
        assertTrue(result.isDetected());
        assertEquals("show-scam: show-item-get-item", result.getKeyword());
    }

    @Test
    public void testShowMultiplier() {
        DetectionResult result = detector.detect("show me items get 2x back", "");
        assertTrue(result.isDetected());
        assertEquals("show-scam: show-multiplier", result.getKeyword());
    }

    @Test
    public void testShowDoubleMultiplier() {
        DetectionResult result = detector.detect("show your bank doubling it", "");
        assertTrue(result.isDetected());
        assertEquals("show-scam: show-multiplier", result.getKeyword());
    }

    @Test
    public void testShowBestItems() {
        DetectionResult result = detector.detect("show me all your best items", "");
        assertTrue(result.isDetected());
        assertEquals("show-scam: show-best-items", result.getKeyword());
    }

    @Test
    public void testShowValuableItems() {
        DetectionResult result = detector.detect("show your valuable items", "");
        assertTrue(result.isDetected());
        assertEquals("show-scam: show-best-items", result.getKeyword());
    }

    @Test
    public void testHeroAfter() {
        DetectionResult result = detector.detect("you will be hero after this", "");
        assertTrue(result.isDetected());
        assertEquals("show-scam: hero-promise", result.getKeyword());
    }

    @Test
    public void testBecomeHero() {
        DetectionResult result = detector.detect("you are hero after showing", "");
        assertTrue(result.isDetected());
        assertEquals("show-scam: hero-promise", result.getKeyword());
    }

    @Test
    public void testShowBank() {
        DetectionResult result = detector.detect("show me your bank", "");
        assertTrue(result.isDetected());
        assertEquals("show-scam: show-bank", result.getKeyword());
    }

    @Test
    public void testShowMeBank() {
        DetectionResult result = detector.detect("show bank please", "");
        assertTrue(result.isDetected());
        assertEquals("show-scam: show-bank", result.getKeyword());
    }

    @Test
    public void testNormalConversation() {
        DetectionResult result = detector.detect("Can you show me how to get to Varrock?", "");
        assertFalse(result.isDetected());
    }

    @Test
    public void testNormalShowcase() {
        DetectionResult result = detector.detect("Let me show you this cool feature", "");
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
        DetectionResult result = detector.detect("SHOW ME YOUR BANK", "");
        assertTrue(result.isDetected());
    }

    @Test
    public void testShowBankVariation() {
        DetectionResult result = detector.detect("can u show ur bank", "");
        assertTrue(result.isDetected());
    }
}
