package com.dmdev.natalliavasilyeva.persistence.repository.jpa.rowmapper;


import com.dmdev.natalliavasilyeva.domain.jpa.BrandJpa;
import com.dmdev.natalliavasilyeva.persistence.exception.RepositoryException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.ResultSet;
import java.sql.SQLException;

public class BrandResultExtractor implements ResultSetExtractor<BrandJpa> {

    private static final Logger logger = LoggerFactory.getLogger(BrandResultExtractor.class);

    @Override
    public BrandJpa extractData(ResultSet rs) {
        try {
            return new BrandJpa.Builder()
                    .id(rs.getLong("id"))
                    .name(rs.getString("name"))
                    .build();
        } catch (SQLException ex) {
            throw new RepositoryException(String.format("Exception for brand jpa in extract data method: %s", ex.getCause()), ex);
        }
    }
}