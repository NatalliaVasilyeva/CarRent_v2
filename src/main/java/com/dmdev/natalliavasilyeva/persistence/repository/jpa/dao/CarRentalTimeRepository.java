package com.dmdev.natalliavasilyeva.persistence.repository.jpa.dao;


import com.dmdev.natalliavasilyeva.domain.jpa.CarRentalTime;
import com.dmdev.natalliavasilyeva.persistence.repository.BaseStatementProvider;
import com.dmdev.natalliavasilyeva.persistence.repository.jpa.rowmapper.ResultSetExtractor;
import com.dmdev.natalliavasilyeva.persistence.utils.ParseObjectUtils;
import com.dmdev.natalliavasilyeva.persistence.repository.jpa.GenericRepository;
import com.dmdev.natalliavasilyeva.persistence.repository.jpa.rowmapper.CarRentalTimeResultExtractor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;


public class CarRentalTimeRepository extends AbstractRepository<CarRentalTime> implements GenericRepository<CarRentalTime, Long> {

    private static final Logger logger = LoggerFactory.getLogger(CarRentalTimeRepository.class);
    ResultSetExtractor<CarRentalTime> extractor;

    public CarRentalTimeRepository() {
        this.extractor = new CarRentalTimeResultExtractor();
    }

    private static final String FIND_QUERY_PREFIX = "" +
            "SELECT crt.id, crt.order_id, crt.start_rental_date, crt.end_rental_date\n" +
            "FROM carrentaltime crt\n";

    private static final String CREATE = "" +
            "INSERT INTO carrentaltime(order_id, start_rental_date, end_rental_date) values (?, ?, ?)";

    private static final String UPDATE = "" +
            "UPDATE carrentaltime SET order_id = ?, start_rental_date = ?, end_rental_date = ? WHERE id = ?";

    private static final String DELETE = "" +
            "DELETE FROM carrentaltime WHERE id = ?";

    private static final String RETURNING = "" +
            "RETURNING id, order_id, start_rental_date, end_rental_date";

    @Override
    public Optional<CarRentalTime> findById(Long id) {
        var statementProvider = new BaseStatementProvider();
        statementProvider
                .append(FIND_QUERY_PREFIX)
                .appendWithSingleArg("WHERE crt.id = ?", id);
        return findOne(statementProvider, extractor);
    }

    @Override
    public List<CarRentalTime> findAll() {
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
    public Optional<CarRentalTime> delete(CarRentalTime carRentalTime) {
        var statementProvider = new BaseStatementProvider();
        statementProvider
                .appendWithSingleArg(DELETE, carRentalTime.getId())
                .append(RETURNING);
        return delete(statementProvider, extractor);
    }

    @Override
    public Optional<CarRentalTime> update(CarRentalTime carRentalTime) {
        List<Object> values = ParseObjectUtils.getFieldObjectsWithoutId(carRentalTime);
        values.add(carRentalTime.getId());
        var statementProvider = new BaseStatementProvider();
        statementProvider
                .appendWithMultipleArgs(UPDATE, values);
        return update(carRentalTime, statementProvider);
    }

    @Override
    public Optional<CarRentalTime> save(CarRentalTime carRentalTime) {
        List<Object> values = ParseObjectUtils.getFieldObjectsWithoutId(carRentalTime);
        var statementProvider = new BaseStatementProvider();
        statementProvider
                .appendWithMultipleArgs(CREATE, values)
                .append(RETURNING);
        return save(statementProvider, extractor);
    }

    public Optional<CarRentalTime> findByOrderId(Long orderId) {
        var statementProvider = new BaseStatementProvider();
        statementProvider
                .append(FIND_QUERY_PREFIX)
                .appendWithSingleArg("WHERE crt.order_id = ?", orderId);
        return findOne(statementProvider, extractor);
    }
}