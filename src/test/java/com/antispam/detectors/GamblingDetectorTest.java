package com.antispam.detectors;

import com.antispam.detectors.impl.GamblingDetector;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class GamblingDetectorTest {

    private GamblingDetector detector;

    @Before
    public void setUp() {
        detector = new GamblingDetector();
    }

    @Test
    public void testRollPattern() {
        DetectionResult result = detector.detect("has won roll dice 55", "");
        assertTrue(result.isDetected());
        assertEquals("gambling: roll", result.getKeyword());
    }

    @Test
    public void testLostRoll() {
        DetectionResult result = detector.detect("player lost dice roll 32", "");
        assertTrue(result.isDetected());
        assertEquals("gambling: roll", result.getKeyword());
    }

    @Test
    public void testRolledDice() {
        DetectionResult result = detector.detect("has rolled dice 60", "");
        assertTrue(result.isDetected());
        assertEquals("gambling: roll", result.getKeyword());
    }

    @Test
    public void testBetPatternMinimum() {
        DetectionResult result = detector.detect("my minimum bet is 10m", "");
        assertTrue(result.isDetected());
        assertEquals("gambling: bet", result.getKeyword());
    }

    @Test
    public void testBetPatternMaximum() {
        DetectionResult result = detector.detect("maximum bet 100m", "");
        assertTrue(result.isDetected());
        assertEquals("gambling: bet", result.getKeyword());
    }

    @Test
    public void testQueuePattern() {
        DetectionResult result = detector.detect("the queue has 5 players waiting", "");
        assertTrue(result.isDetected());
        assertEquals("gambling: queue", result.getKeyword());
    }

    @Test
    public void testPayoutPattern() {
        DetectionResult result = detector.detect("huge payout for winners", "");
        assertTrue(result.isDetected());
        assertEquals("gambling: payout", result.getKeyword());
    }

    @Test
    public void testMassivePayout() {
        DetectionResult result = detector.detect("massive wins available", "");
        assertTrue(result.isDetected());
        assertEquals("gambling: payout", result.getKeyword());
    }

    @Test
    public void testReceivedWinnings() {
        DetectionResult result = detector.detect("has received winning 50m", "");
        assertTrue(result.isDetected());
        assertEquals("gambling: winnings", result.getKeyword());
    }

    @Test
    public void testCashedOut() {
        DetectionResult result = detector.detect("player cashed their 100m", "");
        assertTrue(result.isDetected());
        assertEquals("gambling: winnings", result.getKeyword());
    }

    @Test
    public void testTradeAccepted() {
        DetectionResult result = detector.detect("trade accepted for 50m", "");
        assertTrue(result.isDetected());
        assertEquals("gambling: trade-accepted", result.getKeyword());
    }

    @Test
    public void testRollCommand() {
        DetectionResult result = detector.detect("roll 55+", "");
        assertTrue(result.isDetected());
        assertEquals("gambling: roll-command", result.getKeyword());
    }

    @Test
    public void testDiceHost() {
        DetectionResult result = detector.detect("most trusted dice host", "");
        assertTrue(result.isDetected());
        assertEquals("gambling: dice-host", result.getKeyword());
    }

    @Test
    public void testOriginalCasino() {
        DetectionResult result = detector.detect("the original casino", "");
        assertTrue(result.isDetected());
        assertEquals("gambling: dice-host", result.getKeyword());
    }

    @Test
    public void testLegitHost() {
        DetectionResult result = detector.detect("legit dice games here", "");
        assertTrue(result.isDetected());
        assertEquals("gambling: dice-host", result.getKeyword());
    }

    @Test
    public void testDoublingMoney() {
        DetectionResult result = detector.detect("doubling money 100m max", "");
        assertTrue(result.isDetected());
        assertEquals("gambling: doubling", result.getKeyword());
    }

    @Test
    public void testDoubleGold() {
        DetectionResult result = detector.detect("double your gold", "");
        assertTrue(result.isDetected());
        assertEquals("gambling: doubling", result.getKeyword());
    }

    @Test
    public void testDoublingAmounts() {
        DetectionResult result = detector.detect("doubling all amounts", "");
        assertTrue(result.isDetected());
        assertEquals("gambling: doubling", result.getKeyword());
    }

    @Test
    public void testMultiplyPattern() {
        DetectionResult result = detector.detect("2x your items", "");
        assertTrue(result.isDetected());
        assertEquals("gambling: multiply", result.getKeyword());
    }

    @Test
    public void testMultiplyAll() {
        DetectionResult result = detector.detect("5x them all", "");
        assertTrue(result.isDetected());
        assertEquals("gambling: multiply", result.getKeyword());
    }

    @Test
    public void testGiveYouDouble() {
        DetectionResult result = detector.detect("give you 200m", "");
        assertTrue(result.isDetected());
        assertEquals("gambling: give-you-double", result.getKeyword());
    }

    @Test
    public void testGetYouAmount() {
        DetectionResult result = detector.detect("get you 500k gp", "");
        assertTrue(result.isDetected());
        assertEquals("gambling: give-you-double", result.getKeyword());
    }

    @Test
    public void testNormalConversation() {
        DetectionResult result = detector.detect("I won my first fight", "");
        assertFalse(result.isDetected());
    }

    @Test
    public void testDuelArenaLegit() {
        DetectionResult result = detector.detect("I doubled my money at Duel Arena", "");
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
        DetectionResult result = detector.detect("DOUBLING MONEY 100M MAX", "");
        assertTrue(result.isDetected());
    }
}
