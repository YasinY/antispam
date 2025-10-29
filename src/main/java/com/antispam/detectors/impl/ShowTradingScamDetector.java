package com.antispam.detectors.impl;

import com.antispam.detectors.AbstractYamlDetector;

public class ShowTradingScamDetector extends AbstractYamlDetector {

    public ShowTradingScamDetector() {
        super("patterns/show-scam.yaml");
    }
}