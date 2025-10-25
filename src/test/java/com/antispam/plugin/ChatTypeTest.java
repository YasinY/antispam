package com.antispam.plugin;

import org.junit.Test;

import static org.junit.Assert.*;

public class ChatTypeTest {

    @Test
    public void testEnumValues() {
        ChatType[] values = ChatType.values();
        assertEquals(3, values.length);
    }

    @Test
    public void testPublicExists() {
        ChatType type = ChatType.valueOf("PUBLIC");
        assertEquals(ChatType.PUBLIC, type);
    }

    @Test
    public void testOverheadExists() {
        ChatType type = ChatType.valueOf("OVERHEAD");
        assertEquals(ChatType.OVERHEAD, type);
    }

    @Test
    public void testPrivateExists() {
        ChatType type = ChatType.valueOf("PRIVATE");
        assertEquals(ChatType.PRIVATE, type);
    }

    @Test
    public void testEnumOrdering() {
        ChatType[] values = ChatType.values();
        assertEquals(ChatType.PUBLIC, values[0]);
        assertEquals(ChatType.OVERHEAD, values[1]);
        assertEquals(ChatType.PRIVATE, values[2]);
    }
}
