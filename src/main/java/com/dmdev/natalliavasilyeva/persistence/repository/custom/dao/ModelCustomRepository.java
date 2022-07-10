package com.dmdev.natalliavasilyeva.persistence.repository.custom.dao;


import com.dmdev.natalliavasilyeva.connection.ConnectionPool;
import com.dmdev.natalliavasilyeva.connection.exception.ConnectionPoolException;
import com.dmdev.natalliavasilyeva.domain.model.Model;
import com.dmdev.natalliavasilyeva.persistence.repository.BaseStatementProvider;
import com.dmdev.natalliavasilyeva.persistence.repository.custom.GenericCustomRepository;
import com.dmdev.natalliavasilyeva.persistence.repository.custom.rowmapper.ModelResultExtractor;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ModelCustomRepository implements GenericCustomRepository<Model, Long> {
    ConnectionPool connectionPool;
    ModelResultExtractor extractor;

    public ModelCustomRepository(ConnectionPool connectionPool) {
        this.connectionPool = ConnectionPool.getInstance();
        this.extractor = new ModelResultExtractor();
    }

    private final static String FIND = "" +
            "SELECT m.id as model_id, m.name as model_name, m.transmission as model_transmission, m.engine_type as model_engine_type,\n" +
            "       row_to_json(brand) as brand_description,\n" +
            "       row_to_json(category) as category_description\n" +
            "FROM model m\n" +
            "LEFT JOIN (\n" +
            "       SELECT b.id as brand_id, b.name as brand_name\n" +
            "       FROM brand b) AS brand on m.brand_id = brand.brand_id\n" +
            "LEFT JOIN (\n" +
            "       SELECT c.id as category_id, c.name as category_name, row_to_json(prices) as price_description\n" +
            "       FROM categories c\n" +
            "            LEFT JOIN (\n" +
            "                 SELECT p.id, p.price as sum FROM price p) as prices ON c.price_id = prices.id)\n" +
            "AS category ON m.category_id = category.category_id\n";


    @Override
    public Optional<Model> findById(Long id) throws SQLException, ConnectionPoolException {

        var statementProvider = new BaseStatementProvider();
        statementProvider
                .append(FIND)
                .appendWithSingleArg("WHERE m.id = ?", id);
        try (var prepareStatement = statementProvider.createPreparedStatement(connectionPool.getConnection())) {
            var resultSet = prepareStatement.executeQuery();
            Model model = null;
            if (resultSet.next()) {
                model =  extractor.extractData(resultSet);
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

    public List<Model> findAllByBrandIds(List<Long> brandIds) throws SQLException, ConnectionPoolException {
        List<Model> models = new ArrayList<>();
        var statementProvider = new BaseStatementProvider();
        statementProvider
                .append(FIND)
                .appendWithSingleArg("WHERE brand.brand_id = ANY (?)", brandIds.toArray(new Long[0]));

        try (var prepareStatement = statementProvider.createPreparedStatement(connectionPool.getConnection())) {
            var resultSet = prepareStatement.executeQuery();
            while (resultSet.next()) {
                models.add(extractor.extractData(resultSet));
            }
        }
        return models;
    }

    public List<Model> findAllByBrandName(String brandName) throws SQLException, ConnectionPoolException {
        List<Model> models = new ArrayList<>();
        var statementProvider = new BaseStatementProvider();
        statementProvider
                .append(FIND)
                .appendWithSingleArg("WHERE brand.brand_name LIKE ?", brandName);

        try (var prepareStatement = statementProvider.createPreparedStatement(connectionPool.getConnection())) {
            var resultSet = prepareStatement.executeQuery();
            while (resultSet.next()) {
                models.add(extractor.extractData(resultSet));
            }
        }
        return models;
    }

    public List<Model> findAllByTransmission(String transmission) throws SQLException, ConnectionPoolException {
        List<Model> models = new ArrayList<>();
        var statementProvider = new BaseStatementProvider();
        statementProvider
                .append(FIND)
                .appendWithSingleArg("WHERE m.transmission LIKE ?", transmission);

        try (var prepareStatement = statementProvider.createPreparedStatement(connectionPool.getConnection())) {
            var resultSet = prepareStatement.executeQuery();
            while (resultSet.next()) {
                models.add(extractor.extractData(resultSet));
            }
        }
        return models;
    }

    public List<Model> findAllByEngineType(String engineType) throws SQLException, ConnectionPoolException {
        List<Model> models = new ArrayList<>();
        var statementProvider = new BaseStatementProvider();
        statementProvider
                .append(FIND)
                .appendWithSingleArg("WHERE m.engine_type LIKE ?", engineType);

        try (var prepareStatement = statementProvider.createPreparedStatement(connectionPool.getConnection())) {
            var resultSet = prepareStatement.executeQuery();
            while (resultSet.next()) {
                models.add(extractor.extractData(resultSet));
            }
        }
        return models;
    }

    public List<Model> findAllByCategory(String category) throws SQLException, ConnectionPoolException {
        List<Model> models = new ArrayList<>();
        var statementProvider = new BaseStatementProvider();
        statementProvider
                .append(FIND)
                .appendWithSingleArg("WHERE category_name LIKE ?", category);

        try (var prepareStatement = statementProvider.createPreparedStatement(connectionPool.getConnection())) {
            var resultSet = prepareStatement.executeQuery();
            while (resultSet.next()) {
                models.add(extractor.extractData(resultSet));
            }
        }
        return models;
    }
}