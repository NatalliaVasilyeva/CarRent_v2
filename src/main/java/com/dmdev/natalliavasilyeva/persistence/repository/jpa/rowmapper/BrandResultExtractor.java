package com.dmdev.natalliavasilyeva.persistence.repository.jpa.rowmapper;


import com.dmdev.natalliavasilyeva.domain.jpa.Brand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.ResultSet;
import java.sql.SQLException;

public class BrandResultExtractor implements ResultSetExtractor<Brand> {

    private static final Logger logger = LoggerFactory.getLogger(BrandResultExtractor.class);

    @Override
    public Brand extractData(ResultSet rs) throws SQLException {
        return new Brand.Builder()
                .id(rs.getLong("id"))
                .name(rs.getString("name"))
                .build();
    }
}