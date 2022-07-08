package com.dmdev.natalliavasilyeva.persistence.repository.custom;


import com.dmdev.natalliavasilyeva.connection.exception.ConnectionPoolException;
import com.dmdev.natalliavasilyeva.domain.model.Brand;
import com.dmdev.natalliavasilyeva.domain.model.Identifiable;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface BrandRepository<T extends Identifiable> extends GenericCustomRepository<Brand, Long> {
    Optional<T> findByName(String name) throws SQLException, ConnectionPoolException;
    List<T> findByCategory(String category) throws SQLException, ConnectionPoolException;
}