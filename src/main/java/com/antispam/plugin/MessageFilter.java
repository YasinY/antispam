package com.antispam.plugin;

public class MessageFilter {

    private final BeggarPatternMatcher matcher;

    public MessageFilter(BeggarPatternMatcher matcher) {
        this.matcher = matcher;
    }

    public boolean shouldFilter(String message) {
        return matcher.matches(message);
    }
}
