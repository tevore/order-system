package com.tevore.ordersystem.services;

import com.tevore.ordersystem.controllers.response.OrderDetailResponse;
import com.tevore.ordersystem.controllers.response.OrderSummaryResponse;
import com.tevore.ordersystem.models.Order;
import com.tevore.ordersystem.models.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class OrderQueryService {

    private OrderRepository orderRepository;

    @Autowired
    public OrderQueryService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public OrderDetailResponse getOrderById(String orderId) {

        Optional<Order> optOrder = orderRepository.findById(orderId);
        return new OrderDetailResponse(optOrder.get().getId(), optOrder.get().getCreatedTimestamp(), optOrder.get().getOrderSummaryJson());
    }

    public void save(OrderSummaryResponse orderSummaryResponse) {

        orderRepository.save(new Order(UUID.randomUUID().toString(), LocalDateTime.now(), orderSummaryResponse.toString()));
    }
}
