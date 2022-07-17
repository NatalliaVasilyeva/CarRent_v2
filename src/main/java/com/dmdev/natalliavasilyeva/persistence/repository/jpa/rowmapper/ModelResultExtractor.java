package com.dmdev.natalliavasilyeva.persistence.repository.jpa.rowmapper;


import com.dmdev.natalliavasilyeva.domain.jpa.ModelJpa;
import com.dmdev.natalliavasilyeva.domain.model.EngineType;
import com.dmdev.natalliavasilyeva.domain.model.Transmission;
import com.dmdev.natalliavasilyeva.persistence.exception.RepositoryException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ModelResultExtractor implements ResultSetExtractor<ModelJpa> {

    private static final Logger logger = LoggerFactory.getLogger(ModelResultExtractor.class);

    @Override
    public ModelJpa extractData(ResultSet rs) {
        try {
            return new ModelJpa.Builder()
                    .id(rs.getLong("id"))
                    .brand(rs.getLong("brand_id"))
                    .category(rs.getLong("category_id"))
                    .name(rs.getString("name"))
                    .transmission(Transmission.valueOf(rs.getString("transmission")))
                    .engine(EngineType.valueOf(rs.getString("engine_type")))
                    .build();
        } catch (SQLException ex) {
            throw new RepositoryException(String.format("Exception for model jpa in extract data method: %s", ex.getCause()), ex);
        }
    }
}