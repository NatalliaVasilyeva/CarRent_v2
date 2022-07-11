package com.dmdev.natalliavasilyeva.persistence.repository.jpa.dao;

import com.dmdev.natalliavasilyeva.connection.ConnectionPool;
import com.dmdev.natalliavasilyeva.connection.exception.ConnectionPoolException;
import com.dmdev.natalliavasilyeva.domain.jpa.User;
import com.dmdev.natalliavasilyeva.persistence.repository.BaseStatementProvider;
import com.dmdev.natalliavasilyeva.persistence.utils.ParseObjectUtils;
import com.dmdev.natalliavasilyeva.persistence.repository.jpa.GenericRepository;
import com.dmdev.natalliavasilyeva.persistence.repository.jpa.rowmapper.UserResultExtractor;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserRepository implements GenericRepository<User, Long> {
    ConnectionPool connectionPool;
    UserResultExtractor extractor;

    public UserRepository() {
        this.connectionPool = ConnectionPool.getInstance();
        this.extractor = new UserResultExtractor();
    }

    private final static String FIND = "" +
            "SELECT id, login, email, role\n" +
            "FROM users\n";

    private final static String CREATE = "" +
            "INSERT INTO users(login, email, password, role) values (?, ?, ?, ?)";

    private final static String UPDATE = "" +
            "UPDATE users SET login = ?, email = ?, password = ?, role = ? WHERE id = ?";

    private final static String UPDATE_PASSWORD = "" +
            "UPDATE users SET password = ? WHERE id = ?";

    private final static String DELETE = "" +
            "DELETE FROM users WHERE id = ?";

    private final static String EXISTS_BY_LOGIN = "" +
            "SELECT EXISTS (SELECT * FROM users WHERE login = ?)";

    private final static String EXISTS_BY_EMAIL = "" +
            "SELECT EXISTS (SELECT * FROM users WHERE login = ?)";

    @Override
    public Optional<User> findById(Long id) throws SQLException, ConnectionPoolException {

        var statementProvider = new BaseStatementProvider();
        statementProvider
                .append(FIND)
                .appendWithSingleArg("WHERE id = ?", id);
        try (var prepareStatement = statementProvider.createPreparedStatement(connectionPool.getConnection())) {
            var resultSet = prepareStatement.executeQuery();
            User user = null;
            if (resultSet.next()) {
                user = extractor.extractData(resultSet);
            }
            return Optional.ofNullable(user);
        }
    }

    @Override
    public List<User> findAll() throws SQLException, ConnectionPoolException {
        List<User> users = new ArrayList<>();
        var statementProvider = new BaseStatementProvider();
        statementProvider
                .append(FIND);
        try (var prepareStatement = statementProvider.createPreparedStatement(connectionPool.getConnection())) {
            var resultSet = prepareStatement.executeQuery();

            while (resultSet.next()) {
                users.add(extractor.extractData(resultSet));
            }
        }
        return users;
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
    public Optional<User> delete(User user) throws ConnectionPoolException, SQLException {
        var statementProvider = new BaseStatementProvider();
        statementProvider
                .appendWithSingleArg(DELETE, user.getId())
                .append("RETURNING id, login, email, role");
        try (var prepareStatement = statementProvider.createPreparedStatementWithGeneratedKeys(connectionPool.getConnection())) {
            prepareStatement.executeUpdate();
            var generatedKeys = prepareStatement.getGeneratedKeys();
            User removedUser = null;
            if (generatedKeys.next()) {
                removedUser = extractor.extractData(generatedKeys);
            }
            return Optional.ofNullable(removedUser);
        }
    }

    @Override
    public Optional<User> update(User user) throws ConnectionPoolException, SQLException {
        List<Object> values = ParseObjectUtils.getFieldObjectsWithoutId(user);
        values.add(user.getId());
        var statementProvider = new BaseStatementProvider();
        statementProvider
                .appendWithMultipleArgs(UPDATE, values);
        try (var prepareStatement = statementProvider.createPreparedStatement(connectionPool.getConnection())) {
            return prepareStatement.executeUpdate() == 1 ? Optional.of(user) : Optional.empty();
        }
    }

    public boolean updatePassword(Long userId, String password) throws ConnectionPoolException, SQLException {
        var statementProvider = new BaseStatementProvider();
        statementProvider
                .appendWithMultipleArgs(UPDATE_PASSWORD, userId, password);
        try (var prepareStatement = statementProvider.createPreparedStatement(connectionPool.getConnection())) {
            return prepareStatement.executeUpdate() == 1;
        }
    }

    @Override
    public Optional<User> save(User user) throws ConnectionPoolException, SQLException {
        List<Object> values = ParseObjectUtils.getFieldObjectsWithoutId(user);
        var statementProvider = new BaseStatementProvider();
        statementProvider
                .appendWithMultipleArgs(CREATE, values);
        try (var prepareStatement = statementProvider.createPreparedStatementWithGeneratedKeys(connectionPool.getConnection())) {
            prepareStatement.executeUpdate();
            var generatedKeys = prepareStatement.getGeneratedKeys();
            User savedUser = null;
            if (generatedKeys.next()) {
                savedUser = extractor.extractData(generatedKeys);
            }
            return Optional.ofNullable(savedUser);
        }
    }

    public Optional<User> findByLoginAndPassword(String login, String password) throws SQLException, ConnectionPoolException {

        var statementProvider = new BaseStatementProvider();
        statementProvider
                .append(FIND)
                .appendWithMultipleArgs("WHERE login LIKE ? AND password LIKE ?", login, password);
        try (var prepareStatement = statementProvider.createPreparedStatement(connectionPool.getConnection())) {
            var resultSet = prepareStatement.executeQuery();
            User user = null;
            if (resultSet.next()) {
                user = extractor.extractData(resultSet);
            }
            return Optional.ofNullable(user);
        }
    }

    public Optional<User> findByEmailAndPassword(String email, String password) throws SQLException, ConnectionPoolException {

        var statementProvider = new BaseStatementProvider();
        statementProvider
                .append(FIND)
                .appendWithMultipleArgs("WHERE email LIKE ? AND password LIKE ?", email, password);
        try (var prepareStatement = statementProvider.createPreparedStatement(connectionPool.getConnection())) {
            var resultSet = prepareStatement.executeQuery();
            User user = null;
            if (resultSet.next()) {
                user = extractor.extractData(resultSet);
            }
            return Optional.ofNullable(user);
        }
    }

    public boolean existByLogin(String login) throws SQLException, ConnectionPoolException {

        var statementProvider = new BaseStatementProvider();
        statementProvider
                .appendWithSingleArg(EXISTS_BY_LOGIN, login);
        try (var prepareStatement = statementProvider.createPreparedStatement(connectionPool.getConnection())) {
            var resultSet = prepareStatement.executeQuery();
            boolean result = false;
            if (resultSet.next()) {
                result = resultSet.getBoolean(1);
            }
            return result;
        }
    }

    public boolean existByEmail(String email) throws SQLException, ConnectionPoolException {

        var statementProvider = new BaseStatementProvider();
        statementProvider
                .appendWithSingleArg(EXISTS_BY_EMAIL, email);
        try (var prepareStatement = statementProvider.createPreparedStatement(connectionPool.getConnection())) {
            var resultSet = prepareStatement.executeQuery();
            boolean result = false;
            if (resultSet.next()) {
                result = resultSet.getBoolean(1);
            }
            return result;
        }
    }

    public List<User> findAllByRole(String role) throws SQLException, ConnectionPoolException {
        List<User> users = new ArrayList<>();
        var statementProvider = new BaseStatementProvider();
        statementProvider
                .append(FIND)
                .appendWithSingleArg("WHERE role LIKE ?", role);
        try (var prepareStatement = statementProvider.createPreparedStatement(connectionPool.getConnection())) {
            var resultSet = prepareStatement.executeQuery();

            while (resultSet.next()) {
                users.add(extractor.extractData(resultSet));
            }
        }
        return users;
    }
}