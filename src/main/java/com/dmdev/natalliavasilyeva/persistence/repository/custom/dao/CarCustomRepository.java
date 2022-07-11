package com.dmdev.natalliavasilyeva.persistence.repository.custom.dao;


import com.dmdev.natalliavasilyeva.connection.ConnectionPool;
import com.dmdev.natalliavasilyeva.connection.exception.ConnectionPoolException;
import com.dmdev.natalliavasilyeva.domain.model.Car;
import com.dmdev.natalliavasilyeva.persistence.repository.BaseStatementProvider;
import com.dmdev.natalliavasilyeva.persistence.repository.custom.GenericCustomRepository;
import com.dmdev.natalliavasilyeva.persistence.repository.custom.rowmapper.CarResultExtractor;

import java.sql.SQLException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CarCustomRepository implements GenericCustomRepository<Car, Long> {
    ConnectionPool connectionPool;
    CarResultExtractor extractor;

    public CarCustomRepository() {
        this.connectionPool = ConnectionPool.getInstance();
        this.extractor = new CarResultExtractor();
    }

    private final static String FIND_WITH_ACCIDENTS = "" +
            "SELECT c.id                              as car_id,\n" +
            "       c.color,\n" +
            "       c.year,\n" +
            "       c.car_number,\n" +
            "       c.vin,\n" +
            "       c.is_repaired,\n" +
            "       c.image,\n" +
            "       m.id                             as model_id,\n" +
            "       m.name                           as model_name,\n" +
            "       m.transmission,\n" +
            "       m.engine_type,\n" +
            "       b.id                             as brand_id,\n" +
            "       b.name                           as brand_name,\n" +
            "       ct.id                            as category_id,\n" +
            "       ct.name                          as category_name,\n" +
            "       p.id                             as price_id,\n" +
            "       p.price,\n" +
            "       json_agg(row_to_json(accident_des)) as accident_description\n" +
            "FROM car c\n" +
            "       LEFT JOIN model m ON m.id = c.model_id\n" +
            "       LEFT JOIN brand b ON b.id = m.brand_id\n" +
            "       LEFT JOIN categories ct ON ct.id = m.category_id\n" +
            "       LEFT JOIN price p ON p.id = ct.price_id\n" +
            "       LEFT JOIN (SELECT a.id                      as accident_id,\n" +
            "                         a.accident_date,\n" +
            "                         a.description,\n" +
            "                         a.damage,\n" +
            "                         c.id                      as car_id,\n" +
            "                         o.id                      as order_id\n" +
            "                  FROM accident a\n" +
            "                         LEFT JOIN orders o ON o.id = a.order_id\n" +
            "                         LEFT JOIN car c ON o.car_id = c.id) AS accident_des\n" +
            "                  ON c.id = accident_des.car_id\n";


    private final static String FIND_WITHOUT_ACCIDENTS = "" +
            "SELECT c.id                              as car_id,\n" +
            "       c.color,\n" +
            "       c.year,\n" +
            "       c.car_number,\n" +
            "       c.vin,\n" +
            "       c.is_repaired,\n" +
            "       c.image,\n" +
            "       m.id                             as model_id,\n" +
            "       m.name                           as model_name,\n" +
            "       m.transmission,\n" +
            "       m.engine_type,\n" +
            "       b.id                             as brand_id,\n" +
            "       b.name                           as brand_name,\n" +
            "       ct.id                            as category_id,\n" +
            "       ct.name                          as category_name,\n" +
            "       p.id                             as price_id,\n" +
            "       p.price\n" +
            "FROM car c\n" +
            "       LEFT JOIN model m ON m.id = c.model_id\n" +
            "       LEFT JOIN brand b ON b.id = m.brand_id\n" +
            "       LEFT JOIN categories ct ON ct.id = m.category_id\n" +
            "       LEFT JOIN price p ON p.id = ct.price_id\n";


    @Override
    public List<Car> findAll() throws SQLException, ConnectionPoolException {
        List<Car> cars = new ArrayList<>();
        var statementProvider = new BaseStatementProvider();
        statementProvider
                .append(FIND_WITH_ACCIDENTS)
                .append("GROUP BY c.id, m.id, b.id, ct.id, p.id");
        try (var prepareStatement = statementProvider.createPreparedStatement(connectionPool.getConnection())) {
            var resultSet = prepareStatement.executeQuery();
            while (resultSet.next()) {
                cars.add(extractor.extractData(resultSet));
            }
        }
        return cars;
    }

    @Override
    public Optional<Car> findById(Long id) throws SQLException, ConnectionPoolException {
        var statementProvider = new BaseStatementProvider();
        statementProvider
                .append(FIND_WITH_ACCIDENTS)
                .appendWithSingleArg("WHERE c.id = ?", id)
                .append("GROUP BY c.id, m.id, b.id, ct.id, p.id");
        try (var prepareStatement = statementProvider.createPreparedStatement(connectionPool.getConnection())) {
            var resultSet = prepareStatement.executeQuery();
            Car car = null;
            if (resultSet.next()) {
                car = extractor.extractData(resultSet);
            }
            return Optional.ofNullable(car);
        }
    }

    public Optional<Car> findByCarNumber(String carNumber) throws SQLException, ConnectionPoolException {
        var statementProvider = new BaseStatementProvider();
        statementProvider
                .append(FIND_WITHOUT_ACCIDENTS)
                .appendWithSingleArg("WHERE c.car_number LIKE ?", carNumber)
                .append("GROUP BY c.id, m.id, b.id, ct.id, p.id");
        try (var prepareStatement = statementProvider.createPreparedStatement(connectionPool.getConnection())) {
            var resultSet = prepareStatement.executeQuery();
            Car car = null;
            if (resultSet.next()) {
                car = extractor.extractData(resultSet);
            }
            return Optional.ofNullable(car);
        }
    }

    public List<Car> findAllByYear(String year) throws SQLException, ConnectionPoolException {
        List<Car> cars = new ArrayList<>();
        var statementProvider = new BaseStatementProvider();
        statementProvider
                .append(FIND_WITHOUT_ACCIDENTS)
                .appendWithSingleArg("WHERE c.year LIKE ?", year)
                .append("GROUP BY c.id, m.id, b.id, ct.id, p.id");
        try (var prepareStatement = statementProvider.createPreparedStatement(connectionPool.getConnection())) {
            var resultSet = prepareStatement.executeQuery();
            while (resultSet.next()) {
                cars.add(extractor.extractData(resultSet));
            }
        }
        return cars;
    }

    public List<Car> findAllBetweenYears(String first, String second) throws SQLException, ConnectionPoolException {
        List<Car> cars = new ArrayList<>();
        var statementProvider = new BaseStatementProvider();
        statementProvider
                .append(FIND_WITHOUT_ACCIDENTS)
                .appendWithMultipleArgs("WHERE c.year BETWEEN ? AND ?", first, second)
                .append("GROUP BY c.id, m.id, b.id, ct.id, p.id");
        try (var prepareStatement = statementProvider.createPreparedStatement(connectionPool.getConnection())) {
            var resultSet = prepareStatement.executeQuery();
            while (resultSet.next()) {
                cars.add(extractor.extractData(resultSet));
            }
        }
        return cars;
    }

    public List<Car> isCarAvailable(Long carId, Instant startDate, Instant endDate) throws SQLException, ConnectionPoolException {
        List<Car> cars = new ArrayList<>();
        var statementProvider = new BaseStatementProvider();
        statementProvider
                .append(FIND_WITHOUT_ACCIDENTS)
                .appendWithMultipleArgs("LEFT JOIN orders o ON o.car_id = c.id \n" +
                "WHERE o.car_id = ? AND o.id IN \n" +
                "(SELECT order_id FROM carrentaltime crt WHERE crt.start_rental_date <= ? AND\n" +
                "crt.end_rental_date >= ?)", carId, endDate, startDate)
                .append("GROUP BY c.id, m.id, b.id, ct.id, p.id");
        try (var prepareStatement = statementProvider.createPreparedStatement(connectionPool.getConnection())) {
            var resultSet = prepareStatement.executeQuery();
            while (resultSet.next()) {
                cars.add(extractor.extractData(resultSet));
            }
        }
        return cars;
    }

    public List<Car> findAllByBrandName(String brandName) throws SQLException, ConnectionPoolException {
        List<Car> cars = new ArrayList<>();
        var statementProvider = new BaseStatementProvider();
        statementProvider
                .append(FIND_WITHOUT_ACCIDENTS)
                .appendWithSingleArg("WHERE b.name LIKE ?", brandName)
                .append("GROUP BY c.id, m.id, b.id, ct.id, p.id");
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
                .append(FIND_WITHOUT_ACCIDENTS)
                .appendWithSingleArg("WHERE b.id = ?", brandId)
                .append("GROUP BY c.id, m.id, b.id, ct.id, p.id");
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
                .append(FIND_WITHOUT_ACCIDENTS)
                .appendWithSingleArg("WHERE ct.name LIKE ?", categoryName)
                .append("GROUP BY c.id, m.id, b.id, ct.id, p.id");
        try (var prepareStatement = statementProvider.createPreparedStatement(connectionPool.getConnection())) {
            var resultSet = prepareStatement.executeQuery();
            while (resultSet.next()) {
                cars.add(extractor.extractData(resultSet));
            }
        }
        return cars;
    }

    public List<Car> findAllWithAccidents() throws SQLException, ConnectionPoolException {
        List<Car> cars = new ArrayList<>();
        var statementProvider = new BaseStatementProvider();
        statementProvider
                .append(FIND_WITH_ACCIDENTS)
                .append("WHERE c.id IN (SELECT car_id FROM orders LEFT JOIN accident On accident.order_id = orders.id WHERE order_id IS NOT NULL)")
                .append("GROUP BY c.id, m.id, b.id, ct.id, p.id");
        try (var prepareStatement = statementProvider.createPreparedStatement(connectionPool.getConnection())) {
            var resultSet = prepareStatement.executeQuery();
            while (resultSet.next()) {
                cars.add(extractor.extractData(resultSet));
            }
        }
        return cars;
    }

    public List<Car> findAllWithoutAccidents() throws SQLException, ConnectionPoolException {
        List<Car> cars = new ArrayList<>();
        var statementProvider = new BaseStatementProvider();
        statementProvider
                .append(FIND_WITH_ACCIDENTS)
                .append("WHERE c.id NOT IN (SELECT car_id FROM orders LEFT JOIN accident On accident.order_id = orders.id WHERE order_id IS NOT NULL)")
                .append("GROUP BY c.id, m.id, b.id, ct.id, p.id");
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
                .append(FIND_WITH_ACCIDENTS)
                .append("WHERE c.is_repaired = true")
                .append("GROUP BY c.id, m.id, b.id, ct.id, p.id");
        try (var prepareStatement = statementProvider.createPreparedStatement(connectionPool.getConnection())) {
            var resultSet = prepareStatement.executeQuery();
            while (resultSet.next()) {
                cars.add(extractor.extractData(resultSet));
            }
        }
        return cars;
    }
}