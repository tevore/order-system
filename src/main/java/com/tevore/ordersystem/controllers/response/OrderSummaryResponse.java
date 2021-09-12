package com.tevore.ordersystem.controllers.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class OrderSummaryResponse {

    private String orderId;
    private BigDecimal totalCost;
    private List<OrderSummary> orderSummary;
}
