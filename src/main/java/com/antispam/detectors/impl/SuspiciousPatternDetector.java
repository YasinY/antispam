package com.antispam.detectors.impl;

import com.antispam.detectors.DetectionResult;
import com.antispam.detectors.ISpamDetector;

public class SuspiciousPatternDetector implements ISpamDetector {

    @Override
    public DetectionResult detect(String originalText, String normalizedText) {
        if (containsSuspiciousPattern(originalText)) {
            return DetectionResult.detected("suspicious-pattern");
        }
        return DetectionResult.notDetected();
    }

    private boolean containsSuspiciousPattern(String text) {
        if (text == null || text.isEmpty()) {
            return false;
        }

        if (text.matches(".*[a-z]\\s+[a-z]\\s+[a-z]\\s+[a-z].*")) {
            return true;
        }

        if (text.matches(".*[^a-zA-Z0-9\\s]{6,}.*")) {
            return true;
        }

        return false;
    }
}
