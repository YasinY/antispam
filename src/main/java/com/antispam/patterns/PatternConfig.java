package com.antispam.patterns;

import java.util.List;

/**
 * Represents the complete pattern configuration for a detector.
 */
public class PatternConfig {
    private String detector;
    private String description;
    private List<String> highValueItems;
    private List<PatternDefinition> patterns;

    public String getDetector() {
        return detector;
    }

    public void setDetector(String detector) {
        this.detector = detector;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getHighValueItems() {
        return highValueItems;
    }

    public void setHighValueItems(List<String> highValueItems) {
        this.highValueItems = highValueItems;
    }

    public List<PatternDefinition> getPatterns() {
        return patterns;
    }

    public void setPatterns(List<PatternDefinition> patterns) {
        this.patterns = patterns;
    }
}
