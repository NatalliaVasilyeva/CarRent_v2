package com.dmdev.natalliavasilyeva.persistence.repository.jpa.dao;

import com.dmdev.natalliavasilyeva.domain.jpa.UserDetailsJpa;
import com.dmdev.natalliavasilyeva.persistence.repository.BaseStatementProvider;
import com.dmdev.natalliavasilyeva.utils.ParseObjectUtils;
import com.dmdev.natalliavasilyeva.persistence.repository.jpa.GenericRepository;
import com.dmdev.natalliavasilyeva.persistence.repository.jpa.rowmapper.UserDetailsResultExtractor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

public class UserDetailsRepository extends AbstractRepository<UserDetailsJpa> implements GenericRepository<UserDetailsJpa, Long> {

    private static final Logger logger = LoggerFactory.getLogger(UserDetailsRepository.class);
    UserDetailsResultExtractor extractor;

    public UserDetailsRepository() {
        this.extractor = new UserDetailsResultExtractor();
    }
    private static final String FIND_QUERY_PREFIX = "" +
            "SELECT id, user_id, name, surname, address, phone, birthday, registration_date\n" +
            "FROM userdetails\n";

    private static final String CREATE = "" +
            "INSERT INTO userdetails(user_id, name, surname, address, phone, birthday, registration_date) values (?, ?, ?, ?, ?, ?, ?)";

    private static final String UPDATE = "" +
            "UPDATE userdetails SET user_id = ?, name = ?, surname = ?, address = ?, phone = ?, birthday = ? WHERE id = ?";

    private static final String DELETE = "" +
            "DELETE FROM userdetails WHERE id = ?";

    private static final String RETURNING = "" +
            "RETURNING id, user_id, name, surname, address, phone, birthday, registration_date";

    @Override
    public Optional<UserDetailsJpa> findById(Long id) {
        var statementProvider = new BaseStatementProvider();
        statementProvider
                .append(FIND_QUERY_PREFIX)
                .appendWithSingleArg("WHERE id = ?", id);
        return findOne(statementProvider, extractor);
    }

    @Override
    public List<UserDetailsJpa> findAll() {
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
    public Optional<UserDetailsJpa> delete(UserDetailsJpa userDetailsJpa) {
        var statementProvider = new BaseStatementProvider();
        statementProvider
                .appendWithSingleArg(DELETE, userDetailsJpa.getId())
                .append(RETURNING);
        return delete(statementProvider, extractor);
    }

    @Override
    public Optional<UserDetailsJpa> update(UserDetailsJpa userDetailsJpa) {
        List<Object> values = ParseObjectUtils.getFieldObjectsWithoutId(userDetailsJpa);
        values.add(userDetailsJpa.getId());
        var statementProvider = new BaseStatementProvider();
        statementProvider
                .appendWithMultipleArgs(UPDATE, values);
        return update(userDetailsJpa, statementProvider);
    }

    @Override
    public Optional<UserDetailsJpa> save(UserDetailsJpa userDetailsJpa) {
        List<Object> values = ParseObjectUtils.getFieldObjectsWithoutId(userDetailsJpa);
        var statementProvider = new BaseStatementProvider();
        statementProvider
                .appendWithMultipleArgs(CREATE, values)
                .append(RETURNING);
        return save(statementProvider, extractor);
    }

    public Optional<UserDetailsJpa> findByUserId(Long userId) {
        var statementProvider = new BaseStatementProvider();
        statementProvider
                .append(FIND_QUERY_PREFIX)
                .appendWithSingleArg("WHERE user_id = ?", userId);
        return findOne(statementProvider, extractor);
    }

    public List<UserDetailsJpa> findAllByRegistrationDate(Instant registrationDate) {
        var statementProvider = new BaseStatementProvider();
        statementProvider
                .append(FIND_QUERY_PREFIX)
                .appendWithSingleArg("WHERE registration_date = ?", registrationDate);
        return findAll(statementProvider, extractor);
    }

    public List<UserDetailsJpa> findAllByRegistrationDateLess(Instant registrationDate) {
        var statementProvider = new BaseStatementProvider();
        statementProvider
                .append(FIND_QUERY_PREFIX)
                .appendWithSingleArg("WHERE registration_date < ?", registrationDate);
        return findAll(statementProvider, extractor);
    }

    public List<UserDetailsJpa> findAllByRegistrationDateMore(Instant registrationDate) {
        var statementProvider = new BaseStatementProvider();
        statementProvider
                .append(FIND_QUERY_PREFIX)
                .appendWithSingleArg("WHERE registration_date > ?", registrationDate);
        return findAll(statementProvider, extractor);
    }

    public List<UserDetailsJpa> findAllByRegistrationDates(Instant from, Instant to) {
        var statementProvider = new BaseStatementProvider();
        statementProvider
                .append(FIND_QUERY_PREFIX)
                .appendWithMultipleArgs("WHERE registration_date BETWEEN ? AND ?", from, to);
        return findAll(statementProvider, extractor);
    }
}