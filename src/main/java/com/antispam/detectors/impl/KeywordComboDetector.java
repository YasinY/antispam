package com.antispam.detectors.impl;

import com.antispam.detectors.DetectionResult;
import com.antispam.detectors.ISpamDetector;
import com.antispam.keywords.KeywordCombo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class KeywordComboDetector implements ISpamDetector {

    private final List<KeywordCombo> combos;

    public KeywordComboDetector() {
        this.combos = buildDefaultCombos();
    }

    @Override
    public DetectionResult detect(String originalText, String normalizedText) {
        if (detectNeedWithAmount(originalText)) {
            return DetectionResult.detected("combo: need + amount");
        }

        for (KeywordCombo combo : combos) {
            if (combo.matches(originalText)) {
                return DetectionResult.detected("combo: " + combo.getComboString());
            }
            if (combo.matches(normalizedText)) {
                return DetectionResult.detected("combo: " + combo.getComboString() + " (normalized)");
            }
        }

        return DetectionResult.notDetected();
    }

    private boolean detectNeedWithAmount(String text) {
        if (text == null || text.isEmpty()) {
            return false;
        }

        String lowerText = text.toLowerCase();

        if (lowerText.matches(".*\\bneed\\b.*\\d+\\s*m\\b.*")) {
            return true;
        }

        if (lowerText.matches(".*\\bneed\\b.*\\d+\\s*mil\\b.*")) {
            return true;
        }

        if (lowerText.matches(".*\\bneed\\b.*\\d+\\s*k\\b.*")) {
            return true;
        }

        return false;
    }

    private List<KeywordCombo> buildDefaultCombos() {
        List<KeywordCombo> combos = new ArrayList<>();

        combos.add(new KeywordCombo(
            Arrays.asList("selling", ".com"),
            "Selling with website"
        ));

        combos.add(new KeywordCombo(
            Arrays.asList("selling", ".gg"),
            "Selling with website"
        ));

        combos.add(new KeywordCombo(
            Arrays.asList("selling", ".net"),
            "Selling with website"
        ));

        combos.add(new KeywordCombo(
            Arrays.asList("buying", ".com"),
            "Buying with website"
        ));

        combos.add(new KeywordCombo(
            Arrays.asList("buying", ".gg"),
            "Buying with website"
        ));

        combos.add(new KeywordCombo(
            Arrays.asList("buying", ".net"),
            "Buying with website"
        ));

        combos.add(new KeywordCombo(
            Arrays.asList("free", "gold"),
            "Free gold spam"
        ));

        combos.add(new KeywordCombo(
            Arrays.asList("free", "gp"),
            "Free gp spam"
        ));

        combos.add(new KeywordCombo(
            Arrays.asList("free", "coins"),
            "Free coins spam"
        ));

        combos.add(new KeywordCombo(
            Arrays.asList("visit", "youtube"),
            "Visit youtube spam"
        ));

        combos.add(new KeywordCombo(
            Arrays.asList("check", "youtube"),
            "Check youtube spam"
        ));

        combos.add(new KeywordCombo(
            Arrays.asList("check out", "youtube"),
            "Check out youtube spam"
        ));

        combos.add(new KeywordCombo(
            Arrays.asList("click", "link"),
            "Click link spam"
        ));

        combos.add(new KeywordCombo(
            Arrays.asList("pm", "trade"),
            "PM for trade spam"
        ));

        combos.add(new KeywordCombo(
            Arrays.asList("dm", "trade"),
            "DM for trade spam"
        ));

        combos.add(new KeywordCombo(
            Arrays.asList("trading up", "million"),
            "Trading up million spam"
        ));

        combos.add(new KeywordCombo(
            Arrays.asList("trading up", "mil"),
            "Trading up mil spam"
        ));

        combos.add(new KeywordCombo(
            Arrays.asList("trading up", "coins"),
            "Trading up coins spam"
        ));

        combos.add(new KeywordCombo(
            Arrays.asList("trading up", "gp"),
            "Trading up gp spam"
        ));

        combos.add(new KeywordCombo(
            Arrays.asList("trading up", "gold"),
            "Trading up gold spam"
        ));

        combos.add(new KeywordCombo(
            Arrays.asList("buy", "tbow"),
            "Buy tbow spam"
        ));

        combos.add(new KeywordCombo(
            Arrays.asList("buy", "twisted bow"),
            "Buy twisted bow spam"
        ));

        combos.add(new KeywordCombo(
            Arrays.asList("buy", "scythe"),
            "Buy scythe spam"
        ));

        combos.add(new KeywordCombo(
            Arrays.asList("buy", "ely"),
            "Buy ely spam"
        ));

        combos.add(new KeywordCombo(
            Arrays.asList("buy", "elysian"),
            "Buy elysian spam"
        ));

        combos.add(new KeywordCombo(
            Arrays.asList("sell", "3rd"),
            "Sell 3rd age spam"
        ));

        combos.add(new KeywordCombo(
            Arrays.asList("sell", "erd"),
            "Sell 3rd age spam"
        ));

        combos.add(new KeywordCombo(
            Arrays.asList("buy", "3rd"),
            "Buy 3rd age spam"
        ));

        combos.add(new KeywordCombo(
            Arrays.asList("buy", "erd"),
            "Buy 3rd age spam"
        ));

        combos.add(new KeywordCombo(
            Arrays.asList("buying", "3rd"),
            "Buying 3rd age spam"
        ));

        combos.add(new KeywordCombo(
            Arrays.asList("buying", "erd"),
            "Buying 3rd age spam"
        ));

        combos.add(new KeywordCombo(
            Arrays.asList("selling", "3rd"),
            "Selling 3rd age spam"
        ));

        combos.add(new KeywordCombo(
            Arrays.asList("selling", "erd"),
            "Selling 3rd age spam"
        ));

        combos.add(new KeywordCombo(
            Arrays.asList("need", "gold"),
            "Need gold spam"
        ));

        combos.add(new KeywordCombo(
            Arrays.asList("need", "gp"),
            "Need gp spam"
        ));

        combos.add(new KeywordCombo(
            Arrays.asList("all", "valuable items"),
            "Buying all valuable items"
        ));

        combos.add(new KeywordCombo(
            Arrays.asList("all", "good items"),
            "Buying all good items"
        ));

        combos.add(new KeywordCombo(
            Arrays.asList("sell", "bank"),
            "Sell bank spam"
        ));

        combos.add(new KeywordCombo(
            Arrays.asList("your", "bank"),
            "Your bank spam"
        ));

        combos.add(new KeywordCombo(
            Arrays.asList("paying", "extra"),
            "Paying extra spam"
        ));

        combos.add(new KeywordCombo(
            Arrays.asList("pay", "extra"),
            "Pay extra spam"
        ));

        combos.add(new KeywordCombo(
            Arrays.asList("pls", "items"),
            "Begging for items"
        ));

        combos.add(new KeywordCombo(
            Arrays.asList("plz", "items"),
            "Begging for items"
        ));

        combos.add(new KeywordCombo(
            Arrays.asList("plox", "items"),
            "Begging for items"
        ));

        combos.add(new KeywordCombo(
            Arrays.asList("please", "items"),
            "Begging for items"
        ));

        combos.add(new KeywordCombo(
            Arrays.asList("pls", "item"),
            "Begging for item"
        ));

        combos.add(new KeywordCombo(
            Arrays.asList("plz", "item"),
            "Begging for item"
        ));

        combos.add(new KeywordCombo(
            Arrays.asList("plox", "item"),
            "Begging for item"
        ));

        combos.add(new KeywordCombo(
            Arrays.asList("pls", "some"),
            "Begging please some"
        ));

        combos.add(new KeywordCombo(
            Arrays.asList("plz", "some"),
            "Begging please some"
        ));

        combos.add(new KeywordCombo(
            Arrays.asList("plox", "some"),
            "Begging please some"
        ));

        combos.add(new KeywordCombo(
            Arrays.asList("please", "some"),
            "Begging please some"
        ));

        combos.add(new KeywordCombo(
            Arrays.asList("trading up", "challenge"),
            "Trading up challenge"
        ));

        combos.add(new KeywordCombo(
            Arrays.asList("tradeup", "challenge"),
            "Tradeup challenge"
        ));

        combos.add(new KeywordCombo(
            Arrays.asList("trade up", "challenge"),
            "Trade up challenge"
        ));

        combos.add(new KeywordCombo(
            Arrays.asList("show", "bank value"),
            "Show bank value"
        ));

        combos.add(new KeywordCombo(
            Arrays.asList("show", "bank wealth"),
            "Show bank wealth"
        ));

        combos.add(new KeywordCombo(
            Arrays.asList("show", "total bank"),
            "Show total bank"
        ));

        combos.add(new KeywordCombo(
            Arrays.asList("doubling", "banks"),
            "Doubling banks"
        ));

        combos.add(new KeywordCombo(
            Arrays.asList("first", "shows"),
            "First who shows"
        ));

        combos.add(new KeywordCombo(
            Arrays.asList("coronavirus", "help"),
            "Coronavirus scam"
        ));

        combos.add(new KeywordCombo(
            Arrays.asList("bitcoin millionaire", "coronavirus"),
            "Bitcoin coronavirus scam"
        ));

        combos.add(new KeywordCombo(
            Arrays.asList("late", "christmas"),
            "Late christmas scam"
        ));

        return combos;
    }
}
