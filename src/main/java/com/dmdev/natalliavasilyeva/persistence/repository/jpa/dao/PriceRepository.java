package com.dmdev.natalliavasilyeva.persistence.repository.jpa.dao;


import com.dmdev.natalliavasilyeva.domain.jpa.Price;
import com.dmdev.natalliavasilyeva.persistence.repository.BaseStatementProvider;
import com.dmdev.natalliavasilyeva.persistence.repository.jpa.rowmapper.ResultSetExtractor;
import com.dmdev.natalliavasilyeva.persistence.utils.ParseObjectUtils;
import com.dmdev.natalliavasilyeva.persistence.repository.jpa.GenericRepository;
import com.dmdev.natalliavasilyeva.persistence.repository.jpa.rowmapper.PriceResultExtractor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public class PriceRepository extends AbstractRepository<Price> implements GenericRepository<Price, Long> {

    private static final Logger logger = LoggerFactory.getLogger(PriceRepository.class);
    ResultSetExtractor<Price> extractor;

    public PriceRepository() {
        this.extractor = new PriceResultExtractor();
    }

    private static final String FIND_QUERY_PREFIX = "" +
            "SELECT id, price\n" +
            "FROM price\n";

    private static final String CREATE = "" +
            "INSERT INTO price(price) values (?)";

    private static final String UPDATE = "" +
            "UPDATE price SET price = ? WHERE id = ?";

    private static final String DELETE = "" +
            "DELETE FROM price WHERE id = ?";

    private static final String EXISTS_BY_PRICE = "" +
            "SELECT EXISTS (SELECT * FROM price WHERE price = ?)";

    private static final String RETURNING = "" +
            "RETURNING id, price";

    @Override
    public Optional<Price> findById(Long id) {
        var statementProvider = new BaseStatementProvider();
        statementProvider
                .append(FIND_QUERY_PREFIX)
                .appendWithSingleArg("WHERE id = ?", id);
        return findOne(statementProvider, extractor);
    }

    @Override
    public List<Price> findAll() {
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
    public Optional<Price> delete(Price price) {
        var statementProvider = new BaseStatementProvider();
        statementProvider
                .appendWithSingleArg(DELETE, price.getId())
                .append(RETURNING);
        return delete(statementProvider, extractor);
    }

    @Override
    public Optional<Price> update(Price price) {
        List<Object> values = ParseObjectUtils.getFieldObjectsWithoutId(price);
        values.add(price.getId());
        var statementProvider = new BaseStatementProvider();
        statementProvider
                .appendWithMultipleArgs(UPDATE, values);
        return update(price, statementProvider);
    }

    @Override
    public Optional<Price> save(Price price) {
        List<Object> values = ParseObjectUtils.getFieldObjectsWithoutId(price);
        var statementProvider = new BaseStatementProvider();
        statementProvider
                .appendWithMultipleArgs(CREATE, values)
                .append(RETURNING);
        return save(statementProvider, extractor);
    }

    public boolean existByNameTransmissionEngine(BigDecimal price) {
        var statementProvider = new BaseStatementProvider();
        statementProvider
                .appendWithSingleArg(EXISTS_BY_PRICE, price);
        return exist(statementProvider);
    }
}