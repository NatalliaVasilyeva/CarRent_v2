package com.dmdev.natalliavasilyeva.persistence.repository.jpa.rowmapper;


import com.dmdev.natalliavasilyeva.domain.model.Color;
import com.dmdev.natalliavasilyeva.domain.jpa.Car;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CarResultExtractor implements ResultSetExtractor<Car> {

    private static final Logger logger = LoggerFactory.getLogger(CarResultExtractor.class);

    @Override
    public Car extractData(ResultSet rs) throws SQLException {
        return new Car.Builder()
                .id(rs.getLong("id"))
                .model(rs.getLong("model_id"))
                .color(Color.valueOf(rs.getString("color").toUpperCase()))
                .year(rs.getString("year"))
                .year(rs.getString("car_number"))
                .vin(rs.getString("car_number"))
                .repaired(rs.getBoolean("is_repaired"))
                .image(rs.getString("image"))
                .build();

    }
}