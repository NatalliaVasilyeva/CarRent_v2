package com.dmdev.natalliavasilyeva.persistence.repository.jpa.dao;


import com.dmdev.natalliavasilyeva.domain.jpa.CategoryJpa;
import com.dmdev.natalliavasilyeva.persistence.repository.BaseStatementProvider;
import com.dmdev.natalliavasilyeva.persistence.repository.jpa.rowmapper.ResultSetExtractor;
import com.dmdev.natalliavasilyeva.utils.ParseObjectUtils;
import com.dmdev.natalliavasilyeva.persistence.repository.jpa.GenericRepository;
import com.dmdev.natalliavasilyeva.persistence.repository.jpa.rowmapper.CategoryResultExtractor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;

public class CategoryRepository extends AbstractRepository<CategoryJpa> implements GenericRepository<CategoryJpa, Long> {

    private static final Logger logger = LoggerFactory.getLogger(CategoryRepository.class);
    ResultSetExtractor<CategoryJpa> extractor;

    public CategoryRepository() {
        this.extractor = new CategoryResultExtractor();
    }

    private static final String FIND_QUERY_PREFIX = "" +
            "SELECT id, name, price_id\n" +
            "FROM categories\n";

    private static final String CREATE = "" +
            "INSERT INTO categories(name, price_id) values (?, ?)\n";

    private static final String UPDATE = "" +
            "UPDATE categories SET name = ?, price_id = ? WHERE id = ?";

    private static final String DELETE = "" +
            "DELETE FROM categories WHERE id = ?\n";

    private static final String EXISTS_BY_NAME = "" +
            "SELECT EXISTS (SELECT * FROM categories WHERE name = lower(?))";

    private static final String EXISTS_BY_ID = "" +
            "SELECT EXISTS (SELECT * FROM categories WHERE id = ?)";

    private static final String RETURNING = "" +
            "RETURNING id, name, price_id";

    @Override
    public Optional<CategoryJpa> findById(Long id) {
        var statementProvider = new BaseStatementProvider();
        statementProvider
                .append(FIND_QUERY_PREFIX)
                .appendWithSingleArg("WHERE id = ?", id);
        return findOne(statementProvider, extractor);
    }

    @Override
    public List<CategoryJpa> findAll() {
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
    public Optional<CategoryJpa> delete(CategoryJpa category) {
        var statementProvider = new BaseStatementProvider();
        statementProvider
                .appendWithSingleArg(DELETE, category.getId())
                .append(RETURNING);
        return delete(statementProvider, extractor);
    }

    @Override
    public Optional<CategoryJpa> update(CategoryJpa category) {
        List<Object> values = ParseObjectUtils.getFieldObjectsWithoutId(category);
        values.add(category.getId());
        var statementProvider = new BaseStatementProvider();
        statementProvider
                .appendWithMultipleArgs(UPDATE, values);
        return update(category, statementProvider);
    }

    @Override
    public Optional<CategoryJpa> save(CategoryJpa category) {
        List<Object> values = ParseObjectUtils.getFieldObjectsWithoutId(category);
        var statementProvider = new BaseStatementProvider();
        statementProvider
                .appendWithMultipleArgs(CREATE, values)
                .append(RETURNING);
        return save(statementProvider, extractor);
    }

    public Optional<CategoryJpa> findByName(String name) {
        var statementProvider = new BaseStatementProvider();
        statementProvider
                .append(FIND_QUERY_PREFIX)
                .appendWithSingleArg("WHERE lower(name) LIKE lower(?)", name);
        return findOne(statementProvider, extractor);
    }

    public List<CategoryJpa> findByNames(List<String> categoriesNames) {
        var statementProvider = new BaseStatementProvider();
        statementProvider
                .append(FIND_QUERY_PREFIX)
                .appendWithSingleArg("WHERE lower(name) = ANY (?)", categoriesNames.toArray(new String[0]));
        return findAll(statementProvider, extractor);
    }

    public boolean existByName(String name) {
        var statementProvider = new BaseStatementProvider();
        statementProvider
                .appendWithSingleArg(EXISTS_BY_NAME, name);
        return exist(statementProvider);
    }

    public boolean existById(Long id) {
        var statementProvider = new BaseStatementProvider();
        statementProvider
                .appendWithSingleArg(EXISTS_BY_ID, id);
        return exist(statementProvider);
    }
}