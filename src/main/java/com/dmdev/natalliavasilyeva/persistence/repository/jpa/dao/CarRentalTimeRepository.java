package com.dmdev.natalliavasilyeva.persistence.repository.jpa.dao;


import com.dmdev.natalliavasilyeva.connection.ConnectionPool;
import com.dmdev.natalliavasilyeva.connection.exception.ConnectionPoolException;
import com.dmdev.natalliavasilyeva.domain.jpa.CarRentalTime;
import com.dmdev.natalliavasilyeva.persistence.repository.BaseStatementProvider;
import com.dmdev.natalliavasilyeva.persistence.utils.ParseObjectUtils;
import com.dmdev.natalliavasilyeva.persistence.repository.jpa.GenericRepository;
import com.dmdev.natalliavasilyeva.persistence.repository.jpa.rowmapper.CarRentalTimeResultExtractor;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public class CarRentalTimeRepository implements GenericRepository<CarRentalTime, Long> {
    ConnectionPool connectionPool;
    CarRentalTimeResultExtractor extractor;

    public CarRentalTimeRepository() {
        this.connectionPool = ConnectionPool.getInstance();
        this.extractor = new CarRentalTimeResultExtractor();
    }

    private final static String FIND = "" +
            "SELECT crt.id, crt.order_id, crt.start_rental_date, crt.end_rental_date\n" +
            "FROM carrentaltime crt\n";

    private final static String CREATE = "" +
            "INSERT INTO carrentaltime(order_id, start_rental_date, end_rental_date) values (?, ?, ?)";

    private final static String UPDATE = "" +
            "UPDATE carrentaltime SET order_id = ?, start_rental_date = ?, end_rental_date = ? WHERE id = ?";

    private final static String DELETE = "" +
            "DELETE FROM carrentaltime WHERE id = ?";


    @Override
    public Optional<CarRentalTime> findById(Long id) throws SQLException, ConnectionPoolException {

        var statementProvider = new BaseStatementProvider();
        statementProvider
                .append(FIND)
                .appendWithSingleArg("WHERE crt.id = ?", id);
        try (var prepareStatement = statementProvider.createPreparedStatement(connectionPool.getConnection())) {
            var resultSet = prepareStatement.executeQuery();
            CarRentalTime carRentalTime = null;
            if (resultSet.next()) {
                carRentalTime = extractor.extractData(resultSet);
            }
            return Optional.ofNullable(carRentalTime);
        }
    }


    @Override
    public List<CarRentalTime> findAll() throws SQLException, ConnectionPoolException {
        List<CarRentalTime> carRentalTimes = new ArrayList<>();
        var statementProvider = new BaseStatementProvider();
        statementProvider
                .append(FIND);
        try (var prepareStatement = statementProvider.createPreparedStatement(connectionPool.getConnection())) {
            var resultSet = prepareStatement.executeQuery();
            while (resultSet.next()) {
                carRentalTimes.add(extractor.extractData(resultSet));
            }
        }
        return carRentalTimes;
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
    public Optional<CarRentalTime> delete(CarRentalTime carRentalTime) throws ConnectionPoolException, SQLException {
        var statementProvider = new BaseStatementProvider();
        statementProvider
                .appendWithSingleArg(DELETE, carRentalTime.getId())
                .append("RETURNING id, order_id, start_rental_date, end_rental_date");
        try (var prepareStatement = statementProvider.createPreparedStatementWithGeneratedKeys(connectionPool.getConnection())) {
            prepareStatement.executeUpdate();
            var generatedKeys = prepareStatement.getGeneratedKeys();
            CarRentalTime removedCarRentalTime = null;
            if (generatedKeys.next()) {
                removedCarRentalTime = extractor.extractData(generatedKeys);
            }
            return Optional.ofNullable(removedCarRentalTime);
        }
    }

    @Override
    public Optional<CarRentalTime> update(CarRentalTime carRentalTime) throws ConnectionPoolException, SQLException {
        List<Object> values = ParseObjectUtils.getFieldObjectsWithoutId(carRentalTime);
        values.add(carRentalTime.getId());
        var statementProvider = new BaseStatementProvider();
        statementProvider
                .appendWithMultipleArgs(UPDATE, values);
        try (var prepareStatement = statementProvider.createPreparedStatement(connectionPool.getConnection())) {
            return prepareStatement.executeUpdate() == 1 ? Optional.of(carRentalTime) : Optional.empty();
        }
    }


    @Override
    public Optional<CarRentalTime> save(CarRentalTime carRentalTime) throws ConnectionPoolException, SQLException {
        List<Object> values = ParseObjectUtils.getFieldObjectsWithoutId(carRentalTime);
        var statementProvider = new BaseStatementProvider();
        statementProvider
                .appendWithMultipleArgs(CREATE, values);
        try (var prepareStatement = statementProvider.createPreparedStatementWithGeneratedKeys(connectionPool.getConnection())) {
            prepareStatement.executeUpdate();
            var generatedKeys = prepareStatement.getGeneratedKeys();
            CarRentalTime savedCarRentalTime = null;
            if (generatedKeys.next()) {
                savedCarRentalTime = extractor.extractData(generatedKeys);
            }
            return Optional.ofNullable(savedCarRentalTime);
        }
    }

    public Optional<CarRentalTime> findByOrderId(Long orderId) throws SQLException, ConnectionPoolException {

        var statementProvider = new BaseStatementProvider();
        statementProvider
                .append(FIND)
                .appendWithSingleArg("WHERE crt.order_id = ?", orderId);
        try (var prepareStatement = statementProvider.createPreparedStatement(connectionPool.getConnection())) {
            var resultSet = prepareStatement.executeQuery();
            CarRentalTime carRentalTime = null;
            if (resultSet.next()) {
                carRentalTime = extractor.extractData(resultSet);
            }
            return Optional.ofNullable(carRentalTime);
        }
    }
}