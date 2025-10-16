package com.antispam.detectors.impl;

import com.antispam.detectors.DetectionResult;
import com.antispam.detectors.ISpamDetector;

import java.util.regex.Pattern;

public class CCSpamDetector implements ISpamDetector {

    private static final Pattern JOIN_CC = Pattern.compile(
        "\\bjoin\\b.{0,50}\\b(cc|chat|clan)\\b",
        Pattern.CASE_INSENSITIVE
    );

    private static final Pattern SWAP_CC = Pattern.compile(
        "\\b(swap|swapping|trade)\\b.{0,50}\\b(rs3|osrs|07|dmm|07|d[ -]?m[ -]?m)\\b.{0,50}\\b(gp|gold|cc|chat)\\b",
        Pattern.CASE_INSENSITIVE
    );

    private static final Pattern CC_BRACES = Pattern.compile(
        "(<gt>|>|\\[|\\(|\\{|''|\").{0,30}(cc|chat|clan).{0,30}(<lt>|<|]|\\)|\\}|''|\")",
        Pattern.CASE_INSENSITIVE
    );

    private static final Pattern MOST_TRUSTED = Pattern.compile(
        "\\b(most|best|trusted)\\b.{0,50}\\b(swap|cc|clan|chat)\\b",
        Pattern.CASE_INSENSITIVE
    );

    private static final Pattern DROP_PARTY_CC = Pattern.compile(
        "\\b(drop[ -]?party|giveaway)\\b.{0,50}\\b(cc|chat|tonight|at)\\b",
        Pattern.CASE_INSENSITIVE
    );

    @Override
    public DetectionResult detect(String originalText, String normalizedText) {
        if (originalText == null || originalText.isEmpty()) {
            return DetectionResult.notDetected();
        }

        if (JOIN_CC.matcher(originalText).find()) {
            return DetectionResult.detected("cc-spam: join");
        }

        if (SWAP_CC.matcher(originalText).find()) {
            return DetectionResult.detected("cc-spam: swap");
        }

        if (CC_BRACES.matcher(originalText).find()) {
            return DetectionResult.detected("cc-spam: braces");
        }

        if (MOST_TRUSTED.matcher(originalText).find()) {
            return DetectionResult.detected("cc-spam: trusted");
        }

        if (DROP_PARTY_CC.matcher(originalText).find()) {
            return DetectionResult.detected("cc-spam: drop-party");
        }

        return DetectionResult.notDetected();
    }
}
