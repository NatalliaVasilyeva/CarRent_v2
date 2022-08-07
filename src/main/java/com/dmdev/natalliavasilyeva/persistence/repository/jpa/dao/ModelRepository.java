package com.dmdev.natalliavasilyeva.persistence.repository.jpa.dao;


import com.dmdev.natalliavasilyeva.domain.jpa.ModelJpa;
import com.dmdev.natalliavasilyeva.persistence.repository.BaseStatementProvider;
import com.dmdev.natalliavasilyeva.persistence.repository.jpa.rowmapper.ResultSetExtractor;
import com.dmdev.natalliavasilyeva.utils.ParseObjectUtils;
import com.dmdev.natalliavasilyeva.persistence.repository.jpa.GenericRepository;
import com.dmdev.natalliavasilyeva.persistence.repository.jpa.rowmapper.ModelResultExtractor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;

public class ModelRepository extends AbstractRepository<ModelJpa> implements GenericRepository<ModelJpa, Long> {

    private static final Logger logger = LoggerFactory.getLogger(ModelRepository.class);
    ResultSetExtractor<ModelJpa> extractor;

    public ModelRepository() {
        this.extractor = new ModelResultExtractor();
    }

    private static final String FIND_QUERY_PREFIX = "" +
            "SELECT id, brand_id, category_id, name, transmission, engine_type\n" +
            "FROM model\n";

    private static final String CREATE = "" +
            "INSERT INTO model(brand_id, category_id, name, transmission, engine_type) values (?, ?, ?, ?, ?)\n";

    private static final String UPDATE = "" +
            "UPDATE model SET brand_id = ?, category_id = ?, name = ?, transmission = ?, engine_type = ? WHERE id = ?";

    private static final String DELETE = "" +
            "DELETE FROM model WHERE id = ?\n";

    private static final String EXISTS_BY_NAME_TRANSMISSION_ENGINE = "" +
            "SELECT EXISTS (SELECT * FROM model WHERE name = ? AND transmission = ? AND engine_type = ?)";

    private static final String RETURNING = "" +
            "RETURNING id, brand_id, category_id, name, transmission, engine_type";

    @Override
    public Optional<ModelJpa> findById(Long id) {
        var statementProvider = new BaseStatementProvider();
        statementProvider
                .append(FIND_QUERY_PREFIX)
                .appendWithSingleArg("WHERE id = ?", id);
        return findOne(statementProvider, extractor);
    }

    @Override
    public List<ModelJpa> findAll() {
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
    public Optional<ModelJpa> delete(ModelJpa modelJpa) {
        var statementProvider = new BaseStatementProvider();
        statementProvider
                .appendWithSingleArg(DELETE, modelJpa.getId())
                .append(RETURNING);
        return delete(statementProvider, extractor);
    }

    @Override
    public Optional<ModelJpa> update(ModelJpa modelJpa) {
        List<Object> values = ParseObjectUtils.getFieldObjectsWithoutId(modelJpa);
        values.add(modelJpa.getId());
        var statementProvider = new BaseStatementProvider();
        statementProvider
                .appendWithMultipleArgs(UPDATE, values);
        return update(modelJpa, statementProvider);
    }

    @Override
    public Optional<ModelJpa> save(ModelJpa modelJpa) {
        List<Object> values = ParseObjectUtils.getFieldObjectsWithoutId(modelJpa);
        var statementProvider = new BaseStatementProvider();
        statementProvider
                .appendWithMultipleArgs(CREATE, values)
                .append(RETURNING);
        return save(statementProvider, extractor);
    }

    public boolean existByNameTransmissionEngine(String name, String transmission, String engine) {
        var statementProvider = new BaseStatementProvider();
        statementProvider
                .appendWithMultipleArgs(EXISTS_BY_NAME_TRANSMISSION_ENGINE, name, transmission, engine);
        return exist(statementProvider);
    }

    public List<ModelJpa> findByNameTransmissionEngine(String name, String transmission, String engine) {
        var statementProvider = new BaseStatementProvider();
        statementProvider
                .append(FIND_QUERY_PREFIX)
                .appendWithMultipleArgs("WHERE lower(name) LIKE ? AND lower(transmission) LIKE ? AND lower(engine_type) LIKE ?", name, transmission, engine);
        return findAll(statementProvider, extractor);
    }
}