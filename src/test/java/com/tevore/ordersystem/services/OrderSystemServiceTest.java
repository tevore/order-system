package com.tevore.ordersystem.services;

import com.tevore.ordersystem.RequestGeneratorStub;
import com.tevore.ordersystem.controllers.response.OrderSummaryResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class OrderSystemServiceTest {

    private OrderSystemService orderSystemService;

    @Mock
    private PromotionService promotionService;

    @Mock
    private OrderQueryService orderQueryService;

    @BeforeEach
    public void setUp() {
        orderSystemService = new OrderSystemService(promotionService, orderQueryService);
    }

    @Test
    public void shouldProperlyCalculateOrder() {

        when(promotionService.validateAndApplyPromotion(BigDecimal.valueOf(0.60),5,null)).thenReturn(BigDecimal.valueOf(3.00).setScale(2));
        when(promotionService.validateAndApplyPromotion(BigDecimal.valueOf(0.25),3,null)).thenReturn(BigDecimal.valueOf(0.75).setScale(2));
        when(orderQueryService.save(any())).thenReturn(true);
        OrderSummaryResponse response = orderSystemService.processIncomingOrder(RequestGeneratorStub.generateIncomingOrderRequest());

        assertEquals(BigDecimal.valueOf(3.75), response.getTotalCost());
    }

    @Test
    public void shouldNotAllowForZeroOrNegativeQuantityInOrder() {

        when(promotionService.validateAndApplyPromotion(any(),any(),any())).thenReturn(BigDecimal.valueOf(1.50).setScale(2));
        when(orderQueryService.save(any())).thenReturn(true);
        OrderSummaryResponse response = orderSystemService.processIncomingOrder(RequestGeneratorStub.generateIncomingOrderRequestZeroOrNegativeQuantity());

        assertEquals(BigDecimal.valueOf(1.50).setScale(2), response.getTotalCost());
    }

    @Test
    public void shouldNotAllowNullItemToRuinOrder() {
        when(promotionService.validateAndApplyPromotion(BigDecimal.valueOf(0.60),1, null)).thenReturn(BigDecimal.valueOf(0.60).setScale(2));
        when(promotionService.validateAndApplyPromotion(BigDecimal.valueOf(0.25),2, null)).thenReturn(BigDecimal.valueOf(0.50).setScale(2));
        when(orderQueryService.save(any())).thenReturn(true);
        OrderSummaryResponse response = orderSystemService.processIncomingOrder(RequestGeneratorStub.generateIncomingOrderRequestNullItems());

        assertEquals(BigDecimal.valueOf(1.10).setScale(2), response.getTotalCost());
    }

    @Test
    public void shouldNotAllowForBlankNamesOrEmptyIds() {
        when(orderQueryService.save(any())).thenReturn(true);
        OrderSummaryResponse response = orderSystemService.processIncomingOrder(RequestGeneratorStub.generateIncomingOrderNoNameNoId());

        assertEquals(BigDecimal.valueOf(0.0).setScale(2), response.getTotalCost());
    }

    @Test
    public void shouldNotAllowForZeroOrNegativeCosts() {
        when(orderQueryService.save(any())).thenReturn(true);
        OrderSummaryResponse response = orderSystemService.processIncomingOrder(RequestGeneratorStub.generateIncomingOrderNegativeOrZeroCost());

        assertEquals(BigDecimal.valueOf(0.0).setScale(2), response.getTotalCost());
    }

}