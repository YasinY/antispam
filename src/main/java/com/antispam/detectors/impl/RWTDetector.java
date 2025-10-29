package com.antispam.detectors.impl;

import com.antispam.detectors.AbstractYamlDetector;
import com.antispam.detectors.DetectionResult;

import java.util.List;
import java.util.regex.Pattern;

public class RWTDetector extends AbstractYamlDetector {

    private final List<String> highValueItems;

    public RWTDetector() {
        super("patterns/rwt.yaml");
        this.highValueItems = config.getHighValueItems();
    }

    @Override
    public DetectionResult detect(String originalText, String normalizedText) {
        if (originalText == null || originalText.isEmpty()) {
            return DetectionResult.notDetected();
        }

        String lowerText = originalText.toLowerCase();

        if (highValueItems != null && containsHighValueItemTrade(originalText, lowerText)) {
            return DetectionResult.detected("rwt: high-value-item");
        }

        return super.detect(originalText, normalizedText);
    }

    private boolean containsHighValueItemTrade(String text, String lowerText) {
        Pattern buyingPattern = patterns.get("BUYING_PATTERN");
        if (buyingPattern == null || !buyingPattern.matcher(text).find()) {
            return false;
        }

        for (String item : highValueItems) {
            if (lowerText.contains(item.toLowerCase())) {
                return true;
            }
        }

        return false;
    }
}
