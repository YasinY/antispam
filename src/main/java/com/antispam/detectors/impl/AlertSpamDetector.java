package com.antispam.detectors.impl;

import com.antispam.detectors.DetectionResult;
import com.antispam.detectors.ISpamDetector;

import java.util.regex.Pattern;

public class AlertSpamDetector implements ISpamDetector {

    private static final Pattern ALERT_TAG = Pattern.compile(
        "\\[(alert|warning|beware|caution)\\]",
        Pattern.CASE_INSENSITIVE
    );

    private static final Pattern SCAMMER_ALERT = Pattern.compile(
        "\\b(is a|are|beware of)\\b.{0,30}\\b(scamm?er|lur(er|ing)|fake)\\b",
        Pattern.CASE_INSENSITIVE
    );

    private static final Pattern IGNORE_ALERT = Pattern.compile(
        "\\b(add|put).{0,20}(to )?ignore\\b",
        Pattern.CASE_INSENSITIVE
    );

    private static final Pattern FAKE_ANTISCAM = Pattern.compile(
        "\\bfake\\b.{0,30}\\b(anti[ -]?scam|anti[ -]?lur)\\b",
        Pattern.CASE_INSENSITIVE
    );

    private static final Pattern SCEPTRE_LURE = Pattern.compile(
        "\\bsceptre\\b.{0,50}\\blur",
        Pattern.CASE_INSENSITIVE
    );

    private static final Pattern LOCATION_WARNING = Pattern.compile(
        "\\b(barb\\.?\\s*outpost|shanty\\.?\\s*pass|rogues?\\.?\\s*den)\\b",
        Pattern.CASE_INSENSITIVE
    );

    private static final Pattern UNSAFE_BANKS = Pattern.compile(
        "\\bunsafe\\b.{0,30}\\bbanks?\\b",
        Pattern.CASE_INSENSITIVE
    );

    private static final Pattern NOT_SAFE_PVP = Pattern.compile(
        "\\bnot safe\\b.{0,30}\\b(on )?pvp\\b",
        Pattern.CASE_INSENSITIVE
    );

    private static final Pattern LURERS_TO_IGNORE = Pattern.compile(
        "\\blur(ers?|ing)\\b.{0,30}\\bto ignore\\b",
        Pattern.CASE_INSENSITIVE
    );

    private static final Pattern DO_NOT_ATTEMPT = Pattern.compile(
        "\\bdo not attempt\\b.{0,50}\\b(anti[ -]?scam|lur)\\b",
        Pattern.CASE_INSENSITIVE
    );

    @Override
    public DetectionResult detect(String originalText, String normalizedText) {
        if (originalText == null || originalText.isEmpty()) {
            return DetectionResult.notDetected();
        }

        if (ALERT_TAG.matcher(originalText).find()) {
            return DetectionResult.detected("alert-spam: tag");
        }

        if (SCAMMER_ALERT.matcher(originalText).find()) {
            return DetectionResult.detected("alert-spam: scammer");
        }

        if (IGNORE_ALERT.matcher(originalText).find()) {
            return DetectionResult.detected("alert-spam: ignore");
        }

        if (FAKE_ANTISCAM.matcher(originalText).find()) {
            return DetectionResult.detected("alert-spam: fake-antiscam");
        }

        if (SCEPTRE_LURE.matcher(originalText).find()) {
            return DetectionResult.detected("alert-spam: sceptre-lure");
        }

        if (LOCATION_WARNING.matcher(originalText).find()) {
            return DetectionResult.detected("alert-spam: location-warning");
        }

        if (UNSAFE_BANKS.matcher(originalText).find()) {
            return DetectionResult.detected("alert-spam: unsafe-banks");
        }

        if (NOT_SAFE_PVP.matcher(originalText).find()) {
            return DetectionResult.detected("alert-spam: not-safe-pvp");
        }

        if (LURERS_TO_IGNORE.matcher(originalText).find()) {
            return DetectionResult.detected("alert-spam: lurers-list");
        }

        if (DO_NOT_ATTEMPT.matcher(originalText).find()) {
            return DetectionResult.detected("alert-spam: do-not-attempt");
        }

        return DetectionResult.notDetected();
    }
}
