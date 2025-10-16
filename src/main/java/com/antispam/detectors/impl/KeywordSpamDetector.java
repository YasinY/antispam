package com.antispam.detectors.impl;

import com.antispam.detectors.DetectionResult;
import com.antispam.detectors.ISpamDetector;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

public class KeywordSpamDetector implements ISpamDetector {

    private static final List<String> NEGATION_WORDS = Arrays.asList(
        "no", "not", "dont", "don't", "doesnt", "doesn't",
        "cant", "can't", "wont", "won't", "never", "neither",
        "none", "nobody", "nothing", "nowhere"
    );

    private final List<String> standaloneKeywords;
    private final List<String> substringKeywords;
    private final Pattern standalonePattern;

    public KeywordSpamDetector(List<String> standaloneKeywords, List<String> substringKeywords) {
        this.standaloneKeywords = standaloneKeywords;
        this.substringKeywords = substringKeywords;
        this.standalonePattern = buildStandalonePattern(standaloneKeywords);
    }

    @Override
    public DetectionResult detect(String originalText, String normalizedText) {
        String keyword = findStandaloneKeyword(originalText);
        if (keyword != null) {
            return DetectionResult.detected(keyword);
        }

        keyword = findStandaloneKeyword(normalizedText);
        if (keyword != null) {
            return DetectionResult.detected(keyword + " (normalized)");
        }

        keyword = findSubstringKeyword(originalText);
        if (keyword != null) {
            return DetectionResult.detected(keyword);
        }

        keyword = findSubstringKeyword(normalizedText);
        if (keyword != null) {
            return DetectionResult.detected(keyword + " (normalized)");
        }

        return DetectionResult.notDetected();
    }

    private String findStandaloneKeyword(String text) {
        if (!standalonePattern.matcher(text).find()) {
            return null;
        }

        for (String keyword : standaloneKeywords) {
            Pattern keywordPattern = Pattern.compile("\\b" + toWordBoundary(keyword) + "\\b", Pattern.CASE_INSENSITIVE);
            if (keywordPattern.matcher(text).find()) {
                if (!isNegatedContext(text, keyword)) {
                    return keyword;
                }
            }
        }

        return null;
    }

    private String findSubstringKeyword(String text) {
        for (String substring : substringKeywords) {
            if (text.toLowerCase().contains(substring.toLowerCase())) {
                return substring;
            }
        }
        return null;
    }

    private Pattern buildStandalonePattern(List<String> keywords) {
        if (keywords.isEmpty()) {
            return Pattern.compile("(?!)");
        }

        String regex = keywords.stream()
            .map(this::toWordBoundary)
            .collect(java.util.stream.Collectors.joining("|", "\\b(", ")\\b"));

        return Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
    }

    private String toWordBoundary(String keyword) {
        String[] parts = keyword.split("\\s+");
        return Arrays.stream(parts)
            .map(Pattern::quote)
            .collect(java.util.stream.Collectors.joining("\\s+"));
    }

    private boolean isNegatedContext(String text, String keyword) {
        if (hasNegationBefore(text, keyword)) {
            return true;
        }

        String lowerText = text.toLowerCase();
        String lowerKeyword = keyword.toLowerCase();

        if (lowerText.matches(".*\\b(no|not|dont|don't)\\s+" + Pattern.quote(lowerKeyword) + ".*")) {
            return true;
        }

        return false;
    }

    private boolean hasNegationBefore(String text, String keyword) {
        if (text == null || keyword == null) {
            return false;
        }

        String lowerText = text.toLowerCase();
        String lowerKeyword = keyword.toLowerCase();

        int keywordIndex = lowerText.indexOf(lowerKeyword);
        if (keywordIndex == -1) {
            return false;
        }

        String beforeKeyword = lowerText.substring(0, keywordIndex).trim();

        if (beforeKeyword.isEmpty()) {
            return false;
        }

        String[] words = beforeKeyword.split("\\s+");
        int checkRange = Math.min(3, words.length);

        for (int i = words.length - checkRange; i < words.length; i++) {
            String word = words[i].replaceAll("[^a-z']", "");
            if (NEGATION_WORDS.contains(word)) {
                return true;
            }
        }

        return false;
    }
}
