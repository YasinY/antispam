package com.antispam.keywords;

import lombok.Getter;

import java.util.List;

@Getter
public class Keywords {
    private final List<String> standalone;

    private final List<String> substring;

    public Keywords(List<String> standalone, List<String> substring) {
        this.standalone = standalone;
        this.substring = substring;
    }
}