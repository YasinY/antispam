package com.antispam.patterns;

import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.constructor.Constructor;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * Utility class for loading pattern configurations from YAML files.
 */
public class PatternLoader {

    /**
     * Loads a pattern configuration from a YAML resource file.
     *
     * @param resourcePath Path to YAML resource (e.g., "patterns/gambling.yaml")
     * @return PatternConfig object
     * @throws RuntimeException if resource cannot be loaded or parsed
     */
    public static PatternConfig loadConfig(String resourcePath) {
        Yaml yaml = new Yaml(new Constructor(PatternConfig.class));

        InputStream inputStream = PatternLoader.class.getClassLoader().getResourceAsStream(resourcePath);
        if (inputStream == null) {
            throw new RuntimeException("Could not find pattern resource: " + resourcePath);
        }

        try {
            return yaml.load(inputStream);
        } catch (Exception e) {
            throw new RuntimeException("Failed to parse pattern configuration: " + resourcePath, e);
        }
    }

    /**
     * Converts a PatternConfig into a map of compiled Pattern objects.
     *
     * @param config The pattern configuration
     * @return Map of pattern name to compiled Pattern object
     */
    public static Map<String, Pattern> compilePatterns(PatternConfig config) {
        Map<String, Pattern> patterns = new HashMap<>();

        if (config.getPatterns() == null) {
            return patterns;
        }

        for (PatternDefinition def : config.getPatterns()) {
            int flags = 0;

            if (def.getFlags() != null) {
                for (String flag : def.getFlags()) {
                    if ("CASE_INSENSITIVE".equals(flag)) {
                        flags |= Pattern.CASE_INSENSITIVE;
                    } else if ("MULTILINE".equals(flag)) {
                        flags |= Pattern.MULTILINE;
                    } else if ("DOTALL".equals(flag)) {
                        flags |= Pattern.DOTALL;
                    }
                }
            }

            Pattern pattern = Pattern.compile(def.getRegex(), flags);
            patterns.put(def.getName(), pattern);
        }

        return patterns;
    }

    /**
     * Gets a specific PatternDefinition by name from the config.
     *
     * @param config Pattern configuration
     * @param name Pattern name
     * @return PatternDefinition or null if not found
     */
    public static PatternDefinition getPatternDefinition(PatternConfig config, String name) {
        if (config.getPatterns() == null) {
            return null;
        }

        for (PatternDefinition def : config.getPatterns()) {
            if (name.equals(def.getName())) {
                return def;
            }
        }

        return null;
    }
}
