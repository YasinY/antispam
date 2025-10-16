package com.antispam.keywords;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class KeywordCategory {

    public static Keywords getLenientKeywords() {
        List<String> standalone = Arrays.asList(
                "doubling",
                "trust trade",
                "anti-lure",
                "trimming",
                "armor",
                "armour",
                "high low",
                "quitting",
                "qutting",
                "quitting party",
                "drop party",
                "dropparty",
                "buy gold",
                "sell gold",
                "cheap gold",
                "osrs gold",
                "rs gold",
                "cheapest",
                "instant delivery",
                "paypal",
                "trusted site",
                "only fans",
                "onlyfans",
                "visit",
                "website",
                "search on",
                "youtube",
                "you tube",
                "try now",
                "trade to play",
                "amazing wins",
                "play to trade",
                "instant wins",
                "accepting all",
                "casino",
                "open casino",
                "test your luck",
                "your luck",
                "jackpot",
                "win big",
                "big win",
                "swap",
                "swapping",
                "increasing banks",
                "increasing your bank",
                "dance for",
                "dancing for",
                "selling jokes",
                "taking donations",
                "taking gifts",
                "rewarding generosity"
        );

        List<String> substring = Arrays.asList(
                ".com",
                ".net",
                ".gg",
                ".io",
                ".co",
                "discord.gg"
        );

        return new Keywords(standalone, substring);
    }

    public static Keywords getModerateExtensions() {
        List<String> standalone = Arrays.asList(
                "pls",
                "plz",
                "plox",
                "ples",
                "plss",
                "donate",
                "donation",
                "spare",
                "someone spare me",
                "someone give me",
                "can somebody",
                "can i have",
                "can i get",
                "give me",
                "hook me up",
                "im broke",
                "im poor",
                "poor",
                "help",
                "giveaway",
                "check out",
                "click here",
                "link in",
                "dm me",
                "pm me",
                "add me",
                "twitch",
                "stream",
                "for reward",
                "high reward",
                "play easily",
                "spending",
                "trading up",
                "trade me",
                "message me",
                "follow me",
                "clean out",
                "take junk",
                "taking junk",
                "accept junk",
                "bulk",
                "burnt food"
        );

        List<String> substring = Collections.emptyList();

        return new Keywords(standalone, substring);
    }

    public static Keywords getStrictExtensions() {
        List<String> standalone = Arrays.asList(
                "free",
                "gold",
                "gp",
                "coins",
                "coin",
                "money",
                "give",
                "need",
                "broke",
                "anyone",
                "bank sale",
                "lewt",
                "dollar",
                "usd",
                "euro",
                "payment",
                "crypto",
                "bitcoin",
                "venmo"
        );

        List<String> substring = Collections.emptyList();

        return new Keywords(standalone, substring);
    }
}
