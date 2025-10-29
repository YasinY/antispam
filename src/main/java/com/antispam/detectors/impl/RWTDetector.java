package com.antispam.detectors.impl;

import com.antispam.detectors.DetectionResult;
import com.antispam.detectors.ISpamDetector;

import javax.annotation.Nonnull;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

public class RWTDetector implements ISpamDetector {

    private static final List<String> HIGH_VALUE_ITEMS = Arrays.asList(
        "twisted bow", "t bow", "tbow",
        "elysian", "ely", "elysian spirit shield",
        "scythe", "scythe of vitur",
        "inquisitor",
        "dhcb", "dragon hunter crossbow",
        "rapier", "ghrazi rapier", "ghrazier rapier",
        "dragon claws", "d claws", "claws",
        "ancestral", "ancestral set",
        "harmonised orb", "volatile orb",
        "sanguinesti staff", "sang staff",
        "kodai wand",
        "dragon hunter lance", "dhl",
        "dwh", "dragon warhammer",
        "3rd age", "third age", "erd age",
        "armadyl chestplate", "armadyl chainskirt", "armadyl set",
        "bandos set", "bandos chestplate", "bandos tassets",
        "saradomin godsword", "sgs",
        "spectral spirit shield", "spectral",
        "fang", "fang of venenatis",
        "shadow", "tumeken's shadow", "tumekens shadow"
    );

    private static final Pattern BUYING_PATTERN = Pattern.compile(
        "\\b(buy(ing)?|sell(ing)?|trade(ing)?|trading up)\\b.{0,80}\\b(\\d{1,5}(\\.\\d{1,2})?)\\s*([kmb]|mil+|bil+)\\b",
        Pattern.CASE_INSENSITIVE
    );

    private static final Pattern ABSURD_AMOUNT = Pattern.compile(
        "\\b(spending|have|got|selling|buying)\\b.{0,30}\\b(([2-9]\\d{1,2}|\\d{4,})(\\.\\d{1,2})?)\\s*([b]|bil+)\\b",
        Pattern.CASE_INSENSITIVE
    );

    private static final Pattern BANK_SALE = Pattern.compile(
        "\\b(sell(ing)?|buy(ing)?)\\b.{0,30}\\b(your )?bank\\b.{0,50}\\b(\\d{1,3})\\s*%",
        Pattern.CASE_INSENSITIVE
    );

    private static final Pattern QUICK_SALE = Pattern.compile(
        "\\b(someone|anyone)\\b.{0,50}\\b(sell|buy)\\b.{0,50}\\b(quick(ly)?|now|asap)\\b",
        Pattern.CASE_INSENSITIVE
    );

    private static final Pattern PAY_EXTRA = Pattern.compile(
        "\\b(pay(ing)?|will pay|i pay)\\b.{0,30}\\b(a ?lot|much|alot|extra)\\b",
        Pattern.CASE_INSENSITIVE
    );

    private static final Pattern HELLO_BUYING = Pattern.compile(
        "\\bhello+\\??.{0,30}\\b(buy(ing)?|sell(ing)?)\\b",
        Pattern.CASE_INSENSITIVE
    );

    private static final Pattern ANYONE_SELL_ME = Pattern.compile(
        "\\b(anyone|someone|anybody)\\b.{0,30}\\b(sell|buy)\\b.{0,30}\\bme\\b",
        Pattern.CASE_INSENSITIVE
    );

    @Override
    public DetectionResult detect(@Nonnull String originalText, String normalizedText) {
        if (ABSURD_AMOUNT.matcher(originalText).find()) {
            return DetectionResult.detected("rwt: absurd-amount");
        }

        if (BANK_SALE.matcher(originalText).find()) {
            return DetectionResult.detected("rwt: bank-sale");
        }

        if (containsHighValueItemTrade(originalText)) {
            return DetectionResult.detected("rwt: high-value-item");
        }

        if (QUICK_SALE.matcher(originalText).find()) {
            return DetectionResult.detected("rwt: urgent-trade");
        }

        if (PAY_EXTRA.matcher(originalText).find() && BUYING_PATTERN.matcher(originalText).find()) {
            return DetectionResult.detected("rwt: pay-extra");
        }

        if (HELLO_BUYING.matcher(originalText).find()) {
            return DetectionResult.detected("rwt: hello-buying");
        }

        if (ANYONE_SELL_ME.matcher(originalText).find()) {
            return DetectionResult.detected("rwt: anyone-sell-me");
        }

        return DetectionResult.notDetected();
    }

    private boolean containsHighValueItemTrade(String text) {
        String lowerText = text.toLowerCase();

        for (String item : HIGH_VALUE_ITEMS) {
            if (lowerText.contains(item) && BUYING_PATTERN.matcher(text).find()) {
                return true;
            }
        }

        return false;
    }
}
