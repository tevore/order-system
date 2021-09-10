package com.tevore.ordersystem.controllers.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderSummaryResponse {

    private List<OrderSummary> orderSummary;
    private BigDecimal totalCost;
}
