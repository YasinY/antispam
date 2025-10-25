package com.antispam.detectors;

import com.antispam.detectors.impl.RWTDetector;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class RWTDetectorTest {

    private RWTDetector detector;

    @Before
    public void setUp() {
        detector = new RWTDetector();
    }

    @Test
    public void testAbsurdAmountBillions() {
        DetectionResult result = detector.detect("selling 50b gold", "");
        assertTrue(result.isDetected());
        assertEquals("rwt: absurd-amount", result.getKeyword());
    }

    @Test
    public void testAbsurdAmountBil() {
        DetectionResult result = detector.detect("have 200 bil for sale", "");
        assertTrue(result.isDetected());
        assertEquals("rwt: absurd-amount", result.getKeyword());
    }

    @Test
    public void testBankSaleWithPercentage() {
        DetectionResult result = detector.detect("selling your bank for 10% extra", "");
        assertTrue(result.isDetected());
        assertEquals("rwt: bank-sale", result.getKeyword());
    }

    @Test
    public void testBuyingBankWithPercentage() {
        DetectionResult result = detector.detect("buying bank 15%", "");
        assertTrue(result.isDetected());
        assertEquals("rwt: bank-sale", result.getKeyword());
    }

    @Test
    public void testHighValueItemTradeTwistedBow() {
        DetectionResult result = detector.detect("buying twisted bow 1200m", "");
        assertTrue(result.isDetected());
        assertEquals("rwt: high-value-item", result.getKeyword());
    }

    @Test
    public void testHighValueItemTradeTbow() {
        DetectionResult result = detector.detect("selling tbow 1100m", "");
        assertTrue(result.isDetected());
        assertEquals("rwt: high-value-item", result.getKeyword());
    }

    @Test
    public void testHighValueItemTradeElysian() {
        DetectionResult result = detector.detect("buying elysian 800m", "");
        assertTrue(result.isDetected());
        assertEquals("rwt: high-value-item", result.getKeyword());
    }

    @Test
    public void testHighValueItemTradeScythe() {
        DetectionResult result = detector.detect("buying scythe for 2500m", "");
        assertTrue(result.isDetected());
        assertEquals("rwt: high-value-item", result.getKeyword());
    }

    @Test
    public void testHighValueItemTradeRapier() {
        DetectionResult result = detector.detect("selling ghrazi rapier 300m", "");
        assertTrue(result.isDetected());
        assertEquals("rwt: high-value-item", result.getKeyword());
    }

    @Test
    public void testHighValueItemTradeDragonClaws() {
        DetectionResult result = detector.detect("buying dragon claws 100m", "");
        assertTrue(result.isDetected());
        assertEquals("rwt: high-value-item", result.getKeyword());
    }

    @Test
    public void testHighValueItemTrade3rdAge() {
        DetectionResult result = detector.detect("selling 3rd age platebody 500m", "");
        assertTrue(result.isDetected());
        assertEquals("rwt: high-value-item", result.getKeyword());
    }

    @Test
    public void testQuickSale() {
        DetectionResult result = detector.detect("someone sell me gold quick", "");
        assertTrue(result.isDetected());
        assertEquals("rwt: urgent-trade", result.getKeyword());
    }

    @Test
    public void testQuickBuy() {
        DetectionResult result = detector.detect("anyone buy my items asap", "");
        assertTrue(result.isDetected());
        assertEquals("rwt: urgent-trade", result.getKeyword());
    }

    @Test
    public void testPayExtra() {
        DetectionResult result = detector.detect("paying extra buying tbow 1300m", "");
        assertTrue(result.isDetected());
        assertTrue(result.getKeyword().contains("rwt"));
    }

    @Test
    public void testWillPayALot() {
        DetectionResult result = detector.detect("will pay alot buying scythe 2600m", "");
        assertTrue(result.isDetected());
        assertTrue(result.getKeyword().contains("rwt"));
    }

    @Test
    public void testAnyoneHaveItem() {
        DetectionResult result = detector.detect("anyone have twisted bow? buying 1200m", "");
        assertTrue(result.isDetected());
        assertTrue(result.getKeyword().contains("rwt"));
    }

    @Test
    public void testNoOneHave() {
        DetectionResult result = detector.detect("no one have elysian? buying 800m", "");
        assertTrue(result.isDetected());
        assertTrue(result.getKeyword().contains("rwt"));
    }

    @Test
    public void testHelloBuying() {
        DetectionResult result = detector.detect("hello buying gold", "");
        assertTrue(result.isDetected());
        assertEquals("rwt: hello-buying", result.getKeyword());
    }

    @Test
    public void testHelloSelling() {
        DetectionResult result = detector.detect("helloooo selling items", "");
        assertTrue(result.isDetected());
        assertEquals("rwt: hello-buying", result.getKeyword());
    }

    @Test
    public void testNormalTrade() {
        DetectionResult result = detector.detect("trading my dharoks set for bandos", "");
        assertFalse(result.isDetected());
    }

    @Test
    public void testNormalConversation() {
        DetectionResult result = detector.detect("I just got a twisted bow drop!", "");
        assertFalse(result.isDetected());
    }

    @Test
    public void testLegitPriceCheck() {
        DetectionResult result = detector.detect("how much is scythe worth?", "");
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
        DetectionResult result = detector.detect("BUYING TWISTED BOW 1200M", "");
        assertTrue(result.isDetected());
    }

    @Test
    public void testMisspelledHighValueItem() {
        DetectionResult result = detector.detect("buying ghrazier rapier 300m", "");
        assertTrue(result.isDetected());
        assertEquals("rwt: high-value-item", result.getKeyword());
    }

    @Test
    public void testShortHandItems() {
        DetectionResult result = detector.detect("buying dwh 60m", "");
        assertTrue(result.isDetected());
        assertEquals("rwt: high-value-item", result.getKeyword());
    }

    // Additional tests for missing branch coverage
    @Test
    public void testPayExtraWithoutBuyingPattern() {
        DetectionResult result = detector.detect("paying extra for items", "");
        assertFalse(result.isDetected());
    }

    @Test
    public void testBuyingPatternWithoutPayExtra() {
        DetectionResult result = detector.detect("buying items 100m", "");
        assertFalse(result.isDetected());
    }

    @Test
    public void testPayExtraAndBuyingPattern() {
        DetectionResult result = detector.detect("paying extra buying items 100m", "");
        assertTrue(result.isDetected());
        assertEquals("rwt: pay-extra", result.getKeyword());
    }

    @Test
    public void testAnyoneHaveWithoutHighValueItem() {
        DetectionResult result = detector.detect("anyone have some items?", "");
        assertFalse(result.isDetected());
    }

    @Test
    public void testHighValueItemWithoutAnyoneHave() {
        DetectionResult result = detector.detect("twisted bow is expensive", "");
        assertFalse(result.isDetected());
    }

    @Test
    public void testAnyoneHaveWithHighValueItem() {
        DetectionResult result = detector.detect("anyone have twisted bow buying 1200m", "");
        assertTrue(result.isDetected());
        assertEquals("rwt: high-value-item", result.getKeyword());
    }

    @Test
    public void testSomeoneHaveWithHighValueItem() {
        DetectionResult result = detector.detect("someone have elysian? buying 800m", "");
        assertTrue(result.isDetected());
        assertEquals("rwt: high-value-item", result.getKeyword());
    }

    @Test
    public void testHighValueItemWithoutPrice() {
        DetectionResult result = detector.detect("buying twisted bow", "");
        assertFalse(result.isDetected());
    }

    @Test
    public void testPayAlotBuying() {
        DetectionResult result = detector.detect("will pay alot buying tbow 1300m", "");
        assertTrue(result.isDetected());
        assertEquals("rwt: high-value-item", result.getKeyword());
    }

    @Test
    public void testPayMuchBuying() {
        DetectionResult result = detector.detect("paying much buying scythe 2500m", "");
        assertTrue(result.isDetected());
        assertEquals("rwt: high-value-item", result.getKeyword());
    }
}
