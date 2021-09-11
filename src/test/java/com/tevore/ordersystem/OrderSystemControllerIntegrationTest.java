package com.tevore.ordersystem;

import com.tevore.ordersystem.controllers.OrderSystemController;
import com.tevore.ordersystem.controllers.request.IncomingOrderRequest;
import com.tevore.ordersystem.controllers.response.OrderSummaryResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import javax.validation.ConstraintViolationException;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class OrderSystemControllerIntegrationTest {

    @Autowired
    private OrderSystemController orderSystemController;

    @Test
    public void shouldReturnOrderSummary() {
        OrderSummaryResponse response = 
                orderSystemController.acceptIncomingOrder(RequestGeneratorStub.generateIncomingOrderRequest());
        
        assertEquals(BigDecimal.valueOf(3.75), response.getTotalCost());
    }

    @Test
    public void shouldFailDueToConstraintViolations() {

    List<IncomingOrderRequest> badRequests = Arrays.asList(
                RequestGeneratorStub.generateIncomingOrderNegativeOrZeroCost(),
                RequestGeneratorStub.generateIncomingOrderNoNameNoId(),
                RequestGeneratorStub.generateIncomingOrderRequestZeroOrNegativeQuantity(),
                RequestGeneratorStub.generateIncomingOrderNullList(),
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
                orderSystemController.acceptIncomingOrder(RequestGeneratorStub.generateIncomingOrderRequestWithPromotions());

        assertEquals(BigDecimal.valueOf(6.45), response.getTotalCost());
    }
}
