package com.dmdev.natalliavasilyeva.persistence.repository.jpa.dao;


import com.dmdev.natalliavasilyeva.connection.ConnectionPool;
import com.dmdev.natalliavasilyeva.connection.exception.ConnectionPoolException;
import com.dmdev.natalliavasilyeva.domain.jpa.Accident;
import com.dmdev.natalliavasilyeva.persistence.repository.BaseStatementProvider;
import com.dmdev.natalliavasilyeva.persistence.utils.ParseObjectUtils;
import com.dmdev.natalliavasilyeva.persistence.repository.jpa.GenericRepository;
import com.dmdev.natalliavasilyeva.persistence.repository.jpa.rowmapper.AccidentResultExtractor;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public class AccidentRepository implements GenericRepository<Accident, Long> {
    ConnectionPool connectionPool;
    AccidentResultExtractor extractor;


    public AccidentRepository(ConnectionPool connectionPool) {
        this.connectionPool = ConnectionPool.getInstance();
        this.extractor = new AccidentResultExtractor();
    }

    private final static String FIND = "" +
            "SELECT id, order_id, date, description, damage\n" +
            "FROM accident\n";

    private final static String CREATE = "" +
            "INSERT INTO accident(order_id, date, description, damage) values (?, ?, ?, ?)";

    private final static String UPDATE = "" +
            "UPDATE accident SET order_id = ?, date = ?, description = ?, damage = ? WHERE id = ?";

    private final static String DELETE = "" +
            "DELETE FROM accident WHERE id = ?";

    @Override
    public Optional<Accident> findById(Long id) throws SQLException, ConnectionPoolException {

        var statementProvider = new BaseStatementProvider();
        statementProvider
                .append(FIND)
                .appendWithSingleArg("WHERE id = ?", id);
        try (var prepareStatement = statementProvider.createPreparedStatement(connectionPool.getConnection())) {
            var resultSet = prepareStatement.executeQuery();
            Accident accident = null;
            if (resultSet.next()) {
                accident = extractor.extractData(resultSet);
            }
            return Optional.ofNullable(accident);
        }
    }

    @Override
    public List<Accident> findAll() throws SQLException, ConnectionPoolException {
        List<Accident> accidents = new ArrayList<>();
        var statementProvider = new BaseStatementProvider();
        statementProvider
                .append(FIND);
        try (var prepareStatement = statementProvider.createPreparedStatement(connectionPool.getConnection())) {
            var resultSet = prepareStatement.executeQuery();
            while (resultSet.next()) {
                accidents.add(extractor.extractData(resultSet));
            }
        }
        return accidents;
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
    public Optional<Accident> delete(Accident accident) throws ConnectionPoolException, SQLException {
        var statementProvider = new BaseStatementProvider();
        statementProvider
                .appendWithSingleArg(DELETE, accident.getId())
                .append("RETURNING id, order_id, date, description, damage");
        try (var prepareStatement = statementProvider.createPreparedStatementWithGeneratedKeys(connectionPool.getConnection())) {
            prepareStatement.executeUpdate();
            var generatedKeys = prepareStatement.getGeneratedKeys();
            Accident removedAccident = null;
            if (generatedKeys.next()) {
                removedAccident = extractor.extractData(generatedKeys);
            }
            return Optional.ofNullable(removedAccident);
        }
    }

    @Override
    public Optional<Accident> update(Accident accident) throws ConnectionPoolException, SQLException {
        List<Object> values = ParseObjectUtils.getFieldObjectsWithoutId(accident);
        values.add(accident.getId());
        var statementProvider = new BaseStatementProvider();
        statementProvider
                .appendWithMultipleArgs(UPDATE, values);
        try (var prepareStatement = statementProvider.createPreparedStatement(connectionPool.getConnection())) {
            return prepareStatement.executeUpdate() == 1 ? Optional.of(accident) : Optional.empty();
        }
    }


    @Override
    public Optional<Accident> save(Accident accident) throws ConnectionPoolException, SQLException {
        List<Object> values = ParseObjectUtils.getFieldObjectsWithoutId(accident);
        var statementProvider = new BaseStatementProvider();
        statementProvider
                .appendWithMultipleArgs(CREATE, values);
        try (var prepareStatement = statementProvider.createPreparedStatementWithGeneratedKeys(connectionPool.getConnection())) {
            prepareStatement.executeUpdate();
            var generatedKeys = prepareStatement.getGeneratedKeys();
            Accident savedAccident = null;
            if (generatedKeys.next()) {
                savedAccident = extractor.extractData(generatedKeys);
            }
            return Optional.ofNullable(savedAccident);
        }
    }
}