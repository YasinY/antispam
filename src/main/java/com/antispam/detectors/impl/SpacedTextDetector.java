package com.antispam.detectors.impl;

import com.antispam.detectors.DetectionResult;
import com.antispam.detectors.ISpamDetector;

import java.util.regex.Pattern;

public class SpacedTextDetector implements ISpamDetector {

    private static final Pattern EM_DASH_SPACED = Pattern.compile(
        "[a-z]—[a-z]—[a-z]",
        Pattern.CASE_INSENSITIVE
    );

    private static final Pattern HYPHEN_SPACED = Pattern.compile(
        "[a-z][ -][a-z][ -][a-z][ -][a-z]",
        Pattern.CASE_INSENSITIVE
    );

    private static final Pattern UNDERSCORE_SPACED = Pattern.compile(
        "[a-z]_[a-z]_[a-z]_[a-z]",
        Pattern.CASE_INSENSITIVE
    );

    private static final Pattern DOT_SPACED = Pattern.compile(
        "[a-z]\\.[a-z]\\.[a-z]\\.[a-z]",
        Pattern.CASE_INSENSITIVE
    );

    private static final Pattern COMMA_SPACED = Pattern.compile(
        "[a-z],[a-z],[a-z],[a-z]",
        Pattern.CASE_INSENSITIVE
    );

    @Override
    public DetectionResult detect(String originalText, String normalizedText) {
        if (originalText == null || originalText.isEmpty()) {
            return DetectionResult.notDetected();
        }

        if (EM_DASH_SPACED.matcher(originalText).find()) {
            return DetectionResult.detected("spaced-text: em-dash");
        }

        if (HYPHEN_SPACED.matcher(originalText).find()) {
            return DetectionResult.detected("spaced-text: hyphen");
        }

        if (UNDERSCORE_SPACED.matcher(originalText).find()) {
            return DetectionResult.detected("spaced-text: underscore");
        }

        if (DOT_SPACED.matcher(originalText).find()) {
            return DetectionResult.detected("spaced-text: dot");
        }

        if (COMMA_SPACED.matcher(originalText).find()) {
            return DetectionResult.detected("spaced-text: comma");
        }

        return DetectionResult.notDetected();
    }
}
