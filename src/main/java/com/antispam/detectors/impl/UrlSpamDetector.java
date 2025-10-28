package com.antispam.detectors.impl;

import com.antispam.detectors.DetectionResult;
import com.antispam.detectors.ISpamDetector;

import java.util.regex.Pattern;

/**
 * Detects URL spam in chat messages using various obfuscation patterns.
 *
 * Pattern ordering is intentional for performance (check common patterns first).
 */
public class UrlSpamDetector implements ISpamDetector {

    private static final Pattern STANDARD_URL = Pattern.compile(
        "(?i)(https?://[^\\s]+|www\\.[^\\s]+)",
        Pattern.CASE_INSENSITIVE
    );

    private static final Pattern SPACED_DOMAIN = Pattern.compile(
        "(?i)[a-z0-9-]+\\s*\\.\\s*(com|net|org|gg|io|co|me|tv)",
        Pattern.CASE_INSENSITIVE
    );

    private static final Pattern DOTTED_DOMAIN = Pattern.compile(
        "(?i)[a-z0-9-]+\\s*\\.\\s*[cC]\\s*\\.\\s*[oO0]\\s*\\.\\s*[mM]|" +
        "[a-z0-9-]+\\s*\\.\\s*[nN]\\s*\\.\\s*[eE]\\s*\\.\\s*[tT]|" +
        "[a-z0-9-]+\\s*\\.\\s*[gG]\\s*\\.\\s*[gG]|" +
        "[a-z0-9-]+\\s*\\.\\s*[iI1]\\s*\\.\\s*[oO0]",
        Pattern.CASE_INSENSITIVE
    );

    private static final Pattern WORD_DOT = Pattern.compile(
        "(?i)(d[o0]t|dot)\\s*(com|net|org|gg|co)",
        Pattern.CASE_INSENSITIVE
    );

    private static final Pattern PARENTHESIS_DOT = Pattern.compile(
        "(?i)\\(\\s*(dot|d[o0]t)\\s*\\)\\s*(com|net|org|gg)",
        Pattern.CASE_INSENSITIVE
    );

    private static final Pattern SUBSTITUTION_DOMAIN = Pattern.compile(
        "(?i)[a-z0-9-]+\\s*[.,]\\s*[cC][o0O][mM]|" +
        "[a-z0-9-]+\\s*[.,]\\s*[nN][eE][tT]|" +
        "[a-z0-9-]+\\s*[.,]\\s*[gG][gG]|" +
        "[a-z0-9-]+\\s*[.,]\\s*[iI1][o0O]",
        Pattern.CASE_INSENSITIVE
    );

    private static final Pattern SLASH_SEPARATOR = Pattern.compile(
        "(?i)[a-z0-9-]+\\s*/\\s*(com|net|org|gg|io)",
        Pattern.CASE_INSENSITIVE
    );

    private static final Pattern SPECIAL_CHARS = Pattern.compile(
        "(?i)[a-z0-9-]+\\s*[\\[\\]{}()<>_-]+\\s*(com|net|org|gg)",
        Pattern.CASE_INSENSITIVE
    );

    @Override
    public DetectionResult detect(String originalText, String normalizedText) {
        String pattern = getDetectedUrlPattern(originalText);
        if (pattern != null) {
            return DetectionResult.detected(pattern);
        }
        return DetectionResult.notDetected();
    }

    private String getDetectedUrlPattern(String text) {
        if (text == null || text.isEmpty()) {
            return null;
        }

        String normalized = normalizeText(text);

        if (STANDARD_URL.matcher(text).find()) return "url";
        if (SPACED_DOMAIN.matcher(text).find()) return "spaced-url";
        if (DOTTED_DOMAIN.matcher(text).find()) return "dotted-url";
        if (WORD_DOT.matcher(text).find()) return "word-dot";
        if (PARENTHESIS_DOT.matcher(text).find()) return "parenthesis-dot";
        if (SUBSTITUTION_DOMAIN.matcher(normalized).find()) return "substitution-url";
        if (SLASH_SEPARATOR.matcher(text).find()) return "slash-url";
        if (SPECIAL_CHARS.matcher(text).find()) return "special-chars-url";

        return null;
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
