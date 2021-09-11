package com.tevore.ordersystem;

import com.tevore.ordersystem.controllers.request.IncomingOrderRequest;
import com.tevore.ordersystem.controllers.request.OrderDetail;
import org.aspectj.weaver.ast.Or;

import javax.persistence.criteria.CriteriaBuilder;
import java.math.BigDecimal;
import java.util.Arrays;

public class RequestGeneratorStub {


    public static IncomingOrderRequest generateIncomingOrderRequest() {

        IncomingOrderRequest incomingOrderRequest = new IncomingOrderRequest();

        OrderDetail detail1 = new OrderDetail(5, 1, "apple", BigDecimal.valueOf(0.60), null);
        OrderDetail detail2 = new OrderDetail(3, 2, "orange", BigDecimal.valueOf(0.25), null);

        incomingOrderRequest.setOrderDetails(Arrays.asList(detail1, detail2));

        return incomingOrderRequest;
    }

    public static IncomingOrderRequest generateIncomingOrderRequestZeroOrNegativeQuantity() {

        IncomingOrderRequest incomingOrderRequest = new IncomingOrderRequest();

        OrderDetail detail1 = new OrderDetail(0, 1, "apple", BigDecimal.valueOf(0.60), null);
        OrderDetail detail2 = new OrderDetail(-3, 2, "orange", BigDecimal.valueOf(0.60), null);
        OrderDetail detail3 = new OrderDetail(2, 3, "banana", BigDecimal.valueOf(0.75), null);

        incomingOrderRequest.setOrderDetails(Arrays.asList(detail1, detail2, detail3));

        return incomingOrderRequest;
    }

    public static IncomingOrderRequest generateIncomingOrderRequestNullItems() {
        IncomingOrderRequest incomingOrderRequest = new IncomingOrderRequest();

        OrderDetail detail1 = new OrderDetail(1, 1, "apple", BigDecimal.valueOf(0.60), null);
        OrderDetail detail2 = new OrderDetail(2, 2, "orange", BigDecimal.valueOf(0.25), null);
        incomingOrderRequest.setOrderDetails(Arrays.asList(detail1, null, detail2));

        return incomingOrderRequest;

    }

    public static IncomingOrderRequest generateIncomingOrderNoNameNoId() {
        IncomingOrderRequest incomingOrderRequest = new IncomingOrderRequest();

        OrderDetail detail1 = new OrderDetail(1, 1, "", BigDecimal.valueOf(0.60), null);
        OrderDetail detail2 = new OrderDetail(2, 0, "orange", BigDecimal.valueOf(0.25), null);
        incomingOrderRequest.setOrderDetails(Arrays.asList(detail1, detail2));

        return incomingOrderRequest;
    }

    public static IncomingOrderRequest generateIncomingOrderNegativeOrZeroCost() {
        IncomingOrderRequest incomingOrderRequest = new IncomingOrderRequest();

        OrderDetail detail1 = new OrderDetail(1, 1, "apple", BigDecimal.valueOf(0.60).negate(), null);
        OrderDetail detail2 = new OrderDetail(2, 2, "orange", BigDecimal.ZERO, null);
        incomingOrderRequest.setOrderDetails(Arrays.asList(detail1, detail2));

        return incomingOrderRequest;
    }

    public static IncomingOrderRequest generateIncomingOrderNullList() {
        IncomingOrderRequest incomingOrderRequest = new IncomingOrderRequest();
        incomingOrderRequest.setOrderDetails(null);

        return incomingOrderRequest;
    }

    public static IncomingOrderRequest generateIncomingOrderRequestWithPromotions() {
        IncomingOrderRequest incomingOrderRequest = new IncomingOrderRequest();

        OrderDetail detail1 = new OrderDetail(4, 1, "apple", BigDecimal.valueOf(0.60), "bogo");
        OrderDetail detail2 = new OrderDetail(6, 2, "orange", BigDecimal.valueOf(0.25), "b2go");
        OrderDetail detail3 = new OrderDetail(3, 3, "banana", BigDecimal.valueOf(0.75), null);
        OrderDetail detail4 = new OrderDetail(2, 4, "kumquat", BigDecimal.valueOf(1), "funky");
        incomingOrderRequest.setOrderDetails(Arrays.asList(detail1, detail2, detail3, detail4));


        return incomingOrderRequest;
    }
}
