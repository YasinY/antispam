package com.antispam.keywords;

import java.util.List;

public class KeywordCombo {
    private final List<String> requiredKeywords;
    private final String description;

    public KeywordCombo(List<String> requiredKeywords, String description) {
        this.requiredKeywords = requiredKeywords;
        this.description = description;
    }

    public boolean matches(String text) {
        if (text == null || text.isEmpty()) {
            return false;
        }

        String lowerText = text.toLowerCase();

        for (String keyword : requiredKeywords) {
            if (!lowerText.contains(keyword.toLowerCase())) {
                return false;
            }
        }

        return true;
    }

    public List<String> getRequiredKeywords() {
        return requiredKeywords;
    }

    public String getDescription() {
        return description;
    }

    public String getComboString() {
        return String.join(" + ", requiredKeywords);
    }
}
