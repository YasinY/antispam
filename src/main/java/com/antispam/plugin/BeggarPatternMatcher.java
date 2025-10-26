package com.antispam.plugin;

import com.antispam.detectors.DetectionResult;
import com.antispam.detectors.impl.*;
import com.antispam.keywords.FilterPresetManager;
import com.antispam.keywords.Keywords;
import com.antispam.ui.config.AntiBeggarConfig;
import com.antispam.ui.config.FilterPreset;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class BeggarPatternMatcher {

    private List<String> substringKeywords;
    private List<String> standaloneKeywords;
    private List<String> customKeywords;
    private List<Pattern> customRegexPatterns;
    private DetectorChain detectorChain;
    private final AntiBeggarConfig config;

    public BeggarPatternMatcher(AntiBeggarConfig config) {
        this.config = config;
        this.standaloneKeywords = new ArrayList<>();
        this.substringKeywords = new ArrayList<>();
        this.customKeywords = new ArrayList<>();
        this.customRegexPatterns = new ArrayList<>();
        rebuildDetectorChain();
    }

    public void updatePreset(FilterPreset preset) {
        Keywords keywords = FilterPresetManager.getKeywordsForPreset(preset);
        this.standaloneKeywords = keywords.getStandalone();
        this.substringKeywords = keywords.getSubstring();
        rebuildDetectorChain();
    }

    public void updateCustomKeywords(String customKeywordsInput) {
        this.customKeywords = parseCustomKeywords(customKeywordsInput);
        rebuildDetectorChain();
    }

    public void updateCustomRegex(String regexInput) {
        this.customRegexPatterns = parseCustomRegex(regexInput);
        rebuildDetectorChain();
    }

    private void rebuildDetectorChain() {
        List<String> allStandalone = mergeKeywords(standaloneKeywords, customKeywords);

        detectorChain = new DetectorChain();

        if (config.enableSpacedTextDetector()) {
            detectorChain.addDetector(new SpacedTextDetector());
        }
        if (config.enableAlertSpamDetector()) {
            detectorChain.addDetector(new AlertSpamDetector());
        }
        if (config.enableUrlSpamDetector()) {
            detectorChain.addDetector(new UrlSpamDetector());
        }
        if (config.enableRWTDetector()) {
            detectorChain.addDetector(new RWTDetector());
        }
        if (config.enableGamblingDetector()) {
            detectorChain.addDetector(new GamblingDetector());
        }
        if (config.enableShowTradingScamDetector()) {
            detectorChain.addDetector(new ShowTradingScamDetector());
        }
        if (config.enableCCSpamDetector()) {
            detectorChain.addDetector(new CCSpamDetector());
        }
        if (config.enableSocialMediaDetector()) {
            detectorChain.addDetector(new SocialMediaDetector());
        }
        if (config.enableSuspiciousPatternDetector()) {
            detectorChain.addDetector(new SuspiciousPatternDetector());
        }
        if (config.enableKeywordComboDetector()) {
            detectorChain.addDetector(new KeywordComboDetector());
        }

        if (config.enableKeywordDetector() && (!allStandalone.isEmpty() || !substringKeywords.isEmpty())) {
            detectorChain.addDetector(new KeywordSpamDetector(allStandalone, substringKeywords));
        }

        if (!customRegexPatterns.isEmpty()) {
            detectorChain.addDetector(new CustomRegexDetector(customRegexPatterns));
        }
    }

    public boolean matches(String text) {
        if (text == null || text.isEmpty()) {
            return false;
        }

        String normalizedText = normalize(text);
        DetectionResult result = detectorChain.detect(text, normalizedText);
        return result.isDetected();
    }

    public String getMatchedKeyword(String text) {
        if (text == null || text.isEmpty()) {
            return null;
        }

        String normalizedText = normalize(text);
        DetectionResult result = detectorChain.detect(text, normalizedText);
        return result.isDetected() ? result.getKeyword() : null;
    }

    private List<String> parseCustomKeywords(String input) {
        if (input == null || input.trim().isEmpty()) {
            return new ArrayList<>();
        }

        return Arrays.stream(input.split(",\\s*"))
            .map(String::trim)
            .filter(s -> !s.isEmpty())
            .collect(Collectors.toList());
    }

    private List<Pattern> parseCustomRegex(String input) {
        if (input == null || input.trim().isEmpty()) {
            return new ArrayList<>();
        }

        List<Pattern> patterns = new ArrayList<>();
        String[] lines = input.split("\\r?\\n");

        for (String line : lines) {
            String trimmed = line.trim();
            if (trimmed.isEmpty()) {
                continue;
            }

            try {
                patterns.add(Pattern.compile(trimmed, Pattern.CASE_INSENSITIVE));
            } catch (PatternSyntaxException e) {
            }
        }

        return patterns;
    }

    private List<String> mergeKeywords(List<String> base, List<String> custom) {
        return Stream.concat(base.stream(), custom.stream())
            .distinct()
            .collect(Collectors.toList());
    }

    private String normalize(String text) {
        if (text == null || text.isEmpty()) {
            return text;
        }

        String normalized = text.toLowerCase();

        normalized = removeAccents(normalized);

        normalized = normalizeUnicodeLetters(normalized);

        normalized = normalized.replaceAll("0", "o");
        normalized = normalized.replaceAll("1", "i");
        normalized = normalized.replaceAll("3", "e");
        normalized = normalized.replaceAll("4", "a");
        normalized = normalized.replaceAll("5", "s");
        normalized = normalized.replaceAll("7", "t");
        normalized = normalized.replaceAll("@", "a");
        normalized = normalized.replaceAll("\\$", "s");
        normalized = normalized.replaceAll("\\+", "t");

        normalized = removeRepeatedChars(normalized);

        normalized = normalized.replaceAll("\\s+", " ");

        return normalized;
    }

    private String removeAccents(String text) {
        String nfd = Normalizer.normalize(text, Normalizer.Form.NFD);
        return nfd.replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
    }

    private String normalizeUnicodeLetters(String text) {
        return text
            .replaceAll("[àáâãäåāăąǎǻ]", "a")
            .replaceAll("[èéêëēĕėęě]", "e")
            .replaceAll("[ìíîïĩīĭįı]", "i")
            .replaceAll("[òóôõöøōŏőǒ]", "o")
            .replaceAll("[ùúûüũūŭůűų]", "u")
            .replaceAll("[ýÿŷ]", "y")
            .replaceAll("[çćĉċč]", "c")
            .replaceAll("[ñńņň]", "n")
            .replaceAll("[śŝşš]", "s")
            .replaceAll("[źżž]", "z")
            .replaceAll("[ðđ]", "d")
            .replaceAll("[ģğġ]", "g")
            .replaceAll("[ĥħ]", "h")
            .replaceAll("[ĵ]", "j")
            .replaceAll("[ķ]", "k")
            .replaceAll("[ĺļľŀł]", "l")
            .replaceAll("[ŕŗř]", "r")
            .replaceAll("[ţť]", "t")
            .replaceAll("[ŵ]", "w")
            .replaceAll("[а]", "a")
            .replaceAll("[е]", "e")
            .replaceAll("[о]", "o")
            .replaceAll("[р]", "p")
            .replaceAll("[с]", "c")
            .replaceAll("[у]", "y")
            .replaceAll("[х]", "x")
            .replaceAll("[ν]", "v")
            .replaceAll("[ο]", "o")
            .replaceAll("[ι]", "i")
            .replaceAll("[α]", "a");
    }

    private String removeRepeatedChars(String text) {
        StringBuilder result = new StringBuilder();
        int count = 0;

        for (int i = 0; i < text.length(); i++) {
            if (i > 0 && text.charAt(i) == text.charAt(i - 1)) {
                count++;
                if (count < 2) {
                    result.append(text.charAt(i));
                }
            } else {
                count = 0;
                result.append(text.charAt(i));
            }
        }

        return result.toString();
    }
}
