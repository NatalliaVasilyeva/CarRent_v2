package com.dmdev.natalliavasilyeva.persistence.repository.custom.dao;


import com.dmdev.natalliavasilyeva.connection.ConnectionPool;
import com.dmdev.natalliavasilyeva.connection.exception.ConnectionPoolException;
import com.dmdev.natalliavasilyeva.domain.model.Order;
import com.dmdev.natalliavasilyeva.domain.model.User;
import com.dmdev.natalliavasilyeva.persistence.repository.BaseStatementProvider;
import com.dmdev.natalliavasilyeva.persistence.repository.custom.GenericCustomRepository;
import com.dmdev.natalliavasilyeva.persistence.repository.custom.rowmapper.OrderResultExtractor;
import com.dmdev.natalliavasilyeva.persistence.repository.custom.rowmapper.UserResultExtractor;

import java.sql.SQLException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class OrderCustomRepository implements GenericCustomRepository<Order, Long> {
    ConnectionPool connectionPool;
    OrderResultExtractor extractor;

    public OrderCustomRepository() {
        this.connectionPool = ConnectionPool.getInstance();
        this.extractor = new OrderResultExtractor();
    }

    private final static String FIND= "" +
            "SELECT o.id                             as order_id,\n" +
            "       o.date,\n" +
            "       o.insurance,\n" +
            "       o.order_status,\n" +
            "       o.sum,\n" +
            "       crt.start_rental_date,\n" +
            "       crt.end_rental_date,\n" +
            "       c.car_number,\n" +
            "       m.name                            as model_name,\n" +
            "       b.name                            as brand_name,\n" +
            "       ud.name,\n" +
            "       ud.surname\n,\n" +
            "       ud.phone,\n" +
            "       CASE WHEN o.id IN (SELECT order_id FROM accident) THEN true ELSE false END AS accidents\n" +
            "FROM orders o\n" +
            "       LEFT JOIN users u ON u.id = o.user_id\n" +
            "       LEFT JOIN userdetails ud ON u.id = ud.user_id\n" +
            "       LEFT JOIN carrentaltime crt ON o.id = crt.order_id\n" +
            "       LEFT JOIN car c ON c.id = o.car_id\n" +
            "       LEFT JOIN model m ON m.id = c.model_id\n" +
            "       LEFT JOIN brand b ON b.id = m.brand_id\n";

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
    public Optional<Order> findById(Long id) throws SQLException, ConnectionPoolException {
        var statementProvider = new BaseStatementProvider();
        statementProvider
                .append(FIND)
                .appendWithSingleArg("WHERE o.id = ?", id);
        try (var prepareStatement = statementProvider.createPreparedStatement(connectionPool.getConnection())) {
            var resultSet = prepareStatement.executeQuery();
            Order order = null;
            if (resultSet.next()) {
                order = extractor.extractData(resultSet);
            }
            return Optional.ofNullable(order);
        }
    }

    public List<Order> findAllByUser(Long userId) throws SQLException, ConnectionPoolException {
        List<Order> orders = new ArrayList<>();
        var statementProvider = new BaseStatementProvider();
        statementProvider
                .append(FIND)
                .appendWithSingleArg("WHERE u.id = ?", userId);
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
                .appendWithSingleArg("WHERE c.id = ?", carId);
        try (var prepareStatement = statementProvider.createPreparedStatement(connectionPool.getConnection())) {
            var resultSet = prepareStatement.executeQuery();
            while (resultSet.next()) {
                orders.add(extractor.extractData(resultSet));
            }
        }
        return orders;
    }

    public List<Order> findAllByStatus(String orderStatus) throws SQLException, ConnectionPoolException {
        List<Order> orders = new ArrayList<>();
        var statementProvider = new BaseStatementProvider();
        statementProvider
                .append(FIND)
                .appendWithSingleArg("WHERE o.order_status = ?", orderStatus);
        try (var prepareStatement = statementProvider.createPreparedStatement(connectionPool.getConnection())) {
            var resultSet = prepareStatement.executeQuery();
            while (resultSet.next()) {
                orders.add(extractor.extractData(resultSet));
            }
        }
        return orders;
    }

    public List<Order> findAllBetweenDates(Instant first, Instant second) throws SQLException, ConnectionPoolException {
        List<Order> orders = new ArrayList<>();
        var statementProvider = new BaseStatementProvider();
        statementProvider
                .append(FIND)
                .appendWithMultipleArgs("WHERE o.date BETWEEN ? AND ?", first, second);
        try (var prepareStatement = statementProvider.createPreparedStatement(connectionPool.getConnection())) {
            var resultSet = prepareStatement.executeQuery();
            while (resultSet.next()) {
                orders.add(extractor.extractData(resultSet));
            }
        }
        return orders;
    }

    public List<Order> findAllByDate(Instant date) throws SQLException, ConnectionPoolException {
        List<Order> orders = new ArrayList<>();
        var statementProvider = new BaseStatementProvider();
        statementProvider
                .append(FIND)
                .appendWithSingleArg("WHERE o.date = ?", date);
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
                .append("WHERE CASE WHEN o.id IN (SELECT order_id FROM accident) THEN true ELSE false END = true");
        try (var prepareStatement = statementProvider.createPreparedStatement(connectionPool.getConnection())) {
            var resultSet = prepareStatement.executeQuery();
            while (resultSet.next()) {
                orders.add(extractor.extractData(resultSet));
            }
        }
        return orders;
    }
}