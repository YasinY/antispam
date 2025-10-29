package com.antispam.detectors.impl;

import com.antispam.detectors.AbstractYamlDetector;

public class GamblingDetector extends AbstractYamlDetector {

    public GamblingDetector() {
        super("patterns/gambling.yaml");
    }
}
