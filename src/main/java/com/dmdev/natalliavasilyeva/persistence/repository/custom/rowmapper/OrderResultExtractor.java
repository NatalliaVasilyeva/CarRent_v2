package com.dmdev.natalliavasilyeva.persistence.repository.custom.rowmapper;

import com.dmdev.natalliavasilyeva.domain.model.Brand;
import com.dmdev.natalliavasilyeva.domain.model.Car;
import com.dmdev.natalliavasilyeva.domain.model.CarRentalTime;
import com.dmdev.natalliavasilyeva.domain.model.Model;
import com.dmdev.natalliavasilyeva.domain.model.Order;
import com.dmdev.natalliavasilyeva.domain.model.OrderStatus;
import com.dmdev.natalliavasilyeva.domain.model.User;
import com.dmdev.natalliavasilyeva.persistence.exception.RepositoryException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

public class OrderResultExtractor implements ResultSetExtractor<Order> {

    private static final Logger logger = LoggerFactory.getLogger(OrderResultExtractor.class);

    @Override
    public Order extractData(ResultSet rs) {
        try {
            Timestamp date = rs.getTimestamp("date");

            return new Order.Builder()
                    .id(rs.getLong("order_id"))
                    .date(date == null ? null : date.toInstant())
                    .insurance(rs.getBoolean("insurance"))
                    .orderStatus(OrderStatus.getEnum(rs.getString("order_status").toUpperCase()))
                    .sum(rs.getBigDecimal("sum"))
                    .carRentalTime(mapToCarRentalTime(rs))
                    .car(mapToCar(rs))
                    .user(mapToUser(rs))
                    .withAccidence(rs.getBoolean("accidents"))
                    .build();
        } catch (SQLException ex) {
            throw new RepositoryException(String.format("Exception for order custom in extract data method: %s", ex.getCause()), ex);
        }
    }

    private Car mapToCar(ResultSet rs) throws SQLException {

        return new Car.Builder()
                .number(rs.getString("car_number"))
                .model(mapToModel(rs))
                .build();
    }

    private Model mapToModel(ResultSet rs) throws SQLException {

        return new Model.Builder()
                .name(rs.getString("model_name"))
                .brand(mapToBrand(rs))
                .build();
    }

    private Brand mapToBrand(ResultSet rs) throws SQLException {

        return new Brand.Builder()
                .name(rs.getString("brand_name"))
                .build();
    }

    private CarRentalTime mapToCarRentalTime(ResultSet rs) throws SQLException {
        Timestamp start = rs.getTimestamp("start_rental_date");
        Timestamp end = rs.getTimestamp("end_rental_date");

        return new CarRentalTime.Builder()
                .start(start == null ? null : start.toInstant())
                .end(end == null ? null : end.toInstant())
                .build();
    }

    private User mapToUser(ResultSet rs) throws SQLException {

        return new User.Builder()
                .name(rs.getString("name"))
                .surname(rs.getString("surname"))
                .phone(rs.getString("phone"))
                .build();
    }
}