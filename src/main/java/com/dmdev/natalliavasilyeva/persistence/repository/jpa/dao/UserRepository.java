package com.dmdev.natalliavasilyeva.persistence.repository.jpa.dao;

import com.dmdev.natalliavasilyeva.connection.exception.ConnectionPoolException;
import com.dmdev.natalliavasilyeva.domain.jpa.UserJpa;
import com.dmdev.natalliavasilyeva.persistence.exception.RepositoryException;
import com.dmdev.natalliavasilyeva.persistence.repository.BaseStatementProvider;
import com.dmdev.natalliavasilyeva.persistence.repository.jpa.rowmapper.ResultSetExtractor;
import com.dmdev.natalliavasilyeva.utils.ParseObjectUtils;
import com.dmdev.natalliavasilyeva.persistence.repository.jpa.GenericRepository;
import com.dmdev.natalliavasilyeva.persistence.repository.jpa.rowmapper.UserResultExtractor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class UserRepository extends AbstractRepository<UserJpa> implements GenericRepository<UserJpa, Long> {

    private static final Logger logger = LoggerFactory.getLogger(UserRepository.class);
    ResultSetExtractor<UserJpa> extractor;

    public UserRepository() {
        this.extractor = new UserResultExtractor();
    }

    private static final String FIND_QUERY_PREFIX = "" +
            "SELECT id, login, email, role\n" +
            "FROM users\n";

    private static final String CREATE = "" +
            "INSERT INTO users(login, email, password, role) values (?, ?, ?, ?)\n";

    private static final String UPDATE = "" +
            "UPDATE users SET login = ?, email = ?, password = ?, role = ? WHERE id = ?";

    private static final String UPDATE_PASSWORD = "" +
            "UPDATE users SET password = ? WHERE id = ?";

    private static final String DELETE = "" +
            "DELETE FROM users WHERE id = ?\n";

    private static final String EXISTS_BY_LOGIN = "" +
            "SELECT EXISTS (SELECT * FROM users WHERE login = ?)";

    private static final String EXISTS_BY_EMAIL = "" +
            "SELECT EXISTS (SELECT * FROM users WHERE login = ?)";

    private static final String RETURNING = "" +
            "RETURNING id, login, email, role";

    @Override
    public Optional<UserJpa> findById(Long id) {
        var statementProvider = new BaseStatementProvider();
        statementProvider
                .append(FIND_QUERY_PREFIX)
                .appendWithSingleArg("WHERE id = ?", id);
        return findOne(statementProvider, extractor);
    }

    @Override
    public List<UserJpa> findAll() {
        var statementProvider = new BaseStatementProvider();
        statementProvider
                .append(FIND_QUERY_PREFIX);
        return findAll(statementProvider, extractor);
    }

    @Override
    public boolean deleteById(Long id) {
        var statementProvider = new BaseStatementProvider();
        statementProvider
                .appendWithSingleArg(DELETE, id);
        return deleteById(statementProvider);
    }

    @Override
    public Optional<UserJpa> delete(UserJpa userJpa) {
        var statementProvider = new BaseStatementProvider();
        statementProvider
                .appendWithSingleArg(DELETE, userJpa.getId())
                .append(RETURNING);
        return delete(statementProvider, extractor);
    }

    @Override
    public Optional<UserJpa> update(UserJpa userJpa) {
        List<Object> values = ParseObjectUtils.getFieldObjectsWithoutId(userJpa);
        values.add(userJpa.getId());
        var statementProvider = new BaseStatementProvider();
        statementProvider
                .appendWithMultipleArgs(UPDATE, values);
        return update(userJpa, statementProvider);
    }

    public boolean updatePassword(Long userId, String password) {
        var statementProvider = new BaseStatementProvider();
        statementProvider
                .appendWithMultipleArgs(UPDATE_PASSWORD, userId, password);
        try (var prepareStatement = statementProvider.createPreparedStatement(connectionPool.getConnection())) {
            return prepareStatement.executeUpdate() == 1;
        } catch (ConnectionPoolException | SQLException ex) {
            logger.error("UpdatePassword for user jpa method throws exception", ex);
            throw new RepositoryException();
        }
    }

    @Override
    public Optional<UserJpa> save(UserJpa userJpa) {
        List<Object> values = ParseObjectUtils.getFieldObjectsWithoutId(userJpa);
        var statementProvider = new BaseStatementProvider();
        statementProvider
                .appendWithMultipleArgs(CREATE, values)
                .append(RETURNING);
        return save(statementProvider, extractor);
    }

    public Optional<UserJpa> findByLoginAndPassword(String login, String password) {
        var statementProvider = new BaseStatementProvider();
        statementProvider
                .append(FIND_QUERY_PREFIX)
                .appendWithMultipleArgs("WHERE login LIKE ? AND password LIKE ?", login, password);
        return findOne(statementProvider, extractor);
    }

    public Optional<UserJpa> findByEmailAndPassword(String email, String password) {
        var statementProvider = new BaseStatementProvider();
        statementProvider
                .append(FIND_QUERY_PREFIX)
                .appendWithMultipleArgs("WHERE email LIKE ? AND password LIKE ?", email, password);
        return findOne(statementProvider, extractor);
    }

    public boolean existByLogin(String login) {
        var statementProvider = new BaseStatementProvider();
        statementProvider
                .appendWithSingleArg(EXISTS_BY_LOGIN, login);
        return exist(statementProvider);
    }

    public boolean existByEmail(String email) {
        var statementProvider = new BaseStatementProvider();
        statementProvider
                .appendWithSingleArg(EXISTS_BY_EMAIL, email);
        return exist(statementProvider);
    }

    public List<UserJpa> findAllByRole(String role) {
        var statementProvider = new BaseStatementProvider();
        statementProvider
                .append(FIND_QUERY_PREFIX)
                .appendWithSingleArg("WHERE lower(role) LIKE ?", role);
        return findAll(statementProvider, extractor);
    }
}