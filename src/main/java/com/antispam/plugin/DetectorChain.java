package com.antispam.plugin;

import com.antispam.detectors.DetectionResult;
import com.antispam.detectors.ISpamDetector;

import java.util.ArrayList;
import java.util.List;

public class DetectorChain {

    private final List<ISpamDetector> detectors;

    public DetectorChain() {
        this.detectors = new ArrayList<>();
    }

    public void addDetector(ISpamDetector detector) {
        detectors.add(detector);
    }

    public DetectionResult detect(String originalText, String normalizedText) {
        for (ISpamDetector detector : detectors) {
            DetectionResult result = detector.detect(originalText, normalizedText);
            if (result.isDetected()) {
                return result;
            }
        }
        return DetectionResult.notDetected();
    }
}
