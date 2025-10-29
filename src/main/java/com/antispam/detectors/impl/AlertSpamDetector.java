package com.antispam.detectors.impl;

import com.antispam.detectors.AbstractYamlDetector;

public class AlertSpamDetector extends AbstractYamlDetector {

    public AlertSpamDetector() {
        super("patterns/alert.yaml");
    }
}
