package com.dmdev.natalliavasilyeva.persistence.repository.jpa.rowmapper;


import com.dmdev.natalliavasilyeva.domain.jpa.Category;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CategoryResultExtractor implements ResultSetExtractor<Category> {

    private static final Logger logger = LoggerFactory.getLogger(CategoryResultExtractor.class);

    @Override
    public Category extractData(ResultSet rs) throws SQLException {
        return new Category.Builder()
                .id(rs.getLong("id"))
                .name(rs.getString("name"))
                .price(rs.getLong("price_id"))
                .build();
    }
}