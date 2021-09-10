package com.tevore.ordersystem;

import com.tevore.ordersystem.controllers.request.IncomingOrderRequest;
import com.tevore.ordersystem.controllers.request.OrderDetail;

import java.math.BigDecimal;
import java.util.Arrays;

public class RequestGenerator {


    public static IncomingOrderRequest generateIncomingOrderRequest() {

        IncomingOrderRequest incomingOrderRequest = new IncomingOrderRequest();

        OrderDetail detail1 = new OrderDetail(5, 1, "apple", BigDecimal.valueOf(0.60));
        OrderDetail detail2 = new OrderDetail(3, 2, "orange", BigDecimal.valueOf(0.25));

        incomingOrderRequest.setOrderDetails(Arrays.asList(detail1, detail2));

        return incomingOrderRequest;
    }

    public static IncomingOrderRequest generateIncomingOrderRequestZeroOrNegativeQuantity() {

        IncomingOrderRequest incomingOrderRequest = new IncomingOrderRequest();

        OrderDetail detail1 = new OrderDetail(0, 1, "apple", BigDecimal.valueOf(0.60));
        OrderDetail detail2 = new OrderDetail(-3, 2, "orange", BigDecimal.valueOf(0.60));
        OrderDetail detail3 = new OrderDetail(2, 3, "banana", BigDecimal.valueOf(0.75));

        incomingOrderRequest.setOrderDetails(Arrays.asList(detail1, detail2, detail3));

        return incomingOrderRequest;
    }

    public static IncomingOrderRequest generateIncomingOrderRequestNullItems() {
        IncomingOrderRequest incomingOrderRequest = new IncomingOrderRequest();

        OrderDetail detail1 = new OrderDetail(1, 1, "apple", BigDecimal.valueOf(0.60));
        OrderDetail detail2 = new OrderDetail(2, 2, "orange", BigDecimal.valueOf(0.25));
        incomingOrderRequest.setOrderDetails(Arrays.asList(detail1, null, detail2));

        return incomingOrderRequest;

    }

    public static IncomingOrderRequest generateIncomingOrderNoNameNoId() {
        IncomingOrderRequest incomingOrderRequest = new IncomingOrderRequest();

        OrderDetail detail1 = new OrderDetail(1, 1, "", BigDecimal.valueOf(0.60));
        OrderDetail detail2 = new OrderDetail(2, 0, "orange", BigDecimal.valueOf(0.25));
        incomingOrderRequest.setOrderDetails(Arrays.asList(detail1, detail2));

        return incomingOrderRequest;
    }
}
