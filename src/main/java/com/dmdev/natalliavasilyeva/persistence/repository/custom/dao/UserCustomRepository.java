package com.dmdev.natalliavasilyeva.persistence.repository.custom.dao;


import com.dmdev.natalliavasilyeva.domain.model.User;
import com.dmdev.natalliavasilyeva.persistence.repository.BaseStatementProvider;
import com.dmdev.natalliavasilyeva.persistence.repository.custom.GenericCustomRepository;
import com.dmdev.natalliavasilyeva.persistence.repository.custom.rowmapper.ResultSetExtractor;
import com.dmdev.natalliavasilyeva.persistence.repository.custom.rowmapper.UserResultExtractor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

public class UserCustomRepository extends AbstractCustomRepository<User> implements GenericCustomRepository<User, Long> {

    private static final Logger logger = LoggerFactory.getLogger(UserCustomRepository.class);
    ResultSetExtractor<User> extractor;

    public UserCustomRepository() {
        this.extractor = new UserResultExtractor();
    }

    private static final String FIND_WITH_ORDERS = "" +
            "SELECT u.id                                            as user_id,\n" +
            "       u.login,\n" +
            "       u.email,\n" +
            "       u.role,\n" +
            "       ud.id                                           as user_details_id,\n" +
            "       ud.name,\n" +
            "       ud.surname\n,\n" +
            "       ud.address,\n" +
            "       ud.phone,\n" +
            "       ud.birthday,\n" +
            "       ud.registration_date,\n" +
            "       dl.id                                           as driver_license_id,\n" +
            "       dl.number                                       as driver_license_number,\n" +
            "       dl.issue_date                                   as license_issue_date,\n" +
            "       dl.expired_date                                 as license_expired_date,\n" +
            "       json_agg(row_to_json(order_des))                as order_description\n" +
            "FROM users u\n" +
            "       LEFT JOIN userdetails ud ON u.id = ud.user_id\n" +
            "       LEFT JOIN driverlicense dl ON ud.id = dl.user_details_id\n" +
            "       LEFT JOIN (SELECT o.id                          as order_id,\n" +
            "                         o.user_id                     as order_user_id,\n" +
            "                         o.date                        as order_date,\n" +
            "                         crt.start_rental_date,\n" +
            "                         crt.end_rental_date,\n" +
            "                         c.car_number\n" +
            "                  FROM orders o\n" +
            "                         LEFT JOIN carrentaltime crt ON o.id = crt.order_id\n" +
            "                         LEFT JOIN car c ON o.car_id = c.id) AS order_des\n" +
            "                  ON u.id = order_des.order_user_id\n";

    private final static String GROUP_BY = "" +
            "GROUP BY u.id,ud.id,dl.id";

    private static final String FIND_WITHOUT_ORDERS = "" +
            "SELECT u.id                             as user_id,\n" +
            "       u.login,\n" +
            "       u.email,\n" +
            "       u.role,\n" +
            "       ud.id                            as user_details_id,\n" +
            "       ud.name,\n" +
            "       ud.surname\n,\n" +
            "       ud.address,\n" +
            "       ud.phone,\n" +
            "       ud.birthday,\n" +
            "       ud.registration_date,\n" +
            "       dl.id                            as driver_license_id,\n" +
            "       dl.number                        as driver_license_number,\n" +
            "       dl.issue_date                    as license_issue_date,\n" +
            "       dl.expired_date                  as license_expired_date\n" +
            "FROM users u\n" +
            "       LEFT JOIN userdetails ud ON u.id = ud.user_id\n" +
            "       LEFT JOIN driverlicense dl ON ud.id = dl.user_details_id\n";


    @Override
    public List<User> findAll() {
        var statementProvider = new BaseStatementProvider();
        statementProvider
                .append(FIND_WITH_ORDERS)
                .append(GROUP_BY);
        return findAll(statementProvider, extractor);
    }

    @Override
    public Optional<User> findById(Long id) {
        var statementProvider = new BaseStatementProvider();
        statementProvider
                .append(FIND_WITH_ORDERS)
                .appendWithSingleArg("WHERE u.id = ?\n", id)
                .append(GROUP_BY);
        return findOne(statementProvider, extractor);
    }

