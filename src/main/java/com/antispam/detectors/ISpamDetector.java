package com.antispam.detectors;

public interface ISpamDetector {

    DetectionResult detect(String originalText, String normalizedText);
}
