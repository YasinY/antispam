package com.antispam.detectors.impl;

import com.antispam.detectors.DetectionResult;
import com.antispam.detectors.ISpamDetector;
import com.antispam.patterns.PatternConfig;
import com.antispam.patterns.PatternDefinition;
import com.antispam.patterns.PatternLoader;

import javax.annotation.Nonnull;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * Detects gambling and dice-related spam in OSRS chat.
 * Patterns are loaded from patterns/gambling.yaml for easy configuration.
 */
public class GamblingDetector implements ISpamDetector {

    private final PatternConfig config;
    private final Map<String, Pattern> patterns;

    public GamblingDetector() {
        this.config = PatternLoader.loadConfig("patterns/gambling.yaml");
        this.patterns = PatternLoader.compilePatterns(config);
    }

    @Override
    public DetectionResult detect(@Nonnull String originalText, String normalizedText) {
        String lowerText = originalText.toLowerCase();

        for (PatternDefinition def : config.getPatterns()) {
            Pattern pattern = patterns.get(def.getName());
            if (pattern == null) {
                continue;
            }

            if (pattern.matcher(originalText).find()) {
                // Check if this pattern requires keywords (e.g., H_CURRENCY requires "pot" or "roll")
                if (def.getRequiresKeywords() != null && !def.getRequiresKeywords().isEmpty()) {
                    boolean hasRequiredKeyword = false;
                    for (String keyword : def.getRequiresKeywords()) {
                        if (lowerText.contains(keyword.toLowerCase())) {
                            hasRequiredKeyword = true;
                            break;
                        }
                    }
                    if (!hasRequiredKeyword) {
                        continue;
                    }
                }

                return DetectionResult.detected(def.getDetection());
            }
        }

        return DetectionResult.notDetected();
    }
}