    public Optional<User> findByLogin(String login) {
        var statementProvider = new BaseStatementProvider();
        statementProvider
                .append(FIND_WITHOUT_ORDERS)
                .appendWithSingleArg("WHERE u.login LIKE ?\n", login)
                .append(GROUP_BY);
        return findOne(statementProvider, extractor);
    }

    public Optional<User> findByEmail(String email) {
        var statementProvider = new BaseStatementProvider();
        statementProvider
                .append(FIND_WITHOUT_ORDERS)
                .appendWithSingleArg("WHERE u.email LIKE ?\n", email)
                .append(GROUP_BY);
        return findOne(statementProvider, extractor);
    }

    public List<User> findByRole(String role) {
        var statementProvider = new BaseStatementProvider();
        statementProvider
                .append(FIND_WITHOUT_ORDERS)
                .appendWithSingleArg("WHERE u.role LIKE ?\n", role)
                .append(GROUP_BY);
        return findAll(statementProvider, extractor);
    }

    public List<User> findByRegistrationDate(Instant registrationDate) {
        var statementProvider = new BaseStatementProvider();
        statementProvider
                .append(FIND_WITHOUT_ORDERS)
                .appendWithSingleArg("WHERE ud.registration_date = ?\n", registrationDate)
                .append(GROUP_BY);
        return findAll(statementProvider, extractor);
    }

    public List<User> findByRegistrationDateLess(Instant registrationDate) {
        var statementProvider = new BaseStatementProvider();
        statementProvider
                .append(FIND_WITHOUT_ORDERS)
                .appendWithSingleArg("WHERE ud.registration_date < ?\n", registrationDate)
                .append(GROUP_BY);
        return findAll(statementProvider, extractor);
    }

    public List<User> findByRegistrationDateMore(Instant registrationDate) {
        var statementProvider = new BaseStatementProvider();
        statementProvider
                .append(FIND_WITHOUT_ORDERS)
                .appendWithSingleArg("WHERE ud.registration_date > ?\n", registrationDate)
                .append(GROUP_BY);
        return findAll(statementProvider, extractor);
    }

    public List<User> findByPhone(String phone) {
        var statementProvider = new BaseStatementProvider();
        statementProvider
                .append(FIND_WITHOUT_ORDERS)
                .appendWithSingleArg("WHERE ud.phone LIKE ?\n", phone)
                .append(GROUP_BY);
        return findAll(statementProvider, extractor);
    }

    public List<User> findByBirthday(Instant birthday) {
        var statementProvider = new BaseStatementProvider();
        statementProvider
                .append(FIND_WITHOUT_ORDERS)
                .appendWithSingleArg("WHERE ud.phone = ?\n", birthday)
                .append(GROUP_BY);
        return findAll(statementProvider, extractor);
    }

    public List<User> findAllWithOrders() {
        var statementProvider = new BaseStatementProvider();
        statementProvider
                .append(FIND_WITH_ORDERS)
                .append("WHERE user_id IN (SELECT od.user_id FROM orders od WHERE od.user_id IS NOT NULL)\n")
                .append(GROUP_BY);
        return findAll(statementProvider, extractor);
    }

    public List<User> findAllWithoutOrders() {
        var statementProvider = new BaseStatementProvider();
        statementProvider
                .append(FIND_WITHOUT_ORDERS)
                .append("WHERE user_id NOT IN (SELECT od.user_id FROM orders od WHERE od.user_id IS NOT NULL)\n")
                .append(GROUP_BY);
        return findAll(statementProvider, extractor);
    }

    public List<User> findAllWithExpiredDriverLicense() {
        var statementProvider = new BaseStatementProvider();
        statementProvider
                .append(FIND_WITHOUT_ORDERS)
                .appendWithSingleArg("WHERE dl.expired_date < ?\n", Instant.now())
                .append(GROUP_BY);
        return findAll(statementProvider, extractor);
    }
}