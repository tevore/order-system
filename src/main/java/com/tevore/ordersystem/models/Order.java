package com.tevore.ordersystem.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "orders")
public class Order {

    @Id
    private String id;

    @Column(name = "created_timestamp")
    private LocalDateTime createdTimestamp;

    @Column(name = "order_summary")
    @Lob
    private String orderSummaryJson;

}
