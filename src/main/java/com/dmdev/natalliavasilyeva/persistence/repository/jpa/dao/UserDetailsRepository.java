package com.dmdev.natalliavasilyeva.persistence.repository.jpa.dao;

import com.dmdev.natalliavasilyeva.domain.jpa.UserDetails;
import com.dmdev.natalliavasilyeva.persistence.repository.BaseStatementProvider;
import com.dmdev.natalliavasilyeva.persistence.utils.ParseObjectUtils;
import com.dmdev.natalliavasilyeva.persistence.repository.jpa.GenericRepository;
import com.dmdev.natalliavasilyeva.persistence.repository.jpa.rowmapper.UserDetailsResultExtractor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

public class UserDetailsRepository extends AbstractRepository<UserDetails> implements GenericRepository<UserDetails, Long> {

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
    public Optional<UserDetails> findById(Long id) {
        var statementProvider = new BaseStatementProvider();
        statementProvider
                .append(FIND_QUERY_PREFIX)
                .appendWithSingleArg("WHERE id = ?", id);
        return findOne(statementProvider, extractor);
    }

    @Override
    public List<UserDetails> findAll() {
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
    public Optional<UserDetails> delete(UserDetails userDetails) {
        var statementProvider = new BaseStatementProvider();
        statementProvider
                .appendWithSingleArg(DELETE, userDetails.getId())
                .append(RETURNING);
        return delete(statementProvider, extractor);
    }

    @Override
    public Optional<UserDetails> update(UserDetails userDetails) {
        List<Object> values = ParseObjectUtils.getFieldObjectsWithoutId(userDetails);
        values.add(userDetails.getId());
        var statementProvider = new BaseStatementProvider();
        statementProvider
                .appendWithMultipleArgs(UPDATE, values);
        return update(userDetails, statementProvider);
    }

    @Override
    public Optional<UserDetails> save(UserDetails userDetails) {
        List<Object> values = ParseObjectUtils.getFieldObjectsWithoutId(userDetails);
        var statementProvider = new BaseStatementProvider();
        statementProvider
                .appendWithMultipleArgs(CREATE, values)
                .append(RETURNING);
        return save(statementProvider, extractor);
    }

    public Optional<UserDetails> findByUserId(Long userId) {
        var statementProvider = new BaseStatementProvider();
        statementProvider
                .append(FIND_QUERY_PREFIX)
                .appendWithSingleArg("WHERE user_id = ?", userId);
        return findOne(statementProvider, extractor);
    }

    public List<UserDetails> findAllByRegistrationDate(Instant registrationDate) {
        var statementProvider = new BaseStatementProvider();
        statementProvider
                .append(FIND_QUERY_PREFIX)
                .appendWithSingleArg("WHERE registration_date = ?", registrationDate);
        return findAll(statementProvider, extractor);
    }

    public List<UserDetails> findAllByRegistrationDateLess(Instant registrationDate) {
        var statementProvider = new BaseStatementProvider();
        statementProvider
                .append(FIND_QUERY_PREFIX)
                .appendWithSingleArg("WHERE registration_date < ?", registrationDate);
        return findAll(statementProvider, extractor);
    }

    public List<UserDetails> findAllByRegistrationDateMore(Instant registrationDate) {
        var statementProvider = new BaseStatementProvider();
        statementProvider
                .append(FIND_QUERY_PREFIX)
                .appendWithSingleArg("WHERE registration_date > ?", registrationDate);
        return findAll(statementProvider, extractor);
    }

    public List<UserDetails> findAllByRegistrationDates(Instant from, Instant to) {
        var statementProvider = new BaseStatementProvider();
        statementProvider
                .append(FIND_QUERY_PREFIX)
                .appendWithMultipleArgs("WHERE registration_date BETWEEN ? AND ?", from, to);
        return findAll(statementProvider, extractor);
    }
}