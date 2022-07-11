package com.dmdev.natalliavasilyeva.persistence.repository.jpa.dao;


import com.dmdev.natalliavasilyeva.connection.ConnectionPool;
import com.dmdev.natalliavasilyeva.connection.exception.ConnectionPoolException;
import com.dmdev.natalliavasilyeva.domain.jpa.Model;
import com.dmdev.natalliavasilyeva.persistence.repository.BaseStatementProvider;
import com.dmdev.natalliavasilyeva.persistence.utils.ParseObjectUtils;
import com.dmdev.natalliavasilyeva.persistence.repository.jpa.GenericRepository;
import com.dmdev.natalliavasilyeva.persistence.repository.jpa.rowmapper.ModelResultExtractor;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ModelRepository implements GenericRepository<Model, Long> {
    ConnectionPool connectionPool;
    ModelResultExtractor extractor;

    public ModelRepository() {
        this.connectionPool = ConnectionPool.getInstance();
        this.extractor = new ModelResultExtractor();
    }

    private final static String FIND = "" +
            "SELECT id, brand_id, category_id, name, transmission, engine_type\n" +
            "FROM model\n";

    private final static String CREATE = "" +
            "INSERT INTO model(brand_id, category_id, name, transmission, engine_type) values (?, ?, ?, ?, ?)";

    private final static String UPDATE = "" +
            "UPDATE model SET brand_id = ?, category_id = ?, name = ?, transmission = ?, engine_type = ? WHERE id = ?";

    private final static String DELETE = "" +
            "DELETE FROM model WHERE id = ?";

    @Override
    public Optional<Model> findById(Long id) throws SQLException, ConnectionPoolException {

        var statementProvider = new BaseStatementProvider();
        statementProvider
                .append(FIND)
                .appendWithSingleArg("WHERE id = ?", id);
        try (var prepareStatement = statementProvider.createPreparedStatement(connectionPool.getConnection())) {
            var resultSet = prepareStatement.executeQuery();
            Model model = null;
            if (resultSet.next()) {
                model = extractor.extractData(resultSet);
            }
            return Optional.ofNullable(model);
        }
    }

    @Override
    public List<Model> findAll() throws SQLException, ConnectionPoolException {
        List<Model> models = new ArrayList<>();
        var statementProvider = new BaseStatementProvider();
        statementProvider
                .append(FIND);
        try (var prepareStatement = statementProvider.createPreparedStatement(connectionPool.getConnection())) {
            var resultSet = prepareStatement.executeQuery();

            while (resultSet.next()) {
                models.add(extractor.extractData(resultSet));
            }
        }
        return models;
    }

    @Override
    public boolean deleteById(Long id) throws SQLException, ConnectionPoolException {
        var statementProvider = new BaseStatementProvider();
        statementProvider
                .appendWithSingleArg(DELETE, id);
        try (var prepareStatement = statementProvider.createPreparedStatement(connectionPool.getConnection())) {
            var resultSet = prepareStatement.executeUpdate();
            return resultSet > 0;
        }
    }

    @Override
    public Optional<Model> delete(Model model) throws ConnectionPoolException, SQLException {
        var statementProvider = new BaseStatementProvider();
        statementProvider
                .appendWithSingleArg(DELETE, model.getId())
                .append("RETURNING id, brand_id, category_id, name, transmission, engine_type");
        try (var prepareStatement = statementProvider.createPreparedStatementWithGeneratedKeys(connectionPool.getConnection())) {
            prepareStatement.executeUpdate();
            var generatedKeys = prepareStatement.getGeneratedKeys();
            Model removedModel = null;
            if (generatedKeys.next()) {
                removedModel = extractor.extractData(generatedKeys);
            }
            return Optional.ofNullable(removedModel);
        }
    }

    @Override
    public Optional<Model> update(Model model) throws ConnectionPoolException, SQLException {
        List<Object> values = ParseObjectUtils.getFieldObjectsWithoutId(model);
        values.add(model.getId());
        var statementProvider = new BaseStatementProvider();
        statementProvider
                .appendWithMultipleArgs(UPDATE, values);
        try (var prepareStatement = statementProvider.createPreparedStatement(connectionPool.getConnection())) {
            return prepareStatement.executeUpdate() == 1 ? Optional.of(model) : Optional.empty();
        }
    }


    @Override
    public Optional<Model> save(Model model) throws ConnectionPoolException, SQLException {
        List<Object> values = ParseObjectUtils.getFieldObjectsWithoutId(model);
        var statementProvider = new BaseStatementProvider();
        statementProvider
                .appendWithMultipleArgs(CREATE, values);
        try (var prepareStatement = statementProvider.createPreparedStatementWithGeneratedKeys(connectionPool.getConnection())) {
            prepareStatement.executeUpdate();
            var generatedKeys = prepareStatement.getGeneratedKeys();
            Model savedModel = null;
            if (generatedKeys.next()) {
                savedModel = extractor.extractData(generatedKeys);
            }
            return Optional.ofNullable(savedModel);
        }
    }
}