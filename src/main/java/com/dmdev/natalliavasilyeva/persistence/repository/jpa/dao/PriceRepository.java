package com.dmdev.natalliavasilyeva.persistence.repository.jpa.dao;


import com.dmdev.natalliavasilyeva.connection.ConnectionPool;
import com.dmdev.natalliavasilyeva.connection.exception.ConnectionPoolException;
import com.dmdev.natalliavasilyeva.domain.jpa.Price;
import com.dmdev.natalliavasilyeva.persistence.repository.BaseStatementProvider;
import com.dmdev.natalliavasilyeva.persistence.utils.ParseObjectUtils;
import com.dmdev.natalliavasilyeva.persistence.repository.jpa.GenericRepository;
import com.dmdev.natalliavasilyeva.persistence.repository.jpa.rowmapper.PriceResultExtractor;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PriceRepository implements GenericRepository<Price, Long> {
    ConnectionPool connectionPool;
    PriceResultExtractor extractor;

    public PriceRepository(ConnectionPool connectionPool) {
        this.connectionPool = ConnectionPool.getInstance();
        this.extractor = new PriceResultExtractor();
    }

    private final static String FIND = "" +
            "SELECT id, price\n" +
            "FROM price\n";

    private final static String CREATE = "" +
            "INSERT INTO price(price) values (?)";

    private final static String UPDATE = "" +
            "UPDATE price SET price = ? WHERE id = ?";

    private final static String DELETE = "" +
            "DELETE FROM price WHERE id = ?";


    @Override
    public Optional<Price> findById(Long id) throws SQLException, ConnectionPoolException {

        var statementProvider = new BaseStatementProvider();
        statementProvider
                .append(FIND)
                .appendWithSingleArg("WHERE id = ?", id);
        try (var prepareStatement = statementProvider.createPreparedStatement(connectionPool.getConnection())) {
            var resultSet = prepareStatement.executeQuery();
            Price price = null;
            if (resultSet.next()) {
                price = extractor.extractData(resultSet);
            }
            return Optional.ofNullable(price);
        }
    }

    @Override
    public List<Price> findAll() throws SQLException, ConnectionPoolException {
        List<Price> prices = new ArrayList<>();
        var statementProvider = new BaseStatementProvider();
        statementProvider
                .append(FIND);
        try (var prepareStatement = statementProvider.createPreparedStatement(connectionPool.getConnection())) {
            var resultSet = prepareStatement.executeQuery();

            while (resultSet.next()) {
                prices.add(extractor.extractData(resultSet));
            }
        }
        return prices;
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
    public Optional<Price> delete(Price price) throws ConnectionPoolException, SQLException {
        var statementProvider = new BaseStatementProvider();
        statementProvider
                .appendWithSingleArg(DELETE, price.getId())
                .append("RETURNING id, price");
        try (var prepareStatement = statementProvider.createPreparedStatementWithGeneratedKeys(connectionPool.getConnection())) {
            prepareStatement.executeUpdate();
            var generatedKeys = prepareStatement.getGeneratedKeys();
            Price removedPrice = null;
            if (generatedKeys.next()) {
                removedPrice = extractor.extractData(generatedKeys);
            }
            return Optional.ofNullable(removedPrice);
        }
    }

    @Override
    public Optional<Price> update(Price price) throws ConnectionPoolException, SQLException {
        List<Object> values = ParseObjectUtils.getFieldObjectsWithoutId(price);
        values.add(price.getId());
        var statementProvider = new BaseStatementProvider();
        statementProvider
                .appendWithMultipleArgs(UPDATE, values);
        try (var prepareStatement = statementProvider.createPreparedStatement(connectionPool.getConnection())) {
            return prepareStatement.executeUpdate() == 1 ? Optional.of(price) : Optional.empty();
        }
    }


    @Override
    public Optional<Price> save(Price price) throws ConnectionPoolException, SQLException {
        List<Object> values = ParseObjectUtils.getFieldObjectsWithoutId(price);
        var statementProvider = new BaseStatementProvider();
        statementProvider
                .appendWithMultipleArgs(CREATE, values);
        try (var prepareStatement = statementProvider.createPreparedStatementWithGeneratedKeys(connectionPool.getConnection())) {
            prepareStatement.executeUpdate();
            var generatedKeys = prepareStatement.getGeneratedKeys();
            Price savedPrice = null;
            if (generatedKeys.next()) {
                savedPrice = extractor.extractData(generatedKeys);
            }
            return Optional.ofNullable(savedPrice);
        }
    }
}