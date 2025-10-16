package com.antispam.ui.panel.statistics;

import com.antispam.plugin.ChatType;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class FilterStatistics {

    private final AtomicInteger totalFiltered;
    private final AtomicInteger publicChatFiltered;
    private final AtomicInteger overheadTextFiltered;
    private final AtomicInteger privateMessageFiltered;
    private final Map<String, Integer> keywordCounts;

    public FilterStatistics() {
        this.totalFiltered = new AtomicInteger(0);
        this.publicChatFiltered = new AtomicInteger(0);
        this.overheadTextFiltered = new AtomicInteger(0);
        this.privateMessageFiltered = new AtomicInteger(0);
        this.keywordCounts = new ConcurrentHashMap<>();
    }

    public void recordFilter(ChatType chatType, String keyword) {
        totalFiltered.incrementAndGet();

        switch (chatType) {
            case PUBLIC:
                publicChatFiltered.incrementAndGet();
                break;
            case OVERHEAD:
                overheadTextFiltered.incrementAndGet();
                break;
            case PRIVATE:
                privateMessageFiltered.incrementAndGet();
                break;
        }

        if (keyword != null) {
            keywordCounts.merge(keyword, 1, Integer::sum);
        }
    }

    public void reset() {
        totalFiltered.set(0);
        publicChatFiltered.set(0);
        overheadTextFiltered.set(0);
        privateMessageFiltered.set(0);
        keywordCounts.clear();
    }

    public int getTotalFiltered() {
        return totalFiltered.get();
    }

    public int getPublicChatFiltered() {
        return publicChatFiltered.get();
    }

    public int getOverheadTextFiltered() {
        return overheadTextFiltered.get();
    }

    public int getPrivateMessageFiltered() {
        return privateMessageFiltered.get();
    }

    public Map<String, Integer> getKeywordCounts() {
        return new HashMap<>(keywordCounts);
    }
}
