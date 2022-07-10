package com.dmdev.natalliavasilyeva.persistence.repository.jpa.rowmapper;


import com.dmdev.natalliavasilyeva.domain.jpa.Price;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PriceResultExtractor implements ResultSetExtractor<Price> {

    private static final Logger logger = LoggerFactory.getLogger(PriceResultExtractor.class);

    @Override
    public Price extractData(ResultSet rs) throws SQLException {
        return new Price.Builder()
                .id(rs.getLong("id"))
                .sum(rs.getBigDecimal("price"))
                .build();
    }
}