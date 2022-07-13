package com.dmdev.natalliavasilyeva.persistence.repository.custom.rowmapper;

import com.dmdev.natalliavasilyeva.domain.model.Accident;
import com.dmdev.natalliavasilyeva.persistence.exception.RepositoryException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AccidentResultExtractor implements ResultSetExtractor<Accident> {

    private static final Logger logger = LoggerFactory.getLogger(AccidentResultExtractor.class);

    @Override
    public Accident extractData(ResultSet rs) {
        try {
            var accidentDate = rs.getTimestamp("accident_date");
            return new Accident.Builder()
                    .id(rs.getLong("id"))
                    .order(rs.getLong("order_number"))
                    .date(accidentDate == null ? null : accidentDate.toInstant())
                    .brand(rs.getString("brand"))
                    .model(rs.getString("model"))
                    .car(rs.getString("car_number"))
                    .userName(rs.getString("user_name"))
                    .userSurname(rs.getString("user_surname"))
                    .description(rs.getString("description"))
                    .damage(rs.getBigDecimal("damage"))
                    .build();
        } catch (SQLException ex) {
            throw new RepositoryException(String.format("Exception for accident custom in extract data method: %s", ex.getCause()), ex);
        }
    }
}