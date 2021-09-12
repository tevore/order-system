package com.tevore.ordersystem.services.integration;

import com.tevore.ordersystem.util.TestDataGeneratorStub;
import com.tevore.ordersystem.controllers.response.OrderSummaryResponse;
import com.tevore.ordersystem.models.repositories.OrderRepository;
import com.tevore.ordersystem.services.OrderQueryService;
import com.tevore.ordersystem.services.OrderSystemService;
import com.tevore.ordersystem.services.PromotionService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class OrderSystemServiceIntegrationTest {

    @Autowired
    private OrderSystemService orderSystemService;

    @Autowired
    private PromotionService promotionService;

    @Autowired
    private OrderQueryService orderQueryService;

    @Autowired
    private OrderRepository orderRepository;

    @Test
    public void shouldProperlyCalculateOrder() {

        OrderSummaryResponse response = orderSystemService.processIncomingOrder(TestDataGeneratorStub.generateIncomingOrderRequest());

        assertEquals(BigDecimal.valueOf(3.75), response.getTotalCost());
    }

    @Test
    public void shouldNotAllowForZeroOrNegativeQuantityInOrder() {

        OrderSummaryResponse response = orderSystemService.processIncomingOrder(TestDataGeneratorStub.generateIncomingOrderRequestZeroOrNegativeQuantity());

        assertEquals(BigDecimal.valueOf(1.50).setScale(2), response.getTotalCost());
    }

    @Test
    public void shouldNotAllowNullItemToRuinOrder() {

        OrderSummaryResponse response = orderSystemService.processIncomingOrder(TestDataGeneratorStub.generateIncomingOrderRequestNullItems());

        assertEquals(BigDecimal.valueOf(1.10).setScale(2), response.getTotalCost());
    }

    @Test
    public void shouldNotAllowForBlankNamesOrEmptyIds() {

        OrderSummaryResponse response = orderSystemService.processIncomingOrder(TestDataGeneratorStub.generateIncomingOrderNoNameNoId());

        assertEquals(BigDecimal.valueOf(0.0).setScale(2), response.getTotalCost());
    }

    @Test
    public void shouldNotAllowForZeroOrNegativeCosts() {

        OrderSummaryResponse response = orderSystemService.processIncomingOrder(TestDataGeneratorStub.generateIncomingOrderNegativeOrZeroCost());

        assertEquals(BigDecimal.valueOf(0.0).setScale(2), response.getTotalCost());
    }

}
