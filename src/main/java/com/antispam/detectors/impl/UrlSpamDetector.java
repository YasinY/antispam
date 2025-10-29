package com.antispam.detectors.impl;

import com.antispam.detectors.AbstractYamlDetector;
import com.antispam.detectors.DetectionResult;
import com.antispam.patterns.PatternDefinition;

import java.util.regex.Pattern;

public class UrlSpamDetector extends AbstractYamlDetector {

    public UrlSpamDetector() {
        super("patterns/url.yaml");
    }

    @Override
    public DetectionResult detect(String originalText, String normalizedText) {
        if (originalText == null || originalText.isEmpty()) {
            return DetectionResult.notDetected();
        }

        String normalized = normalizeText(originalText);

        for (PatternDefinition def : config.getPatterns()) {
            if (def.getDetection() == null || def.getDetection().isEmpty()) {
                continue;
            }

            Pattern pattern = patterns.get(def.getName());
            if (pattern == null) {
                continue;
            }

            String textToCheck = "SUBSTITUTION_DOMAIN".equals(def.getName()) ? normalized : originalText;

            if (pattern.matcher(textToCheck).find()) {
                return DetectionResult.detected(def.getDetection());
            }
        }

        return DetectionResult.notDetected();
    }

    private String normalizeText(String text) {
        return text
            .replaceAll("0", "o")
            .replaceAll("1", "i")
            .replaceAll("3", "e")
            .replaceAll("4", "a")
            .replaceAll("5", "s")
            .replaceAll("7", "t")
            .replaceAll("@", "a")
            .replaceAll("\\$", "s");
    }
}
