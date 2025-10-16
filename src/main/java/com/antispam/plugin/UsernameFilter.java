package com.antispam.plugin;

import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;
import java.util.stream.Collectors;

public class UsernameFilter {

    private List<String> blacklistedNames;
    private List<Pattern> regexPatterns;

    public UsernameFilter() {
        this.blacklistedNames = new ArrayList<>();
        this.regexPatterns = new ArrayList<>();
    }

    public void updateBlacklist(String blacklistInput) {
        if (blacklistInput == null || blacklistInput.trim().isEmpty()) {
            blacklistedNames.clear();
            regexPatterns.clear();
            return;
        }

        List<String> names = new ArrayList<>();
        List<Pattern> patterns = new ArrayList<>();

        String[] entries = blacklistInput.split("[,\n]");

        for (String entry : entries) {
            String trimmed = entry.trim();
            if (trimmed.isEmpty()) {
                continue;
            }

            if (trimmed.startsWith("regex:")) {
                String regex = trimmed.substring(6).trim();
                try {
                    patterns.add(Pattern.compile(regex, Pattern.CASE_INSENSITIVE));
                } catch (PatternSyntaxException e) {
                }
            } else {
                names.add(normalize(trimmed));
            }
        }

        this.blacklistedNames = names;
        this.regexPatterns = patterns;
    }

    public boolean isBlacklisted(String username) {
        if (username == null || username.isEmpty()) {
            return false;
        }

        String normalizedUsername = normalize(username);

        for (String blacklisted : blacklistedNames) {
            if (normalizedUsername.equalsIgnoreCase(blacklisted)) {
                return true;
            }
        }

        for (Pattern pattern : regexPatterns) {
            if (pattern.matcher(username).find() || pattern.matcher(normalizedUsername).find()) {
                return true;
            }
        }

        return false;
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
