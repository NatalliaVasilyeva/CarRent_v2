package com.dmdev.natalliavasilyeva.persistence.repository.custom;


import com.dmdev.natalliavasilyeva.connection.exception.ConnectionPoolException;
import com.dmdev.natalliavasilyeva.domain.model.Accident;
import com.dmdev.natalliavasilyeva.domain.model.Identifiable;
import com.dmdev.natalliavasilyeva.domain.model.Model;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.Instant;
import java.util.List;

public interface ModelRepository<T extends Identifiable> extends GenericCustomRepository<Model, Long> {
    List<T> findAllByBrandIds(List<Long> ids) throws SQLException, ConnectionPoolException;
    List<T> findAllByBrandName(String brandName) throws SQLException, ConnectionPoolException;
    List<T> findAllByTransmission(String transmission) throws SQLException, ConnectionPoolException;
    List<T> findAllByEngineType(String engineType) throws SQLException, ConnectionPoolException;
    List<T> findAllByCategory(String category) throws SQLException, ConnectionPoolException;

}