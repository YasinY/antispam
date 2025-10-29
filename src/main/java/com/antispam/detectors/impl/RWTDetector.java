package com.antispam.detectors.impl;

import com.antispam.detectors.DetectionResult;
import com.antispam.detectors.ISpamDetector;
import com.antispam.patterns.PatternConfig;
import com.antispam.patterns.PatternDefinition;
import com.antispam.patterns.PatternLoader;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * Detects Real World Trading (RWT) and high-value item trading spam.
 * Patterns are loaded from patterns/rwt.yaml for easy configuration.
 */
public class RWTDetector implements ISpamDetector {

    private final PatternConfig config;
    private final Map<String, Pattern> patterns;
    private final List<String> highValueItems;

    public RWTDetector() {
        this.config = PatternLoader.loadConfig("patterns/rwt.yaml");
        this.patterns = PatternLoader.compilePatterns(config);
        this.highValueItems = config.getHighValueItems();
    }

    @Override
    public DetectionResult detect(@Nonnull String originalText, String normalizedText) {
        String lowerText = originalText.toLowerCase();

        // Check for high value item trading first (custom logic)
        if (highValueItems != null && containsHighValueItemTrade(originalText, lowerText)) {
            return DetectionResult.detected("rwt: high-value-item");
        }

        for (PatternDefinition def : config.getPatterns()) {
            // Skip helper patterns that don't have a detection string
            if (def.getDetection() == null || def.getDetection().isEmpty()) {
                continue;
            }

            Pattern pattern = patterns.get(def.getName());
            if (pattern == null) {
                continue;
            }

            if (pattern.matcher(originalText).find()) {
                // Check if this pattern requires another pattern to also match (e.g., PAY_EXTRA requires BUYING_PATTERN)
                if (def.getRequiresPattern() != null && !def.getRequiresPattern().isEmpty()) {
                    Pattern requiredPattern = patterns.get(def.getRequiresPattern());
                    if (requiredPattern == null || !requiredPattern.matcher(originalText).find()) {
                        continue;
                    }
                }

                return DetectionResult.detected(def.getDetection());
            }
        }

        return DetectionResult.notDetected();
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
