package com.dmdev.natalliavasilyeva.persistence.repository.jpa.dao;

import com.dmdev.natalliavasilyeva.domain.jpa.DriverLicense;
import com.dmdev.natalliavasilyeva.persistence.repository.BaseStatementProvider;
import com.dmdev.natalliavasilyeva.persistence.repository.jpa.rowmapper.ResultSetExtractor;
import com.dmdev.natalliavasilyeva.persistence.utils.ParseObjectUtils;
import com.dmdev.natalliavasilyeva.persistence.repository.jpa.GenericRepository;
import com.dmdev.natalliavasilyeva.persistence.repository.jpa.rowmapper.DriverLicenseResultExtractor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

public class DriverLicenseRepository extends AbstractRepository<DriverLicense> implements GenericRepository<DriverLicense, Long> {

    private static final Logger logger = LoggerFactory.getLogger(DriverLicenseRepository.class);
    ResultSetExtractor<DriverLicense> extractor;

    public DriverLicenseRepository() {
        this.extractor = new DriverLicenseResultExtractor();
    }

    private static final String FIND_QUERY_PREFIX = "" +
            "SELECT id, user_details_id, number, issue_date, expired_date\n" +
            "FROM driverlicense\n";

    private static final String CREATE = "" +
            "INSERT INTO driverlicense(user_details_id, number, issue_date, expired_date) values (?, ?, ?, ?)";

    private static final String UPDATE = "" +
            "UPDATE driverlicense SET user_details_id = ?, number = ?, issue_date = ?, expired_date = ? WHERE id = ?";

    private static final String DELETE = "" +
            "DELETE FROM driverlicense WHERE id = ?";

    private static final String RETURNING = "" +
            "RETURNING id, user_details_id, number, issue_date, expired_date";


    @Override
    public Optional<DriverLicense> findById(Long id) {
        var statementProvider = new BaseStatementProvider();
        statementProvider
                .append(FIND_QUERY_PREFIX)
                .appendWithSingleArg("WHERE id = ?", id);
        return findOne(statementProvider, extractor);
    }

    @Override
    public List<DriverLicense> findAll() {
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
    public Optional<DriverLicense> delete(DriverLicense driverLicense) {
        var statementProvider = new BaseStatementProvider();
        statementProvider
                .appendWithSingleArg(DELETE, driverLicense.getId())
                .append(RETURNING);
        return delete(statementProvider, extractor);
    }

    @Override
    public Optional<DriverLicense> update(DriverLicense driverLicense) {
        List<Object> values = ParseObjectUtils.getFieldObjectsWithoutId(driverLicense);
        values.add(driverLicense.getId());
        var statementProvider = new BaseStatementProvider();
        statementProvider
                .appendWithMultipleArgs(UPDATE, values);
        return update(driverLicense, statementProvider);
    }

    @Override
    public Optional<DriverLicense> save(DriverLicense driverLicense) {
        List<Object> values = ParseObjectUtils.getFieldObjectsWithoutId(driverLicense);
        var statementProvider = new BaseStatementProvider();
        statementProvider
                .appendWithMultipleArgs(CREATE, values)
                .append(RETURNING);
        return save(statementProvider, extractor);
    }

    public Optional<DriverLicense> findByUserDetailsId(Long userDetailsId) {
        var statementProvider = new BaseStatementProvider();
        statementProvider
                .append(FIND_QUERY_PREFIX)
                .appendWithSingleArg("WHERE user_details_id = ?", userDetailsId);
        return findOne(statementProvider, extractor);
    }

    public Optional<DriverLicense> findByUserId(Long userId) {
        var statementProvider = new BaseStatementProvider();
        statementProvider
                .append(FIND_QUERY_PREFIX)
                .append("LEFT JOIN userdetails ud ON driverlicense.user_details_id = ud.user_id")
                .appendWithSingleArg("WHERE ud.user_id = ?", userId);
        return findOne(statementProvider, extractor);
    }

    public List<DriverLicense> findAllExpired() {
        var statementProvider = new BaseStatementProvider();
        statementProvider
                .append(FIND_QUERY_PREFIX)
                .appendWithSingleArg("WHERE expired_date = ?", Instant.now());
        return findAll(statementProvider, extractor);
    }
}