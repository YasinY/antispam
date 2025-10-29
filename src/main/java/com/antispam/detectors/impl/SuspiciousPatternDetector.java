package com.antispam.detectors.impl;

import com.antispam.detectors.AbstractYamlDetector;

public class SuspiciousPatternDetector extends AbstractYamlDetector {

    public SuspiciousPatternDetector() {
        super("patterns/suspicious.yaml");
    }
}
