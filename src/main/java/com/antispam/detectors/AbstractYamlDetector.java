package com.antispam.detectors;

import com.antispam.patterns.PatternConfig;
import com.antispam.patterns.PatternDefinition;
import com.antispam.patterns.PatternLoader;

import java.util.Map;
import java.util.regex.Pattern;

public abstract class AbstractYamlDetector implements ISpamDetector {

    protected final PatternConfig config;
    protected final Map<String, Pattern> patterns;

    protected AbstractYamlDetector(String yamlResourcePath) {
        this.config = PatternLoader.loadConfig(yamlResourcePath);
        this.patterns = PatternLoader.compilePatterns(config);
    }

    @Override
    public DetectionResult detect(String originalText, String normalizedText) {
        if (originalText == null || originalText.isEmpty()) {
            return DetectionResult.notDetected();
        }

        String lowerText = originalText.toLowerCase();

        for (PatternDefinition def : config.getPatterns()) {
            if (def.getDetection() == null || def.getDetection().isEmpty()) {
                continue;
            }

            Pattern pattern = patterns.get(def.getName());
            if (pattern == null) {
                continue;
            }

            if (pattern.matcher(originalText).find()) {
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
}
