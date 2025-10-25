package com.antispam.detectors;

import com.antispam.detectors.impl.SuspiciousPatternDetector;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class SuspiciousPatternDetectorTest {

    private SuspiciousPatternDetector detector;

    @Before
    public void setUp() {
        detector = new SuspiciousPatternDetector();
    }

    @Test
    public void testSpacedSingleLetters() {
        DetectionResult result = detector.detect("b u y i n g gold", "");
        assertTrue(result.isDetected());
        assertEquals("suspicious-pattern", result.getKeyword());
    }

    @Test
    public void testSpacedLettersLonger() {
        DetectionResult result = detector.detect("s e l l i n g items", "");
        assertTrue(result.isDetected());
        assertEquals("suspicious-pattern", result.getKeyword());
    }

    @Test
    public void testExcessiveSpecialChars() {
        DetectionResult result = detector.detect("!!!!!!! gold for sale", "");
        assertTrue(result.isDetected());
        assertEquals("suspicious-pattern", result.getKeyword());
    }

    @Test
    public void testExcessiveDashes() {
        DetectionResult result = detector.detect("------buying------", "");
        assertTrue(result.isDetected());
        assertEquals("suspicious-pattern", result.getKeyword());
    }

    @Test
    public void testExcessiveUnderscores() {
        DetectionResult result = detector.detect("______selling______", "");
        assertTrue(result.isDetected());
        assertEquals("suspicious-pattern", result.getKeyword());
    }

    @Test
    public void testExcessiveDots() {
        DetectionResult result = detector.detect("......trading......", "");
        assertTrue(result.isDetected());
        assertEquals("suspicious-pattern", result.getKeyword());
    }

    @Test
    public void testExcessiveSymbols() {
        DetectionResult result = detector.detect("****** gp for sale ******", "");
        assertTrue(result.isDetected());
        assertEquals("suspicious-pattern", result.getKeyword());
    }

    @Test
    public void testMixedSpecialChars() {
        DetectionResult result = detector.detect("!@#$%^ buying", "");
        assertTrue(result.isDetected());
        assertEquals("suspicious-pattern", result.getKeyword());
    }

    @Test
    public void testNormalMessage() {
        DetectionResult result = detector.detect("buying twisted bow", "");
        assertFalse(result.isDetected());
    }

    @Test
    public void testNormalExclamation() {
        DetectionResult result = detector.detect("wow! that's amazing!", "");
        assertFalse(result.isDetected());
    }

    @Test
    public void testNormalSpacing() {
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
    public void testShortSpacedText() {
        DetectionResult result = detector.detect("a b c", "");
        assertFalse(result.isDetected());
    }

    @Test
    public void testJustEnoughSpacedLetters() {
        DetectionResult result = detector.detect("g o l d", "");
        assertTrue(result.isDetected());
        assertEquals("suspicious-pattern", result.getKeyword());
    }

    @Test
    public void testFiveSpecialChars() {
        DetectionResult result = detector.detect("!!!!!selling", "");
        assertFalse(result.isDetected());
    }

    @Test
    public void testSixSpecialChars() {
        DetectionResult result = detector.detect("!!!!!!selling", "");
        assertTrue(result.isDetected());
        assertEquals("suspicious-pattern", result.getKeyword());
    }

    @Test
    public void testSpacedTextInMiddle() {
        DetectionResult result = detector.detect("check out g o l d here", "");
        assertTrue(result.isDetected());
    }
}
