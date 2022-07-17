package com.dmdev.natalliavasilyeva.persistence.repository.jpa.dao;


import com.dmdev.natalliavasilyeva.domain.jpa.OrderJpa;
import com.dmdev.natalliavasilyeva.persistence.repository.BaseStatementProvider;
import com.dmdev.natalliavasilyeva.persistence.repository.jpa.rowmapper.ResultSetExtractor;
import com.dmdev.natalliavasilyeva.utils.ParseObjectUtils;
import com.dmdev.natalliavasilyeva.persistence.repository.jpa.GenericRepository;
import com.dmdev.natalliavasilyeva.persistence.repository.jpa.rowmapper.OrderResultExtractor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Instant;
import java.util.List;
import java.util.Optional;


public class OrderRepository extends AbstractRepository<OrderJpa> implements GenericRepository<OrderJpa, Long> {

    private static final Logger logger = LoggerFactory.getLogger(OrderRepository.class);
    ResultSetExtractor<OrderJpa> extractor;

    public OrderRepository() {
        this.extractor = new OrderResultExtractor();
    }

    private static final String FIND_QUERY_PREFIX = "" +
            "SELECT id, date, user_id, car_id, passport, insurance, order_status, sum \n" +
            "FROM orders \n";

    private static final String CREATE = "" +
            "INSERT INTO orders(date, user_id, car_id, passport, insurance, order_status, sum) values (?, ?, ?, ?, ?, ?, ?)";

    private static final String UPDATE = "" +
            "UPDATE orders SET date = ?, user_id = ?, car_id = ?, passport = ?, insurance = ?, order_status = ?, sum = ? WHERE id = ?";

    private static final String DELETE = "" +
            "DELETE FROM orders WHERE id = ?";

    private static final String RETURNING = "" +
            "RETURNING id, date, user_id, car_id, passport, insurance, order_status, sum";


    @Override
    public Optional<OrderJpa> findById(Long id) {
        var statementProvider = new BaseStatementProvider();
        statementProvider
                .append(FIND_QUERY_PREFIX)
                .appendWithSingleArg("WHERE id = ?", id);
        return findOne(statementProvider, extractor);
    }

    @Override
    public List<OrderJpa> findAll() {
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
    public Optional<OrderJpa> delete(OrderJpa orderJpa) {
        var statementProvider = new BaseStatementProvider();
        statementProvider
                .appendWithSingleArg(DELETE, orderJpa.getId())
                .append(RETURNING);
        return delete(statementProvider, extractor);
    }

    @Override
    public Optional<OrderJpa> update(OrderJpa orderJpa) {
        List<Object> values = ParseObjectUtils.getFieldObjectsWithoutId(orderJpa);
        values.add(orderJpa.getId());
        var statementProvider = new BaseStatementProvider();
        statementProvider
                .appendWithMultipleArgs(UPDATE, values);
        return update(orderJpa, statementProvider);
    }

    @Override
    public Optional<OrderJpa> save(OrderJpa orderJpa) {
        List<Object> values = ParseObjectUtils.getFieldObjectsWithoutId(orderJpa);
        var statementProvider = new BaseStatementProvider();
        statementProvider
                .appendWithMultipleArgs(CREATE, values)
                .append(RETURNING);
        return save(statementProvider, extractor);
    }

    public List<OrderJpa> findAllByUser(Long userId) {
        var statementProvider = new BaseStatementProvider();
        statementProvider
                .append(FIND_QUERY_PREFIX)
                .appendWithSingleArg("WHERE user_id = ?", userId);
        return findAll(statementProvider, extractor);
    }

    public List<OrderJpa> findAllByCar(Long carId) {
        var statementProvider = new BaseStatementProvider();
        statementProvider
                .append(FIND_QUERY_PREFIX)
                .appendWithSingleArg("WHERE car_id = ?", carId);
        return findAll(statementProvider, extractor);
    }

    public List<OrderJpa> findAllByStatus(String status) {
        var statementProvider = new BaseStatementProvider();
        statementProvider
                .append(FIND_QUERY_PREFIX)
                .appendWithSingleArg("WHERE status LIKE  ?", status);
        return findAll(statementProvider, extractor);
    }

    public List<OrderJpa> findAllBetweenDates(Instant firstDate, Instant secondDate) {
        var statementProvider = new BaseStatementProvider();
        statementProvider
                .append(FIND_QUERY_PREFIX)
                .appendWithMultipleArgs("WHERE date BETWEEN ? AND ?", firstDate, secondDate);
        return findAll(statementProvider, extractor);
    }

    public List<OrderJpa> findAllWithAccidents() {
        var statementProvider = new BaseStatementProvider();
        statementProvider
                .append(FIND_QUERY_PREFIX)
                .append("WHERE id IN (SELECT order_id from accident)");
        return findAll(statementProvider, extractor);
    }
}