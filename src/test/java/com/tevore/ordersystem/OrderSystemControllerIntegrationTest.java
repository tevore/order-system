package com.tevore.ordersystem;

import com.tevore.ordersystem.controllers.OrderSystemController;
import com.tevore.ordersystem.controllers.request.IncomingOrderRequest;
import com.tevore.ordersystem.controllers.response.OrderDetailResponse;
import com.tevore.ordersystem.controllers.response.OrderSummaryResponse;
import com.tevore.ordersystem.models.Order;
import com.tevore.ordersystem.util.TestDataGeneratorStub;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;

import javax.persistence.EntityNotFoundException;
import javax.validation.ConstraintViolationException;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class OrderSystemControllerIntegrationTest {

    @Autowired
    private OrderSystemController orderSystemController;

    @Test
    public void shouldReturnOrderSummary() {
        OrderSummaryResponse response = 
                orderSystemController.acceptIncomingOrder(TestDataGeneratorStub.generateIncomingOrderRequest());
        
        assertEquals(BigDecimal.valueOf(3.75), response.getTotalCost());
        assertNotNull(response.getOrderId());
    }

    @Test
    public void shouldFailDueToConstraintViolations() {

    List<IncomingOrderRequest> badRequests = Arrays.asList(
                TestDataGeneratorStub.generateIncomingOrderNegativeOrZeroCost(),
                TestDataGeneratorStub.generateIncomingOrderNoNameNoId(),
                TestDataGeneratorStub.generateIncomingOrderRequestZeroOrNegativeQuantity(),
                TestDataGeneratorStub.generateIncomingOrderNullList(),
                null);

        Optional<Integer> constraintCount = badRequests.stream()
                .map(x -> assertThrows(ConstraintViolationException.class, () ->
                        orderSystemController.acceptIncomingOrder(x)).getConstraintViolations().size())
                .reduce(Integer::sum);

       assertEquals(8, constraintCount.get());
    }

    @Test
    public void shouldCorrectlyApplyPromotionAmountsInRequest() {
        OrderSummaryResponse response =
                orderSystemController.acceptIncomingOrder(TestDataGeneratorStub.generateIncomingOrderRequestWithPromotions());

        assertEquals(BigDecimal.valueOf(6.45), response.getTotalCost());
    }

    @Test
    public void shouldRetrieveCorrectOrderId() {

        OrderSummaryResponse response =
                orderSystemController.acceptIncomingOrder(TestDataGeneratorStub.generateIncomingOrderRequest());

        assertEquals(BigDecimal.valueOf(3.75), response.getTotalCost());


        OrderDetailResponse orderDetailResponse = orderSystemController.getOrderById(response.getOrderId());

        assertNotNull(orderDetailResponse.getOrderSummaryResponse());
    }

    @Test
    public void shouldFailToRetrieveId() {
        assertThrows(EntityNotFoundException.class, () -> orderSystemController.getOrderById("123"));
    }

    @Test
    public void shouldRetrieveAllPersistedOrder() {

        Page<Order> orders = orderSystemController.getAllOrders(0, 5);

        assertEquals(2, orders.getTotalElements());
        assertEquals(1, orders.getTotalPages());

    }
}
