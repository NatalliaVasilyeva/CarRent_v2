package com.dmdev.natalliavasilyeva.persistence.repository.custom;


import com.dmdev.natalliavasilyeva.connection.exception.ConnectionPoolException;
import com.dmdev.natalliavasilyeva.domain.model.Accident;
import com.dmdev.natalliavasilyeva.domain.model.Identifiable;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.Instant;
import java.util.List;

public interface AccidentRepository<T extends Identifiable> extends GenericCustomRepository<Accident, Long> {
    List<T> findByDate(Instant date) throws SQLException, ConnectionPoolException;
    List<T> findByDates(Instant start, Instant end) throws SQLException, ConnectionPoolException;
    List<T> findByOrderId(Long id) throws SQLException, ConnectionPoolException;
    List<T> findByUsernameAndSurname(String username, String surname) throws SQLException, ConnectionPoolException;
    List<T> findByCarNumbers(List<String> carNumbers) throws SQLException, ConnectionPoolException;
    List<T> findByDamage(BigDecimal minDamage) throws SQLException, ConnectionPoolException;

}