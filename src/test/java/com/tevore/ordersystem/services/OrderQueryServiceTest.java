package com.tevore.ordersystem.services;

import com.tevore.ordersystem.controllers.response.OrderDetailResponse;
import com.tevore.ordersystem.models.repositories.OrderRepository;
import com.tevore.ordersystem.util.TestDataGeneratorStub;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class OrderQueryServiceTest {

    @Mock
    private OrderRepository orderRepositoryMock;

    @InjectMocks
    private OrderQueryService orderQueryService;

    @Test
    public void shouldFindByOrderId() {
        when(orderRepositoryMock.findById(any())).thenReturn(Optional.of(TestDataGeneratorStub.generateOrder()));

        OrderDetailResponse orderDetailResponse = orderQueryService.getOrderById("123");

        assertEquals("123", orderDetailResponse.getOrderId());
        assertEquals("{\"some_key\":\"some_value\"}", orderDetailResponse.getOrderSummaryResponse());
    }

    @Test
    public void shouldThrowEntityNotFoundException() {

        assertThrows(EntityNotFoundException.class, () -> {
            orderQueryService.getOrderById("zz113");
        });
    }
}
