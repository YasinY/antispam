package com.antispam.detectors;

import com.antispam.detectors.impl.SocialMediaDetector;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class SocialMediaDetectorTest {

    private SocialMediaDetector detector;

    @Before
    public void setUp() {
        detector = new SocialMediaDetector();
    }

    @Test
    public void testYoutubeCode() {
        DetectionResult result = detector.detect("search abc123 on youtube", "");
        assertTrue(result.isDetected());
        assertEquals("social: youtube-code", result.getKeyword());
    }

    @Test
    public void testYoutubeCodeVariation() {
        DetectionResult result = detector.detect("xyz999 on y0utube", "");
        assertTrue(result.isDetected());
        assertEquals("social: youtube-code", result.getKeyword());
    }

    @Test
    public void testSearchYoutube() {
        DetectionResult result = detector.detect("search my channel on youtube", "");
        assertTrue(result.isDetected());
        assertEquals("social: search", result.getKeyword());
    }

    @Test
    public void testWatchOnYoutube() {
        DetectionResult result = detector.detect("watch this video on youtube", "");
        assertTrue(result.isDetected());
        assertEquals("social: search", result.getKeyword());
    }

    @Test
    public void testCheckOutTwitch() {
        DetectionResult result = detector.detect("check out my stream on twitch", "");
        assertTrue(result.isDetected());
        assertEquals("social: search", result.getKeyword());
    }

    @Test
    public void testVisitFacebook() {
        DetectionResult result = detector.detect("visit my facebook page", "");
        assertTrue(result.isDetected());
        assertEquals("social: search", result.getKeyword());
    }

    @Test
    public void testLookOnTwitter() {
        DetectionResult result = detector.detect("look for me on twitter", "");
        assertTrue(result.isDetected());
        assertEquals("social: search", result.getKeyword());
    }

    @Test
    public void testDiscordVariation() {
        DetectionResult result = detector.detect("discörd gg giveaway", "");
        assertTrue(result.isDetected());
        assertEquals("social: discord-variation", result.getKeyword());
    }

    @Test
    public void testDiscordJoin() {
        DetectionResult result = detector.detect("discord dot join us", "");
        assertTrue(result.isDetected());
        assertEquals("social: discord-variation", result.getKeyword());
    }

    @Test
    public void testGiveawaySocial() {
        DetectionResult result = detector.detect("giveaway abc123 for details", "");
        assertTrue(result.isDetected());
        assertEquals("social: giveaway", result.getKeyword());
    }

    @Test
    public void testWinGiveaway() {
        DetectionResult result = detector.detect("win big xyz456", "");
        assertTrue(result.isDetected());
        assertEquals("social: giveaway", result.getKeyword());
    }

    @Test
    public void testVideoInstructions() {
        DetectionResult result = detector.detect("watch the video for instructions", "");
        assertTrue(result.isDetected());
        assertEquals("social: instructions", result.getKeyword());
    }

    @Test
    public void testFollowSteps() {
        DetectionResult result = detector.detect("follow these steps in video", "");
        assertTrue(result.isDetected());
        assertEquals("social: instructions", result.getKeyword());
    }

    @Test
    public void testDontSearch() {
        DetectionResult result = detector.detect("dont search youtube", "");
        assertTrue(result.isDetected());
        assertTrue(result.getKeyword().contains("social"));
    }

    @Test
    public void testDoNotSearchKeyword() {
        DetectionResult result = detector.detect("dont search giveaway", "");
        assertTrue(result.isDetected());
        assertTrue(result.getKeyword().contains("social"));
    }

    @Test
    public void testNormalConversation() {
        DetectionResult result = detector.detect("I saw this on reddit", "");
        assertFalse(result.isDetected());
    }

    @Test
    public void testNormalYoutubeMention() {
        DetectionResult result = detector.detect("I learned this from a youtube guide", "");
        assertFalse(result.isDetected());
    }

    @Test
    public void testNullText() {
        DetectionResult result = detector.detect(null, "");
        assertFalse(result.isDetected());
    }

    @Test
    public void testEmptyText() {
        DetectionResult result = detector.detect("", "");
        assertFalse(result.isDetected());
    }

    @Test
    public void testCaseInsensitive() {
        DetectionResult result = detector.detect("SEARCH ABC123 ON YOUTUBE", "");
        assertTrue(result.isDetected());
    }

    @Test
    public void testInstagramPromotion() {
        DetectionResult result = detector.detect("check out my instagram profile", "");
        assertTrue(result.isDetected());
        assertTrue(result.getKeyword().contains("social"));
    }

    @Test
    public void testYoutubeObfuscated() {
        DetectionResult result = detector.detect("abc123 on y0utube", "");
        assertTrue(result.isDetected());
        assertTrue(result.getKeyword().contains("social"));
    }
}
