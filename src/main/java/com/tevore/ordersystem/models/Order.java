package com.tevore.ordersystem.models;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.tevore.ordersystem.controllers.response.OrderSummaryResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "orders")
public class Order {

    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    @Column(name = "created_timestamp")
    private LocalDateTime createdTimestamp;

    @Column(name = "order_summary")
    private String orderSummaryJson;

}
