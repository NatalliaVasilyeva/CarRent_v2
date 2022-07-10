package com.dmdev.natalliavasilyeva.persistence.repository.jpa.dao;


import com.dmdev.natalliavasilyeva.connection.ConnectionPool;
import com.dmdev.natalliavasilyeva.connection.exception.ConnectionPoolException;
import com.dmdev.natalliavasilyeva.domain.jpa.Car;
import com.dmdev.natalliavasilyeva.persistence.repository.BaseStatementProvider;
import com.dmdev.natalliavasilyeva.persistence.utils.ParseObjectUtils;
import com.dmdev.natalliavasilyeva.persistence.repository.jpa.Repository;
import com.dmdev.natalliavasilyeva.persistence.repository.jpa.rowmapper.CarResultExtractor;

import java.sql.SQLException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public class CarRepositoryImpl implements Repository<Car, Long> {
    ConnectionPool connectionPool;
    CarResultExtractor extractor;


    public CarRepositoryImpl(ConnectionPool connectionPool) {
        this.connectionPool = ConnectionPool.getInstance();
        this.extractor = new CarResultExtractor();
    }

    private final static String FIND = "" +
            "SELECT c.id, c.model_id, c.color, c.year, c.car_number, c.vin, c.is_repaired, c.image\n" +
            "FROM car c\n";

    private final static String CREATE = "" +
            "INSERT INTO car(model_id, color, year, car_number, vin, is_repaired, image) values (?, ?, ?, ?, ?, ?, ?)";

    private final static String UPDATE = "" +
            "UPDATE car SET model_id = ?, color = ?, year = ?, car_number = ?, vin = ?, is_repaired = ?, image = ? WHERE id = ?";

    private final static String DELETE = "" +
            "DELETE FROM car WHERE id = ?";


    @Override
    public Optional<Car> findById(Long id) throws SQLException, ConnectionPoolException {

        var statementProvider = new BaseStatementProvider();
        statementProvider
                .append(FIND)
                .appendWithSingleArg("WHERE c.id = ?", id);
        try (var prepareStatement = statementProvider.createPreparedStatement(connectionPool.getConnection())) {
            var resultSet = prepareStatement.executeQuery();
            Car car = null;
            if (resultSet.next()) {
                car = extractor.extractData(resultSet);
            }
            return Optional.ofNullable(car);
        }
    }

    @Override
    public List<Car> findAll() throws SQLException, ConnectionPoolException {
        List<Car> cars = new ArrayList<>();
        var statementProvider = new BaseStatementProvider();
        statementProvider
                .append(FIND);
        try (var prepareStatement = statementProvider.createPreparedStatement(connectionPool.getConnection())) {
            var resultSet = prepareStatement.executeQuery();
            while (resultSet.next()) {
                cars.add(extractor.extractData(resultSet));
            }
        }
        return cars;
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
    public Optional<Car> delete(Car car) throws ConnectionPoolException, SQLException {
        var statementProvider = new BaseStatementProvider();
        statementProvider
                .appendWithSingleArg(DELETE, car.getId())
                .append("RETURNING id, model_id, color, year, car_number, vin, is_repaired, image");
        try (var prepareStatement = statementProvider.createPreparedStatementWithGeneratedKeys(connectionPool.getConnection())) {
            prepareStatement.executeUpdate();
            var generatedKeys = prepareStatement.getGeneratedKeys();
            Car removedCar = null;
            if (generatedKeys.next()) {
                removedCar = extractor.extractData(generatedKeys);
            }
            return Optional.ofNullable(removedCar);
        }
    }

    @Override
    public Optional<Car> update(Car car) throws ConnectionPoolException, SQLException {
        List<Object> values = ParseObjectUtils.getFieldObjectsWithoutId(car);
        values.add(car.getId());
        var statementProvider = new BaseStatementProvider();
        statementProvider
                .appendWithMultipleArgs(UPDATE, values);
        try (var prepareStatement = statementProvider.createPreparedStatement(connectionPool.getConnection())) {
            return prepareStatement.executeUpdate() == 1 ? Optional.of(car) : Optional.empty();
        }
    }

    @Override
    public Optional<Car> save(Car car) throws ConnectionPoolException, SQLException {
        List<Object> values = ParseObjectUtils.getFieldObjectsWithoutId(car);
        var statementProvider = new BaseStatementProvider();
        statementProvider
                .appendWithMultipleArgs(CREATE, values);
        try (var prepareStatement = statementProvider.createPreparedStatementWithGeneratedKeys(connectionPool.getConnection())) {
            prepareStatement.executeUpdate();
            var generatedKeys = prepareStatement.getGeneratedKeys();
            Car savedCar = null;
            if (generatedKeys.next()) {
                savedCar = extractor.extractData(generatedKeys);
            }
            return Optional.ofNullable(savedCar);
        }
    }

    public List<Car> findAllByYear(String year) throws SQLException, ConnectionPoolException {
        List<Car> cars = new ArrayList<>();
        var statementProvider = new BaseStatementProvider();
        statementProvider
                .append(FIND)
                .appendWithSingleArg("WHERE c.year LIKE ?", year);
        try (var prepareStatement = statementProvider.createPreparedStatement(connectionPool.getConnection())) {
            var resultSet = prepareStatement.executeQuery();
            while (resultSet.next()) {
                cars.add(extractor.extractData(resultSet));
            }
        }
        return cars;
    }

    public List<Car> findAllUnderRepair() throws SQLException, ConnectionPoolException {
        List<Car> cars = new ArrayList<>();
        var statementProvider = new BaseStatementProvider();
        statementProvider
                .append(FIND)
                .append("WHERE c.is_repaired = true");
        try (var prepareStatement = statementProvider.createPreparedStatement(connectionPool.getConnection())) {
            var resultSet = prepareStatement.executeQuery();
            while (resultSet.next()) {
                cars.add(extractor.extractData(resultSet));
            }
        }
        return cars;
    }

    public Optional<Car> findByCarNumber(String car_number) throws SQLException, ConnectionPoolException {

        var statementProvider = new BaseStatementProvider();
        statementProvider
                .append(FIND)
                .appendWithSingleArg("WHERE c.car_number LIKE ?", car_number);
        try (var prepareStatement = statementProvider.createPreparedStatement(connectionPool.getConnection())) {
            var resultSet = prepareStatement.executeQuery();
            Car car = null;
            if (resultSet.next()) {
                car = extractor.extractData(resultSet);
            }
            return Optional.ofNullable(car);
        }
    }

    public List<Car> findAllByBrandName(String brandName) throws SQLException, ConnectionPoolException {
        List<Car> cars = new ArrayList<>();
        var statementProvider = new BaseStatementProvider();
        statementProvider
                .append(FIND)
                .appendWithSingleArg("LEFT JOIN model m ON c.model_id = m.id \n" +
                        "LEFT JOIN brand  b ON m.brand_id = b.id \n" +
                        "WHERE b.name LIKE ?", brandName);
        try (var prepareStatement = statementProvider.createPreparedStatement(connectionPool.getConnection())) {
            var resultSet = prepareStatement.executeQuery();
            while (resultSet.next()) {
                cars.add(extractor.extractData(resultSet));
            }
        }
        return cars;
    }

    public List<Car> findAllByBrandId(Long brandId) throws SQLException, ConnectionPoolException {
        List<Car> cars = new ArrayList<>();
        var statementProvider = new BaseStatementProvider();
        statementProvider
                .append(FIND)
                .appendWithSingleArg("LEFT JOIN model m ON c.model_id = m.id \n" +
                        "LEFT JOIN brand  b ON m.brand_id = b.id \n" +
                        "WHERE b.id = ?", brandId);
        try (var prepareStatement = statementProvider.createPreparedStatement(connectionPool.getConnection())) {
            var resultSet = prepareStatement.executeQuery();
            while (resultSet.next()) {
                cars.add(extractor.extractData(resultSet));
            }
        }
        return cars;
    }

    public List<Car> findAllByCategoryName(String categoryName) throws SQLException, ConnectionPoolException {
        List<Car> cars = new ArrayList<>();
        var statementProvider = new BaseStatementProvider();
        statementProvider
                .append(FIND)
                .appendWithSingleArg("LEFT JOIN model m ON c.model_id = m.id \n" +
                        "LEFT JOIN categories ct ON m.category_id = ct.id \n" +
                        "WHERE ct.name LIKE ?", categoryName);
        try (var prepareStatement = statementProvider.createPreparedStatement(connectionPool.getConnection())) {
            var resultSet = prepareStatement.executeQuery();
            while (resultSet.next()) {
                cars.add(extractor.extractData(resultSet));
            }
        }
        return cars;
    }

    public boolean isCarAvailable(Long carId, Instant startDate, Instant endDate) throws SQLException, ConnectionPoolException {
        var statementProvider = new BaseStatementProvider();
        statementProvider
                .append(FIND)
                .appendWithMultipleArgs("LEFT JOIN orders o ON o.car_id = c.id \n" +
                        "WHERE o.car_id = ? AND o.id IN \n" +
                        "(SELECT order_id FROM carrentaltime crt WHERE crt.start_rental_date <= ? AND\n" +
                        "crt.end_rental_date >= ?)", carId, endDate, startDate);
        try (var prepareStatement = statementProvider.createPreparedStatement(connectionPool.getConnection())) {
            var resultSet = prepareStatement.executeQuery();
            return !resultSet.isBeforeFirst();
        }
    }
}