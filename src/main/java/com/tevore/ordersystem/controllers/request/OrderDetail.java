package com.tevore.ordersystem.controllers.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.*;
import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderDetail {

    @NotNull(message = "Quantity should not be null")
    @Min(value = 1, message = "Quantity must be at least 1")
    private Integer quantity;

    @NotNull(message = "Id must not be null")
    private Integer id;

    @NotNull(message = "Name must not be null")
    @NotBlank(message = "Name must not be blank")
    private String name;

    @NotNull(message = "Cost must not be null")
    @Positive(message = "Cost must be greater than 0")
    private BigDecimal cost;

    private String promotion;
}
