package com.dmdev.natalliavasilyeva.persistence.repository.jpa.rowmapper;

import com.dmdev.natalliavasilyeva.domain.jpa.CarRentalTimeJpa;
import com.dmdev.natalliavasilyeva.persistence.exception.RepositoryException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

public class CarRentalTimeResultExtractor implements ResultSetExtractor<CarRentalTimeJpa> {

    private static final Logger logger = LoggerFactory.getLogger(CarRentalTimeResultExtractor.class);

    @Override
    public CarRentalTimeJpa extractData(ResultSet rs) {
        try {
            Timestamp startRentalDate = rs.getTimestamp("start_rental_date");
            Timestamp endRentalDate = rs.getTimestamp("end_rental_date");

            return new CarRentalTimeJpa.Builder()
                    .id(rs.getLong("id"))
                    .order(rs.getLong("order_id"))
                    .start(startRentalDate == null ? null : startRentalDate.toInstant())
                    .end(endRentalDate == null ? null : endRentalDate.toInstant())
                    .build();
        } catch (SQLException ex) {
            throw new RepositoryException(String.format("Exception for car rental time jpa in extract data method: %s", ex.getCause()), ex);
        }
    }
}