package com.dmdev.natalliavasilyeva.persistence.repository.jpa.rowmapper;

import com.dmdev.natalliavasilyeva.domain.jpa.CarRentalTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

public class CarRentalTimeResultExtractor implements ResultSetExtractor<CarRentalTime> {

    private static final Logger logger = LoggerFactory.getLogger(CarRentalTimeResultExtractor.class);

    @Override
    public CarRentalTime extractData(ResultSet rs) throws SQLException {
        Timestamp startRentalDate = rs.getTimestamp("start_rental_date");
        Timestamp endRentalDate = rs.getTimestamp("end_rental_date");

        return new CarRentalTime.Builder()
                .id(rs.getLong("id"))
                .order(rs.getLong("order_id"))
                .start(startRentalDate == null ? null : startRentalDate.toInstant())
                .end(endRentalDate == null ? null : endRentalDate.toInstant())
                .build();

    }
}