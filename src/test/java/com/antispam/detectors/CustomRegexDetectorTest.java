package com.antispam.detectors;

import com.antispam.detectors.impl.CustomRegexDetector;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.regex.Pattern;

import static org.junit.Assert.*;

public class CustomRegexDetectorTest {

    private CustomRegexDetector detector;

    @Before
    public void setUp() {
        detector = new CustomRegexDetector(Arrays.asList(
            Pattern.compile("\\b(gold|gp)\\s+(selling|buying)\\b", Pattern.CASE_INSENSITIVE),
            Pattern.compile("\\d{3,}\\s*[kmb]", Pattern.CASE_INSENSITIVE),
            Pattern.compile("discord\\.gg/[\\w-]+", Pattern.CASE_INSENSITIVE)
        ));
    }

    @Test
    public void testGoldSellingPattern() {
        DetectionResult result = detector.detect("gold selling here", "");
        assertTrue(result.isDetected());
        assertEquals("custom-regex", result.getKeyword());
    }

    @Test
    public void testGpBuyingPattern() {
        DetectionResult result = detector.detect("gp buying cheap", "");
        assertTrue(result.isDetected());
        assertEquals("custom-regex", result.getKeyword());
    }

    @Test
    public void testAmountPattern() {
        DetectionResult result = detector.detect("selling items 100m", "");
        assertTrue(result.isDetected());
        assertEquals("custom-regex", result.getKeyword());
    }

    @Test
    public void testAmountPatternK() {
        DetectionResult result = detector.detect("buying for 500k", "");
        assertTrue(result.isDetected());
        assertEquals("custom-regex", result.getKeyword());
    }

    @Test
    public void testAmountPatternB() {
        DetectionResult result = detector.detect("trading 500b", "");
        assertTrue(result.isDetected());
        assertEquals("custom-regex", result.getKeyword());
    }

    @Test
    public void testDiscordPattern() {
        DetectionResult result = detector.detect("join discord.gg/goldtrades", "");
        assertTrue(result.isDetected());
        assertEquals("custom-regex", result.getKeyword());
    }

    @Test
    public void testDiscordPatternWithDashes() {
        DetectionResult result = detector.detect("discord.gg/osrs-gold", "");
        assertTrue(result.isDetected());
        assertEquals("custom-regex", result.getKeyword());
    }

    @Test
    public void testNormalizedTextMatch() {
        DetectionResult result = detector.detect("regular text", "gold selling");
        assertTrue(result.isDetected());
        assertEquals("custom-regex (normalized)", result.getKeyword());
    }

    @Test
    public void testNormalizedAmountPattern() {
        DetectionResult result = detector.detect("", "trading 1000k");
        assertTrue(result.isDetected());
        assertEquals("custom-regex (normalized)", result.getKeyword());
    }

    @Test
    public void testNoMatch() {
        DetectionResult result = detector.detect("normal conversation", "");
        assertFalse(result.isDetected());
    }

    @Test
    public void testNoMatchSmallAmount() {
        DetectionResult result = detector.detect("only 99k", "");
        assertFalse(result.isDetected());
    }

    @Test
    public void testEmptyPatternList() {
        CustomRegexDetector emptyDetector = new CustomRegexDetector(Collections.emptyList());
        DetectionResult result = emptyDetector.detect("gold selling 100m", "");
        assertFalse(result.isDetected());
    }

    @Test
    public void testEmptyText() {
        DetectionResult result = detector.detect("", "");
        assertFalse(result.isDetected());
    }

    @Test
    public void testEmptyTexts() {
        DetectionResult result = detector.detect("", "");
        assertFalse(result.isDetected());
    }

    @Test
    public void testCaseInsensitive() {
        DetectionResult result = detector.detect("GOLD SELLING NOW", "");
        assertTrue(result.isDetected());
        assertEquals("custom-regex", result.getKeyword());
    }

    @Test
    public void testMultiplePatternsFirstMatches() {
        DetectionResult result = detector.detect("gold selling for 100m", "");
        assertTrue(result.isDetected());
        assertEquals("custom-regex", result.getKeyword());
    }

    @Test
    public void testComplexRegexPattern() {
        CustomRegexDetector complexDetector = new CustomRegexDetector(Arrays.asList(
            Pattern.compile("\\b(buy|sell)(ing)?\\s+(tbow|twisted\\s+bow|scythe)\\b", Pattern.CASE_INSENSITIVE)
        ));
        DetectionResult result = complexDetector.detect("buying tbow 1200m", "");
        assertTrue(result.isDetected());
        assertEquals("custom-regex", result.getKeyword());
    }

    @Test
    public void testRegexWithGroups() {
        CustomRegexDetector groupDetector = new CustomRegexDetector(Arrays.asList(
            Pattern.compile("(selling|buying)\\s+(gold|gp|coins)", Pattern.CASE_INSENSITIVE)
        ));
        DetectionResult result = groupDetector.detect("selling gold cheap", "");
        assertTrue(result.isDetected());
    }

    @Test
    public void testRegexWithLookahead() {
        CustomRegexDetector lookaheadDetector = new CustomRegexDetector(Arrays.asList(
            Pattern.compile("\\b\\d+(?=[kmb])", Pattern.CASE_INSENSITIVE)
        ));
        DetectionResult result = lookaheadDetector.detect("selling 100m gp", "");
        assertTrue(result.isDetected());
    }

    @Test
    public void testBothOriginalAndNormalizedNoMatch() {
        DetectionResult result = detector.detect("just chatting", "normalized text");
        assertFalse(result.isDetected());
    }

    @Test
    public void testOriginalMatchesNormalizedDoesNot() {
        DetectionResult result = detector.detect("gold selling here", "normal chat");
        assertTrue(result.isDetected());
        assertEquals("custom-regex", result.getKeyword());
    }

    @Test
    public void testNormalizedMatchesOriginalDoesNot() {
        DetectionResult result = detector.detect("normal chat", "gold selling here");
        assertTrue(result.isDetected());
        assertEquals("custom-regex (normalized)", result.getKeyword());
    }
}
