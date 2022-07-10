package com.dmdev.natalliavasilyeva.persistence.repository.jpa.dao;


import com.dmdev.natalliavasilyeva.connection.ConnectionPool;
import com.dmdev.natalliavasilyeva.connection.exception.ConnectionPoolException;
import com.dmdev.natalliavasilyeva.domain.jpa.Order;
import com.dmdev.natalliavasilyeva.persistence.repository.BaseStatementProvider;
import com.dmdev.natalliavasilyeva.persistence.utils.ParseObjectUtils;
import com.dmdev.natalliavasilyeva.persistence.repository.jpa.Repository;
import com.dmdev.natalliavasilyeva.persistence.repository.jpa.rowmapper.OrderResultExtractor;

import java.sql.SQLException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public class OrderRepositoryImpl implements Repository<Order, Long> {
    ConnectionPool connectionPool;
    OrderResultExtractor extractor;


    public OrderRepositoryImpl(ConnectionPool connectionPool) {
        this.connectionPool = ConnectionPool.getInstance();
        this.extractor = new OrderResultExtractor();
    }

    private final static String FIND = "" +
            "SELECT id, date, user_id, car_id, passport, insurance, order_status, sum \n" +
            "FROM orders \n";

    private final static String CREATE = "" +
            "INSERT INTO orders(date, user_id, car_id, passport, insurance, order_status, sum) values (?, ?, ?, ?, ?, ?, ?)";

    private final static String UPDATE = "" +
            "UPDATE orders SET date = ?, user_id = ?, car_id = ?, passport = ?, insurance = ?, order_status = ?, sum = ? WHERE id = ?";

    private final static String DELETE = "" +
            "DELETE FROM orders WHERE id = ?";


    @Override
    public Optional<Order> findById(Long id) throws SQLException, ConnectionPoolException {

        var statementProvider = new BaseStatementProvider();
        statementProvider
                .append(FIND)
                .appendWithSingleArg("WHERE id = ?", id);
        try (var prepareStatement = statementProvider.createPreparedStatement(connectionPool.getConnection())) {
            var resultSet = prepareStatement.executeQuery();
            Order order = null;
            if (resultSet.next()) {
                order = extractor.extractData(resultSet);
            }
            return Optional.ofNullable(order);
        }
    }


    @Override
    public List<Order> findAll() throws SQLException, ConnectionPoolException {
        List<Order> orders = new ArrayList<>();
        var statementProvider = new BaseStatementProvider();
        statementProvider
                .append(FIND);
        try (var prepareStatement = statementProvider.createPreparedStatement(connectionPool.getConnection())) {
            var resultSet = prepareStatement.executeQuery();
            while (resultSet.next()) {
                orders.add(extractor.extractData(resultSet));
            }
        }
        return orders;
    }

    @Override
    public boolean deleteById(Long id) throws SQLException, ConnectionPoolException {
        var statementProvider = new BaseStatementProvider();
        statementProvider
                .appendWithSingleArg(DELETE, id);
        try (var prepareStatement = statementProvider.createPreparedStatement(connectionPool.getConnection())) {
            var resultSet = prepareStatement.executeUpdate();
            return resultSet > 0;
        }
    }

    @Override
    public Optional<Order> delete(Order order) throws ConnectionPoolException, SQLException {
        var statementProvider = new BaseStatementProvider();
        statementProvider
                .appendWithSingleArg(DELETE, order.getId())
                .append("RETURNING id, date, user_id, car_id, passport, insurance, order_status, sum");
        try (var prepareStatement = statementProvider.createPreparedStatementWithGeneratedKeys(connectionPool.getConnection())) {
            prepareStatement.executeUpdate();
            var generatedKeys = prepareStatement.getGeneratedKeys();
            Order removedOrder = null;
            if (generatedKeys.next()) {
                removedOrder = extractor.extractData(generatedKeys);
            }
            return Optional.ofNullable(removedOrder);
        }
    }

    @Override
    public Optional<Order> update(Order order) throws ConnectionPoolException, SQLException {
        List<Object> values = ParseObjectUtils.getFieldObjectsWithoutId(order);
        values.add(order.getId());
        var statementProvider = new BaseStatementProvider();
        statementProvider
                .appendWithMultipleArgs(UPDATE, values);
        try (var prepareStatement = statementProvider.createPreparedStatement(connectionPool.getConnection())) {
            return prepareStatement.executeUpdate() == 1 ? Optional.of(order) : Optional.empty();
        }
    }


    @Override
    public Optional<Order> save(Order order) throws ConnectionPoolException, SQLException {
        List<Object> values = ParseObjectUtils.getFieldObjectsWithoutId(order);
        var statementProvider = new BaseStatementProvider();
        statementProvider
                .appendWithMultipleArgs(CREATE, values);
        try (var prepareStatement = statementProvider.createPreparedStatementWithGeneratedKeys(connectionPool.getConnection())) {
            prepareStatement.executeUpdate();
            var generatedKeys = prepareStatement.getGeneratedKeys();
            Order savedOrder = null;
            if (generatedKeys.next()) {
                savedOrder = extractor.extractData(generatedKeys);
            }
            return Optional.ofNullable(savedOrder);
        }
    }

    public List<Order> findAllByUser(Long userId) throws SQLException, ConnectionPoolException {
        List<Order> orders = new ArrayList<>();
        var statementProvider = new BaseStatementProvider();
        statementProvider
                .append(FIND)
                .appendWithSingleArg("WHERE user_id = ?", userId);
        try (var prepareStatement = statementProvider.createPreparedStatement(connectionPool.getConnection())) {
            var resultSet = prepareStatement.executeQuery();
            while (resultSet.next()) {
                orders.add(extractor.extractData(resultSet));
            }
        }
        return orders;
    }

    public List<Order> findAllByCar(Long carId) throws SQLException, ConnectionPoolException {
        List<Order> orders = new ArrayList<>();
        var statementProvider = new BaseStatementProvider();
        statementProvider
                .append(FIND)
                .appendWithSingleArg("WHERE car_id = ?", carId);
        try (var prepareStatement = statementProvider.createPreparedStatement(connectionPool.getConnection())) {
            var resultSet = prepareStatement.executeQuery();
            while (resultSet.next()) {
                orders.add(extractor.extractData(resultSet));
            }
        }
        return orders;
    }

    public List<Order> findAllByStatus(String status) throws SQLException, ConnectionPoolException {
        List<Order> orders = new ArrayList<>();
        var statementProvider = new BaseStatementProvider();
        statementProvider
                .append(FIND)
                .appendWithSingleArg("WHERE status LIKE  ?", status);
        try (var prepareStatement = statementProvider.createPreparedStatement(connectionPool.getConnection())) {
            var resultSet = prepareStatement.executeQuery();
            while (resultSet.next()) {
                orders.add(extractor.extractData(resultSet));
            }
        }
        return orders;
    }

    public List<Order> findAllBetweenDates(Instant firstDate, Instant secondDate) throws SQLException, ConnectionPoolException {
        List<Order> orders = new ArrayList<>();
        var statementProvider = new BaseStatementProvider();
        statementProvider
                .append(FIND)
                .appendWithMultipleArgs("WHERE date BETWEEN ? AND ?", firstDate, secondDate);
        try (var prepareStatement = statementProvider.createPreparedStatement(connectionPool.getConnection())) {
            var resultSet = prepareStatement.executeQuery();
            while (resultSet.next()) {
                orders.add(extractor.extractData(resultSet));
            }
        }
        return orders;
    }

    public List<Order> findAllWithAccidents() throws SQLException, ConnectionPoolException {
        List<Order> orders = new ArrayList<>();
        var statementProvider = new BaseStatementProvider();
        statementProvider
                .append(FIND)
                .append("WHERE id IN (SELECT order_id from accident)");
        try (var prepareStatement = statementProvider.createPreparedStatement(connectionPool.getConnection())) {
            var resultSet = prepareStatement.executeQuery();
            while (resultSet.next()) {
                orders.add(extractor.extractData(resultSet));
            }
        }
        return orders;
    }
}