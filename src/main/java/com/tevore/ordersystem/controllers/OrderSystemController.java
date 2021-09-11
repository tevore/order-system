package com.tevore.ordersystem.controllers;

import com.tevore.ordersystem.controllers.request.IncomingOrderRequest;
import com.tevore.ordersystem.controllers.response.OrderDetailResponse;
import com.tevore.ordersystem.controllers.response.OrderSummaryResponse;
import com.tevore.ordersystem.services.OrderQueryService;
import com.tevore.ordersystem.services.OrderSystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@RestController
@Validated
public class OrderSystemController {

    private OrderSystemService orderSystemService;
    private OrderQueryService orderQueryService;

    @Autowired
    public OrderSystemController(OrderSystemService orderSystemService, OrderQueryService orderQueryService) {
        this.orderSystemService = orderSystemService;
        this.orderQueryService = orderQueryService;
    }

    @PostMapping(value = "/orders",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public OrderSummaryResponse acceptIncomingOrder(@RequestBody @Valid @NotNull IncomingOrderRequest request) {
        return orderSystemService.processIncomingOrder(request);
    }

    @GetMapping(value = "/orders/{orderId}")
    public OrderDetailResponse getOrderById(@PathVariable String orderId) {
        return orderQueryService.getOrderById(orderId);
    }
}
