package com.dmdev.natalliavasilyeva.persistence.repository.custom.dao;


import com.dmdev.natalliavasilyeva.connection.exception.ConnectionPoolException;
import com.dmdev.natalliavasilyeva.domain.model.Car;
import com.dmdev.natalliavasilyeva.persistence.exception.RepositoryException;
import com.dmdev.natalliavasilyeva.persistence.repository.BaseStatementProvider;
import com.dmdev.natalliavasilyeva.persistence.repository.custom.GenericCustomRepository;
import com.dmdev.natalliavasilyeva.persistence.repository.custom.rowmapper.CarResultExtractor;
import com.dmdev.natalliavasilyeva.persistence.repository.custom.rowmapper.ResultSetExtractor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

public class CarCustomRepository extends AbstractCustomRepository<Car> implements GenericCustomRepository<Car, Long> {

    private static final Logger logger = LoggerFactory.getLogger(CarCustomRepository.class);
    ResultSetExtractor<Car> extractor;

    public CarCustomRepository() {
        this.extractor = new CarResultExtractor();
    }

    private static final String FIND_WITH_ACCIDENTS = "" +
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


    private static final String FIND_WITHOUT_ACCIDENTS = "" +
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

    private static final String GROUP_BY = "" +
            "GROUP BY c.id, m.id, b.id, ct.id, p.id";


    @Override
    public List<Car> findAll() {
        var statementProvider = new BaseStatementProvider();
        statementProvider
                .append(FIND_WITH_ACCIDENTS)
                .append(GROUP_BY);
        return findAll(statementProvider, extractor);
    }

    @Override
    public Optional<Car> findById(Long id) {
        var statementProvider = new BaseStatementProvider();
        statementProvider
                .append(FIND_WITH_ACCIDENTS)
                .appendWithSingleArg("WHERE c.id = ?\n", id)
                .append(GROUP_BY);
        return findOne(statementProvider, extractor);
    }

    public Optional<Car> findByCarNumber(String carNumber) {
        var statementProvider = new BaseStatementProvider();
        statementProvider
                .append(FIND_WITHOUT_ACCIDENTS)
                .appendWithSingleArg("WHERE lower(c.car_number) LIKE ?\n", carNumber)
                .append(GROUP_BY);
        return findOne(statementProvider, extractor);
    }

    public List<Car> findAllByYear(String year) {
        var statementProvider = new BaseStatementProvider();
        statementProvider
                .append(FIND_WITHOUT_ACCIDENTS)
                .appendWithSingleArg("WHERE c.year LIKE ?\n", year)
                .append(GROUP_BY);
        return findAll(statementProvider, extractor);
    }

    public List<Car> findAllBetweenYears(String first, String second) {
        var statementProvider = new BaseStatementProvider();
        statementProvider
                .append(FIND_WITHOUT_ACCIDENTS)
                .appendWithMultipleArgs("WHERE c.year BETWEEN ? AND ?\n", first, second)
                .append(GROUP_BY);
        return findAll(statementProvider, extractor);
    }

    public boolean isCarAvailable(Long carId, Instant startDate, Instant endDate) {
        var statementProvider = new BaseStatementProvider();
        statementProvider
                .append(FIND_WITHOUT_ACCIDENTS)
                .appendWithMultipleArgs("LEFT JOIN orders o ON o.car_id = c.id \n" +
                        "WHERE o.car_id = ? AND o.id IN \n" +
                        "(SELECT order_id FROM carrentaltime crt WHERE crt.start_rental_date <= ? AND\n" +
                        "crt.end_rental_date >= ?\n)", carId, endDate, startDate)
                .append(GROUP_BY);
        try (var prepareStatement = statementProvider.createPreparedStatement(connectionPool.getConnection())) {
            var resultSet = prepareStatement.executeQuery();
            return !resultSet.isBeforeFirst();
        } catch (ConnectionPoolException | SQLException ex) {
            logger.error("IsCarAvailable custom method throws exception", ex);
            throw new RepositoryException();
        }
    }

    public List<Car> findAllByBrandName(String brandName) {
        var statementProvider = new BaseStatementProvider();
        statementProvider
                .append(FIND_WITHOUT_ACCIDENTS)
                .appendWithSingleArg("WHERE lower(b.name) LIKE ?\n", brandName)
                .append(GROUP_BY);
        return findAll(statementProvider, extractor);
    }

    public List<Car> findAllByBrandId(Long brandId) {
        var statementProvider = new BaseStatementProvider();
        statementProvider
                .append(FIND_WITHOUT_ACCIDENTS)
                .appendWithSingleArg("WHERE b.id = ?\n", brandId)
                .append(GROUP_BY);
        return findAll(statementProvider, extractor);
    }

    public List<Car> findAllByCategoryName(String categoryName) {
        var statementProvider = new BaseStatementProvider();
        statementProvider
                .append(FIND_WITHOUT_ACCIDENTS)
                .appendWithSingleArg("WHERE lower(ct.name) LIKE ?\n", categoryName)
                .append(GROUP_BY);
        return findAll(statementProvider, extractor);
    }

    public List<Car> findAllWithAccidents() {
        var statementProvider = new BaseStatementProvider();
        statementProvider
                .append(FIND_WITH_ACCIDENTS)
                .append("WHERE c.id IN (SELECT car_id FROM orders LEFT JOIN accident ON accident.order_id = orders.id WHERE order_id IS NOT NULL)\n")
                .append(GROUP_BY);
        return findAll(statementProvider, extractor);
    }

    public List<Car> findAllWithoutAccidents() {
        var statementProvider = new BaseStatementProvider();
        statementProvider
                .append(FIND_WITH_ACCIDENTS)
                .append("WHERE c.id NOT IN (SELECT car_id FROM orders LEFT JOIN accident ON accident.order_id = orders.id WHERE order_id IS NOT NULL)\n")
                .append(GROUP_BY);
        return findAll(statementProvider, extractor);
    }

    public List<Car> findAllUnderRepair() {
        var statementProvider = new BaseStatementProvider();
        statementProvider
                .append(FIND_WITH_ACCIDENTS)
                .append("WHERE c.is_repaired = true\n")
                .append(GROUP_BY);
        return findAll(statementProvider, extractor);
    }

    public List<Car> findAllAvailable() {
        var statementProvider = new BaseStatementProvider();
        statementProvider
                .append(FIND_WITH_ACCIDENTS)
                .append("WHERE c.is_repaired = false\n")
                .append(GROUP_BY);
        return findAll(statementProvider, extractor);
    }
}