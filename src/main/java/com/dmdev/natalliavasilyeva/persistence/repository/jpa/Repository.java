package com.dmdev.natalliavasilyeva.persistence.repository.jpa;


import com.dmdev.natalliavasilyeva.connection.exception.ConnectionPoolException;
import com.dmdev.natalliavasilyeva.domain.jpa.Entity;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface Repository<T extends Entity, K> {
    List<T> findAll() throws SQLException, ConnectionPoolException;

    Optional<T> findById(K id) throws SQLException, ConnectionPoolException;

    boolean deleteById(K id) throws SQLException, ConnectionPoolException;

    Optional<T> delete(T object) throws SQLException, ConnectionPoolException;

    Optional<T> update(T object) throws ConnectionPoolException, SQLException;

    Optional<T> save(T object) throws ConnectionPoolException, SQLException;

}