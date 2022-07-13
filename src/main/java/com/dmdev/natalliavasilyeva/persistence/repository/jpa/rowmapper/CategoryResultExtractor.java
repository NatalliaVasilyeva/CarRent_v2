package com.dmdev.natalliavasilyeva.persistence.repository.jpa.rowmapper;


import com.dmdev.natalliavasilyeva.domain.jpa.Category;
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
                    .id(rs.getLong("id"))
                    .name(rs.getString("name"))
                    .price(rs.getLong("price_id"))
                    .build();
        } catch (SQLException ex) {
            throw new RepositoryException(String.format("Exception for category jpa in extract data method: %s", ex.getCause()), ex);
        }
    }
}