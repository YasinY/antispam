package com.antispam.detectors.impl;

import com.antispam.detectors.DetectionResult;
import com.antispam.detectors.ISpamDetector;

import javax.annotation.Nonnull;
import java.util.regex.Pattern;

// TODO extract patterns to a separate file to reduce bloat
public class GamblingDetector implements ISpamDetector {

    private static final Pattern ROLL_PATTERN = Pattern.compile(
        "\\b(has )?(won|lost|rolled)\\b.{0,50}\\b(roll|dice)\\b.{0,30}\\b\\d{1,3}\\b",
        Pattern.CASE_INSENSITIVE
    );

    private static final Pattern SIMPLE_ROLL = Pattern.compile(
        "\\broll\\b.{0,5}\\b\\d{1,3}\\b",
        Pattern.CASE_INSENSITIVE
    );

    private static final Pattern BET_PATTERN = Pattern.compile(
        "\\b(my )?(minimum|maximum|min|max)\\b.{0,30}\\b(bet|is)\\b",
        Pattern.CASE_INSENSITIVE
    );

    private static final Pattern QUEUE_PATTERN = Pattern.compile(
        "\\b(the )?queue\\b.{0,50}\\b(has|people|players|waiting)\\b",
        Pattern.CASE_INSENSITIVE
    );

    private static final Pattern PAYOUT_PATTERN = Pattern.compile(
        "\\b(huge|massive|big)\\b.{0,20}\\b(payout|win)s?\\b",
        Pattern.CASE_INSENSITIVE
    );

    private static final Pattern RECEIVED_WINNINGS = Pattern.compile(
        "\\b(has )?(received|cashed|been paid)\\b.{0,30}(winning|their)?.{0,30}\\b\\d{1,4}\\s*[kmb]",
        Pattern.CASE_INSENSITIVE
    );

    private static final Pattern TRADE_ACCEPTED = Pattern.compile(
        "\\btrade accepted\\b.{0,30}\\b(for|by|from)\\b.{0,30}\\b\\d{1,4}\\s*[kmb]",
        Pattern.CASE_INSENSITIVE
    );

    private static final Pattern ROLL_COMMAND = Pattern.compile(
        "\\broll\\b.{0,10}\\b5[0-9]\\+",
        Pattern.CASE_INSENSITIVE
    );

    private static final Pattern DICE_HOST = Pattern.compile(
        "\\b(original|trusted|legit)\\b.{0,20}\\b(dice|host|casino)\\b",
        Pattern.CASE_INSENSITIVE
    );

    private static final Pattern DOUBLING_MONEY = Pattern.compile(
        "\\bdoubl(ing|e)\\b.{0,30}\\b(money|gold|gp|coins|all|amounts)\\b",
        Pattern.CASE_INSENSITIVE
    );

    private static final Pattern MULTIPLY_PATTERN = Pattern.compile(
        "\\b(2x|\\d+x)\\b.{0,30}\\b(them|it|your|all)\\b",
        Pattern.CASE_INSENSITIVE
    );

    private static final Pattern GIVE_YOU_DOUBLE = Pattern.compile(
        "\\b(give|get)\\b.{0,30}\\byou\\b.{0,30}\\b\\d{3,5}\\s*[kmb]\\b",
        Pattern.CASE_INSENSITIVE
    );

    private static final Pattern ADDED_TO_POT = Pattern.compile(
        "\\b(add(ed)?|put|deposit(ed)?)\\b.{0,30}\\b(to )?(the )?pot\\b",
        Pattern.CASE_INSENSITIVE
    );

    private static final Pattern VICTORY_PATTERN = Pattern.compile(
        "\\b(victory|defeat|loss)\\b.{0,30}\\b(total|feet|pot)\\b",
        Pattern.CASE_INSENSITIVE
    );

    private static final Pattern H_CURRENCY = Pattern.compile(
        "\\b\\d{1,5}\\s*H\\b",
        Pattern.CASE_INSENSITIVE
    );

    @Override
    public DetectionResult detect(@Nonnull String originalText, String normalizedText) {
        if (ROLL_PATTERN.matcher(originalText).find()) {
            return DetectionResult.detected("gambling: roll");
        }

        if (BET_PATTERN.matcher(originalText).find()) {
            return DetectionResult.detected("gambling: bet");
        }

        if (QUEUE_PATTERN.matcher(originalText).find()) {
            return DetectionResult.detected("gambling: queue");
        }

        if (PAYOUT_PATTERN.matcher(originalText).find()) {
            return DetectionResult.detected("gambling: payout");
        }

        if (RECEIVED_WINNINGS.matcher(originalText).find()) {
            return DetectionResult.detected("gambling: winnings");
        }

        if (TRADE_ACCEPTED.matcher(originalText).find()) {
            return DetectionResult.detected("gambling: trade-accepted");
        }

        if (ROLL_COMMAND.matcher(originalText).find()) {
            return DetectionResult.detected("gambling: roll-command");
        }

        if (DICE_HOST.matcher(originalText).find()) {
            return DetectionResult.detected("gambling: dice-host");
        }

        if (DOUBLING_MONEY.matcher(originalText).find()) {
            return DetectionResult.detected("gambling: doubling");
        }

        if (MULTIPLY_PATTERN.matcher(originalText).find()) {
            return DetectionResult.detected("gambling: multiply");
        }

        if (GIVE_YOU_DOUBLE.matcher(originalText).find()) {
            return DetectionResult.detected("gambling: give-you-double");
        }

        if (SIMPLE_ROLL.matcher(originalText).find()) {
            return DetectionResult.detected("gambling: simple-roll");
        }

        if (ADDED_TO_POT.matcher(originalText).find()) {
            return DetectionResult.detected("gambling: pot");
        }

        if (VICTORY_PATTERN.matcher(originalText).find()) {
            return DetectionResult.detected("gambling: victory");
        }

        if (H_CURRENCY.matcher(originalText).find() && (originalText.toLowerCase().contains("pot") || originalText.toLowerCase().contains("roll"))) {
            return DetectionResult.detected("gambling: h-currency");
        }

        return DetectionResult.notDetected();
    }
}
