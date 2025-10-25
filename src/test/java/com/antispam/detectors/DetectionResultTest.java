package com.antispam.detectors;

import org.junit.Test;

import static org.junit.Assert.*;

public class DetectionResultTest {

    @Test
    public void testNotDetected() {
        DetectionResult result = DetectionResult.notDetected();
        assertFalse(result.isDetected());
        assertNull(result.getKeyword());
    }

    @Test
    public void testDetectedWithKeyword() {
        DetectionResult result = DetectionResult.detected("spam");
        assertTrue(result.isDetected());
        assertEquals("spam", result.getKeyword());
    }

    @Test
    public void testDetectedWithNullKeyword() {
        DetectionResult result = DetectionResult.detected(null);
        assertTrue(result.isDetected());
        assertNull(result.getKeyword());
    }

    @Test
    public void testDetectedWithEmptyKeyword() {
        DetectionResult result = DetectionResult.detected("");
        assertTrue(result.isDetected());
        assertEquals("", result.getKeyword());
    }

    @Test
    public void testDetectedWithComplexKeyword() {
        String keyword = "rwt: high-value-item";
        DetectionResult result = DetectionResult.detected(keyword);
        assertTrue(result.isDetected());
        assertEquals(keyword, result.getKeyword());
    }

    @Test
    public void testMultipleNotDetectedCalls() {
        DetectionResult result1 = DetectionResult.notDetected();
        DetectionResult result2 = DetectionResult.notDetected();
        assertFalse(result1.isDetected());
        assertFalse(result2.isDetected());
    }

    @Test
    public void testMultipleDetectedCalls() {
        DetectionResult result1 = DetectionResult.detected("keyword1");
        DetectionResult result2 = DetectionResult.detected("keyword2");
        assertTrue(result1.isDetected());
        assertTrue(result2.isDetected());
        assertEquals("keyword1", result1.getKeyword());
        assertEquals("keyword2", result2.getKeyword());
    }
}
