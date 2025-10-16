package com.antispam.keywords;

import com.antispam.ui.config.FilterPreset;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FilterPresetManager {

    public static Keywords getKeywordsForPreset(FilterPreset preset) {
        switch (preset) {
            case LENIENT:
                return buildLenientKeywords();
            case MODERATE:
                return buildModerateKeywords();
            case STRICT:
                return buildStrictKeywords();
            case CUSTOM:
                return new Keywords(new ArrayList<>(), new ArrayList<>());
            default:
                return buildModerateKeywords();
        }
    }

    private static Keywords buildLenientKeywords() {
        return KeywordCategory.getLenientKeywords();
    }

    private static Keywords buildModerateKeywords() {
        Keywords lenient = KeywordCategory.getLenientKeywords();
        Keywords moderate = KeywordCategory.getModerateExtensions();

        List<String> standalone = Stream.concat(
            lenient.getStandalone().stream(),
            moderate.getStandalone().stream()
        ).distinct().collect(Collectors.toList());

        List<String> substring = Stream.concat(
            lenient.getSubstring().stream(),
            moderate.getSubstring().stream()
        ).distinct().collect(Collectors.toList());

        return new Keywords(standalone, substring);
    }

    private static Keywords buildStrictKeywords() {
        Keywords lenient = KeywordCategory.getLenientKeywords();
        Keywords moderate = KeywordCategory.getModerateExtensions();
        Keywords strict = KeywordCategory.getStrictExtensions();

        List<String> standalone = Stream.of(
            lenient.getStandalone().stream(),
            moderate.getStandalone().stream(),
            strict.getStandalone().stream()
        ).flatMap(s -> s).distinct().collect(Collectors.toList());

        List<String> substring = Stream.of(
            lenient.getSubstring().stream(),
            moderate.getSubstring().stream(),
            strict.getSubstring().stream()
        ).flatMap(s -> s).distinct().collect(Collectors.toList());

        return new Keywords(standalone, substring);
    }
}
