package com.dmdev.natalliavasilyeva.persistence.repository.custom;

import com.dmdev.natalliavasilyeva.connection.exception.ConnectionPoolException;
import com.dmdev.natalliavasilyeva.domain.model.Identifiable;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface GenericCustomRepository<T extends Identifiable, K> {
    List<T> findAll() throws SQLException, ConnectionPoolException;

    Optional<T> findById(K id) throws SQLException, ConnectionPoolException;
}