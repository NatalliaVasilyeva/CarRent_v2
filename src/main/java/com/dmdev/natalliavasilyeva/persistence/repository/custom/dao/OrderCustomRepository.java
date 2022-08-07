package com.dmdev.natalliavasilyeva.persistence.repository.custom.dao;


import com.dmdev.natalliavasilyeva.domain.model.Order;
import com.dmdev.natalliavasilyeva.persistence.repository.BaseStatementProvider;
import com.dmdev.natalliavasilyeva.persistence.repository.custom.GenericCustomRepository;
import com.dmdev.natalliavasilyeva.persistence.repository.custom.rowmapper.OrderResultExtractor;
import com.dmdev.natalliavasilyeva.persistence.repository.custom.rowmapper.ResultSetExtractor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

public class OrderCustomRepository extends AbstractCustomRepository<Order> implements GenericCustomRepository<Order, Long> {

    private static final Logger logger = LoggerFactory.getLogger(OrderCustomRepository.class);
    ResultSetExtractor<Order> extractor;

    public OrderCustomRepository() {
        this.extractor = new OrderResultExtractor();
    }

    private static final String FIND_QUERY_PREFIX = "" +
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
    public List<Order> findAll() {
        var statementProvider = new BaseStatementProvider();
        statementProvider
                .append(FIND_QUERY_PREFIX);
        return findAll(statementProvider, extractor);
    }

    @Override
    public Optional<Order> findById(Long id) {
        var statementProvider = new BaseStatementProvider();
        statementProvider
                .append(FIND_QUERY_PREFIX)
                .appendWithSingleArg("WHERE o.id = ?", id);
        return findOne(statementProvider, extractor);
    }

    public List<Order> findAllByUser(Long userId) {
        var statementProvider = new BaseStatementProvider();
        statementProvider
                .append(FIND_QUERY_PREFIX)
                .appendWithSingleArg("WHERE u.id = ?", userId);
        return findAll(statementProvider, extractor);
    }

    public List<Order> findAllByCar(Long carId) {
        var statementProvider = new BaseStatementProvider();
        statementProvider
                .append(FIND_QUERY_PREFIX)
                .appendWithSingleArg("WHERE c.id = ?", carId);
        return findAll(statementProvider, extractor);
    }

    public List<Order> findAllByStatus(String orderStatus) {
        var statementProvider = new BaseStatementProvider();
        statementProvider
                .append(FIND_QUERY_PREFIX)
                .appendWithSingleArg("WHERE lower(o.order_status) = ?", orderStatus);
        return findAll(statementProvider, extractor);
    }

    public List<Order> findAllBetweenDates(Instant first, Instant second) {
        var statementProvider = new BaseStatementProvider();
        statementProvider
                .append(FIND_QUERY_PREFIX)
                .appendWithMultipleArgs("WHERE o.date BETWEEN ? AND ?", first, second);
        return findAll(statementProvider, extractor);
    }

    public List<Order> findAllByDate(Instant date) {
        var statementProvider = new BaseStatementProvider();
        statementProvider
                .append(FIND_QUERY_PREFIX)
                .appendWithSingleArg("WHERE o.date = ?", date);
        return findAll(statementProvider, extractor);
    }

    public List<Order> findAllWithAccidents() {
        var statementProvider = new BaseStatementProvider();
        statementProvider
                .append(FIND_QUERY_PREFIX)
                .append("WHERE CASE WHEN o.id IN (SELECT order_id FROM accident) THEN true ELSE false END = true");
        return findAll(statementProvider, extractor);
    }
}