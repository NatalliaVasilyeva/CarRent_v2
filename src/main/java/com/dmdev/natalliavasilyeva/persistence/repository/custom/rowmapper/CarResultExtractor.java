package com.dmdev.natalliavasilyeva.persistence.repository.custom.rowmapper;

import com.dmdev.natalliavasilyeva.domain.model.Accident;
import com.dmdev.natalliavasilyeva.domain.model.Brand;
import com.dmdev.natalliavasilyeva.domain.model.Car;
import com.dmdev.natalliavasilyeva.domain.model.Category;
import com.dmdev.natalliavasilyeva.domain.model.Color;
import com.dmdev.natalliavasilyeva.domain.model.EngineType;
import com.dmdev.natalliavasilyeva.domain.model.Model;
import com.dmdev.natalliavasilyeva.domain.model.Price;
import com.dmdev.natalliavasilyeva.domain.model.Transmission;
import com.dmdev.natalliavasilyeva.persistence.utils.DateTimeService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class CarResultExtractor implements ResultSetExtractor<Car> {

    private static final Logger logger = LoggerFactory.getLogger(CarResultExtractor.class);

    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public Car extractData(ResultSet rs) throws SQLException {

        String accidentsJson = getAccidentDescription(rs);
        List<Accident> accidents =
                accidentsJson.isBlank() ? Collections.emptyList() :
                        this.mapJsonResultToAccident(rs.getString("accident_description"));


        return new Car.Builder()
                .id(rs.getLong("car_id"))
                .model(mapToModel(rs))
                .color(Color.valueOf(rs.getString("color").toUpperCase()))
                .year(rs.getString("year"))
                .number(rs.getString("car_number"))
                .vin(rs.getString("vin"))
                .repaired(rs.getBoolean("is_repaired"))
                .image(rs.getString("image"))
                .accidents(accidents)
                .build();
    }

    private Model mapToModel(ResultSet rs) throws SQLException {

        return new Model.Builder()
                .name(rs.getString("model_name"))
                .transmission(Transmission.valueOf(rs.getString("transmission").toUpperCase()))
                .engine(EngineType.valueOf(rs.getString("engine_type").toUpperCase()))
                .brand(mapToBrand(rs))
                .category(mapToCategory(rs))
                .build();
    }

    private Brand mapToBrand(ResultSet rs) throws SQLException {

        return new Brand.Builder()
                .name(rs.getString("brand_name"))
                .build();
    }

    private Category mapToCategory(ResultSet rs) throws SQLException {

        return new Category.Builder()
                .name(rs.getString("category_name"))
                .price(mapToPrice(rs))
                .build();
    }

    private Price mapToPrice(ResultSet rs) throws SQLException {

        return new Price.Builder()
                .sum(rs.getBigDecimal("price"))
                .build();
    }

    private List<Accident> mapJsonResultToAccident(String ordersJson) {
        List<Accident> accidents = new ArrayList<>();
        if (ordersJson == null) {
            return accidents;
        }

        ObjectMapper objectMapper = new ObjectMapper();

        try {
            List<Map<String, Object>> elements = objectMapper.readValue(ordersJson, ArrayList.class);
            for (Map<String, Object> element : elements) {
                if (element != null) {

                    Instant date = DateTimeService.fromStringDateToInstant((String) element.get("accident_date"));

                    accidents.add(new Accident.Builder()
                            .id(Long.valueOf((Integer) element.get("accident_id")))
                            .date(date)
                            .description((String) element.get("description"))
                            .damage(new BigDecimal((String) element.get("damage")))
                            .build()
                    );
                }
            }
        } catch (JsonProcessingException e) {
            logger.error("Error deserializing elements");
        }
        return accidents;
    }

    private String getAccidentDescription(ResultSet rs) {
        String json;
        try {
            json = rs.getString("accident_description");
        } catch (SQLException e) {
            json = "";
        }
        return json;
    }
}