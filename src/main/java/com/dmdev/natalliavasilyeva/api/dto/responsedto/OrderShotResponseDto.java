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
}