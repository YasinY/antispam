package com.antispam.plugin;

import org.junit.Test;

public class NormalizationDebugTest {

    @Test
    public void testNormalization() {
        UsernameFilter filter = new UsernameFilter();
        filter.updateBlacklist("spammer");

        System.out.println("Testing: spammer");
        System.out.println("Result: " + filter.isBlacklisted("spammer"));

        System.out.println("Testing: sp4mm3r");
        System.out.println("Result: " + filter.isBlacklisted("sp4mm3r"));

        System.out.println("Testing: 5p4mm3r");
        System.out.println("Result: " + filter.isBlacklisted("5p4mm3r"));

        System.out.println("Testing: spam$er");
        System.out.println("Result: " + filter.isBlacklisted("spam$er"));
    }
}
