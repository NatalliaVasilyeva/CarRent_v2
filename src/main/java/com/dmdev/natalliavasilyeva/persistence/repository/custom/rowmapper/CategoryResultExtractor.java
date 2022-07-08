package com.dmdev.natalliavasilyeva.persistence.repository.custom.rowmapper;

import com.dmdev.natalliavasilyeva.domain.model.Category;
import com.dmdev.natalliavasilyeva.domain.model.Price;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;


public class CategoryResultExtractor implements ResultSetExtractor<Category> {

    private static final Logger logger = LoggerFactory.getLogger(CategoryResultExtractor.class);

    @Override
    public Category extractData(ResultSet rs) throws SQLException {

        return new Category.Builder()
                .id(rs.getLong("category_id"))
                .name(rs.getString("category_name"))
                .price(new Price.Builder().sum(rs.getBigDecimal("price")).build())
                .build();
    }
}