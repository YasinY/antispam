package com.antispam.detectors;

import com.antispam.detectors.impl.KeywordComboDetector;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class KeywordComboDetectorTest {

    private KeywordComboDetector detector;

    @Before
    public void setUp() {
        detector = new KeywordComboDetector();
    }

    @Test
    public void testNeedWithAmountMil() {
        DetectionResult result = detector.detect("need 100m for bond", "");
        assertTrue(result.isDetected());
        assertEquals("combo: need + amount", result.getKeyword());
    }

    @Test
    public void testNeedWithAmountK() {
        DetectionResult result = detector.detect("need 50k gp", "");
        assertTrue(result.isDetected());
        assertEquals("combo: need + amount", result.getKeyword());
    }

    @Test
    public void testNeedWithAmountMil2() {
        DetectionResult result = detector.detect("need 5 mil please", "");
        assertTrue(result.isDetected());
        assertEquals("combo: need + amount", result.getKeyword());
    }

    @Test
    public void testFreeGold() {
        DetectionResult result = detector.detect("free gold for everyone", "");
        assertTrue(result.isDetected());
        assertTrue(result.getKeyword().contains("free + gold"));
    }

    @Test
    public void testFreeGp() {
        DetectionResult result = detector.detect("free gp giveaway", "");
        assertTrue(result.isDetected());
        assertTrue(result.getKeyword().contains("free + gp"));
    }

    @Test
    public void testFreeCoins() {
        DetectionResult result = detector.detect("free coins for newbies", "");
        assertTrue(result.isDetected());
        assertTrue(result.getKeyword().contains("free + coins"));
    }

    @Test
    public void testSellingWithWebsite() {
        DetectionResult result = detector.detect("selling gold at rsgold.com", "");
        assertTrue(result.isDetected());
        assertTrue(result.getKeyword().contains("selling + .com"));
    }

    @Test
    public void testBuyingWithWebsite() {
        DetectionResult result = detector.detect("buying items at trade.net", "");
        assertTrue(result.isDetected());
        assertTrue(result.getKeyword().contains("buying + .net"));
    }

    @Test
    public void testVisitYoutube() {
        DetectionResult result = detector.detect("visit my youtube channel", "");
        assertTrue(result.isDetected());
        assertTrue(result.getKeyword().contains("visit + youtube"));
    }

    @Test
    public void testCheckYoutube() {
        DetectionResult result = detector.detect("check my youtube for guides", "");
        assertTrue(result.isDetected());
        assertTrue(result.getKeyword().contains("check + youtube"));
    }

    @Test
    public void testCheckOutYoutube() {
        DetectionResult result = detector.detect("check out my youtube channel", "");
        assertTrue(result.isDetected());
        assertTrue(result.getKeyword().contains("youtube"));
    }

    @Test
    public void testClickLink() {
        DetectionResult result = detector.detect("click this link for rewards", "");
        assertTrue(result.isDetected());
        assertTrue(result.getKeyword().contains("click + link"));
    }

    @Test
    public void testPmTrade() {
        DetectionResult result = detector.detect("pm me to trade", "");
        assertTrue(result.isDetected());
        assertTrue(result.getKeyword().contains("pm + trade"));
    }

    @Test
    public void testDmTrade() {
        DetectionResult result = detector.detect("dm for trade details", "");
        assertTrue(result.isDetected());
        assertTrue(result.getKeyword().contains("dm + trade"));
    }

    @Test
    public void testTradingUpMillion() {
        DetectionResult result = detector.detect("trading up from 100 million", "");
        assertTrue(result.isDetected());
        assertTrue(result.getKeyword().contains("trading up + million"));
    }

    @Test
    public void testTradingUpGp() {
        DetectionResult result = detector.detect("trading up 50k gp", "");
        assertTrue(result.isDetected());
        assertTrue(result.getKeyword().contains("trading up + gp"));
    }

    @Test
    public void testBuyTbow() {
        DetectionResult result = detector.detect("buy my tbow now", "");
        assertTrue(result.isDetected());
        assertTrue(result.getKeyword().contains("buy + tbow"));
    }

    @Test
    public void testBuyTwistedBow() {
        DetectionResult result = detector.detect("buy this twisted bow", "");
        assertTrue(result.isDetected());
        assertTrue(result.getKeyword().contains("buy + twisted bow"));
    }

    @Test
    public void testBuyScythe() {
        DetectionResult result = detector.detect("buy my scythe 2500m", "");
        assertTrue(result.isDetected());
        assertTrue(result.getKeyword().contains("buy + scythe"));
    }

    @Test
    public void testBuyEly() {
        DetectionResult result = detector.detect("buy ely shield", "");
        assertTrue(result.isDetected());
        assertTrue(result.getKeyword().contains("buy + ely"));
    }

    @Test
    public void testSell3rdAge() {
        DetectionResult result = detector.detect("sell 3rd age items", "");
        assertTrue(result.isDetected());
        assertTrue(result.getKeyword().contains("sell + 3rd"));
    }

    @Test
    public void testBuying3rdAge() {
        DetectionResult result = detector.detect("buying 3rd age platebody", "");
        assertTrue(result.isDetected());
        assertTrue(result.getKeyword().contains("3rd") || result.getKeyword().contains("buying"));
    }

    @Test
    public void testNeedGold() {
        DetectionResult result = detector.detect("need gold for supplies", "");
        assertTrue(result.isDetected());
        assertTrue(result.getKeyword().contains("need + gold"));
    }

    @Test
    public void testNeedGp() {
        DetectionResult result = detector.detect("need gp for gear", "");
        assertTrue(result.isDetected());
        assertTrue(result.getKeyword().contains("need + gp"));
    }

    @Test
    public void testPleaseItems() {
        DetectionResult result = detector.detect("pls some gp", "");
        assertTrue(result.isDetected());
        assertTrue(result.getKeyword().contains("combo"));
    }

    @Test
    public void testPlzItems() {
        DetectionResult result = detector.detect("plz items please", "");
        assertTrue(result.isDetected());
        assertTrue(result.getKeyword().contains("plz + items"));
    }

    @Test
    public void testSellBank() {
        DetectionResult result = detector.detect("sell your bank for cash", "");
        assertTrue(result.isDetected());
        assertTrue(result.getKeyword().contains("sell + bank") || result.getKeyword().contains("your + bank"));
    }

    @Test
    public void testPayingExtra() {
        DetectionResult result = detector.detect("paying extra for items", "");
        assertTrue(result.isDetected());
        assertTrue(result.getKeyword().contains("paying + extra"));
    }

    @Test
    public void testShowBankValue() {
        DetectionResult result = detector.detect("show me your bank value", "");
        assertTrue(result.isDetected());
        assertTrue(result.getKeyword().contains("bank") || result.getKeyword().contains("show"));
    }

    @Test
    public void testDoublingBanks() {
        DetectionResult result = detector.detect("doubling banks 100m max", "");
        assertTrue(result.isDetected());
        assertTrue(result.getKeyword().contains("doubling + banks"));
    }

    @Test
    public void testFirstShows() {
        DetectionResult result = detector.detect("first who shows wins", "");
        assertTrue(result.isDetected());
        assertTrue(result.getKeyword().contains("first + shows"));
    }

    @Test
    public void testNormalMessage() {
        DetectionResult result = detector.detect("I need to go to the bank", "");
        assertFalse(result.isDetected());
    }

    @Test
    public void testNormalNeed() {
        DetectionResult result = detector.detect("need help with quest", "");
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
        DetectionResult result = detector.detect("FREE GOLD FOR ALL", "");
        assertTrue(result.isDetected());
    }

    @Test
    public void testNormalizedTextDetection() {
        // originalText has leetspeak that won't match, but normalizedText will
        DetectionResult result = detector.detect("fr33 g0ld for everyone", "free gold for everyone");
        assertTrue(result.isDetected());
        assertTrue(result.getKeyword().contains("normalized"));
    }

    @Test
    public void testNormalizedSellingWebsite() {
        // Test normalized text matching for selling + .com combo
        DetectionResult result = detector.detect("s3lling at rsg0ld.c0m", "selling at rsgold.com");
        assertTrue(result.isDetected());
        assertTrue(result.getKeyword().contains("normalized"));
    }

    @Test
    public void testNormalizedNeedGp() {
        // Test normalized text matching for need + gp combo
        DetectionResult result = detector.detect("n33d gp pl0x", "need gp plox");
        assertTrue(result.isDetected());
        assertTrue(result.getKeyword().contains("normalized"));
    }
}
