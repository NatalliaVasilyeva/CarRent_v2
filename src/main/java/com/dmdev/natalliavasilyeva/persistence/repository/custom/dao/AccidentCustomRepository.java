package com.dmdev.natalliavasilyeva.persistence.repository.custom.dao;


import com.dmdev.natalliavasilyeva.connection.ConnectionPool;
import com.dmdev.natalliavasilyeva.connection.exception.ConnectionPoolException;
import com.dmdev.natalliavasilyeva.domain.model.Accident;
import com.dmdev.natalliavasilyeva.persistence.repository.BaseStatementProvider;
import com.dmdev.natalliavasilyeva.persistence.repository.custom.GenericCustomRepository;
import com.dmdev.natalliavasilyeva.persistence.repository.custom.rowmapper.AccidentResultExtractor;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AccidentCustomRepository implements GenericCustomRepository<Accident, Long> {
    ConnectionPool connectionPool;
    AccidentResultExtractor extractor;

    public AccidentCustomRepository() {
        this.connectionPool = ConnectionPool.getInstance();
        this.extractor = new AccidentResultExtractor();
    }

    private final static String FIND = "" +
            "SELECT ac.id, ac.accident_date, ac.description, ac.damage, o.id as order_number,\n" +
                    "br.name as brand, md.name as model, car.car_number as car_number,\n" +
                    "ud.name as user_name, ud.surname as user_surname\n" +
            "FROM accident ac\n" +
                    "LEFT JOIN orders o ON o.id = ac.order_id\n" +
                    "LEFT JOIN users u ON u.id = o.user_id\n" +
                    "LEFT JOIN userdetails ud ON ud.user_id = u.id\n" +
                    "LEFT JOIN car ON car.id = o.car_id\n" +
                    "LEFT JOIN model md ON md.id = car.model_id\n" +
                    "LEFT JOIN brand br ON br.id = md.brand_id\n";

    @Override
    public Optional<Accident> findById(Long id) throws SQLException, ConnectionPoolException {

        var statementProvider = new BaseStatementProvider();
        statementProvider
                .append(FIND)
                .appendWithSingleArg("WHERE ac.id = ?", id);
        try (var prepareStatement = statementProvider.createPreparedStatement(connectionPool.getConnection())) {
            var resultSet = prepareStatement.executeQuery();
            Accident accident = null;
            if (resultSet.next()) {
                accident = extractor.extractData(resultSet);
            }
            return Optional.ofNullable(accident);
        }
    }

    @Override
    public List<Accident> findAll() throws SQLException, ConnectionPoolException {
        List<Accident> accidents = new ArrayList<>();
        var statementProvider = new BaseStatementProvider();
        statementProvider
                .append(FIND);
        try (var prepareStatement = statementProvider.createPreparedStatement(connectionPool.getConnection())) {
            var resultSet = prepareStatement.executeQuery();

            while (resultSet.next()) {
                accidents.add(extractor.extractData(resultSet));
            }
        }
        return accidents;
    }

    public List<Accident> findByDate(Instant date) throws SQLException, ConnectionPoolException {
        List<Accident> accidents = new ArrayList<>();
        var statementProvider = new BaseStatementProvider();
        statementProvider
                .append(FIND)
                .appendWithSingleArg("WHERE ac.accident_date = ?", date);

        try (var prepareStatement = statementProvider.createPreparedStatement(connectionPool.getConnection())) {
            var resultSet = prepareStatement.executeQuery();
            while (resultSet.next()) {
                accidents.add(extractor.extractData(resultSet));
            }
        }
        return accidents;
    }

    public List<Accident> findByDates(Instant startDate, Instant endDate) throws SQLException, ConnectionPoolException {
        List<Accident> accidents = new ArrayList<>();
        var statementProvider = new BaseStatementProvider();
        statementProvider
                .append(FIND)
                .appendWithMultipleArgs("WHERE ac.accident_date BETWEEN ? AND ?", startDate, endDate);

        try (var prepareStatement = statementProvider.createPreparedStatement(connectionPool.getConnection())) {
            var resultSet = prepareStatement.executeQuery();
            while (resultSet.next()) {
                accidents.add(extractor.extractData(resultSet));
            }
        }
        return accidents;
    }

    public List<Accident> findByOrderId(Long orderId) throws SQLException, ConnectionPoolException {
        List<Accident> accidents = new ArrayList<>();
        var statementProvider = new BaseStatementProvider();
        statementProvider
                .append(FIND)
                .appendWithSingleArg("WHERE o.id = ?", orderId);

        try (var prepareStatement = statementProvider.createPreparedStatement(connectionPool.getConnection())) {
            var resultSet = prepareStatement.executeQuery();
            while (resultSet.next()) {
                accidents.add(extractor.extractData(resultSet));
            }
        }
        return accidents;
    }

    public List<Accident> findByUsernameAndSurname(String username, String surname) throws SQLException, ConnectionPoolException {
        List<Accident> accidents = new ArrayList<>();
        var statementProvider = new BaseStatementProvider();
        statementProvider
                .append(FIND)
                .appendWithMultipleArgs("WHERE ud.name LIKE ? AND ud.surname LIKE ?", username, surname);

        try (var prepareStatement = statementProvider.createPreparedStatement(connectionPool.getConnection())) {
            var resultSet = prepareStatement.executeQuery();
            while (resultSet.next()) {
                accidents.add(extractor.extractData(resultSet));
            }
        }
        return accidents;
    }

    public List<Accident> findByCarNumbers(List<String> carNumbers) throws SQLException, ConnectionPoolException {
        List<Accident> accidents = new ArrayList<>();
        var statementProvider = new BaseStatementProvider();
        statementProvider
                .append(FIND)
                .appendWithSingleArg("WHERE car.car_number = ANY (?)", carNumbers.toArray(new String[0]))
                .append("ORDER BY ac.accident_date DESC");

        try (var prepareStatement = statementProvider.createPreparedStatement(connectionPool.getConnection())) {
            var resultSet = prepareStatement.executeQuery();
            while (resultSet.next()) {
                accidents.add(this.extractor.extractData(resultSet));
            }
        }
        return accidents;
    }

    public List<Accident> findByDamage(BigDecimal minDamage) throws SQLException, ConnectionPoolException {
        List<Accident> accidents = new ArrayList<>();
        var statementProvider = new BaseStatementProvider();
        statementProvider
                .append(FIND)
                .appendWithSingleArg("WHERE ac.damage >= ?", minDamage)
                .append("ORDER BY ac.damage DESC");

        try (var prepareStatement = statementProvider.createPreparedStatement(connectionPool.getConnection())) {
            var resultSet = prepareStatement.executeQuery();
            while (resultSet.next()) {
                accidents.add(extractor.extractData(resultSet));
            }
        }
        return accidents;
    }
}