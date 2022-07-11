package com.dmdev.natalliavasilyeva.persistence.repository.jpa.dao;

import com.dmdev.natalliavasilyeva.connection.ConnectionPool;
import com.dmdev.natalliavasilyeva.connection.exception.ConnectionPoolException;
import com.dmdev.natalliavasilyeva.domain.jpa.DriverLicense;
import com.dmdev.natalliavasilyeva.persistence.repository.BaseStatementProvider;
import com.dmdev.natalliavasilyeva.persistence.utils.ParseObjectUtils;
import com.dmdev.natalliavasilyeva.persistence.repository.jpa.GenericRepository;
import com.dmdev.natalliavasilyeva.persistence.repository.jpa.rowmapper.DriverLicenseResultExtractor;

import java.sql.SQLException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DriverLicenseRepository implements GenericRepository<DriverLicense, Long> {
    ConnectionPool connectionPool;
    DriverLicenseResultExtractor extractor;


    public DriverLicenseRepository(ConnectionPool connectionPool) {
        this.connectionPool = ConnectionPool.getInstance();
        this.extractor = new DriverLicenseResultExtractor();
    }

    private final static String FIND = "" +
            "SELECT id, user_details_id, number, issue_date, expired_date\n" +
            "FROM driverlicense\n";

    private final static String CREATE = "" +
            "INSERT INTO driverlicense(user_details_id, number, issue_date, expired_date) values (?, ?, ?, ?)";

    private final static String UPDATE = "" +
            "UPDATE driverlicense SET number = ?, issue_date = ?, expired_date = ? WHERE id = ?";

    private final static String DELETE = "" +
            "DELETE FROM driverlicense WHERE id = ?";


    @Override
    public Optional<DriverLicense> findById(Long id) throws SQLException, ConnectionPoolException {

        var statementProvider = new BaseStatementProvider();
        statementProvider
                .append(FIND)
                .appendWithSingleArg("WHERE id = ?", id);
        try (var prepareStatement = statementProvider.createPreparedStatement(connectionPool.getConnection())) {
            var resultSet = prepareStatement.executeQuery();
            DriverLicense driverLicense = null;
            if (resultSet.next()) {
                driverLicense = extractor.extractData(resultSet);
            }
            return Optional.ofNullable(driverLicense);
        }
    }


    @Override
    public List<DriverLicense> findAll() throws SQLException, ConnectionPoolException {
        List<DriverLicense> driverLicenses = new ArrayList<>();
        var statementProvider = new BaseStatementProvider();
        statementProvider
                .append(FIND);
        try (var prepareStatement = statementProvider.createPreparedStatement(connectionPool.getConnection())) {
            var resultSet = prepareStatement.executeQuery();

            while (resultSet.next()) {
                driverLicenses.add(extractor.extractData(resultSet));
            }
        }
        return driverLicenses;
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
    public Optional<DriverLicense> delete(DriverLicense driverLicense) throws ConnectionPoolException, SQLException {
        var statementProvider = new BaseStatementProvider();
        statementProvider
                .appendWithSingleArg(DELETE, driverLicense.getId())
                .append("RETURNING id, user_details_id, number, issue_date, expired_date");
        try (var prepareStatement = statementProvider.createPreparedStatementWithGeneratedKeys(connectionPool.getConnection())) {
            prepareStatement.executeUpdate();
            var generatedKeys = prepareStatement.getGeneratedKeys();
            DriverLicense removedDriverLicense = null;
            if (generatedKeys.next()) {
                removedDriverLicense = extractor.extractData(generatedKeys);
            }
            return Optional.ofNullable(removedDriverLicense);
        }
    }

    @Override
    public Optional<DriverLicense> update(DriverLicense driverLicense) throws ConnectionPoolException, SQLException {
        List<Object> values = ParseObjectUtils.getFieldObjectsWithoutId(driverLicense);
        values.add(driverLicense.getId());
        var statementProvider = new BaseStatementProvider();
        statementProvider
                .appendWithMultipleArgs(UPDATE, values);
        try (var prepareStatement = statementProvider.createPreparedStatement(connectionPool.getConnection())) {
            return prepareStatement.executeUpdate() == 1 ? Optional.of(driverLicense) : Optional.empty();
        }
    }

    @Override
    public Optional<DriverLicense> save(DriverLicense driverLicense) throws ConnectionPoolException, SQLException {
        List<Object> values = ParseObjectUtils.getFieldObjectsWithoutId(driverLicense);
        var statementProvider = new BaseStatementProvider();
        statementProvider
                .appendWithMultipleArgs(CREATE, values);
        try (var prepareStatement = statementProvider.createPreparedStatementWithGeneratedKeys(connectionPool.getConnection())) {
            prepareStatement.executeUpdate();
            var generatedKeys = prepareStatement.getGeneratedKeys();
            DriverLicense savedDriverLicense = null;
            if (generatedKeys.next()) {
                savedDriverLicense = extractor.extractData(generatedKeys);
            }
            return Optional.ofNullable(savedDriverLicense);
        }
    }

    public Optional<DriverLicense> findByUserDetailsId(Long userDetailsId) throws SQLException, ConnectionPoolException {

        var statementProvider = new BaseStatementProvider();
        statementProvider
                .append(FIND)
                .appendWithSingleArg("WHERE user_details_id = ?", userDetailsId);
        try (var prepareStatement = statementProvider.createPreparedStatement(connectionPool.getConnection())) {
            var resultSet = prepareStatement.executeQuery();
            DriverLicense driverLicense = null;
            if (resultSet.next()) {
                driverLicense = extractor.extractData(resultSet);
            }
            return Optional.ofNullable(driverLicense);
        }
    }

    public Optional<DriverLicense> findByUserId(Long userId) throws SQLException, ConnectionPoolException {
        var statementProvider = new BaseStatementProvider();
        statementProvider
                .append(FIND)
                .append("LEFT JOIN userdetails ud ON driverlicense.user_details_id = ud.user_id")
                .appendWithSingleArg("WHERE ud.user_id = ?", userId);
        try (var prepareStatement = statementProvider.createPreparedStatement(connectionPool.getConnection())) {
            var resultSet = prepareStatement.executeQuery();

            DriverLicense driverLicense = null;
            if (resultSet.next()) {
                driverLicense = extractor.extractData(resultSet);
            }
            return Optional.ofNullable(driverLicense);
        }
    }

    public List<DriverLicense> findAllExpired() throws SQLException, ConnectionPoolException {
        List<DriverLicense> driverLicenses = new ArrayList<>();
        var statementProvider = new BaseStatementProvider();
        statementProvider
                .append(FIND)
                .appendWithSingleArg("WHERE expired_date = ?", Instant.now());
        try (var prepareStatement = statementProvider.createPreparedStatement(connectionPool.getConnection())) {
            var resultSet = prepareStatement.executeQuery();

            while (resultSet.next()) {
                driverLicenses.add(extractor.extractData(resultSet));
            }
        }
        return driverLicenses;
    }
}