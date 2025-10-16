package com.antispam.ui;

import com.antispam.AntiBeggarPlugin;
import com.antispam.ui.panel.statistics.FilterStatistics;
import net.runelite.client.ui.ColorScheme;
import net.runelite.client.ui.PluginPanel;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.Map;

public class AntiBeggarPanel extends PluginPanel {

    private final AntiBeggarPlugin plugin;

    private final JLabel totalFilteredLabel;
    private final JLabel publicChatLabel;
    private final JLabel overheadTextLabel;
    private final JLabel privateMessageLabel;
    private final JPanel topKeywordsPanel;

    public AntiBeggarPanel(AntiBeggarPlugin plugin) {
        super(false);
        this.plugin = plugin;

        setLayout(new BorderLayout());
        setBackground(ColorScheme.DARK_GRAY_COLOR);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBackground(ColorScheme.DARK_GRAY_COLOR);
        mainPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        JLabel titleLabel = new JLabel("Anti-Spam Statistics");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(titleLabel);
        mainPanel.add(Box.createVerticalStrut(20));

        JPanel statsPanel = new JPanel();
        statsPanel.setLayout(new GridLayout(0, 2, 10, 10));
        statsPanel.setBackground(ColorScheme.DARKER_GRAY_COLOR);
        statsPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(ColorScheme.LIGHT_GRAY_COLOR),
            new EmptyBorder(10, 10, 10, 10)
        ));

        totalFilteredLabel = createStatLabel();
        publicChatLabel = createStatLabel();
        overheadTextLabel = createStatLabel();
        privateMessageLabel = createStatLabel();

        statsPanel.add(createLabel("Total Filtered:"));
        statsPanel.add(totalFilteredLabel);
        statsPanel.add(createLabel("Public Chat:"));
        statsPanel.add(publicChatLabel);
        statsPanel.add(createLabel("Overhead Text:"));
        statsPanel.add(overheadTextLabel);
        statsPanel.add(createLabel("Private Messages:"));
        statsPanel.add(privateMessageLabel);

        mainPanel.add(statsPanel);
        mainPanel.add(Box.createVerticalStrut(20));

        JLabel topKeywordsTitle = new JLabel("Top Keywords");
        topKeywordsTitle.setFont(new Font("Arial", Font.BOLD, 14));
        topKeywordsTitle.setForeground(Color.WHITE);
        topKeywordsTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(topKeywordsTitle);
        mainPanel.add(Box.createVerticalStrut(10));

        topKeywordsPanel = new JPanel();
        topKeywordsPanel.setLayout(new BoxLayout(topKeywordsPanel, BoxLayout.Y_AXIS));
        topKeywordsPanel.setBackground(ColorScheme.DARKER_GRAY_COLOR);
        topKeywordsPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(ColorScheme.LIGHT_GRAY_COLOR),
            new EmptyBorder(10, 10, 10, 10)
        ));
        mainPanel.add(topKeywordsPanel);

        mainPanel.add(Box.createVerticalStrut(20));

        JButton resetButton = new JButton("Reset Statistics");
        resetButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        resetButton.addActionListener(e -> {
            plugin.getStatistics().reset();
            updateStatistics();
        });
        mainPanel.add(resetButton);

        add(mainPanel, BorderLayout.NORTH);

        updateStatistics();
    }

    public void updateStatistics() {
        FilterStatistics stats = plugin.getStatistics();

        totalFilteredLabel.setText(String.valueOf(stats.getTotalFiltered()));
        publicChatLabel.setText(String.valueOf(stats.getPublicChatFiltered()));
        overheadTextLabel.setText(String.valueOf(stats.getOverheadTextFiltered()));
        privateMessageLabel.setText(String.valueOf(stats.getPrivateMessageFiltered()));

        updateTopKeywords(stats.getKeywordCounts());
    }

    private void updateTopKeywords(Map<String, Integer> keywordCounts) {
        topKeywordsPanel.removeAll();

        if (keywordCounts.isEmpty()) {
            JLabel noDataLabel = createLabel("No data yet");
            noDataLabel.setForeground(Color.GRAY);
            noDataLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            topKeywordsPanel.add(noDataLabel);
        } else {
            keywordCounts.entrySet().stream()
                .sorted((e1, e2) -> e2.getValue().compareTo(e1.getValue()))
                .limit(10)
                .forEach(entry -> {
                    JPanel keywordRow = new JPanel(new BorderLayout());
                    keywordRow.setBackground(ColorScheme.DARKER_GRAY_COLOR);
                    keywordRow.setMaximumSize(new Dimension(Integer.MAX_VALUE, 25));

                    JLabel keywordLabel = createLabel(entry.getKey());
                    JLabel countLabel = createStatLabel();
                    countLabel.setText(String.valueOf(entry.getValue()));

                    keywordRow.add(keywordLabel, BorderLayout.WEST);
                    keywordRow.add(countLabel, BorderLayout.EAST);

                    topKeywordsPanel.add(keywordRow);
                    topKeywordsPanel.add(Box.createVerticalStrut(5));
                });
        }

        topKeywordsPanel.revalidate();
        topKeywordsPanel.repaint();
    }

    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setForeground(Color.WHITE);
        return label;
    }

    private JLabel createStatLabel() {
        JLabel label = new JLabel("0");
        label.setForeground(new Color(255, 144, 64));
        label.setFont(new Font("Arial", Font.BOLD, 12));
        return label;
    }
}
