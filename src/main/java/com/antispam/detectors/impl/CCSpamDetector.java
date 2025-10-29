package com.antispam.detectors.impl;

import com.antispam.detectors.AbstractYamlDetector;

public class CCSpamDetector extends AbstractYamlDetector {

    public CCSpamDetector() {
        super("patterns/cc.yaml");
    }
}
