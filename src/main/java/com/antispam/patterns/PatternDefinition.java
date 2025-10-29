package com.antispam.patterns;

import java.util.List;

/**
 * Represents a single pattern definition from YAML configuration.
 */
public class PatternDefinition {
    private String name;
    private String regex;
    private List<String> flags;
    private String detection;
    private String description;
    private List<String> requiresKeywords;
    private String requiresPattern;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRegex() {
        return regex;
    }

    public void setRegex(String regex) {
        this.regex = regex;
    }

    public List<String> getFlags() {
        return flags;
    }

    public void setFlags(List<String> flags) {
        this.flags = flags;
    }

    public String getDetection() {
        return detection;
    }

    public void setDetection(String detection) {
        this.detection = detection;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getRequiresKeywords() {
        return requiresKeywords;
    }

    public void setRequiresKeywords(List<String> requiresKeywords) {
        this.requiresKeywords = requiresKeywords;
    }

    public String getRequiresPattern() {
        return requiresPattern;
    }

    public void setRequiresPattern(String requiresPattern) {
        this.requiresPattern = requiresPattern;
    }
}
