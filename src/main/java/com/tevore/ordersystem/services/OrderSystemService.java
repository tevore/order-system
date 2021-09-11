package com.tevore.ordersystem.services;

import com.tevore.ordersystem.controllers.request.IncomingOrderRequest;
import com.tevore.ordersystem.controllers.request.OrderDetail;
import com.tevore.ordersystem.controllers.response.OrderSummary;
import com.tevore.ordersystem.controllers.response.OrderSummaryResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

//TODO bad cases should likely return an IllegalArgumentException to the client ( if it got this far... )

/**
 * Base service for compiling transactions and processing totals for orders received
 */
@Service
public class OrderSystemService {

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


        OrderSummaryResponse orderSummaryResponse = new OrderSummaryResponse(orderSummaryList,
                totalOrderCost.orElse(BigDecimal.valueOf(0.00).setScale(2)));

        orderQueryService.save(orderSummaryResponse);

        return orderSummaryResponse;

    }

    /**
     * Simple validator util to add redundancy and further check out for unexpected data
     * @param orderDetail
     * @return result of validations
     */
    //TODO see if there is a nicer method of doing this
    private boolean orderValidator(OrderDetail orderDetail) {

        if(orderDetail.getQuantity() < 1) {
            return false;
        } else if(orderDetail.getCost().compareTo(BigDecimal.ZERO) <= 0) {
            return false;
        } else if(orderDetail.getId() == null || orderDetail.getId() <= 0) {
            return false;
        } else if(!StringUtils.hasLength(orderDetail.getName())) {
            return false;
        }

        return true;
    }




}
