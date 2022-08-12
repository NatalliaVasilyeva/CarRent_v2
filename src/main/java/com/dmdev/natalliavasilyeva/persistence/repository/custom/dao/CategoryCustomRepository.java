package com.dmdev.natalliavasilyeva.persistence.repository.custom.dao;


import com.dmdev.natalliavasilyeva.domain.model.Category;
import com.dmdev.natalliavasilyeva.persistence.repository.BaseStatementProvider;
import com.dmdev.natalliavasilyeva.persistence.repository.custom.GenericCustomRepository;
import com.dmdev.natalliavasilyeva.persistence.repository.custom.rowmapper.CategoryResultExtractor;
import com.dmdev.natalliavasilyeva.persistence.repository.custom.rowmapper.ResultSetExtractor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;

import java.util.List;
import java.util.Optional;

public class CategoryCustomRepository extends AbstractCustomRepository<Category> implements GenericCustomRepository<Category, Long> {

    private static final Logger logger = LoggerFactory.getLogger(CategoryCustomRepository.class);
    ResultSetExtractor<Category> extractor;

    public CategoryCustomRepository() {
        this.extractor = new CategoryResultExtractor();
    }

    private static final String FIND_QUERY_PREFIX = "" +
            "SELECT c.id                as category_id,\n" +
            "       c.name              as category_name,\n" +
            "       p.price\n" +
            "FROM categories c\n" +
            "       LEFT JOIN price p ON p.id = c.price_id\n";

    @Override
    public List<Category> findAll() {
        var statementProvider = new BaseStatementProvider();
        statementProvider
                .append(FIND_QUERY_PREFIX);
        return findAll(statementProvider, extractor);
    }

    @Override
    public Optional<Category> findById(Long id) {
        var statementProvider = new BaseStatementProvider();
        statementProvider
                .append(FIND_QUERY_PREFIX)
                .appendWithSingleArg("WHERE c.id = ?", id);
        return findOne(statementProvider, extractor);
    }

    public Optional<Category> findByName(String name) {
        var statementProvider = new BaseStatementProvider();
        statementProvider
                .append(FIND_QUERY_PREFIX)
                .appendWithSingleArg("WHERE lower(c.name) = ?", name);
        return findOne(statementProvider, extractor);
    }

    public List<Category> findByPrice(String sign, BigDecimal price) {
        var statementProvider = new BaseStatementProvider();
        statementProvider
                .append(FIND_QUERY_PREFIX)
                .appendWithSingleArg("WHERE price " + sign + " ?", price);
        return findAll(statementProvider, extractor);
    }
}