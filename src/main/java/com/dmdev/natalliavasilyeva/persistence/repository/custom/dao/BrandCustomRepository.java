package com.dmdev.natalliavasilyeva.persistence.repository.custom.dao;


import com.dmdev.natalliavasilyeva.connection.ConnectionPool;
import com.dmdev.natalliavasilyeva.connection.exception.ConnectionPoolException;
import com.dmdev.natalliavasilyeva.domain.model.Brand;
import com.dmdev.natalliavasilyeva.persistence.repository.BaseStatementProvider;
import com.dmdev.natalliavasilyeva.persistence.repository.custom.BrandRepository;
import com.dmdev.natalliavasilyeva.persistence.repository.custom.rowmapper.BrandResultExtractor;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BrandCustomRepository implements BrandRepository<Brand> {
    ConnectionPool connectionPool;
    BrandResultExtractor extractor;


    public BrandCustomRepository(ConnectionPool connectionPool) {
        this.connectionPool = ConnectionPool.getInstance();
        this.extractor = new BrandResultExtractor();
    }

    private final static String FIND = "" +
            "SELECT b.id as brand_id, b.name as brand_name, json_agg(row_to_json(mod)) as model_description\n" +
                    "FROM brand b\n" +
                    "LEFT JOIN (\n" +
                            "SELECT m.id as mod_id, m.name as model_name, m.transmission as transmission, m.engine_type as engine,\n" +
                                    "c.name as category_name\n" +
                            "FROM model m\n" +
                            "LEFT JOIN  categories c ON m.category_id = c.id\n" +
                    ") AS mod ON b.id = mod.mod_id\n";


    @Override
    public List<Brand> findAll() throws SQLException, ConnectionPoolException {
        List<Brand> brands = new ArrayList<>();
        var statementProvider = new BaseStatementProvider();
        statementProvider
                .append(FIND)
                .append("GROUP BY b.id");
        try (var prepareStatement = statementProvider.createPreparedStatement(connectionPool.getConnection())) {
            var resultSet = prepareStatement.executeQuery();
            while (resultSet.next()) {
                brands.add(extractor.extractData(resultSet));
            }
        }
        return brands;
    }

    @Override
    public Optional<Brand> findById(Long id) throws SQLException, ConnectionPoolException {
        var statementProvider = new BaseStatementProvider();
        statementProvider
                .append(FIND)
                .appendWithSingleArg("WHERE b.id = ?", id)
                .append("GROUP BY b.id");
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
    public Optional<Brand> findByName(String name) throws SQLException, ConnectionPoolException {
        var statementProvider = new BaseStatementProvider();
        statementProvider
                .append(FIND)
                .appendWithSingleArg("WHERE b.name = ?", name)
                .append("GROUP BY b.id");
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
    public List<Brand> findByCategory(String category) throws SQLException, ConnectionPoolException {
        List<Brand> brands = new ArrayList<>();
        var statementProvider = new BaseStatementProvider();
        statementProvider
                .append(FIND)
                .appendWithSingleArg("WHERE category_name = ?", category)
                .append("GROUP BY b.id");
        try (var prepareStatement = statementProvider.createPreparedStatement(connectionPool.getConnection())) {
            var resultSet = prepareStatement.executeQuery();
            while (resultSet.next()) {
                brands.add(extractor.extractData(resultSet));
            }
        }
        return brands;
    }

}