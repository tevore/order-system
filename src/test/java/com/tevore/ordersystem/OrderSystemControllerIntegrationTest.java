package com.tevore.ordersystem;

import com.tevore.ordersystem.controllers.OrderSystemController;
import com.tevore.ordersystem.controllers.response.OrderSummaryResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import javax.validation.ConstraintViolationException;
import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class OrderSystemControllerIntegrationTest {

    @Autowired
    private OrderSystemController orderSystemController;

    @Test
    public void testController() {
        OrderSummaryResponse response = 
                orderSystemController.acceptIncomingOrder(RequestGenerator.generateIncomingOrderRequest());
        
        assertEquals(BigDecimal.valueOf(3.75), response.getTotalCost());
    }

    @Test
    public void testController2() {

        assertThrows(ConstraintViolationException.class, () ->
                orderSystemController.acceptIncomingOrder(RequestGenerator.generateIncomingOrderRequestZeroOrNegativeQuantity()));
    }
}
