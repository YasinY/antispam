package com.antispam.detectors.impl;

import com.antispam.detectors.DetectionResult;
import com.antispam.detectors.ISpamDetector;

import java.util.regex.Pattern;

public class SocialMediaDetector implements ISpamDetector {

    private static final Pattern YOUTUBE_CODE = Pattern.compile(
        "\\b[a-z]{3,4}\\d{3,4}\\b.{0,20}\\b(y[o0]u[ -]?t[u]?be?|ytbe?|video)\\b",
        Pattern.CASE_INSENSITIVE
    );

    private static final Pattern SEARCH_SOCIAL = Pattern.compile(
        "\\b(search|watch|check out|visit|look)\\b.{0,50}\\b(on )?(youtube|y[o0]u[ -]?t[u]?be?|facebook|twitter|twitch|instagram)\\b",
        Pattern.CASE_INSENSITIVE
    );

    private static final Pattern DISCORD_VARIATIONS = Pattern.compile(
        "\\b(disc[oöô]rd|díscord|discörd)\\b.{0,30}\\b(gg|dot|giveaway|join)\\b",
        Pattern.CASE_INSENSITIVE
    );

    private static final Pattern GIVEAWAY_SOCIAL = Pattern.compile(
        "\\b(giveaway|win|drop[ -]?party)\\b.{0,50}\\b[a-z]{3,4}\\d{3,4}\\b",
        Pattern.CASE_INSENSITIVE
    );

    private static final Pattern VIDEO_INSTRUCTIONS = Pattern.compile(
        "\\b(watch|follow|check)\\b.{0,30}\\b(video|instructions|steps)\\b",
        Pattern.CASE_INSENSITIVE
    );

    private static final Pattern DONT_SEARCH = Pattern.compile(
        "\\bdo?n[o']?t search\\b.{0,30}\\b(for|youtube|keyword|giveaway)\\b",
        Pattern.CASE_INSENSITIVE
    );

    @Override
    public DetectionResult detect(String originalText, String normalizedText) {
        if (originalText == null || originalText.isEmpty()) {
            return DetectionResult.notDetected();
        }

        if (YOUTUBE_CODE.matcher(originalText).find()) {
            return DetectionResult.detected("social: youtube-code");
        }

        if (SEARCH_SOCIAL.matcher(originalText).find()) {
            return DetectionResult.detected("social: search");
        }

        if (GIVEAWAY_SOCIAL.matcher(originalText).find()) {
            return DetectionResult.detected("social: giveaway");
        }

        if (VIDEO_INSTRUCTIONS.matcher(originalText).find()) {
            return DetectionResult.detected("social: instructions");
        }

        if (DONT_SEARCH.matcher(originalText).find()) {
            return DetectionResult.detected("social: dont-search");
        }

        if (DISCORD_VARIATIONS.matcher(originalText).find()) {
            return DetectionResult.detected("social: discord-variation");
        }

        return DetectionResult.notDetected();
    }
}
