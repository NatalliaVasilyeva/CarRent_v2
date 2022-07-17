package com.dmdev.natalliavasilyeva.persistence.repository.jpa.rowmapper;


import com.dmdev.natalliavasilyeva.domain.jpa.CategoryJpa;
import com.dmdev.natalliavasilyeva.persistence.exception.RepositoryException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CategoryResultExtractor implements ResultSetExtractor<CategoryJpa> {

    private static final Logger logger = LoggerFactory.getLogger(CategoryResultExtractor.class);

    @Override
    public CategoryJpa extractData(ResultSet rs) {
        try {
            return new CategoryJpa.Builder()
                    .id(rs.getLong("id"))
                    .name(rs.getString("name"))
                    .price(rs.getLong("price_id"))
                    .build();
        } catch (SQLException ex) {
            throw new RepositoryException(String.format("Exception for category jpa in extract data method: %s", ex.getCause()), ex);
        }
    }
}