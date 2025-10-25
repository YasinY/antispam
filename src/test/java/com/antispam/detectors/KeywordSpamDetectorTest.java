package com.antispam.detectors;

import com.antispam.detectors.impl.KeywordSpamDetector;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;

import static org.junit.Assert.*;

public class KeywordSpamDetectorTest {

    private KeywordSpamDetector detector;

    @Before
    public void setUp() {
        detector = new KeywordSpamDetector(
            Arrays.asList("selling", "buying", "gold", "scam"),
            Arrays.asList("rsgold", "gpstore")
        );
    }

    @Test
    public void testStandaloneKeyword() {
        DetectionResult result = detector.detect("I am selling items", "");
        assertTrue(result.isDetected());
        assertEquals("selling", result.getKeyword());
    }

    @Test
    public void testStandaloneKeywordAtStart() {
        DetectionResult result = detector.detect("selling gold now", "");
        assertTrue(result.isDetected());
        assertEquals("selling", result.getKeyword());
    }

    @Test
    public void testStandaloneKeywordAtEnd() {
        DetectionResult result = detector.detect("I am buying", "");
        assertTrue(result.isDetected());
        assertEquals("buying", result.getKeyword());
    }

    @Test
    public void testSubstringKeyword() {
        DetectionResult result = detector.detect("check out rsgold website", "");
        assertTrue(result.isDetected());
        assertEquals("rsgold", result.getKeyword());
    }

    @Test
    public void testSubstringKeywordInMiddle() {
        DetectionResult result = detector.detect("visit gpstore for deals", "");
        assertTrue(result.isDetected());
        assertEquals("gpstore", result.getKeyword());
    }

    @Test
    public void testNegatedKeywordDont() {
        DetectionResult result = detector.detect("don't buy from scammers", "");
        assertFalse(result.isDetected());
    }

    @Test
    public void testNegatedKeywordNot() {
        DetectionResult result = detector.detect("not selling anything", "");
        assertFalse(result.isDetected());
    }

    @Test
    public void testNegatedKeywordNo() {
        DetectionResult result = detector.detect("no gold here", "");
        assertFalse(result.isDetected());
    }

    @Test
    public void testNegatedKeywordNever() {
        DetectionResult result = detector.detect("never buying gold", "");
        assertFalse(result.isDetected());
    }

    @Test
    public void testNegatedKeywordCant() {
        DetectionResult result = detector.detect("can't sell my items", "");
        assertFalse(result.isDetected());
    }

    @Test
    public void testCaseInsensitive() {
        DetectionResult result = detector.detect("SELLING GOLD NOW", "");
        assertTrue(result.isDetected());
        assertEquals("selling", result.getKeyword());
    }

    @Test
    public void testMixedCase() {
        DetectionResult result = detector.detect("BuYiNg items", "");
        assertTrue(result.isDetected());
        assertEquals("buying", result.getKeyword());
    }

    @Test
    public void testMultiWordKeyword() {
        KeywordSpamDetector multiWordDetector = new KeywordSpamDetector(
            Arrays.asList("twisted bow", "dragon claws"),
            Collections.emptyList()
        );
        DetectionResult result = multiWordDetector.detect("selling twisted bow", "");
        assertTrue(result.isDetected());
        assertEquals("twisted bow", result.getKeyword());
    }

    @Test
    public void testNormalizedText() {
        DetectionResult result = detector.detect("regular text", "selling gold");
        assertTrue(result.isDetected());
        assertTrue(result.getKeyword().contains("(normalized)"));
    }

    @Test
    public void testSubstringInNormalizedText() {
        DetectionResult result = detector.detect("regular text", "visit rsgold site");
        assertTrue(result.isDetected());
        assertTrue(result.getKeyword().contains("rsgold"));
    }

    @Test
    public void testNoMatchStandalone() {
        DetectionResult result = detector.detect("I need help with a quest", "");
        assertFalse(result.isDetected());
    }

    @Test
    public void testNoMatchSubstring() {
        DetectionResult result = detector.detect("regular conversation", "");
        assertFalse(result.isDetected());
    }

    @Test
    public void testPartialWordMatch() {
        DetectionResult result = detector.detect("I am reselling items", "");
        assertFalse(result.isDetected());
    }

    @Test
    public void testNullText() {
        KeywordSpamDetector emptyDetector = new KeywordSpamDetector(
            Arrays.asList("test"),
            Collections.emptyList()
        );
        DetectionResult result = emptyDetector.detect("", "");
        assertFalse(result.isDetected());
    }

    @Test
    public void testEmptyText() {
        DetectionResult result = detector.detect("", "");
        assertFalse(result.isDetected());
    }

    @Test
    public void testEmptyKeywordLists() {
        KeywordSpamDetector emptyDetector = new KeywordSpamDetector(
            Collections.emptyList(),
            Collections.emptyList()
        );
        DetectionResult result = emptyDetector.detect("selling gold", "");
        assertFalse(result.isDetected());
    }

    @Test
    public void testNegationBeforeKeyword() {
        DetectionResult result = detector.detect("I do not want gold", "");
        assertFalse(result.isDetected());
    }

    @Test
    public void testNegationTwoWordsBeforeKeyword() {
        DetectionResult result = detector.detect("I never wanted to sell items", "");
        assertFalse(result.isDetected());
    }

    @Test
    public void testNegationThreeWordsBeforeKeyword() {
        DetectionResult result = detector.detect("I will never ever buy gold", "");
        assertFalse(result.isDetected());
    }

    @Test
    public void testNegationFarAwayDoesNotApply() {
        DetectionResult result = detector.detect("I don't play much but I am selling items", "");
        assertTrue(result.isDetected());
    }

    @Test
    public void testKeywordAtWordBoundary() {
        DetectionResult result = detector.detect("gold", "");
        assertTrue(result.isDetected());
        assertEquals("gold", result.getKeyword());
    }

    @Test
    public void testSubstringCaseInsensitive() {
        DetectionResult result = detector.detect("visit RSGOLD site", "");
        assertTrue(result.isDetected());
        assertEquals("rsgold", result.getKeyword());
    }

    @Test
    public void testMultipleKeywordsReturnsFirst() {
        DetectionResult result = detector.detect("selling and buying gold", "");
        assertTrue(result.isDetected());
        assertEquals("selling", result.getKeyword());
    }

    @Test
    public void testNegationPatternAnywhere() {
        // "selling" appears first, then "not selling" appears later
        // hasNegationBefore will check before first "selling" and find no negation
        // Then the pattern check should find "not selling"
        DetectionResult result = detector.detect("selling items but I'm not selling gold", "");
        assertFalse(result.isDetected());
    }

    @Test
    public void testDontKeywordPattern() {
        DetectionResult result = detector.detect("gold is good but dont gold farm", "");
        assertFalse(result.isDetected());
    }

    @Test
    public void testNoKeywordPattern() {
        DetectionResult result = detector.detect("gold diggers but no gold here", "");
        assertFalse(result.isDetected());
    }
}
