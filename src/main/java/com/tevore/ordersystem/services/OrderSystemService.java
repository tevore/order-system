package com.tevore.ordersystem.services;

import com.tevore.ordersystem.controllers.request.IncomingOrderRequest;
import com.tevore.ordersystem.controllers.request.OrderDetail;
import com.tevore.ordersystem.controllers.response.OrderSummary;
import com.tevore.ordersystem.controllers.response.OrderSummaryResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

//TODO bad cases should likely return an IllegalArgumentException to the client ( if it got this far... )

/**
 * Base service for compiling transactions and processing totals for orders received
 */
@Service
public class OrderSystemService {

    private final Logger LOGGER = LoggerFactory.getLogger(OrderSystemService.class);

    private PromotionService promotionService;
    private OrderQueryService orderQueryService;

    @Autowired
    public OrderSystemService (PromotionService promotionService, OrderQueryService orderQueryService) {
        this.promotionService = promotionService;
        this.orderQueryService = orderQueryService;
    }

    /**
     *
     * Applies additional validations that may not be caught by validation annotations
     * Removes items via filtering that may not fit the actual total
     *
     * @param request
     * @return the response with totals accounted for
     */
    public OrderSummaryResponse processIncomingOrder(IncomingOrderRequest request) {

        List<OrderSummary> orderSummaryList = request.getOrderDetails().stream()
                .filter(Objects::nonNull)
                .filter(this::orderValidator)
                .map(order -> new OrderSummary(order.getName(),
                        order.getQuantity(),
                        order.getCost(),
                        promotionService.validateAndApplyPromotion(order.getCost(), order.getQuantity(), order.getPromotion()),
                        order.getPromotion()))
                .collect(Collectors.toList());

        Optional<BigDecimal> totalOrderCost = orderSummaryList.stream()
                .filter(Objects::nonNull)
                .map(order -> order.getTotalCost())
                . reduce(BigDecimal::add);


        OrderSummaryResponse orderSummaryResponse = new OrderSummaryResponse(UUID.randomUUID().toString(),
                totalOrderCost.orElse(BigDecimal.valueOf(0.00).setScale(2)),
                orderSummaryList);

        boolean successfulSave = orderQueryService.save(orderSummaryResponse);

        return successfulSave ? orderSummaryResponse : new OrderSummaryResponse();
    }

    /**
     * Simple validator util to add redundancy and further check out for unexpected data
     * @param orderDetail
     * @return result of validations
     */
    //TODO see if there is a nicer method of doing this
    private boolean orderValidator(OrderDetail orderDetail) {

        return orderDetail.getQuantity() >= 1 && orderDetail.getCost().compareTo(BigDecimal.ZERO) > 0
                && (orderDetail.getId() != null && orderDetail.getId() > 0) && StringUtils.hasLength(orderDetail.getName());
    }




}
