package com.dmdev.natalliavasilyeva.persistence.repository.custom.dao;


import com.dmdev.natalliavasilyeva.connection.ConnectionPool;
import com.dmdev.natalliavasilyeva.connection.exception.ConnectionPoolException;
import com.dmdev.natalliavasilyeva.domain.model.Category;
import com.dmdev.natalliavasilyeva.persistence.repository.BaseStatementProvider;
import com.dmdev.natalliavasilyeva.persistence.repository.custom.GenericCustomRepository;
import com.dmdev.natalliavasilyeva.persistence.repository.custom.rowmapper.CategoryResultExtractor;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CategoryCustomRepository implements GenericCustomRepository<Category, Long> {
    ConnectionPool connectionPool;
    CategoryResultExtractor extractor;

    public CategoryCustomRepository() {
        this.connectionPool = ConnectionPool.getInstance();
        this.extractor = new CategoryResultExtractor();
    }

    private final static String FIND = "" +
            "SELECT c.id as category_id, c.name as category_name, p.price\n" +
                    "FROM categories c\n" +
                    "LEFT JOIN price p ON p.id = c.price_id\n";

    @Override
    public List<Category> findAll() throws SQLException, ConnectionPoolException {
        List<Category> categories = new ArrayList<>();
        var statementProvider = new BaseStatementProvider();
        statementProvider
                .append(FIND);
        try (var prepareStatement = statementProvider.createPreparedStatement(connectionPool.getConnection())) {
            var resultSet = prepareStatement.executeQuery();
            while (resultSet.next()) {
                categories.add(extractor.extractData(resultSet));
            }
        }
        return categories;
    }

    @Override
    public Optional<Category> findById(Long id) throws SQLException, ConnectionPoolException {
        var statementProvider = new BaseStatementProvider();
        statementProvider
                .append(FIND)
                .appendWithSingleArg("WHERE c.id = ?", id);
        try (var prepareStatement = statementProvider.createPreparedStatement(connectionPool.getConnection())) {
            var resultSet = prepareStatement.executeQuery();
            Category category = null;
            if (resultSet.next()) {
                category = extractor.extractData(resultSet);
            }
            return Optional.ofNullable(category);
        }
    }

    public Optional<Category> findByName(String name) throws SQLException, ConnectionPoolException {
        var statementProvider = new BaseStatementProvider();
        statementProvider
                .append(FIND)
                .appendWithSingleArg("WHERE c.name = ?", name);
        try (var prepareStatement = statementProvider.createPreparedStatement(connectionPool.getConnection())) {
            var resultSet = prepareStatement.executeQuery();
            Category category = null;
            if (resultSet.next()) {
                category = extractor.extractData(resultSet);
            }
            return Optional.ofNullable(category);
        }
    }

    public List<Category> findByPrice(String sign, BigDecimal price) throws SQLException, ConnectionPoolException {
        List<Category> categories = new ArrayList<>();
        var statementProvider = new BaseStatementProvider();
        statementProvider
                .append(FIND)
                .appendWithSingleArg("WHERE price " + sign + " ?", price);
        try (var prepareStatement = statementProvider.createPreparedStatement(connectionPool.getConnection())) {
            var resultSet = prepareStatement.executeQuery();
            while (resultSet.next()) {
                categories.add(extractor.extractData(resultSet));
            }
        }
        return categories;
    }
}