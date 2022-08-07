package com.dmdev.natalliavasilyeva.api.dto.responsedto;


import java.time.LocalDateTime;

public class OrderShotResponseDto {

    private long id;
    private LocalDateTime date;
    private String orderStatus;

    public OrderShotResponseDto(long id, LocalDateTime date, String orderStatus) {
        this.id = id;
        this.date = date;
        this.orderStatus = orderStatus;
    }

    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public LocalDateTime getDate() {
        return date;
    }
    public void setDate(LocalDateTime date) {
        this.date = date;
    }
    public String getOrderStatus() {
        return orderStatus;
    }
    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    @Override
    public String toString() {
        return "OrderShotResponseDto{" +
                "id=" + id +
                ", date=" + date +
                ", orderStatus='" + orderStatus + '\'' +
                '}';
    }
}