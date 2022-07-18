package com.dmdev.natalliavasilyeva.persistence.repository.jpa.dao;


import com.dmdev.natalliavasilyeva.domain.jpa.BrandJpa;
import com.dmdev.natalliavasilyeva.persistence.repository.BaseStatementProvider;
import com.dmdev.natalliavasilyeva.persistence.repository.jpa.rowmapper.ResultSetExtractor;
import com.dmdev.natalliavasilyeva.utils.ParseObjectUtils;
import com.dmdev.natalliavasilyeva.persistence.repository.jpa.GenericRepository;
import com.dmdev.natalliavasilyeva.persistence.repository.jpa.rowmapper.BrandResultExtractor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;

public class BrandRepository extends AbstractRepository<BrandJpa> implements GenericRepository<BrandJpa, Long> {

    private static final Logger logger = LoggerFactory.getLogger(BrandRepository.class);
    ResultSetExtractor<BrandJpa> extractor;

    public BrandRepository() {
        this.extractor = new BrandResultExtractor();
    }

    private static final String FIND_QUERY_PREFIX = "" +
            "SELECT id, name\n" +
            "FROM brand\n";

    private static final String CREATE = "" +
            "INSERT INTO brand(name) values (?)\n";

    private static final String UPDATE = "" +
            "UPDATE brand SET name = ? WHERE id = ?";

    private static final String DELETE = "" +
            "DELETE FROM brand WHERE id = ?\n";

    private static final String EXISTS_BY_NAME = "" +
            "SELECT EXISTS (SELECT * FROM brand WHERE name = ?)";

    private static final String EXISTS_BY_ID = "" +
            "SELECT EXISTS (SELECT * FROM brand WHERE id = ?)";

    private static final String RETURNING = "" +
            "RETURNING id, name";

    @Override
    public Optional<BrandJpa> findById(Long id) {
        var statementProvider = new BaseStatementProvider();
        statementProvider
                .append(FIND_QUERY_PREFIX)
                .appendWithSingleArg("WHERE id = ?", id);
        return findOne(statementProvider, extractor);
    }

    @Override
    public List<BrandJpa> findAll() {
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
    public Optional<BrandJpa> delete(BrandJpa brandJpa) {
        var statementProvider = new BaseStatementProvider();
        statementProvider
                .appendWithSingleArg(DELETE, brandJpa.getId())
                .append(RETURNING);
        return delete(statementProvider, extractor);
    }

    @Override
    public Optional<BrandJpa> update(BrandJpa brandJpa) {
        List<Object> values = ParseObjectUtils.getFieldObjectsWithoutId(brandJpa);
        values.add(brandJpa.getId());
        var statementProvider = new BaseStatementProvider();
        statementProvider
                .appendWithMultipleArgs(UPDATE, values);
        return update(brandJpa, statementProvider);
    }

    @Override
    public Optional<BrandJpa> save(BrandJpa brandJpa) {
        List<Object> values = ParseObjectUtils.getFieldObjectsWithoutId(brandJpa);
        var statementProvider = new BaseStatementProvider();
        statementProvider
                .appendWithMultipleArgs(CREATE, values)
                .append(RETURNING);
        return save(statementProvider, extractor);
    }

    public Optional<BrandJpa> findByName(String name) {
        var statementProvider = new BaseStatementProvider();
        statementProvider
                .append(FIND_QUERY_PREFIX)
                .appendWithSingleArg("WHERE name = ?", name);
        return findOne(statementProvider, extractor);
    }

    public List<BrandJpa> findByNames(List<String> brandNames) {
        var statementProvider = new BaseStatementProvider();
        statementProvider
                .append(FIND_QUERY_PREFIX)
                .appendWithSingleArg("WHERE name = ANY (?)", brandNames.toArray(new String[0]));
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