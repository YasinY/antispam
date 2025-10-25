package com.antispam.detectors;

import com.antispam.detectors.impl.AlertSpamDetector;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class AlertSpamDetectorTest {

    private AlertSpamDetector detector;

    @Before
    public void setUp() {
        detector = new AlertSpamDetector();
    }

    @Test
    public void testAlertTag() {
        DetectionResult result = detector.detect("[ALERT] Player123 is suspicious", "");
        assertTrue(result.isDetected());
        assertEquals("alert-spam: tag", result.getKeyword());
    }

    @Test
    public void testWarningTag() {
        DetectionResult result = detector.detect("[Warning] Beware of scammers", "");
        assertTrue(result.isDetected());
        assertEquals("alert-spam: tag", result.getKeyword());
    }

    @Test
    public void testBewareTag() {
        DetectionResult result = detector.detect("[Beware] This player is a lurer", "");
        assertTrue(result.isDetected());
        assertEquals("alert-spam: tag", result.getKeyword());
    }

    @Test
    public void testCautionTag() {
        DetectionResult result = detector.detect("[Caution] Avoid this player", "");
        assertTrue(result.isDetected());
        assertEquals("alert-spam: tag", result.getKeyword());
    }

    @Test
    public void testScammerAlert() {
        DetectionResult result = detector.detect("Player123 is a scammer", "");
        assertTrue(result.isDetected());
        assertEquals("alert-spam: scammer", result.getKeyword());
    }

    @Test
    public void testScammerAlertWithTypo() {
        DetectionResult result = detector.detect("Player123 is a scamer", "");
        assertTrue(result.isDetected());
        assertEquals("alert-spam: scammer", result.getKeyword());
    }

    @Test
    public void testBewareOfScammer() {
        DetectionResult result = detector.detect("beware of Player123 he is a scammer", "");
        assertTrue(result.isDetected());
        assertEquals("alert-spam: scammer", result.getKeyword());
    }

    @Test
    public void testLurerAlert() {
        DetectionResult result = detector.detect("Player123 is a lurer beware", "");
        assertTrue(result.isDetected());
        assertTrue(result.getKeyword().contains("alert-spam"));
    }

    @Test
    public void testFakePlayerAlert() {
        DetectionResult result = detector.detect("Player123 are fake don't trust", "");
        assertTrue(result.isDetected());
        assertEquals("alert-spam: scammer", result.getKeyword());
    }

    @Test
    public void testIgnoreAlert() {
        DetectionResult result = detector.detect("add Player123 to ignore", "");
        assertTrue(result.isDetected());
        assertEquals("alert-spam: ignore", result.getKeyword());
    }

    @Test
    public void testPutToIgnore() {
        DetectionResult result = detector.detect("put these lurers to ignore", "");
        assertTrue(result.isDetected());
        assertEquals("alert-spam: ignore", result.getKeyword());
    }

    @Test
    public void testFakeAntiscam() {
        DetectionResult result = detector.detect("fake anti-scam plugin beware", "");
        assertTrue(result.isDetected());
        assertEquals("alert-spam: fake-antiscam", result.getKeyword());
    }

    @Test
    public void testFakeAntilur() {
        DetectionResult result = detector.detect("fake antilur dont trust", "");
        assertTrue(result.isDetected());
        assertEquals("alert-spam: fake-antiscam", result.getKeyword());
    }

    @Test
    public void testSceptreLure() {
        DetectionResult result = detector.detect("sceptre lure at wilderness", "");
        assertTrue(result.isDetected());
        assertEquals("alert-spam: sceptre-lure", result.getKeyword());
    }

    @Test
    public void testLocationWarningBarbOutpost() {
        DetectionResult result = detector.detect("don't go to barb outpost", "");
        assertTrue(result.isDetected());
        assertEquals("alert-spam: location-warning", result.getKeyword());
    }

    @Test
    public void testLocationWarningShantyPass() {
        DetectionResult result = detector.detect("avoid shanty pass area", "");
        assertTrue(result.isDetected());
        assertEquals("alert-spam: location-warning", result.getKeyword());
    }

    @Test
    public void testLocationWarningRoguesDen() {
        DetectionResult result = detector.detect("rogues den is dangerous", "");
        assertTrue(result.isDetected());
        assertEquals("alert-spam: location-warning", result.getKeyword());
    }

    @Test
    public void testUnsafeBanks() {
        DetectionResult result = detector.detect("unsafe banks in wilderness", "");
        assertTrue(result.isDetected());
        assertEquals("alert-spam: unsafe-banks", result.getKeyword());
    }

    @Test
    public void testNotSafePvp() {
        DetectionResult result = detector.detect("not safe on pvp worlds", "");
        assertTrue(result.isDetected());
        assertEquals("alert-spam: not-safe-pvp", result.getKeyword());
    }

    @Test
    public void testLurersToIgnore() {
        DetectionResult result = detector.detect("lurers to ignore list", "");
        assertTrue(result.isDetected());
        assertEquals("alert-spam: lurers-list", result.getKeyword());
    }

    @Test
    public void testDoNotAttempt() {
        DetectionResult result = detector.detect("do not attempt anti-scam methods", "");
        assertTrue(result.isDetected());
        assertEquals("alert-spam: do-not-attempt", result.getKeyword());
    }

    @Test
    public void testNeverGetSceptre() {
        DetectionResult result = detector.detect("never get sceptre from strangers", "");
        assertTrue(result.isDetected());
        assertEquals("alert-spam: never-get-sceptre", result.getKeyword());
    }

    @Test
    public void testNeverGiveSceptre() {
        DetectionResult result = detector.detect("never give your sceptre away", "");
        assertTrue(result.isDetected());
        assertEquals("alert-spam: never-get-sceptre", result.getKeyword());
    }

    @Test
    public void testGlitchesToScam() {
        DetectionResult result = detector.detect("using glitches to scam players", "");
        assertTrue(result.isDetected());
        assertEquals("alert-spam: glitches-scam", result.getKeyword());
    }

    @Test
    public void testBringYouToUnsafe() {
        DetectionResult result = detector.detect("will bring you to unsafe area", "");
        assertTrue(result.isDetected());
        assertEquals("alert-spam: bring-unsafe", result.getKeyword());
    }

    @Test
    public void testNormalMessage() {
        DetectionResult result = detector.detect("anyone want to do a quest?", "");
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
        DetectionResult result = detector.detect("[ALERT] SCAMMER HERE", "");
        assertTrue(result.isDetected());
        assertEquals("alert-spam: tag", result.getKeyword());
    }
}
