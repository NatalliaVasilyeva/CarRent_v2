package com.dmdev.natalliavasilyeva.persistence.repository.custom;


import com.dmdev.natalliavasilyeva.connection.exception.ConnectionPoolException;
import com.dmdev.natalliavasilyeva.domain.model.Category;
import com.dmdev.natalliavasilyeva.domain.model.Identifiable;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface CategoryRepository<T extends Identifiable> extends GenericCustomRepository<Category, Long> {

    List<T> findByPrice(String sign, BigDecimal price) throws SQLException, ConnectionPoolException;
    Optional<T> findByName(String name) throws SQLException, ConnectionPoolException;

}