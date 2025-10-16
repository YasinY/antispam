package com.antispam.detectors.impl;

import com.antispam.detectors.DetectionResult;
import com.antispam.detectors.ISpamDetector;

import java.util.regex.Pattern;

public class ShowTradingScamDetector implements ISpamDetector {

    private static final Pattern SHOW_BANK = Pattern.compile(
        "\\bshow\\b.{0,30}\\b(me|your)?\\b.{0,30}\\bbank\\b",
        Pattern.CASE_INSENSITIVE
    );

    private static final Pattern SHOW_BANK_WITH_AMOUNT = Pattern.compile(
        "\\bshow\\b.{0,30}\\b(me)?\\b.{0,30}\\b\\d+\\s*[kmb]\\b.{0,30}\\bbank\\b",
        Pattern.CASE_INSENSITIVE
    );

    private static final Pattern TRADE_AND_SHOW = Pattern.compile(
        "\\btrade\\b.{0,30}\\b(me)?\\b.{0,30}\\band\\b.{0,30}\\bshow\\b",
        Pattern.CASE_INSENSITIVE
    );

    private static final Pattern SHOW_ITEM_GET_ITEM = Pattern.compile(
        "\\bshow\\b.{0,50}\\b(and|you will|you'll|u will)\\b.{0,30}\\b(get|receive)\\b",
        Pattern.CASE_INSENSITIVE
    );

    private static final Pattern SHOW_MULTIPLIER = Pattern.compile(
        "\\bshow\\b.{0,50}\\b(2x|double|doubl(ing|e)|\\d+x)\\b",
        Pattern.CASE_INSENSITIVE
    );

    private static final Pattern DOUBLING_WHAT_YOU_SHOW = Pattern.compile(
        "\\bdoubl(ing|e)\\b.{0,50}\\bwhat you show\\b",
        Pattern.CASE_INSENSITIVE
    );

    private static final Pattern SHOW_BEST_ITEMS = Pattern.compile(
        "\\bshow\\b.{0,30}\\b(me)?\\b.{0,30}\\b(all|your)?\\b.{0,30}\\b(best|good|valuable)\\b.{0,30}\\bitems\\b",
        Pattern.CASE_INSENSITIVE
    );

    private static final Pattern HERO_AFTER = Pattern.compile(
        "\\byou\\b.{0,30}\\b(are|will be|become)\\b.{0,30}\\bhero\\b.{0,30}\\bafter\\b",
        Pattern.CASE_INSENSITIVE
    );

    private static final Pattern BANK_VALUE_PERCENT = Pattern.compile(
        "\\bbank\\b.{0,30}\\bvalue\\b.{0,50}\\b\\d+\\s*%",
        Pattern.CASE_INSENSITIVE
    );

    @Override
    public DetectionResult detect(String originalText, String normalizedText) {
        if (originalText == null || originalText.isEmpty()) {
            return DetectionResult.notDetected();
        }

        if (BANK_VALUE_PERCENT.matcher(originalText).find()) {
            return DetectionResult.detected("show-scam: bank-value-percent");
        }

        if (SHOW_BANK_WITH_AMOUNT.matcher(originalText).find()) {
            return DetectionResult.detected("show-scam: show-bank-amount");
        }

        if (DOUBLING_WHAT_YOU_SHOW.matcher(originalText).find()) {
            return DetectionResult.detected("show-scam: doubling-shown");
        }

        if (TRADE_AND_SHOW.matcher(originalText).find()) {
            return DetectionResult.detected("show-scam: trade-and-show");
        }

        if (SHOW_ITEM_GET_ITEM.matcher(originalText).find()) {
            return DetectionResult.detected("show-scam: show-item-get-item");
        }

        if (SHOW_MULTIPLIER.matcher(originalText).find()) {
            return DetectionResult.detected("show-scam: show-multiplier");
        }

        if (SHOW_BEST_ITEMS.matcher(originalText).find()) {
            return DetectionResult.detected("show-scam: show-best-items");
        }

        if (HERO_AFTER.matcher(originalText).find()) {
            return DetectionResult.detected("show-scam: hero-promise");
        }

        if (SHOW_BANK.matcher(originalText).find()) {
            return DetectionResult.detected("show-scam: show-bank");
        }

        return DetectionResult.notDetected();
    }
}