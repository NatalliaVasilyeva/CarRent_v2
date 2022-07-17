package com.dmdev.natalliavasilyeva.api.dto.requestdto;

public class OrderUpdateStatusDto {

    private long orderId;
    private String orderStatus;

    public OrderUpdateStatusDto(long orderId, String orderStatus) {
        this.orderId = orderId;
        this.orderStatus = orderStatus;
    }

    public long getOrderId() {
        return orderId;
    }

    public String getOrderStatus() {
        return orderStatus;
    }
}