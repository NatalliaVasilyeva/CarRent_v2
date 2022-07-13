package com.dmdev.natalliavasilyeva.persistence.repository.jpa.dao;


import com.dmdev.natalliavasilyeva.domain.jpa.Accident;
import com.dmdev.natalliavasilyeva.persistence.repository.BaseStatementProvider;
import com.dmdev.natalliavasilyeva.persistence.repository.jpa.rowmapper.ResultSetExtractor;
import com.dmdev.natalliavasilyeva.persistence.utils.ParseObjectUtils;
import com.dmdev.natalliavasilyeva.persistence.repository.jpa.GenericRepository;
import com.dmdev.natalliavasilyeva.persistence.repository.jpa.rowmapper.AccidentResultExtractor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;


public class AccidentRepository extends AbstractRepository<Accident> implements GenericRepository<Accident, Long> {

    // TODO: add logs
    private static final Logger logger = LoggerFactory.getLogger(AccidentRepository.class);
    ResultSetExtractor<Accident> extractor;

    public AccidentRepository() {
        this.extractor = new AccidentResultExtractor();
    }

    private static final String FIND_QUERY_PREFIX = "" +
            "SELECT id, order_id, accident_date, description, damage\n" +
            "FROM accident\n";

    private static final String CREATE = "" +
            "INSERT INTO accident(order_id, accident_date, description, damage) values (?, ?, ?, ?)";

    private static final String UPDATE = "" +
            "UPDATE accident SET order_id = ?, accident_date = ?, description = ?, damage = ? WHERE id = ?";

    private static final String DELETE = "" +
            "DELETE FROM accident WHERE id = ?";

    private static final String RETURNING = "" +
            "RETURNING id, order_id, accident_date, description, damage";

    @Override
    public Optional<Accident> findById(Long id) {
        var statementProvider = new BaseStatementProvider();
        statementProvider
                .append(FIND_QUERY_PREFIX)
                .appendWithSingleArg("WHERE id = ?", id);
        return findOne(statementProvider, extractor);
    }

    @Override
    public List<Accident> findAll() {
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
    public Optional<Accident> delete(Accident accident) {
        var statementProvider = new BaseStatementProvider();
        statementProvider
                .appendWithSingleArg(DELETE, accident.getId())
                .append(RETURNING);
        return delete(statementProvider, extractor);
    }

    @Override
    public Optional<Accident> update(Accident accident) {
        List<Object> values = ParseObjectUtils.getFieldObjectsWithoutId(accident);
        values.add(accident.getId());
        var statementProvider = new BaseStatementProvider();
        statementProvider
                .appendWithMultipleArgs(UPDATE, values);
        return update(accident, statementProvider);
    }

    @Override
    public Optional<Accident> save(Accident accident) {
        List<Object> values = ParseObjectUtils.getFieldObjectsWithoutId(accident);
        var statementProvider = new BaseStatementProvider();
        statementProvider
                .appendWithMultipleArgs(CREATE, values)
                .append(RETURNING);
        return save(statementProvider, extractor);
    }
}