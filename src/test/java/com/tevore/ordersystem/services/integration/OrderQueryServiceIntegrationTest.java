package com.tevore.ordersystem.services.integration;

import com.tevore.ordersystem.controllers.response.OrderSummaryResponse;
import com.tevore.ordersystem.models.Order;
import com.tevore.ordersystem.models.repositories.OrderRepository;
import com.tevore.ordersystem.services.OrderQueryService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;

import javax.persistence.EntityNotFoundException;
import java.math.BigDecimal;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class OrderQueryServiceIntegrationTest {

    @Autowired
    public OrderQueryService orderQueryService;

    @Autowired
    public OrderRepository orderRepository;

    @Test
    public void shouldPersistEntity() {

        OrderSummaryResponse orderSummaryResponse = new OrderSummaryResponse();
        orderSummaryResponse.setOrderId("123");
        orderSummaryResponse.setTotalCost(BigDecimal.valueOf(3.75));
        orderSummaryResponse.setOrderSummary(Arrays.asList());
        boolean saved = orderQueryService.save(orderSummaryResponse);

        assertTrue(saved);

    }

    @Test
    public void shouldNotFindEntity() {

        assertThrows(EntityNotFoundException.class, () -> orderQueryService.getOrderById("zzz123344"));

    }

    @Test
    public void shouldReturnPageableSetOfOrders() {

        //to ensure correct number
        orderRepository.deleteAll();

        OrderSummaryResponse orderSummaryResponse1 = new OrderSummaryResponse();
        orderSummaryResponse1.setOrderId("123");
        orderSummaryResponse1.setTotalCost(BigDecimal.valueOf(3.75));
        orderSummaryResponse1.setOrderSummary(Arrays.asList());
        orderQueryService.save(orderSummaryResponse1);

        OrderSummaryResponse orderSummaryResponse2 = new OrderSummaryResponse();
        orderSummaryResponse2.setOrderId("1234");
        orderSummaryResponse2.setTotalCost(BigDecimal.valueOf(3.75));
        orderSummaryResponse2.setOrderSummary(Arrays.asList());
        orderQueryService.save(orderSummaryResponse2);

        OrderSummaryResponse orderSummaryResponse3 = new OrderSummaryResponse();
        orderSummaryResponse3.setOrderId("12345");
        orderSummaryResponse3.setTotalCost(BigDecimal.valueOf(3.75));
        orderSummaryResponse3.setOrderSummary(Arrays.asList());
        orderQueryService.save(orderSummaryResponse3);
        
        Page<Order> orderPage = orderQueryService.getAllOrders(0, 5);
        
        
        assertEquals(3, orderPage.getTotalElements());
        assertEquals(1, orderPage.getTotalPages());

    }

}
