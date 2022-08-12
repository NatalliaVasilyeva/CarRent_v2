package com.dmdev.natalliavasilyeva.persistence.repository.custom.dao;


import com.dmdev.natalliavasilyeva.domain.model.Accident;
import com.dmdev.natalliavasilyeva.persistence.repository.BaseStatementProvider;
import com.dmdev.natalliavasilyeva.persistence.repository.custom.GenericCustomRepository;
import com.dmdev.natalliavasilyeva.persistence.repository.custom.rowmapper.AccidentResultExtractor;
import com.dmdev.natalliavasilyeva.persistence.repository.custom.rowmapper.ResultSetExtractor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

public class AccidentCustomRepository extends AbstractCustomRepository<Accident> implements GenericCustomRepository<Accident, Long> {

    private static final Logger logger = LoggerFactory.getLogger(AccidentCustomRepository.class);
    ResultSetExtractor<Accident> extractor;

    public AccidentCustomRepository() {
        this.extractor = new AccidentResultExtractor();
    }

    private static final String FIND_QUERY_PREFIX = "" +
            "SELECT ac.id, " +
            "       ac.accident_date,\n" +
            "       ac.description,\n" +
            "       ac.damage,\n" +
            "       o.id                            as order_number,\n" +
            "       br.name                         as brand,\n" +
            "       md.name                         as model,\n" +
            "       car.car_number                  as car_number,\n" +
            "       ud.name                         as user_name,\n" +
            "       ud.surname                      as user_surname\n" +
            "FROM accident ac\n" +
            "       LEFT JOIN orders o ON o.id = ac.order_id\n" +
            "       LEFT JOIN users u ON u.id = o.user_id\n" +
            "       LEFT JOIN userdetails ud ON ud.user_id = u.id\n" +
            "       LEFT JOIN car ON car.id = o.car_id\n" +
            "       LEFT JOIN model md ON md.id = car.model_id\n" +
            "       LEFT JOIN brand br ON br.id = md.brand_id\n";

    @Override
    public Optional<Accident> findById(Long id) {
        var statementProvider = new BaseStatementProvider();
        statementProvider
                .append(FIND_QUERY_PREFIX)
                .appendWithSingleArg("WHERE ac.id = ?", id);
        return findOne(statementProvider, extractor);
    }

    @Override
    public List<Accident> findAll() {
        var statementProvider = new BaseStatementProvider();
        statementProvider
                .append(FIND_QUERY_PREFIX);
        return findAll(statementProvider, extractor);
    }

    public List<Accident> findByDate(Instant date) {
        var statementProvider = new BaseStatementProvider();
        statementProvider
                .append(FIND_QUERY_PREFIX)
                .appendWithSingleArg("WHERE ac.accident_date = ?", date);
        return findAll(statementProvider, extractor);
    }

    public List<Accident> findByDates(Instant startDate, Instant endDate) {
        var statementProvider = new BaseStatementProvider();
        statementProvider
                .append(FIND_QUERY_PREFIX)
                .appendWithMultipleArgs("WHERE ac.accident_date BETWEEN ? AND ?", startDate, endDate);
        return findAll(statementProvider, extractor);
    }

    public List<Accident> findByOrderId(Long orderId) {
        var statementProvider = new BaseStatementProvider();
        statementProvider
                .append(FIND_QUERY_PREFIX)
                .appendWithSingleArg("WHERE o.id = ?", orderId);
        return findAll(statementProvider, extractor);
    }

    public List<Accident> findByUsernameAndSurname(String username, String surname) {
        var statementProvider = new BaseStatementProvider();
        statementProvider
                .append(FIND_QUERY_PREFIX)
                .appendWithMultipleArgs("WHERE lower(ud.name) LIKE ? AND lower(ud.surname) LIKE ?", username, surname);
        return findAll(statementProvider, extractor);
    }

    public List<Accident> findByCarNumbers(List<String> carNumbers) {
        var statementProvider = new BaseStatementProvider();
        statementProvider
                .append(FIND_QUERY_PREFIX)
                .appendWithSingleArg("WHERE lower(car.car_number) = ANY (?)\n", carNumbers.toArray(new String[0]))
                .append("ORDER BY ac.accident_date DESC");
        return findAll(statementProvider, extractor);
    }

    public List<Accident> findByDamage(BigDecimal minDamage) {
        var statementProvider = new BaseStatementProvider();
        statementProvider
                .append(FIND_QUERY_PREFIX)
                .appendWithSingleArg("WHERE ac.damage >= ?\n", minDamage)
                .append("ORDER BY ac.damage DESC");
        return findAll(statementProvider, extractor);
    }
}