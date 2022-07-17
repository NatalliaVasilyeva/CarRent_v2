package com.dmdev.natalliavasilyeva.persistence.repository.jpa.dao;


import com.dmdev.natalliavasilyeva.connection.exception.ConnectionPoolException;
import com.dmdev.natalliavasilyeva.domain.jpa.CarJpa;
import com.dmdev.natalliavasilyeva.persistence.exception.RepositoryException;
import com.dmdev.natalliavasilyeva.persistence.repository.BaseStatementProvider;
import com.dmdev.natalliavasilyeva.persistence.repository.jpa.rowmapper.ResultSetExtractor;
import com.dmdev.natalliavasilyeva.utils.ParseObjectUtils;
import com.dmdev.natalliavasilyeva.persistence.repository.jpa.GenericRepository;
import com.dmdev.natalliavasilyeva.persistence.repository.jpa.rowmapper.CarResultExtractor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;
import java.time.Instant;
import java.util.List;
import java.util.Optional;


public class CarRepository extends AbstractRepository<CarJpa> implements GenericRepository<CarJpa, Long> {

    private static final Logger logger = LoggerFactory.getLogger(CarRepository.class);
    ResultSetExtractor<CarJpa> extractor;

    public CarRepository() {
        this.extractor = new CarResultExtractor();
    }

    private static final String FIND_QUERY_PREFIX = "" +
            "SELECT c.id, c.model_id, c.color, c.year, c.car_number, c.vin, c.is_repaired, c.image\n" +
            "FROM car c\n";

    private static final String CREATE = "" +
            "INSERT INTO car(model_id, color, year, car_number, vin, is_repaired, image) values (?, ?, ?, ?, ?, ?, ?)";

    private static final String UPDATE = "" +
            "UPDATE car SET model_id = ?, color = ?, year = ?, car_number = ?, vin = ?, is_repaired = ?, image = ? WHERE id = ?";

    private static final String DELETE = "" +
            "DELETE FROM car WHERE id = ?";

    private static final String RETURNING = "" +
            "RETURNING id, model_id, color, year, car_number, vin, is_repaired, image";


    @Override
    public Optional<CarJpa> findById(Long id) {
        var statementProvider = new BaseStatementProvider();
        statementProvider
                .append(FIND_QUERY_PREFIX)
                .appendWithSingleArg("WHERE c.id = ?", id);
        return findOne(statementProvider, extractor);
    }

    @Override
    public List<CarJpa> findAll() {
        var statementProvider = new BaseStatementProvider();
        statementProvider
                .append(FIND_QUERY_PREFIX);
        return findAll(statementProvider, extractor);
    }

    @Override
    public boolean deleteById(Long id) {
        var statementProvider = new BaseStatementProvider();
        statementProvider
                .appendWithSingleArg(DELETE, id);
        return deleteById(statementProvider);
    }

    @Override
    public Optional<CarJpa> delete(CarJpa car) {
        var statementProvider = new BaseStatementProvider();
        statementProvider
                .appendWithSingleArg(DELETE, car.getId())
                .append(RETURNING);
        return delete(statementProvider, extractor);
    }

    @Override
    public Optional<CarJpa> update(CarJpa car) {
        List<Object> values = ParseObjectUtils.getFieldObjectsWithoutId(car);
        values.add(car.getId());
        var statementProvider = new BaseStatementProvider();
        statementProvider
                .appendWithMultipleArgs(UPDATE, values);
        return update(car, statementProvider);
    }

    @Override
    public Optional<CarJpa> save(CarJpa car) {
        List<Object> values = ParseObjectUtils.getFieldObjectsWithoutId(car);
        var statementProvider = new BaseStatementProvider();
        statementProvider
                .appendWithMultipleArgs(CREATE, values)
                .append(RETURNING);
        return save(statementProvider, extractor);
    }

    public List<CarJpa> findAllByYear(String year) {
        var statementProvider = new BaseStatementProvider();
        statementProvider
                .append(FIND_QUERY_PREFIX)
                .appendWithSingleArg("WHERE c.year LIKE ?", year);
        return findAll(statementProvider, extractor);
    }

    public List<CarJpa> findAllUnderRepair() {
        var statementProvider = new BaseStatementProvider();
        statementProvider
                .append(FIND_QUERY_PREFIX)
                .append("WHERE c.is_repaired = true");
        return findAll(statementProvider, extractor);
    }

    public Optional<CarJpa> findByCarNumber(String car_number) {
        var statementProvider = new BaseStatementProvider();
        statementProvider
                .append(FIND_QUERY_PREFIX)
                .appendWithSingleArg("WHERE c.car_number LIKE ?", car_number);
        return findOne(statementProvider, extractor);
    }

    public List<CarJpa> findAllByBrandName(String brandName) {
        var statementProvider = new BaseStatementProvider();
        statementProvider
                .append(FIND_QUERY_PREFIX)
                .appendWithSingleArg("LEFT JOIN model m ON c.model_id = m.id \n" +
                        "LEFT JOIN brand  b ON m.brand_id = b.id \n" +
                        "WHERE b.name LIKE ?", brandName);
        return findAll(statementProvider, extractor);
    }

    public List<CarJpa> findAllByBrandId(Long brandId) {
        var statementProvider = new BaseStatementProvider();
        statementProvider
                .append(FIND_QUERY_PREFIX)
                .appendWithSingleArg("LEFT JOIN model m ON c.model_id = m.id \n" +
                        "LEFT JOIN brand  b ON m.brand_id = b.id \n" +
                        "WHERE b.id = ?", brandId);
        return findAll(statementProvider, extractor);
    }

    public List<CarJpa> findAllByCategoryName(String categoryName) {
        var statementProvider = new BaseStatementProvider();
        statementProvider
                .append(FIND_QUERY_PREFIX)
                .appendWithSingleArg("LEFT JOIN model m ON c.model_id = m.id \n" +
                        "LEFT JOIN categories ct ON m.category_id = ct.id \n" +
                        "WHERE ct.name LIKE ?", categoryName);
        return findAll(statementProvider, extractor);
    }

    public boolean isCarAvailable(Long carId, Instant startDate, Instant endDate) {
        var statementProvider = new BaseStatementProvider();
        statementProvider
                .append(FIND_QUERY_PREFIX)
                .appendWithMultipleArgs("LEFT JOIN orders o ON o.car_id = c.id \n" +
                        "WHERE o.car_id = ? AND o.id IN \n" +
                        "(SELECT order_id FROM carrentaltime crt WHERE crt.start_rental_date <= ? AND\n" +
                        "crt.end_rental_date >= ?)", carId, endDate, startDate);
        try (var prepareStatement = statementProvider.createPreparedStatement(connectionPool.getConnection())) {
            var resultSet = prepareStatement.executeQuery();
            return !resultSet.isBeforeFirst();
        } catch (ConnectionPoolException | SQLException ex) {
            logger.error("IsCarAvailable car jpa method throws exception", ex);
            throw new RepositoryException();
        }
    }
}