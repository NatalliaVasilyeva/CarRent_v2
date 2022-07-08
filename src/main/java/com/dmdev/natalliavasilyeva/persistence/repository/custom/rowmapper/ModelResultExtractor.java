package com.dmdev.natalliavasilyeva.persistence.repository.custom.rowmapper;

import com.dmdev.natalliavasilyeva.domain.model.Brand;
import com.dmdev.natalliavasilyeva.domain.model.Category;
import com.dmdev.natalliavasilyeva.domain.model.EngineType;
import com.dmdev.natalliavasilyeva.domain.model.Model;
import com.dmdev.natalliavasilyeva.domain.model.Price;
import com.dmdev.natalliavasilyeva.domain.model.Transmission;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class ModelResultExtractor implements ResultSetExtractor<Model> {

    private static final Logger logger = LoggerFactory.getLogger(ModelResultExtractor.class);

    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public Model extractData(ResultSet rs) throws SQLException {


        return new Model.Builder()
                .id(rs.getLong("model_id"))
                .brand(mapToBrand(rs))
                .category(mapToCategory(rs))
                .name(rs.getString("model_name"))
                .transmission(Transmission.valueOf(rs.getString("model_transmission").toUpperCase()))
                .engine(EngineType.valueOf(rs.getString("model_engine_type").toUpperCase()))
                .build();
    }

    private Brand mapToBrand(ResultSet rs) throws SQLException {
        Map<String, Object> queryMap;
        try {
            queryMap = rs.getString("brand_description") == null ?
                    Collections.emptyMap() : objectMapper.readValue(rs.getString("brand_description"), new TypeReference<HashMap<String, Object>>() {
            });
        } catch (JsonProcessingException e) {
            throw new SQLException("JSON parsing error while processing brand.", e);
        }

        return new Brand.Builder()
                .id(Long.valueOf((Integer) queryMap.get("brand_id")))
                .name((String) queryMap.get(("brand_name")))
                .build();
    }

    private Category mapToCategory(ResultSet rs) throws SQLException {
        Map<String, Object> queryMap;
        try {
            queryMap = objectMapper.readValue(rs.getString("category_description"), new TypeReference<HashMap<String, Object>>() {
            });
        } catch (JsonProcessingException e) {
            throw new SQLException("JSON parsing error while processing category.", e);
        }

        return new Category.Builder()
                .id(Long.valueOf((Integer) queryMap.get("category_id")))
                .name((String) queryMap.get("category_name"))
                .price(mapToPrice(queryMap.get("price_description")))
                .build();
    }

    private Price mapToPrice(Object o) {

        ObjectMapper objectMapper = new ObjectMapper();
        return o != null ? objectMapper.convertValue(o, Price.class) : null;
    }
}