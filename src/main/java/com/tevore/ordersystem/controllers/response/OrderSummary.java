package com.tevore.ordersystem.controllers.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class OrderSummary {

    private String name;
    private Integer quantity;
    private BigDecimal itemCost;
    private BigDecimal totalCost;
}
