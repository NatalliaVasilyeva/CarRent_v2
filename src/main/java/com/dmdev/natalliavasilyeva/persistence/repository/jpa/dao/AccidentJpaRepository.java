package com.dmdev.natalliavasilyeva.persistence.repository.jpa.dao;


import com.dmdev.natalliavasilyeva.domain.jpa.AccidentJpa;
import com.dmdev.natalliavasilyeva.persistence.repository.BaseStatementProvider;
import com.dmdev.natalliavasilyeva.persistence.repository.jpa.rowmapper.ResultSetExtractor;
import com.dmdev.natalliavasilyeva.utils.ParseObjectUtils;
import com.dmdev.natalliavasilyeva.persistence.repository.jpa.GenericRepository;
import com.dmdev.natalliavasilyeva.persistence.repository.jpa.rowmapper.AccidentResultExtractor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;


public class AccidentJpaRepository extends AbstractRepository<AccidentJpa> implements GenericRepository<AccidentJpa, Long> {

    // TODO: add logs
    private static final Logger logger = LoggerFactory.getLogger(AccidentJpaRepository.class);
    ResultSetExtractor<AccidentJpa> extractor;

    public AccidentJpaRepository() {
        this.extractor = new AccidentResultExtractor();
    }

    private static final String FIND_QUERY_PREFIX = "" +
            "SELECT id, order_id, accident_date, description, damage\n" +
            "FROM accident\n";

    private static final String CREATE = "" +
            "INSERT INTO accident(order_id, accident_date, description, damage) values (?, ?, ?, ?)\n";

    private static final String UPDATE = "" +
            "UPDATE accident SET order_id = ?, accident_date = ?, description = ?, damage = ? WHERE id = ?";

    private static final String DELETE = "" +
            "DELETE FROM accident WHERE id = ?\n";

    private static final String RETURNING = "" +
            "RETURNING id, order_id, accident_date, description, damage";

    @Override
    public Optional<AccidentJpa> findById(Long id) {
        var statementProvider = new BaseStatementProvider();
        statementProvider
                .append(FIND_QUERY_PREFIX)
                .appendWithSingleArg("WHERE id = ?", id);
        return findOne(statementProvider, extractor);
    }

    @Override
    public List<AccidentJpa> findAll() {
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
    public Optional<AccidentJpa> delete(AccidentJpa accidentJpa) {
        var statementProvider = new BaseStatementProvider();
        statementProvider
                .appendWithSingleArg(DELETE, accidentJpa.getId())
                .append(RETURNING);
        return delete(statementProvider, extractor);
    }

    @Override
    public Optional<AccidentJpa> update(AccidentJpa accidentJpa) {
        List<Object> values = ParseObjectUtils.getFieldObjectsWithoutId(accidentJpa);
        values.add(accidentJpa.getId());
        var statementProvider = new BaseStatementProvider();
        statementProvider
                .appendWithMultipleArgs(UPDATE, values);
        return update(accidentJpa, statementProvider);
    }

    @Override
    public Optional<AccidentJpa> save(AccidentJpa accidentJpa) {
        List<Object> values = ParseObjectUtils.getFieldObjectsWithoutId(accidentJpa);
        var statementProvider = new BaseStatementProvider();
        statementProvider
                .appendWithMultipleArgs(CREATE, values)
                .append(RETURNING);
        return save(statementProvider, extractor);
    }
}