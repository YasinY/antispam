package com.antispam.detectors.impl;

import com.antispam.detectors.AbstractYamlDetector;

public class SocialMediaDetector extends AbstractYamlDetector {

    public SocialMediaDetector() {
        super("patterns/social.yaml");
    }
}
