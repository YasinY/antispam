package com.antispam.detectors;

import com.antispam.detectors.impl.SpacedTextDetector;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class SpacedTextDetectorTest {

    private SpacedTextDetector detector;

    @Before
    public void setUp() {
        detector = new SpacedTextDetector();
    }

    @Test
    public void testEmDashSpacedText() {
        DetectionResult result = detector.detect("s—e—l—l—i—n—g gold", "");
        assertTrue(result.isDetected());
        assertEquals("spaced-text: em-dash", result.getKeyword());
    }

    @Test
    public void testHyphenSpacedText() {
        DetectionResult result = detector.detect("b-u-y-i-n-g items", "");
        assertTrue(result.isDetected());
        assertEquals("spaced-text: hyphen", result.getKeyword());
    }

    @Test
    public void testHyphenSpacedTextWithSpaces() {
        DetectionResult result = detector.detect("s e l l i n g gold", "");
        assertTrue(result.isDetected());
        assertEquals("spaced-text: hyphen", result.getKeyword());
    }

    @Test
    public void testUnderscoreSpacedText() {
        DetectionResult result = detector.detect("g_o_l_d for sale", "");
        assertTrue(result.isDetected());
        assertEquals("spaced-text: underscore", result.getKeyword());
    }

    @Test
    public void testDotSpacedText() {
        DetectionResult result = detector.detect("b.u.y.i.n.g now", "");
        assertTrue(result.isDetected());
        assertEquals("spaced-text: dot", result.getKeyword());
    }

    @Test
    public void testCommaSpacedText() {
        DetectionResult result = detector.detect("s,e,l,l gp", "");
        assertTrue(result.isDetected());
        assertEquals("spaced-text: comma", result.getKeyword());
    }

    @Test
    public void testMixedCaseSpacedText() {
        DetectionResult result = detector.detect("S-E-L-L-I-N-G", "");
        assertTrue(result.isDetected());
        assertEquals("spaced-text: hyphen", result.getKeyword());
    }

    @Test
    public void testNormalText() {
        DetectionResult result = detector.detect("buying twisted bow 1200m", "");
        assertFalse(result.isDetected());
    }

    @Test
    public void testNormalAbbreviation() {
        DetectionResult result = detector.detect("gf for cooking", "");
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
    public void testTooShortSpacedText() {
        DetectionResult result = detector.detect("a-b-c", "");
        assertFalse(result.isDetected());
    }

    @Test
    public void testSpacedTextInMiddleOfSentence() {
        DetectionResult result = detector.detect("check out s.e.l.l.i.n.g website", "");
        assertTrue(result.isDetected());
        assertEquals("spaced-text: dot", result.getKeyword());
    }

    @Test
    public void testMultipleSpacedPatterns() {
        DetectionResult result = detector.detect("g—o—l—d for sale", "");
        assertTrue(result.isDetected());
        assertEquals("spaced-text: em-dash", result.getKeyword());
    }

    @Test
    public void testSpacedTextWithNumbers() {
        DetectionResult result = detector.detect("g.o.l.d 100k", "");
        assertTrue(result.isDetected());
        assertEquals("spaced-text: dot", result.getKeyword());
    }
}
