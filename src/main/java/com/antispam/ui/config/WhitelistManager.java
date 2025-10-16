package com.antispam.ui.config;

import net.runelite.api.Client;
import net.runelite.api.Friend;
import net.runelite.api.clan.ClanChannelMember;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class WhitelistManager {

    private final Client client;
    private final AntiBeggarConfig config;
    private Set<String> customWhitelist;

    public WhitelistManager(Client client, AntiBeggarConfig config) {
        this.client = client;
        this.config = config;
        this.customWhitelist = new HashSet<>();
    }

    public void updateCustomWhitelist(String input) {
        if (input == null || input.trim().isEmpty()) {
            customWhitelist = new HashSet<>();
            return;
        }

        customWhitelist = Arrays.stream(input.split(",\\s*"))
            .map(String::trim)
            .filter(s -> !s.isEmpty())
            .map(String::toLowerCase)
            .collect(Collectors.toSet());
    }

    public boolean isWhitelisted(String playerName) {
        if (playerName == null || playerName.isEmpty()) {
            return false;
        }

        String normalizedName = playerName.toLowerCase().trim();

        if (customWhitelist.contains(normalizedName)) {
            return true;
        }

        if (config.whitelistFriends() && isFriend(playerName)) {
            return true;
        }

        if (config.whitelistClan() && isClanMember(playerName)) {
            return true;
        }

        return false;
    }

    private boolean isFriend(String playerName) {
        if (client.getFriendContainer() == null) {
            return false;
        }

        for (Friend friend : client.getFriendContainer().getMembers()) {
            if (friend.getName().equalsIgnoreCase(playerName)) {
                return true;
            }
        }

        return false;
    }

    private boolean isClanMember(String playerName) {
        if (client.getClanChannel() == null) {
            return false;
        }

        for (ClanChannelMember member : client.getClanChannel().getMembers()) {
            if (member.getName().equalsIgnoreCase(playerName)) {
                return true;
            }
        }

        return false;
    }
}
