package com.dmdev.natalliavasilyeva.persistence.repository.jpa.dao;

import com.dmdev.natalliavasilyeva.connection.ConnectionPool;
import com.dmdev.natalliavasilyeva.connection.exception.ConnectionPoolException;
import com.dmdev.natalliavasilyeva.domain.jpa.Entity;
import com.dmdev.natalliavasilyeva.persistence.exception.RepositoryException;
import com.dmdev.natalliavasilyeva.persistence.repository.BaseStatementProvider;
import com.dmdev.natalliavasilyeva.persistence.repository.jpa.rowmapper.ResultSetExtractor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AbstractRepository<T extends Entity> {
    private static final Logger logger = LoggerFactory.getLogger(AbstractRepository.class);

    protected final ConnectionPool connectionPool = ConnectionPool.getInstance();

    public void startTransaction() throws RepositoryException {
        try {
            connectionPool.getConnection().setAutoCommit(false);
            logger.debug("start Transaction");
        } catch (SQLException | ConnectionPoolException e) {
            throw new RepositoryException(String.format("Start transaction method throws exception: %s ", e.getMessage()), e);
        }
    }

    public void finishTransaction() throws RepositoryException {
        try {
            connectionPool.getConnection().setAutoCommit(true);
            logger.debug("finish Transaction");
        } catch (SQLException | ConnectionPoolException e) {
            throw new RepositoryException(String.format("Finish transaction method throws exception: %s ", e.getMessage()), e);
        }
    }

    public void rollbackTransaction() throws RepositoryException {
        try {
            connectionPool.getConnection().rollback();
            logger.debug("rollback Transaction");
        } catch (SQLException | ConnectionPoolException e) {
            throw new RepositoryException(String.format("Rollback transaction method throws exception: %s ", e.getMessage()), e);
        }
    }

    public Optional<T> findOne(BaseStatementProvider statementProvider, ResultSetExtractor<T> resultSetExtractor) {
        try (var prepareStatement = statementProvider.createPreparedStatement(connectionPool.getConnection())) {
            var resultSet = prepareStatement.executeQuery();
            return Optional.of(resultSet.next()).filter(it -> it).map(rs -> resultSetExtractor.extractData(resultSet));
        } catch (ConnectionPoolException | SQLException ex) {
            throw new RepositoryException(String.format("Find method throws exception: %s ", ex.getMessage()), ex);
        }
    }

    public List<T> findAll(BaseStatementProvider statementProvider, ResultSetExtractor<T> resultSetExtractor) {
        List<T> items = new ArrayList<>();
        try (var prepareStatement = statementProvider.createPreparedStatement(connectionPool.getConnection())) {
            var resultSet = prepareStatement.executeQuery();
            while (resultSet.next()) {
                items.add(resultSetExtractor.extractData(resultSet));
            }
            return items;
        } catch (ConnectionPoolException | SQLException ex) {
            throw new RepositoryException(String.format("Find for all entities method throws exception: %s ", ex.getMessage()), ex);
        }
    }

    public boolean deleteById(BaseStatementProvider statementProvider) {
        try (var prepareStatement = statementProvider.createPreparedStatement(connectionPool.getConnection())) {
            var resultSet = prepareStatement.executeUpdate();
            return resultSet > 0;
        } catch (ConnectionPoolException | SQLException ex) {
            throw new RepositoryException(String.format("Delete by id entity method throws exception: %s ", ex.getMessage()), ex);
        }
    }

    public Optional<T> delete(BaseStatementProvider statementProvider, ResultSetExtractor<T> resultSetExtractor) {
        try (var prepareStatement = statementProvider.createPreparedStatementWithGeneratedKeys(connectionPool.getConnection())) {
            prepareStatement.executeUpdate();
            var generatedKeys = prepareStatement.getGeneratedKeys();
            return Optional.of(generatedKeys.next()).filter(it -> it).map(rs -> resultSetExtractor.extractData(generatedKeys));
        } catch (ConnectionPoolException | SQLException ex) {
            throw new RepositoryException(String.format("Delete entity method throws exception: %s ", ex.getMessage()), ex);
        }
    }

    public Optional<T> update(T entity, BaseStatementProvider statementProvider) {
        try (var prepareStatement = statementProvider.createPreparedStatement(connectionPool.getConnection())) {
            return prepareStatement.executeUpdate() == 1 ? Optional.of(entity) : Optional.empty();
        } catch (ConnectionPoolException | SQLException ex) {
            throw new RepositoryException(String.format("Update entity method throws exception: %s ", ex.getMessage()), ex);
        }
    }

    public Optional<T> save(BaseStatementProvider statementProvider, ResultSetExtractor<T> resultSetExtractor) {
        try (var prepareStatement = statementProvider.createPreparedStatementWithGeneratedKeys(connectionPool.getConnection())) {
            prepareStatement.executeUpdate();
            var generatedKeys = prepareStatement.getGeneratedKeys();
            return Optional.of(generatedKeys.next()).filter(it -> it).map(rs -> resultSetExtractor.extractData(generatedKeys));
        } catch (ConnectionPoolException | SQLException ex) {
            throw new RepositoryException(String.format("Save entity method throws exception: %s ", ex.getMessage()), ex);
        }
    }

    public boolean exist(BaseStatementProvider statementProvider) {
        try (var prepareStatement = statementProvider.createPreparedStatement(connectionPool.getConnection())) {
            var resultSet = prepareStatement.executeQuery();
            boolean result = false;
            if (resultSet.next()) {
                result = resultSet.getBoolean(1);
            }
            return result;
        } catch (ConnectionPoolException | SQLException ex) {
            logger.error("Exists entity method throws exception", ex);
            throw new RepositoryException();
        }
    }
}