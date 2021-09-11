package com.tevore.ordersystem.controllers;

import com.tevore.ordersystem.controllers.request.IncomingOrderRequest;
import com.tevore.ordersystem.controllers.response.OrderSummaryResponse;
import com.tevore.ordersystem.services.OrderSystemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@RestController
@Validated
public class OrderSystemController {

    private OrderSystemService orderSystemService;

    @Autowired
    public OrderSystemController(OrderSystemService orderSystemService) {
        this.orderSystemService = orderSystemService;
    }

    @PostMapping("/orders")
    public OrderSummaryResponse acceptIncomingOrder(@RequestBody @Valid @NotNull IncomingOrderRequest request) {
        return orderSystemService.processIncomingOrder(request);
    }
}
