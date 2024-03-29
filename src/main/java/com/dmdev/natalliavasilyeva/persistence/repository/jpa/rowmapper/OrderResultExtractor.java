package com.dmdev.natalliavasilyeva.persistence.repository.jpa.rowmapper;


import com.dmdev.natalliavasilyeva.domain.model.OrderStatus;
import com.dmdev.natalliavasilyeva.domain.jpa.OrderJpa;
import com.dmdev.natalliavasilyeva.persistence.exception.RepositoryException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

public class OrderResultExtractor implements ResultSetExtractor<OrderJpa> {

    private static final Logger logger = LoggerFactory.getLogger(OrderResultExtractor.class);

    @Override
    public OrderJpa extractData(ResultSet rs) {
        try {
            Timestamp date = rs.getTimestamp("date");

            return new OrderJpa.Builder()
                    .id(rs.getLong("id"))
                    .date(date == null ? null : date.toInstant())
                    .user(rs.getLong("user_id"))
                    .car(rs.getLong("car_id"))
                    .passport(rs.getString("passport"))
                    .insurance(rs.getBoolean("insurance"))
                    .orderStatus(OrderStatus.getEnum(rs.getString("order_status").toUpperCase()))
                    .sum(rs.getBigDecimal("sum"))
                    .build();
        } catch (SQLException ex) {
            throw new RepositoryException(String.format("Exception for order jpa in extract data method: %s", ex.getCause()), ex);
        }
    }
}