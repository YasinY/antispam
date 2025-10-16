package com.antispam.ui.config;

public enum FilterPreset {
        LENIENT("Lenient"),
        MODERATE("Moderate"),
        STRICT("Strict"),
        CUSTOM("Custom");

        private final String name;

        FilterPreset(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return name;
        }
    }