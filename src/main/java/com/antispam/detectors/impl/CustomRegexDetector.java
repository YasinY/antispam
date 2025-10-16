package com.antispam.detectors.impl;

import com.antispam.detectors.DetectionResult;
import com.antispam.detectors.ISpamDetector;

import java.util.List;
import java.util.regex.Pattern;

public class CustomRegexDetector implements ISpamDetector {

    private final List<Pattern> customRegexPatterns;

    public CustomRegexDetector(List<Pattern> customRegexPatterns) {
        this.customRegexPatterns = customRegexPatterns;
    }

    @Override
    public DetectionResult detect(String originalText, String normalizedText) {
        for (Pattern regexPattern : customRegexPatterns) {
            if (regexPattern.matcher(originalText).find()) {
                return DetectionResult.detected("custom-regex");
            }
            if (regexPattern.matcher(normalizedText).find()) {
                return DetectionResult.detected("custom-regex (normalized)");
            }
        }
        return DetectionResult.notDetected();
    }
}
