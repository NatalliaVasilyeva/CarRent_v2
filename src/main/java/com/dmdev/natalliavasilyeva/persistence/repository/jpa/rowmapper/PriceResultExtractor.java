package com.dmdev.natalliavasilyeva.persistence.repository.jpa.rowmapper;


import com.dmdev.natalliavasilyeva.domain.jpa.PriceJpa;
import com.dmdev.natalliavasilyeva.persistence.exception.RepositoryException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PriceResultExtractor implements ResultSetExtractor<PriceJpa> {

    private static final Logger logger = LoggerFactory.getLogger(PriceResultExtractor.class);

    @Override
    public PriceJpa extractData(ResultSet rs) {
        try {
            return new PriceJpa.Builder()
                    .id(rs.getLong("id"))
                    .sum(rs.getBigDecimal("price"))
                    .build();
        } catch (SQLException ex) {
            throw new RepositoryException(String.format("Exception for price jpa in extract data method: %s", ex.getCause()), ex);
        }
    }
}