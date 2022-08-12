package com.dmdev.natalliavasilyeva.domain.model;

public enum OrderStatus {
    CONFIRMATION_WAIT("confirmation_wait"),
    DECLINED("declined"),
    PAYED("payed"),
    NOT_PAYED("not_payed"),
    CANCELLED("cancelled");

    OrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    String orderStatus;

    public String getOrderStatus() {
        return orderStatus;
    }

    @Override
    public String toString() {
        return this.getOrderStatus();
    }

    public static OrderStatus getEnum(String value) {
        for(OrderStatus v : values())
            if(v.getOrderStatus().equalsIgnoreCase(value)) return v;
        throw new IllegalArgumentException();
    }
}