package com.tevore.ordersystem.services;

import com.tevore.ordersystem.RequestGenerator;
import com.tevore.ordersystem.controllers.response.OrderSummaryResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

public class OrderSystemServiceTest {

    private OrderSystemService orderSystemService;
    private PromotionService promotionService;

    @BeforeEach
    public void setUp() {
        promotionService = new PromotionService();
        orderSystemService = new OrderSystemService(promotionService);
    }

    @Test
    public void shouldProperlyCalculateOrder() {

        OrderSummaryResponse response = orderSystemService.processIncomingOrder(RequestGenerator.generateIncomingOrderRequest());

        assertEquals(BigDecimal.valueOf(3.75), response.getTotalCost());
    }

    @Test
    public void shouldNotAllowForZeroOrNegativeQuantityInOrder() {
        OrderSummaryResponse response = orderSystemService.processIncomingOrder(RequestGenerator.generateIncomingOrderRequestZeroOrNegativeQuantity());

        assertEquals(BigDecimal.valueOf(1.50).setScale(2 ), response.getTotalCost());
    }

    @Test
    public void shouldNotAllowNullItemToRuinOrder() {
        OrderSummaryResponse response = orderSystemService.processIncomingOrder(RequestGenerator.generateIncomingOrderRequestNullItems());

        assertEquals(BigDecimal.valueOf(1.10).setScale(2), response.getTotalCost());
    }

    @Test
    public void shouldNotAllowForBlankNamesOrEmptyIds() {
        OrderSummaryResponse response = orderSystemService.processIncomingOrder(RequestGenerator.generateIncomingOrderNoNameNoId());

        assertEquals(BigDecimal.valueOf(0.0).setScale(2), response.getTotalCost());
    }

}