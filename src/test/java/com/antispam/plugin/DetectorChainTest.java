package com.antispam.plugin;

import com.antispam.detectors.DetectionResult;
import com.antispam.detectors.ISpamDetector;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class DetectorChainTest {

    private DetectorChain chain;

    @Before
    public void setUp() {
        chain = new DetectorChain();
    }

    @Test
    public void testEmptyChain() {
        DetectionResult result = chain.detect("test message", "test message");
        assertFalse(result.isDetected());
    }

    @Test
    public void testSingleDetectorMatches() {
        chain.addDetector(new ISpamDetector() {
            @Override
            public DetectionResult detect(String originalText, String normalizedText) {
                if (originalText.contains("spam")) {
                    return DetectionResult.detected("test-spam");
                }
                return DetectionResult.notDetected();
            }
        });

        DetectionResult result = chain.detect("this is spam", "this is spam");
        assertTrue(result.isDetected());
        assertEquals("test-spam", result.getKeyword());
    }

    @Test
    public void testSingleDetectorNoMatch() {
        chain.addDetector(new ISpamDetector() {
            @Override
            public DetectionResult detect(String originalText, String normalizedText) {
                if (originalText.contains("spam")) {
                    return DetectionResult.detected("test-spam");
                }
                return DetectionResult.notDetected();
            }
        });

        DetectionResult result = chain.detect("normal message", "normal message");
        assertFalse(result.isDetected());
    }

    @Test
    public void testMultipleDetectorsFirstMatches() {
        chain.addDetector(new ISpamDetector() {
            @Override
            public DetectionResult detect(String originalText, String normalizedText) {
                if (originalText.contains("spam")) {
                    return DetectionResult.detected("detector1");
                }
                return DetectionResult.notDetected();
            }
        });

        chain.addDetector(new ISpamDetector() {
            @Override
            public DetectionResult detect(String originalText, String normalizedText) {
                if (originalText.contains("scam")) {
                    return DetectionResult.detected("detector2");
                }
                return DetectionResult.notDetected();
            }
        });

        DetectionResult result = chain.detect("this is spam", "this is spam");
        assertTrue(result.isDetected());
        assertEquals("detector1", result.getKeyword());
    }

    @Test
    public void testMultipleDetectorsSecondMatches() {
        chain.addDetector(new ISpamDetector() {
            @Override
            public DetectionResult detect(String originalText, String normalizedText) {
                if (originalText.contains("spam")) {
                    return DetectionResult.detected("detector1");
                }
                return DetectionResult.notDetected();
            }
        });

        chain.addDetector(new ISpamDetector() {
            @Override
            public DetectionResult detect(String originalText, String normalizedText) {
                if (originalText.contains("scam")) {
                    return DetectionResult.detected("detector2");
                }
                return DetectionResult.notDetected();
            }
        });

        DetectionResult result = chain.detect("this is scam", "this is scam");
        assertTrue(result.isDetected());
        assertEquals("detector2", result.getKeyword());
    }

    @Test
    public void testMultipleDetectorsNoneMatch() {
        chain.addDetector(new ISpamDetector() {
            @Override
            public DetectionResult detect(String originalText, String normalizedText) {
                if (originalText.contains("spam")) {
                    return DetectionResult.detected("detector1");
                }
                return DetectionResult.notDetected();
            }
        });

        chain.addDetector(new ISpamDetector() {
            @Override
            public DetectionResult detect(String originalText, String normalizedText) {
                if (originalText.contains("scam")) {
                    return DetectionResult.detected("detector2");
                }
                return DetectionResult.notDetected();
            }
        });

        DetectionResult result = chain.detect("normal message", "normal message");
        assertFalse(result.isDetected());
    }

    @Test
    public void testDetectorOrderMatters() {
        chain.addDetector(new ISpamDetector() {
            @Override
            public DetectionResult detect(String originalText, String normalizedText) {
                return DetectionResult.detected("first");
            }
        });

        chain.addDetector(new ISpamDetector() {
            @Override
            public DetectionResult detect(String originalText, String normalizedText) {
                return DetectionResult.detected("second");
            }
        });

        DetectionResult result = chain.detect("test", "test");
        assertEquals("first", result.getKeyword());
    }

    @Test
    public void testMultipleDetectorsAllMatch() {
        chain.addDetector(new ISpamDetector() {
            @Override
            public DetectionResult detect(String originalText, String normalizedText) {
                if (originalText.contains("spam")) {
                    return DetectionResult.detected("detector1");
                }
                return DetectionResult.notDetected();
            }
        });

        chain.addDetector(new ISpamDetector() {
            @Override
            public DetectionResult detect(String originalText, String normalizedText) {
                if (originalText.contains("spam")) {
                    return DetectionResult.detected("detector2");
                }
                return DetectionResult.notDetected();
            }
        });

        DetectionResult result = chain.detect("this is spam", "this is spam");
        // Should return first match
        assertEquals("detector1", result.getKeyword());
    }
}
