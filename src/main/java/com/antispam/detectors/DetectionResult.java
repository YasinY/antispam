package com.antispam.detectors;

public class DetectionResult {

    private final boolean detected;
    private final String keyword;

    private DetectionResult(boolean detected, String keyword) {
        this.detected = detected;
        this.keyword = keyword;
    }

    public static DetectionResult notDetected() {
        return new DetectionResult(false, null);
    }

    public static DetectionResult detected(String keyword) {
        return new DetectionResult(true, keyword);
    }

    public boolean isDetected() {
        return detected;
    }

    public String getKeyword() {
        return keyword;
    }
}
