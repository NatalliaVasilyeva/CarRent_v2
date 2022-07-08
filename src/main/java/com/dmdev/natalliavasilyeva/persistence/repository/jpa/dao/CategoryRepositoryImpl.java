package com.dmdev.natalliavasilyeva.persistence.repository.jpa.dao;



import com.dmdev.natalliavasilyeva.connection.ConnectionPool;
import com.dmdev.natalliavasilyeva.connection.exception.ConnectionPoolException;
import com.dmdev.natalliavasilyeva.persistence.jpa.Category;
import com.dmdev.natalliavasilyeva.persistence.repository.BaseStatementProvider;
import com.dmdev.natalliavasilyeva.persistence.repository.ParseObjectUtils;
import com.dmdev.natalliavasilyeva.persistence.repository.jpa.Repository;
import com.dmdev.natalliavasilyeva.persistence.repository.jpa.rowmapper.CategoryResultExtractor;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public class CategoryRepositoryImpl implements Repository<Category, Long> {
    ConnectionPool connectionPool;
    CategoryResultExtractor extractor;


    public CategoryRepositoryImpl(ConnectionPool connectionPool) {
        this.connectionPool = ConnectionPool.getInstance();
        this.extractor = new CategoryResultExtractor();
    }

    private final static String FIND = "" +
            "SELECT id, name, price_id\n" +
            "FROM categories\n";

    private final static String CREATE = "" +
            "INSERT INTO categories(name, price_id) values (?, ?)";

    private final static String UPDATE = "" +
            "UPDATE categories SET name = ?, price_id = ? WHERE id = ?";

    private final static String DELETE = "" +
            "DELETE FROM categories WHERE id = ?";


    @Override
    public Optional<Category> findById(Long id) throws SQLException, ConnectionPoolException {

        var statementProvider = new BaseStatementProvider();
        statementProvider
                .append(FIND)
                .appendWithSingleArg("WHERE id = ?", id);
        try (var prepareStatement = statementProvider.createPreparedStatement(connectionPool.getConnection())) {
            var resultSet = prepareStatement.executeQuery();
            return Optional.ofNullable(extractor.extractData(resultSet));
        }
    }


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
    public Optional<Category> delete(Category category) throws ConnectionPoolException, SQLException {
        var statementProvider = new BaseStatementProvider();
        statementProvider
                .appendWithSingleArg(DELETE, category.getId())
                .append("RETURNING id, name, price_id");
        try (var prepareStatement = statementProvider.createPreparedStatementWithGeneratedKeys(connectionPool.getConnection())) {
            prepareStatement.executeUpdate();
            var generatedKeys = prepareStatement.getGeneratedKeys();
            return Optional.ofNullable(extractor.extractData(generatedKeys));
        }
    }

    @Override
    public Optional<Category> update(Category category) throws ConnectionPoolException, SQLException {
        List<Object> values = ParseObjectUtils.getFieldObjectsWithoutId(category);
        values.add(category.getId());
        var statementProvider = new BaseStatementProvider();
        statementProvider
                .appendWithMultipleArgs(UPDATE, values);
        try (var prepareStatement = statementProvider.createPreparedStatement(connectionPool.getConnection())) {
            return prepareStatement.executeUpdate() == 1 ? Optional.of(category) : Optional.empty();
        }
    }


    @Override
    public Optional<Category> save(Category category) throws ConnectionPoolException, SQLException {
        List<Object> values = ParseObjectUtils.getFieldObjectsWithoutId(category);
        var statementProvider = new BaseStatementProvider();
        statementProvider
                .appendWithMultipleArgs(CREATE, values);
        try (var prepareStatement = statementProvider.createPreparedStatementWithGeneratedKeys(connectionPool.getConnection())) {
            prepareStatement.executeUpdate();
            var generatedKeys = prepareStatement.getGeneratedKeys();
            return Optional.ofNullable(extractor.extractData(generatedKeys));
        }
    }

    public Optional<Category> findByName(String name) throws SQLException, ConnectionPoolException {

        var statementProvider = new BaseStatementProvider();
        statementProvider
                .append(FIND)
                .appendWithSingleArg("WHERE name = ?", name);
        try (var prepareStatement = statementProvider.createPreparedStatement(connectionPool.getConnection())) {
            var resultSet = prepareStatement.executeQuery();
            return Optional.ofNullable(extractor.extractData(resultSet));
        }
    }

    public List<Category> findByNames(List<String> categoriesNames) throws SQLException, ConnectionPoolException {
        List<Category> brands = new ArrayList<>();
        var statementProvider = new BaseStatementProvider();
        statementProvider
                .append(FIND)
                .appendWithSingleArg("WHERE name = ANY (?)", categoriesNames.toArray(new String[0]));
        try (var prepareStatement = statementProvider.createPreparedStatement(connectionPool.getConnection())) {
            var resultSet = prepareStatement.executeQuery();

            while (resultSet.next()) {
                brands.add(extractor.extractData(resultSet));
            }
        }
        return brands;
    }
}