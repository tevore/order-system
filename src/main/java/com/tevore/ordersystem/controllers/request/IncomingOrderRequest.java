package com.tevore.ordersystem.controllers.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Validated
public class IncomingOrderRequest {

    @NotNull(message = "Order details must not be null")
    @NotEmpty(message = "Order details must not be empty")
    @Valid
    private List<OrderDetail> orderDetails;
}
