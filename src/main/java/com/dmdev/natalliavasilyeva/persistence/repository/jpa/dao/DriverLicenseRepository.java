package com.dmdev.natalliavasilyeva.persistence.repository.jpa.dao;

import com.dmdev.natalliavasilyeva.domain.jpa.DriverLicenseJpa;
import com.dmdev.natalliavasilyeva.persistence.repository.BaseStatementProvider;
import com.dmdev.natalliavasilyeva.persistence.repository.jpa.rowmapper.ResultSetExtractor;
import com.dmdev.natalliavasilyeva.utils.ParseObjectUtils;
import com.dmdev.natalliavasilyeva.persistence.repository.jpa.GenericRepository;
import com.dmdev.natalliavasilyeva.persistence.repository.jpa.rowmapper.DriverLicenseResultExtractor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

public class DriverLicenseRepository extends AbstractRepository<DriverLicenseJpa> implements GenericRepository<DriverLicenseJpa, Long> {

    private static final Logger logger = LoggerFactory.getLogger(DriverLicenseRepository.class);
    ResultSetExtractor<DriverLicenseJpa> extractor;

    public DriverLicenseRepository() {
        this.extractor = new DriverLicenseResultExtractor();
    }

    private static final String FIND_QUERY_PREFIX = "" +
            "SELECT id, user_details_id, number, issue_date, expired_date\n" +
            "FROM driverlicense\n";

    private static final String CREATE = "" +
            "INSERT INTO driverlicense(user_details_id, number, issue_date, expired_date) values (?, ?, ?, ?)\n";

    private static final String UPDATE = "" +
            "UPDATE driverlicense SET user_details_id = ?, number = ?, issue_date = ?, expired_date = ? WHERE id = ?";

    private static final String DELETE = "" +
            "DELETE FROM driverlicense WHERE id = ?\n";

    private static final String RETURNING = "" +
            "RETURNING id, user_details_id, number, issue_date, expired_date";


    @Override
    public Optional<DriverLicenseJpa> findById(Long id) {
        var statementProvider = new BaseStatementProvider();
        statementProvider
                .append(FIND_QUERY_PREFIX)
                .appendWithSingleArg("WHERE id = ?", id);
        return findOne(statementProvider, extractor);
    }

    @Override
    public List<DriverLicenseJpa> findAll() {
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
    public Optional<DriverLicenseJpa> delete(DriverLicenseJpa driverLicenseJpa) {
        var statementProvider = new BaseStatementProvider();
        statementProvider
                .appendWithSingleArg(DELETE, driverLicenseJpa.getId())
                .append(RETURNING);
        return delete(statementProvider, extractor);
    }

    @Override
    public Optional<DriverLicenseJpa> update(DriverLicenseJpa driverLicenseJpa) {
        List<Object> values = ParseObjectUtils.getFieldObjectsWithoutId(driverLicenseJpa);
        values.add(driverLicenseJpa.getId());
        var statementProvider = new BaseStatementProvider();
        statementProvider
                .appendWithMultipleArgs(UPDATE, values);
        return update(driverLicenseJpa, statementProvider);
    }

    @Override
    public Optional<DriverLicenseJpa> save(DriverLicenseJpa driverLicenseJpa) {
        List<Object> values = ParseObjectUtils.getFieldObjectsWithoutId(driverLicenseJpa);
        var statementProvider = new BaseStatementProvider();
        statementProvider
                .appendWithMultipleArgs(CREATE, values)
                .append(RETURNING);
        return save(statementProvider, extractor);
    }

    public Optional<DriverLicenseJpa> findByUserDetailsId(Long userDetailsId) {
        var statementProvider = new BaseStatementProvider();
        statementProvider
                .append(FIND_QUERY_PREFIX)
                .appendWithSingleArg("WHERE user_details_id = ?", userDetailsId);
        return findOne(statementProvider, extractor);
    }

    public Optional<DriverLicenseJpa> findByUserId(Long userId) {
        var statementProvider = new BaseStatementProvider();
        statementProvider
                .append(FIND_QUERY_PREFIX)
                .append("LEFT JOIN userdetails ud ON driverlicense.user_details_id = ud.user_id")
                .appendWithSingleArg("WHERE ud.user_id = ?", userId);
        return findOne(statementProvider, extractor);
    }

    public List<DriverLicenseJpa> findAllExpired() {
        var statementProvider = new BaseStatementProvider();
        statementProvider
                .append(FIND_QUERY_PREFIX)
                .appendWithSingleArg("WHERE expired_date = ?", Instant.now());
        return findAll(statementProvider, extractor);
    }
}