package com.dmdev.natalliavasilyeva.persistence.repository.custom.rowmapper;


import com.dmdev.natalliavasilyeva.domain.model.Brand;
import com.dmdev.natalliavasilyeva.domain.model.Category;
import com.dmdev.natalliavasilyeva.domain.model.EngineType;
import com.dmdev.natalliavasilyeva.domain.model.Model;
import com.dmdev.natalliavasilyeva.domain.model.Transmission;
import com.dmdev.natalliavasilyeva.persistence.exception.RepositoryException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class BrandResultExtractor implements ResultSetExtractor<Brand> {

    private static final Logger logger = LoggerFactory.getLogger(BrandResultExtractor.class);

    @Override
    public Brand extractData(ResultSet rs) {
        try {
            List<Model> models = this.mapJsonResultToModel(rs.getString("model_description"));

            return new Brand.Builder()
                    .id(rs.getLong("brand_id"))
                    .name(rs.getString("brand_name"))
                    .models(models)
                    .build();
        } catch (SQLException ex) {
            throw new RepositoryException(String.format("Exception for brand custom in extract data method: %s", ex.getCause()), ex);
        }
    }

    private List<Model> mapJsonResultToModel(String modelsJson) {
        List<Model> models = new ArrayList<>();
        if (modelsJson == null) {
            return models;
        }

        ObjectMapper objectMapper = new ObjectMapper();

        try {
            List<Map<String, Object>> elements = objectMapper.readValue(modelsJson, ArrayList.class);
            for (Map<String, Object> element : elements) {
                if (element != null) {
                    Long id = Long.valueOf((Integer) element.get("mod_id"));
                    String name = (String) element.get("model_name");
                    Transmission transmission = Transmission.getEnum(((String) element.get("transmission")).toUpperCase());
                    EngineType engineType = EngineType.getEnum(((String) element.get("engine")).toUpperCase());
                    Category category = new Category.Builder().name(((String) element.get("category_name")).toUpperCase()).build();
                    models.add(new Model.Builder()
                            .id(id)
                            .name(name)
                            .transmission(transmission)
                            .engine(engineType)
                            .category(category)
                            .build());
                }
            }
        } catch (JsonProcessingException e) {
            logger.error("Error deserializing elements");
        }
        return models;
    }
}