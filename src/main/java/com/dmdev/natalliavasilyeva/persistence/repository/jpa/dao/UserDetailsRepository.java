package com.dmdev.natalliavasilyeva.persistence.repository.jpa.dao;

import com.dmdev.natalliavasilyeva.connection.ConnectionPool;
import com.dmdev.natalliavasilyeva.connection.exception.ConnectionPoolException;
import com.dmdev.natalliavasilyeva.domain.jpa.UserDetails;
import com.dmdev.natalliavasilyeva.persistence.repository.BaseStatementProvider;
import com.dmdev.natalliavasilyeva.persistence.utils.ParseObjectUtils;
import com.dmdev.natalliavasilyeva.persistence.repository.jpa.GenericRepository;
import com.dmdev.natalliavasilyeva.persistence.repository.jpa.rowmapper.UserDetailsResultExtractor;

import java.sql.SQLException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserDetailsRepository implements GenericRepository<UserDetails, Long> {
    ConnectionPool connectionPool;
    UserDetailsResultExtractor extractor;

    public UserDetailsRepository() {
        this.connectionPool = ConnectionPool.getInstance();
        this.extractor = new UserDetailsResultExtractor();
    }
    private final static String FIND = "" +
            "SELECT id, user_id, name, surname, address, phone, birthday, registration_date\n" +
            "FROM userdetails\n";

    private final static String CREATE = "" +
            "INSERT INTO userdetails(user_id, name, surname, address, phone, birthday, registration_date) values (?, ?, ?, ?, ?, ?, ?)";

    private final static String UPDATE = "" +
            "UPDATE userdetails SET user_id = ?, name = ?, surname = ?, address = ?, phone = ?, birthday = ? WHERE id = ?";

    private final static String DELETE = "" +
            "DELETE FROM userdetails WHERE id = ?";

    @Override
    public Optional<UserDetails> findById(Long id) throws SQLException, ConnectionPoolException {

        var statementProvider = new BaseStatementProvider();
        statementProvider
                .append(FIND)
                .appendWithSingleArg("WHERE id = ?", id);
        try (var prepareStatement = statementProvider.createPreparedStatement(connectionPool.getConnection())) {
            var resultSet = prepareStatement.executeQuery();
            UserDetails userDetails = null;
            if (resultSet.next()) {
                userDetails = extractor.extractData(resultSet);
            }
            return Optional.ofNullable(userDetails);
        }
    }

    @Override
    public List<UserDetails> findAll() throws SQLException, ConnectionPoolException {
        List<UserDetails> userDetails = new ArrayList<>();
        var statementProvider = new BaseStatementProvider();
        statementProvider
                .append(FIND);
        try (var prepareStatement = statementProvider.createPreparedStatement(connectionPool.getConnection())) {
            var resultSet = prepareStatement.executeQuery();

            while (resultSet.next()) {
                userDetails.add(extractor.extractData(resultSet));
            }
        }
        return userDetails;
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
    public Optional<UserDetails> delete(UserDetails userDetails) throws ConnectionPoolException, SQLException {
        var statementProvider = new BaseStatementProvider();
        statementProvider
                .appendWithSingleArg(DELETE, userDetails.getId())
                .append("RETURNING id, user_id, name, surname, address, phone, birthday, registration_date");
        try (var prepareStatement = statementProvider.createPreparedStatementWithGeneratedKeys(connectionPool.getConnection())) {
            prepareStatement.executeUpdate();
            var generatedKeys = prepareStatement.getGeneratedKeys();
            UserDetails removedUserDetails = null;
            if (generatedKeys.next()) {
                removedUserDetails = extractor.extractData(generatedKeys);
            }
            return Optional.ofNullable(removedUserDetails);
        }
    }

    @Override
    public Optional<UserDetails> update(UserDetails userDetails) throws ConnectionPoolException, SQLException {
        List<Object> values = ParseObjectUtils.getFieldObjectsWithoutId(userDetails);
        values.add(userDetails.getId());
        var statementProvider = new BaseStatementProvider();
        statementProvider
                .appendWithMultipleArgs(UPDATE, values);
        try (var prepareStatement = statementProvider.createPreparedStatement(connectionPool.getConnection())) {
            return prepareStatement.executeUpdate() == 1 ? Optional.of(userDetails) : Optional.empty();
        }
    }

    @Override
    public Optional<UserDetails> save(UserDetails userDetails) throws ConnectionPoolException, SQLException {
        List<Object> values = ParseObjectUtils.getFieldObjectsWithoutId(userDetails);
        var statementProvider = new BaseStatementProvider();
        statementProvider
                .appendWithMultipleArgs(CREATE, values);
        try (var prepareStatement = statementProvider.createPreparedStatementWithGeneratedKeys(connectionPool.getConnection())) {
            prepareStatement.executeUpdate();
            var generatedKeys = prepareStatement.getGeneratedKeys();
            UserDetails savedUserDetails = null;
            if (generatedKeys.next()) {
                savedUserDetails = extractor.extractData(generatedKeys);
            }
            return Optional.ofNullable(savedUserDetails);
        }
    }

    public Optional<UserDetails> findByUserId(Long userId) throws SQLException, ConnectionPoolException {

        var statementProvider = new BaseStatementProvider();
        statementProvider
                .append(FIND)
                .appendWithSingleArg("WHERE user_id = ?", userId);
        try (var prepareStatement = statementProvider.createPreparedStatement(connectionPool.getConnection())) {
            var resultSet = prepareStatement.executeQuery();
            UserDetails userDetails = null;
            if (resultSet.next()) {
                userDetails = extractor.extractData(resultSet);
            }
            return Optional.ofNullable(userDetails);
        }
    }

    public List<UserDetails> findAllByRegistrationDate(Instant registrationDate) throws SQLException, ConnectionPoolException {
        List<UserDetails> userDetails = new ArrayList<>();
        var statementProvider = new BaseStatementProvider();
        statementProvider
                .append(FIND)
                .appendWithSingleArg("WHERE registration_date = ?", registrationDate);
        try (var prepareStatement = statementProvider.createPreparedStatement(connectionPool.getConnection())) {
            var resultSet = prepareStatement.executeQuery();

            while (resultSet.next()) {
                userDetails.add(extractor.extractData(resultSet));
            }
        }
        return userDetails;
    }

    public List<UserDetails> findAllByRegistrationDateLess(Instant registrationDate) throws SQLException, ConnectionPoolException {
        List<UserDetails> userDetails = new ArrayList<>();
        var statementProvider = new BaseStatementProvider();
        statementProvider
                .append(FIND)
                .appendWithSingleArg("WHERE registration_date < ?", registrationDate);
        try (var prepareStatement = statementProvider.createPreparedStatement(connectionPool.getConnection())) {
            var resultSet = prepareStatement.executeQuery();

            while (resultSet.next()) {
                userDetails.add(extractor.extractData(resultSet));
            }
        }
        return userDetails;
    }

    public List<UserDetails> findAllByRegistrationDateMore(Instant registrationDate) throws SQLException, ConnectionPoolException {
        List<UserDetails> userDetails = new ArrayList<>();
        var statementProvider = new BaseStatementProvider();
        statementProvider
                .append(FIND)
                .appendWithSingleArg("WHERE registration_date > ?", registrationDate);
        try (var prepareStatement = statementProvider.createPreparedStatement(connectionPool.getConnection())) {
            var resultSet = prepareStatement.executeQuery();

            while (resultSet.next()) {
                userDetails.add(extractor.extractData(resultSet));
            }
        }
        return userDetails;
    }

    public List<UserDetails> findAllByRegistrationDates(Instant from, Instant to) throws SQLException, ConnectionPoolException {
        List<UserDetails> userDetails = new ArrayList<>();
        var statementProvider = new BaseStatementProvider();
        statementProvider
                .append(FIND)
                .appendWithMultipleArgs("WHERE registration_date BETWEEN ? AND ?", from, to);
        try (var prepareStatement = statementProvider.createPreparedStatement(connectionPool.getConnection())) {
            var resultSet = prepareStatement.executeQuery();

            while (resultSet.next()) {
                userDetails.add(extractor.extractData(resultSet));
            }
        }
        return userDetails;
    }
}