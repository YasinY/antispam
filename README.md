# Anti-Spam

<div align="center">

![Java](https://img.shields.io/badge/Java-11-orange?style=flat-square&logo=java)
[![codecov](https://codecov.io/gh/YasinY/antispam/graph/badge.svg?token=CETOE6I8X4)](https://codecov.io/gh/YasinY/antispam)
![License](https://img.shields.io/badge/license-BSD--2--Clause-blue?style=flat-square)
![RuneLite](https://img.shields.io/badge/RuneLite-compatible-blueviolet?style=flat-square)

[![Ko-fi](https://img.shields.io/badge/Ko--fi-Support%20Me-FF5E5B?style=flat-square&logo=ko-fi&logoColor=white)](https://ko-fi.com/alwaysonosrs)

</div>

> Advanced spam filtering plugin for RuneLite that blocks begging, advertisements, scams, and other unwanted chat messages in Old School RuneScape.

---

## Table of Contents

- [Overview](#overview)
- [Features](#features)
- [Installation](#installation)
- [Configuration](#configuration)
- [Detection System](#detection-system)
- [Test Coverage](#test-coverage)
- [Building from Source](#building-from-source)
- [How It Works](#how-it-works)
- [Examples](#examples)
- [Contributing](#contributing)
- [Contact](#contact)
- [Support](#support)

---

## Overview

Anti-Spam is a comprehensive chat filtering solution designed to clean up your OSRS chat experience by automatically detecting and blocking various types of spam.

### What Gets Blocked

| Category | Examples |
|----------|----------|
| **Real-World Trading** | GP selling, account trading, gold swapping |
| **Scams** | Doubling money, "show me bank" trades, fake giveaways |
| **Begging** | "pls gp", "need bond", free item requests |
| **Advertisements** | Clan recruitment spam, social media promotion |
| **URL Spam** | Website links, Discord invites, domain spam |
| **Alert Spam** | Scammer warnings, player callouts |

---

## Features

### Filter Presets

Choose the filtering level that works best for you:

| Preset | Description | Best For |
|--------|-------------|----------|
| **Lenient** | Blocks scams and advertisements only | Casual filtering |
| **Moderate** | Adds begging and spam keywords | **Recommended** |
| **Strict** | Blocks all spam, begging, and advertisements | Maximum filtering |
| **Custom** | Use only your own keywords and patterns | Advanced users |

### Intelligent Detection System

The plugin uses **12 specialized detectors** working in a chain:

<table>
<tr>
<td width="50%">

**Core Detectors**
- Keyword Detector
- Custom Regex Detector
- Keyword Combo Detector
- URL Spam Detector

</td>
<td width="50%">

**Specialized Detectors**
- RWT Detector
- Gambling Detector
- Spaced Text Detector
- Alert Spam Detector

</td>
</tr>
<tr>
<td width="50%">

**Scam Detectors**
- Show Trading Scam Detector
- CC Spam Detector

</td>
<td width="50%">

**Pattern Detectors**
- Social Media Detector
- Suspicious Pattern Detector

</td>
</tr>
</table>

### Whitelist & Blacklist

#### Whitelist Options
- **Automatic**: Friends and clan members
- **Manual**: Add custom players by name
- **Flexible**: Comma-separated list

#### Blacklist Options
- **Username blocking**: Block specific players
- **Pattern matching**: Use regex for advanced filtering
- **Format examples**:
  ```
  Sp4mmer, B0t123
  regex:.*[0-9]{3}.*
  ```

### Chat Filtering Options

| Feature | Description |
|---------|-------------|
| **Public Chat** | Filter spam in public chat channels |
| **Private Messages** | Apply filtering to direct messages |
| **Overhead Text** | Block spam above player heads |
| **Visual Indicators** | Show `[ - ]` for blocked messages (optional) |

### Statistics Panel

Track your filtering performance in real-time:

- **Total Messages Filtered**: By chat type (public/private/overhead)
- **Detection Breakdown**: See which detectors catch the most spam
- **Performance Metrics**: Filter processing times
- **Auto-Update**: Statistics refresh every 5 seconds

### Customization

```java
// Add your own keywords
selling, buying, noob, scam

// Create custom regex patterns (one per line)
\b(gold|gp)\s+(selling|buying)\b
discord\.gg/[\w-]+

// Username blacklist with regex support
Sp4mmer123
regex:^Bot.*[0-9]+$
```

---

## Installation

### Method 1: RuneLite Plugin Hub (Recommended)

1. Open the **RuneLite** client
2. Click the **Plugin Hub** button (puzzle piece icon) in the sidebar
3. Search for **"Anti-Spam"**
4. Click **Install**
5. Configure settings in the **Anti-Spam** panel

### Method 2: Manual Installation

1. Download or build the plugin JAR file
2. Locate your RuneLite plugins directory:
   - **Windows**: `%USERPROFILE%\.runelite\plugins`
   - **macOS**: `~/.runelite/plugins`
   - **Linux**: `~/.runelite/plugins`
3. Copy the JAR file to the plugins directory
4. Restart RuneLite

---

## Configuration

Access plugin settings through: **RuneLite Settings** → **Anti-Spam**

### Filter Settings

```
┌─────────────────────────────────────┐
│ Filter Settings                     │
├─────────────────────────────────────┤
│ Filter preset:        [Moderate ▼]  │
│ ☑ Filter public chat                │
│ ☑ Filter overhead text              │
│ ☐ Filter private messages           │
└─────────────────────────────────────┘
```

| Setting | Default | Description |
|---------|---------|-------------|
| Filter preset | Moderate | Choose filtering level |
| Filter public chat | ✓ Enabled | Filter public chat messages |
| Filter overhead text | ✓ Enabled | Filter text above player heads |
| Filter private messages | ✗ Disabled | Filter private messages |

### Whitelist Configuration

```
┌─────────────────────────────────────┐
│ Whitelist                           │
├─────────────────────────────────────┤
│ ☑ Whitelist friends                 │
│ ☑ Whitelist clan members            │
│ Custom whitelist:                   │
│ [Player1, Player2, ...]             │
└─────────────────────────────────────┘
```

### Blacklist Configuration

```
┌─────────────────────────────────────┐
│ Blacklist                           │
├─────────────────────────────────────┤
│ Username blacklist:                 │
│ ┌─────────────────────────────────┐ │
│ │ Sp4mmer                         │ │
│ │ B0t123                          │ │
│ │ regex:.*[0-9]{3}.*              │ │
│ └─────────────────────────────────┘ │
└─────────────────────────────────────┘
```

### Detector Configuration

Toggle individual detectors on/off:

| Detector | Default | Detects |
|----------|---------|---------|
| Spaced Text | ✓ | `s e l l i n g  g o l d` |
| Alert Spam | ✓ | `[Beware] Player is scammer` |
| URL Spam | ✓ | `rsgold.com`, `discord.gg/xyz` |
| RWT | ✓ | `Buying tbow 1200m` |
| Gambling | ✓ | `Doubling money 100m max` |
| Show Trading Scam | ✓ | `Show me 900m bank` |
| CC Spam | ✓ | `Join cc SWAP for rates` |
| Social Media | ✓ | `Follow my twitch` |
| Suspicious Pattern | ✓ | `!!!!!!`, excessive spacing |
| Keyword Combo | ✓ | `pls some items` |
| Keyword | ✓ | Preset + custom keywords |

### Advanced Options

```
┌─────────────────────────────────────┐
│ Advanced                            │
├─────────────────────────────────────┤
│ Custom keywords:                    │
│ [selling, buying, scam]             │
│                                     │
│ Custom regex patterns:              │
│ ┌─────────────────────────────────┐ │
│ │ \b(gold|gp)\s+selling\b         │ │
│ │ discord\.gg/[\w-]+              │ │
│ └─────────────────────────────────┘ │
│                                     │
│ ☑ Show [ - ] above heads            │
│ ☐ Show [ - ] in chatbox             │
│ ☐ Show block notifications          │
└─────────────────────────────────────┘
```

---

## Detection System

### Detection Flow

```
Message Received
    ↓
[1] Whitelist Check → Whitelisted? → Allow Message
    ↓ (Not Whitelisted)
[2] Blacklist Check → Blacklisted? → Block Message
    ↓ (Not Blacklisted)
[3] Detector Chain
    ├─ Spaced Text Detector
    ├─ Alert Spam Detector
    ├─ URL Spam Detector
    ├─ RWT Detector
    ├─ Gambling Detector
    ├─ Show Trading Scam Detector
    ├─ CC Spam Detector
    ├─ Social Media Detector
    ├─ Suspicious Pattern Detector
    ├─ Keyword Combo Detector
    ├─ Keyword Detector
    └─ Custom Regex Detector
    ↓
Spam Detected?
    ├─ Yes → Block/Hide Message → Record Statistics
    └─ No  → Allow Message
```

### Performance Characteristics

| Metric | Value |
|--------|-------|
| **Average Filter Time** | < 1ms per message |
| **Memory Overhead** | Minimal (concurrent set for deduplication) |
| **CPU Impact** | Negligible (short-circuit on first match) |
| **Regex Optimization** | Compiled patterns cached |

---

## Test Coverage

The plugin has comprehensive test coverage to ensure reliability and quality:

### Overall Coverage Statistics

| Metric | Coverage | Status |
|--------|----------|--------|
| **Instruction Coverage** | 86% | ✅ Excellent |
| **Branch Coverage** | 82% | ✅ Excellent |
| **Total Tests** | 401 | ✅ All Passing |

### Coverage by Package

| Package | Instruction | Branch | Tests |
|---------|------------|--------|-------|
| **com.antispam.detectors.impl** | 98% | 95% | 298 tests |
| **com.antispam.plugin** | 99% | 92% | 75 tests |
| **com.antispam.keywords** | 78% | 76% | 28 tests |
| **com.antispam.detectors** | 100% | n/a | Interfaces |

### Detector-Specific Coverage

All 12 spam detectors have extensive test coverage:

<table>
<tr>
<td width="50%">

**Perfect Coverage (100%)**
- Alert Spam Detector
- Gambling Detector
- Show Trading Scam Detector
- CC Spam Detector
- Social Media Detector
- Spaced Text Detector

</td>
<td width="50%">

**Excellent Coverage (95%+)**
- RWT Detector (99% / 96%)
- Keyword Spam Detector (98% / 92%)
- Keyword Combo Detector (98% / 94%)
- URL Spam Detector (94% / 84%)
- Custom Regex Detector (100%)
- Suspicious Pattern Detector (100%)

</td>
</tr>
</table>

### Test Categories

| Test Type | Count | Description |
|-----------|-------|-------------|
| **Unit Tests** | 401 | Individual component testing |
| **Detection Tests** | 298 | Spam pattern validation |
| **Integration Tests** | 75 | Component interaction tests |
| **Edge Case Tests** | 28 | Boundary condition handling |

### Running Tests

```bash
# Run all tests
./gradlew test

# Generate coverage report
./gradlew jacocoTestReport

# View HTML coverage report
open build/reports/jacoco/html/index.html
```

### Coverage Notes

- **UrlSpamDetector**: 84% branch coverage is optimal due to pattern check ordering (see code comments)
- **Plugin Package**: Some branches are untestable without RuneLite client mocking
- **Continuous Testing**: All tests pass on every commit

---

## Building from Source

### Prerequisites

| Requirement | Version              |
|-------------|----------------------|
| Java Development Kit (JDK) | 11                   |
| Gradle | Included via wrapper |

### Build Commands

#### Clone Repository
```bash
git clone <repository-url>
cd antispam
```

#### Build Plugin
```bash
# Unix/macOS/Linux
./gradlew build

# Windows
gradlew.bat build
```

#### Run Tests
```bash
# Unix/macOS/Linux
./gradlew test

# Windows
gradlew.bat test
```

#### Output Location
```
build/libs/antispam-1.0.0.jar
```

### Project Structure

```
antispam/
├── src/
│   ├── main/
│   │   └── java/com/antispam/
│   │       ├── AntiBeggarPlugin.java          # Main plugin class
│   │       ├── detectors/                     # Detection modules
│   │       │   ├── impl/                      # Detector implementations
│   │       │   ├── ISpamDetector.java         # Detector interface
│   │       │   └── DetectionResult.java       # Detection result
│   │       ├── keywords/                      # Keyword management
│   │       ├── plugin/                        # Core plugin logic
│   │       └── ui/                            # User interface
│   └── test/
│       └── java/com/antispam/
│           └── MessageFilterTest.java         # Unit tests
├── build.gradle                               # Build configuration
└── runelite-plugin.properties                # Plugin metadata
```

---

## How It Works

### Technical Implementation

#### 1. Message Interception

The plugin hooks into RuneLite's event system:

```java
@Subscribe
public void onScriptCallbackEvent(ScriptCallbackEvent event)
@Subscribe
public void onOverheadTextChanged(OverheadTextChanged event)
```

#### 2. Processing Pipeline

| Step | Action | Performance |
|------|--------|-------------|
| **Receive Message** | Event triggered by RuneLite | Instant |
| **Check Whitelist** | Friends, clan, custom list | O(1) lookup |
| **Check Blacklist** | Username matching | O(1) lookup |
| **Run Detectors** | Chain of responsibility pattern | Short-circuits on match |
| **Apply Action** | Hide or replace message | Instant |
| **Record Stats** | Update filter statistics | O(1) operation |

#### 3. Detector Chain Pattern

Each detector implements the `ISpamDetector` interface:

```java
public interface ISpamDetector {
    DetectionResult detect(String message);
}
```

The chain processes messages sequentially and **stops on first match** for optimal performance.

#### 4. Concurrent Message Tracking

```java
private final Set<Integer> processedMessages = ConcurrentHashMap.newKeySet();
```

Prevents duplicate processing of the same message across multiple events.

---

## Examples

### RWT (Real-World Trading) Detection

| Message | Result |
|---------|--------|
| `Buying tbow 1200m` | **BLOCKED** - RWT keyword |
| `Selling GP 0.50/m paypal` | **BLOCKED** - RWT + payment method |
| `Swapping OSRS to RS3 1:7 ratio` | **BLOCKED** - Gold swapping |
| `Trading my twisted bow for 1.2b` | **ALLOWED** - Legitimate trade |

### Gambling Detection

| Message | Result |
|---------|--------|
| `Doubling money 100m max` | **BLOCKED** - Classic doubling scam |
| `2x your items trust trade` | **BLOCKED** - Trust trade scam |
| `55x2 dice game cc` | **BLOCKED** - Gambling promotion |
| `I doubled my money at Duel Arena` | **ALLOWED** - Past tense conversation |

### Begging Detection

| Message | Result |
|---------|--------|
| `pls some gp I'm new` | **BLOCKED** - Begging keywords |
| `need help with bond plz` | **BLOCKED** - Bond begging |
| `free stuff anyone?` | **BLOCKED** - Free item request |
| `Can you help me with this quest?` | **ALLOWED** - Legitimate help request |

### URL Spam Detection

| Message | Result |
|---------|--------|
| `check out rsgold dot com` | **BLOCKED** - Obfuscated URL |
| `discord.gg/goldtrades` | **BLOCKED** - Discord invite |
| `visit runescape-gold.com for cheap gp` | **BLOCKED** - Direct URL |
| `I posted on reddit about this` | **ALLOWED** - Casual mention |

### Spaced Text Detection

| Message | Result |
|---------|--------|
| `s e l l i n g  g o l d` | **BLOCKED** - Spaced obfuscation |
| `b u y i n g  i t e m s` | **BLOCKED** - Spaced obfuscation |
| `G O L D  4  S A L E` | **BLOCKED** - Spaced + leetspeak |
| `g f for cooking` | **ALLOWED** - Normal abbreviation |

### Social Media Detection

| Message | Result |
|---------|--------|
| `Follow my twitch for giveaways` | **BLOCKED** - Social media promo |
| `Check out my youtube channel` | **BLOCKED** - Social media promo |
| `streaming on twitch.tv/username` | **BLOCKED** - Stream promotion |
| `I saw a funny video on youtube` | **ALLOWED** - Casual conversation |

### Keyword Combo Detection

| Message | Result |
|---------|--------|
| `pls some items` | **BLOCKED** - "pls" + "items" |
| `free gold for noobs` | **BLOCKED** - "free" + "gold" |
| `need gp help plz` | **BLOCKED** - Multiple begging keywords |
| `I need to buy some items` | **ALLOWED** - Legitimate statement |

---

## Contributing

We welcome contributions to improve the Anti-Spam plugin!

### How to Contribute

1. **Fork** the repository
2. **Create** a feature branch
   ```bash
   git checkout -b feature/new-detector
   ```
3. **Make** your changes
   - Add new detectors in `src/main/java/com/antispam/detectors/impl/`
   - Update keywords in `Keywords.java`
   - Add tests in `src/test/java/com/antispam/`
4. **Test** your changes
   ```bash
   ./gradlew test
   ```
5. **Commit** with clear messages
   ```bash
   git commit -m "Add: New detector for X type spam"
   ```
6. **Submit** a pull request

### Contribution Ideas

- Add new spam detection patterns
- Improve existing detector accuracy
- Add support for more languages
- Optimize performance
- Improve documentation
- Add more unit tests

---

## Contact

### Developer Information

You can reach me through the following channels:

#### Discord
- **Username**: `pengyon`
- **Discord ID**: `235034590513725440`
- ⚠️ **Anti-Impersonation**: Please verify the Discord ID if someone claims to be me!

#### Old School RuneScape
- **RSN**: `AlwaysOnOSRS`
- Feel free to PM me in-game for questions or feedback

#### Support the Plugin
- **Ko-fi**: [ko-fi.com/alwaysonosrs](https://ko-fi.com/alwaysonosrs)
- Help keep the plugin updated and maintained!

### Reporting Issues

For bug reports or feature requests, please use the GitHub Issues page with detailed information about:
- Your RuneLite version
- Plugin version
- Steps to reproduce
- Expected vs actual behavior
- Screenshots (if applicable)

---

## Support

### Getting Help

If you encounter issues or have questions:

#### 1. Check Existing Resources
- Review this README
- Check the [Issues](../../issues) page for known problems
- Look at closed issues for solutions

#### 2. Report a Bug

Create a new issue with:
- **RuneLite Version**: (e.g., 1.9.0)
- **Plugin Version**: (e.g., 1.0.0)
- **Description**: Clear explanation of the problem
- **Steps to Reproduce**: How to recreate the issue
- **Expected Behavior**: What should happen
- **Actual Behavior**: What actually happens
- **Screenshots**: If applicable

#### 3. Request a Feature

Create a new issue with:
- **Feature Description**: What you'd like to see
- **Use Case**: Why this feature would be helpful
- **Examples**: Specific scenarios or messages

### Common Issues

| Problem | Solution |
|---------|----------|
| Plugin not appearing | Check Plugin Hub installation, restart RuneLite |
| Not filtering messages | Verify filter preset is not "Custom" with empty keywords |
| Filtering too much | Switch to "Lenient" preset or disable specific detectors |
| Performance issues | Disable unused detectors, simplify custom regex |

---

## License

This project is provided as-is for use with RuneLite. Please check the repository for specific license information.

---

## Acknowledgments

Built for [RuneLite](https://runelite.net/), the open-source OSRS client.

Special thanks to the OSRS community for feedback and support!

---

<div align="center">

### 💬 Contact

**Discord**: `pengyon` (ID: `235034590513725440`)
**OSRS**: `AlwaysOnOSRS`
**Support**: [Ko-fi](https://ko-fi.com/alwaysonosrs)

---

**[⬆ Back to Top](#anti-spam)**

*Made with ❤️ for the OSRS community*

![Test Coverage](https://img.shields.io/badge/tests-401%20passing-brightgreen?style=flat-square)
![Coverage](https://img.shields.io/badge/coverage-86%25-brightgreen?style=flat-square)

</div>
