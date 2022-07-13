package com.dmdev.natalliavasilyeva.persistence.repository.custom.rowmapper;

import com.dmdev.natalliavasilyeva.domain.model.Category;
import com.dmdev.natalliavasilyeva.domain.model.Price;

import com.dmdev.natalliavasilyeva.persistence.exception.RepositoryException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CategoryResultExtractor implements ResultSetExtractor<Category> {

    private static final Logger logger = LoggerFactory.getLogger(CategoryResultExtractor.class);
    @Override
    public Category extractData(ResultSet rs) {
        try {
            return new Category.Builder()
                    .id(rs.getLong("category_id"))
                    .name(rs.getString("category_name"))
                    .price(new Price.Builder().sum(rs.getBigDecimal("price")).build())
                    .build();
        } catch (SQLException ex) {
            throw new RepositoryException(String.format("Exception for category custom in extract data method: %s", ex.getCause()), ex);
        }
    }
}