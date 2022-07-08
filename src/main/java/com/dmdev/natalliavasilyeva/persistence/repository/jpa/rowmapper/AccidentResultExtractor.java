package com.dmdev.natalliavasilyeva.persistence.repository.jpa.rowmapper;


import com.dmdev.natalliavasilyeva.persistence.jpa.Accident;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AccidentResultExtractor implements ResultSetExtractor<Accident> {

    private static final Logger logger = LoggerFactory.getLogger(AccidentResultExtractor.class);

    @Override
    public Accident extractData(ResultSet rs) throws SQLException {
        var accidentDate = rs.getTimestamp("date");
        return new Accident.Builder()
                .id(rs.getLong("id"))
                .order(rs.getLong("order_id"))
                .date(accidentDate == null ? null : accidentDate.toInstant())
                .description(rs.getString("description"))
                .damage(rs.getBigDecimal("damage"))
                .build();

    }
}