package com.antispam.detectors.impl;

import com.antispam.detectors.AbstractYamlDetector;

public class SpacedTextDetector extends AbstractYamlDetector {

    public SpacedTextDetector() {
        super("patterns/spaced-text.yaml");
    }
}
