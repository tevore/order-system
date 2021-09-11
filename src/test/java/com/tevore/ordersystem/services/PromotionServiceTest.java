package com.tevore.ordersystem.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PromotionServiceTest {

    private PromotionService promotionService;

    private BigDecimal cost;

    @BeforeEach
    public void setUp() {
        promotionService = new PromotionService();
        cost = BigDecimal.valueOf(1).setScale(2);
    }

    @Test
    public void shouldApplyCorrectBOGOPromotionAndReturnCorrectAmountWithEvenQuantity() {
        BigDecimal promoAmount = promotionService.validateAndApplyPromotion(cost, 4, "bogo");

        assertEquals(BigDecimal.valueOf(2).setScale(2), promoAmount);
    }

    @Test
    public void shouldApplyCorrectBOGOPromotionAndReturnCorrectAmountWithOddQuantity() {
        BigDecimal promoAmount = promotionService.validateAndApplyPromotion(cost, 5, "bogo");

        assertEquals(BigDecimal.valueOf(3).setScale(2), promoAmount);
    }

    @Test
    public void shouldApplyCorrectB2GOPromotionAndReturnCorrectAmount() {
        BigDecimal promoAmount = promotionService.validateAndApplyPromotion(cost, 4, "b2go");

        assertEquals(BigDecimal.valueOf(3).setScale(2), promoAmount);
    }

    @Test
    public void shouldNotApplyInvalidPromotionAndReturnCorrectAmount() {
        BigDecimal promoAmount = promotionService.validateAndApplyPromotion(cost, 4, "funky");

        assertEquals(BigDecimal.valueOf(4).setScale(2), promoAmount);
    }


    @Test
    public void shouldIgnoreNullPromotionAndReturnCorrectAmount() {
        BigDecimal promoAmount = promotionService.validateAndApplyPromotion(cost, 4, null);

        assertEquals(BigDecimal.valueOf(4).setScale(2), promoAmount);
    }

    //TODO useless test?
    @Test
    public void shouldIgnoreNullPromotionAndReturnCorrectNegativeAmount() {
        BigDecimal promoAmount = promotionService.validateAndApplyPromotion(cost.negate(), 4, null);

        assertEquals(BigDecimal.valueOf(4).setScale(2).negate(), promoAmount);
    }


}
