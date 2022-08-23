package com.dmdev.natalliavasilyeva.persistence.repository.jpa.dao;


import com.dmdev.natalliavasilyeva.domain.jpa.PriceJpa;
import com.dmdev.natalliavasilyeva.persistence.repository.BaseStatementProvider;
import com.dmdev.natalliavasilyeva.persistence.repository.jpa.rowmapper.ResultSetExtractor;
import com.dmdev.natalliavasilyeva.utils.ParseObjectUtils;
import com.dmdev.natalliavasilyeva.persistence.repository.jpa.GenericRepository;
import com.dmdev.natalliavasilyeva.persistence.repository.jpa.rowmapper.PriceResultExtractor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public class PriceRepository extends AbstractRepository<PriceJpa> implements GenericRepository<PriceJpa, Long> {

    private static final Logger logger = LoggerFactory.getLogger(PriceRepository.class);
    ResultSetExtractor<PriceJpa> extractor;

    public PriceRepository() {
        this.extractor = new PriceResultExtractor();
    }

    private static final String FIND_QUERY_PREFIX = "" +
            "SELECT id, price\n" +
            "FROM price\n";

    private static final String CREATE = "" +
            "INSERT INTO price(price) values (?)\n";

    private static final String UPDATE = "" +
            "UPDATE price SET price = ? WHERE id = ?";

    private static final String DELETE = "" +
            "DELETE FROM price WHERE id = ?\n";

    private static final String EXISTS_BY_PRICE = "" +
            "SELECT EXISTS (SELECT * FROM price WHERE price = ?)";

    private static final String RETURNING = "" +
            "RETURNING id, price";

    @Override
    public Optional<PriceJpa> findById(Long id) {
        var statementProvider = new BaseStatementProvider();
        statementProvider
                .append(FIND_QUERY_PREFIX)
                .appendWithSingleArg("WHERE id = ?", id);
        return findOne(statementProvider, extractor);
    }

    @Override
    public List<PriceJpa> findAll() {
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
    public Optional<PriceJpa> delete(PriceJpa priceJpa) {
        var statementProvider = new BaseStatementProvider();
        statementProvider
                .appendWithSingleArg(DELETE, priceJpa.getId())
                .append(RETURNING);
        return delete(statementProvider, extractor);
    }

    @Override
    public Optional<PriceJpa> update(PriceJpa priceJpa) {
        List<Object> values = ParseObjectUtils.getFieldObjectsWithoutId(priceJpa);
        values.add(priceJpa.getId());
        var statementProvider = new BaseStatementProvider();
        statementProvider
                .appendWithMultipleArgs(UPDATE, values);
        return update(priceJpa, statementProvider);
    }

    @Override
    public Optional<PriceJpa> save(PriceJpa priceJpa) {
        List<Object> values = ParseObjectUtils.getFieldObjectsWithoutId(priceJpa);
        var statementProvider = new BaseStatementProvider();
        statementProvider
                .appendWithMultipleArgs(CREATE, values)
                .append(RETURNING);
        return save(statementProvider, extractor);
    }

    public boolean existByPriceSum(BigDecimal price) {
        var statementProvider = new BaseStatementProvider();
        statementProvider
                .appendWithSingleArg(EXISTS_BY_PRICE, price);
        return exist(statementProvider);
    }

    public Optional<PriceJpa> findByPriceSum(BigDecimal price) {
        var statementProvider = new BaseStatementProvider();
        statementProvider
                .append(FIND_QUERY_PREFIX)
                .appendWithSingleArg("WHERE price = ?", price);
        return findOne(statementProvider, extractor);
    }
}