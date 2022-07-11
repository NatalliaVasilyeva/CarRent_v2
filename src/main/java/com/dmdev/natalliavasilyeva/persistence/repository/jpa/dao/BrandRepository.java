package com.dmdev.natalliavasilyeva.persistence.repository.jpa.dao;


import com.dmdev.natalliavasilyeva.connection.ConnectionPool;
import com.dmdev.natalliavasilyeva.connection.exception.ConnectionPoolException;
import com.dmdev.natalliavasilyeva.domain.jpa.Brand;
import com.dmdev.natalliavasilyeva.persistence.repository.BaseStatementProvider;
import com.dmdev.natalliavasilyeva.persistence.utils.ParseObjectUtils;
import com.dmdev.natalliavasilyeva.persistence.repository.jpa.GenericRepository;
import com.dmdev.natalliavasilyeva.persistence.repository.jpa.rowmapper.BrandResultExtractor;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BrandRepository implements GenericRepository<Brand, Long> {
    ConnectionPool connectionPool;
    BrandResultExtractor extractor;

    public BrandRepository() {
        this.connectionPool = ConnectionPool.getInstance();
        this.extractor = new BrandResultExtractor();
    }

    private final static String FIND = "" +
            "SELECT id, name\n" +
            "FROM brand\n";

    private final static String CREATE = "" +
            "INSERT INTO brand(name) values (?)";

    private final static String UPDATE = "" +
            "UPDATE brand SET name = ? WHERE id = ?";

    private final static String DELETE = "" +
            "DELETE FROM brand WHERE id = ?";


    @Override
    public Optional<Brand> findById(Long id) throws SQLException, ConnectionPoolException {

        var statementProvider = new BaseStatementProvider();
        statementProvider
                .append(FIND)
                .appendWithSingleArg("WHERE id = ?", id);
        try (var prepareStatement = statementProvider.createPreparedStatement(connectionPool.getConnection())) {
            var resultSet = prepareStatement.executeQuery();
            Brand brand = null;
            if (resultSet.next()) {
                brand = extractor.extractData(resultSet);
            }
            return Optional.ofNullable(brand);
        }
    }


    @Override
    public List<Brand> findAll() throws SQLException, ConnectionPoolException {
        List<Brand> brands = new ArrayList<>();
        var statementProvider = new BaseStatementProvider();
        statementProvider
                .append(FIND);
        try (var prepareStatement = statementProvider.createPreparedStatement(connectionPool.getConnection())) {
            var resultSet = prepareStatement.executeQuery();
            while (resultSet.next()) {
                brands.add(extractor.extractData(resultSet));
            }
        }
        return brands;
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
    public Optional<Brand> delete(Brand brand) throws ConnectionPoolException, SQLException {
        var statementProvider = new BaseStatementProvider();
        statementProvider
                .appendWithSingleArg(DELETE, brand.getId())
                .append("RETURNING id, name");
        try (var prepareStatement = statementProvider.createPreparedStatementWithGeneratedKeys(connectionPool.getConnection())) {
            prepareStatement.executeUpdate();
            var generatedKeys = prepareStatement.getGeneratedKeys();
            Brand removedBrand = null;
            if (generatedKeys.next()) {
                removedBrand = extractor.extractData(generatedKeys);
            }
            return Optional.ofNullable(removedBrand);
        }
    }

    @Override
    public Optional<Brand> update(Brand brand) throws ConnectionPoolException, SQLException {
        List<Object> values = ParseObjectUtils.getFieldObjectsWithoutId(brand);
        values.add(brand.getId());
        var statementProvider = new BaseStatementProvider();
        statementProvider
                .appendWithMultipleArgs(UPDATE, values);
        try (var prepareStatement = statementProvider.createPreparedStatement(connectionPool.getConnection())) {
            return prepareStatement.executeUpdate() == 1 ? Optional.of(brand) : Optional.empty();
        }
    }


    @Override
    public Optional<Brand> save(Brand brand) throws ConnectionPoolException, SQLException {
        List<Object> values = ParseObjectUtils.getFieldObjectsWithoutId(brand);
        var statementProvider = new BaseStatementProvider();
        statementProvider
                .appendWithMultipleArgs(CREATE, values);
        try (var prepareStatement = statementProvider.createPreparedStatementWithGeneratedKeys(connectionPool.getConnection())) {
            prepareStatement.executeUpdate();
            var generatedKeys = prepareStatement.getGeneratedKeys();
            Brand savedBrand = null;
            if (generatedKeys.next()) {
                savedBrand = extractor.extractData(generatedKeys);
            }
            return Optional.ofNullable(savedBrand);
        }
    }

    public Optional<Brand> findByName(String name) throws SQLException, ConnectionPoolException {

        var statementProvider = new BaseStatementProvider();
        statementProvider
                .append(FIND)
                .appendWithSingleArg("WHERE name = ?", name);
        try (var prepareStatement = statementProvider.createPreparedStatement(connectionPool.getConnection())) {
            var resultSet = prepareStatement.executeQuery();
            Brand brand = null;
            if (resultSet.next()) {
                brand = extractor.extractData(resultSet);
            }
            return Optional.ofNullable(brand);
        }
    }

    public List<Brand> findByNames(List<String> brandNames) throws SQLException, ConnectionPoolException {
        List<Brand> brands = new ArrayList<>();
        var statementProvider = new BaseStatementProvider();
        statementProvider
                .append(FIND)
                .appendWithSingleArg("WHERE name = ANY (?)", brandNames.toArray(new String[0]));
        try (var prepareStatement = statementProvider.createPreparedStatement(connectionPool.getConnection())) {
            var resultSet = prepareStatement.executeQuery();
            while (resultSet.next()) {
                brands.add(extractor.extractData(resultSet));
            }
        }
        return brands;
    }
}