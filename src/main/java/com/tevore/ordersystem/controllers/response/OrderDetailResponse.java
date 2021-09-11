package com.tevore.ordersystem.controllers.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetailResponse {

    private String orderId;
    private LocalDateTime orderDate;
    private String orderSummaryResponse;
}
