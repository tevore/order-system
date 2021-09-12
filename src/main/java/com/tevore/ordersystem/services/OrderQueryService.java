package com.tevore.ordersystem.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tevore.ordersystem.controllers.response.OrderDetailResponse;
import com.tevore.ordersystem.controllers.response.OrderSummaryResponse;
import com.tevore.ordersystem.models.Order;
import com.tevore.ordersystem.models.repositories.OrderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class OrderQueryService {

    private final Logger LOGGER = LoggerFactory.getLogger(OrderQueryService.class);

    private OrderRepository orderRepository;
    private ObjectMapper objectMapper;

    @Autowired
    public OrderQueryService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
        this.objectMapper = new ObjectMapper();
    }

    public OrderDetailResponse getOrderById(String orderId) {

        Order order = orderRepository.findById(orderId).orElseThrow(EntityNotFoundException::new);
        return new OrderDetailResponse(order.getId(), order.getCreatedTimestamp(), order.getOrderSummaryJson());
    }

    public Page<Order> getAllOrders(int page, int size) {

        return orderRepository.findAll(PageRequest.of(page, size));
    }

    public boolean save(OrderSummaryResponse orderSummaryResponse) {

        try {
            String orderSummaryJson = objectMapper.writeValueAsString(orderSummaryResponse);
            Order orderEntity = orderRepository.save(new Order(orderSummaryResponse.getOrderId(), LocalDateTime.now(), orderSummaryJson));
            return Optional.ofNullable(orderEntity).isPresent();
        } catch(JsonProcessingException jpe) {
            return false;
        }
    }
}
