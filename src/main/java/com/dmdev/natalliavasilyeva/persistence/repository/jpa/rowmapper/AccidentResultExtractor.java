package com.dmdev.natalliavasilyeva.persistence.repository.jpa.rowmapper;


import com.dmdev.natalliavasilyeva.domain.jpa.AccidentJpa;
import com.dmdev.natalliavasilyeva.persistence.exception.RepositoryException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.ResultSet;
import java.sql.SQLException;

public class AccidentResultExtractor implements ResultSetExtractor<AccidentJpa> {

    private static final Logger logger = LoggerFactory.getLogger(AccidentResultExtractor.class);

    @Override
    public AccidentJpa extractData(ResultSet rs) {
        try {
            var accidentDate = rs.getTimestamp("accident_date");
            return new AccidentJpa.Builder()
                    .id(rs.getLong("id"))
                    .order(rs.getLong("order_id"))
                    .date(accidentDate == null ? null : accidentDate.toInstant())
                    .description(rs.getString("description"))
                    .damage(rs.getBigDecimal("damage"))
                    .build();
        } catch (SQLException ex) {
            throw new RepositoryException(String.format("Exception for accident jpa in extract data method: %s", ex.getCause()), ex);
        }
    }
}