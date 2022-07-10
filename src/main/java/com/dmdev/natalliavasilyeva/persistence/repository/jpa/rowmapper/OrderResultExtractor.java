package com.dmdev.natalliavasilyeva.persistence.repository.jpa.rowmapper;


import com.dmdev.natalliavasilyeva.domain.model.OrderStatus;
import com.dmdev.natalliavasilyeva.domain.jpa.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

public class OrderResultExtractor implements ResultSetExtractor<Order> {

    private static final Logger logger = LoggerFactory.getLogger(OrderResultExtractor.class);

    @Override
    public Order extractData(ResultSet rs) throws SQLException {
        Timestamp date = rs.getTimestamp("date");

        return new Order.Builder()
                .id(rs.getLong("id"))
                .date(date == null ? null : date.toInstant())
                .user(rs.getLong("user_id"))
                .car(rs.getLong("car_id"))
                .passport(rs.getString("passport"))
                .insurance(rs.getBoolean("insurance"))
                .orderStatus(OrderStatus.valueOf(rs.getString("order_status").toUpperCase()))
                .sum(rs.getBigDecimal("sum"))
                .build();
    }
}