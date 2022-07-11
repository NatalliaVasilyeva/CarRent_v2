package com.dmdev.natalliavasilyeva.persistence.repository.custom.dao;


import com.dmdev.natalliavasilyeva.connection.ConnectionPool;
import com.dmdev.natalliavasilyeva.connection.exception.ConnectionPoolException;
import com.dmdev.natalliavasilyeva.domain.model.User;
import com.dmdev.natalliavasilyeva.persistence.repository.BaseStatementProvider;
import com.dmdev.natalliavasilyeva.persistence.repository.custom.GenericCustomRepository;
import com.dmdev.natalliavasilyeva.persistence.repository.custom.rowmapper.UserResultExtractor;

import java.sql.SQLException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserCustomRepository implements GenericCustomRepository<User, Long> {
    ConnectionPool connectionPool;
    UserResultExtractor extractor;

    public UserCustomRepository() {
        this.connectionPool = ConnectionPool.getInstance();
        this.extractor = new UserResultExtractor();
    }

    private final static String FIND_WITH_ORDERS = "" +
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
            "       dl.expired_date                  as license_expired_date,\n" +
            "       json_agg(row_to_json(order_des)) as order_description\n" +
            "FROM users u\n" +
            "       LEFT JOIN userdetails ud ON u.id = ud.user_id\n" +
            "       LEFT JOIN driverlicense dl ON ud.id = dl.user_details_id\n" +
            "       LEFT JOIN (SELECT o.id      as order_id,\n" +
            "                         o.user_id as order_user_id,\n" +
            "                         o.date    as order_date,\n" +
            "                         crt.start_rental_date,\n" +
            "                         crt.end_rental_date,\n" +
            "                         c.car_number\n" +
            "                  FROM orders o\n" +
            "                         LEFT JOIN carrentaltime crt ON o.id = crt.order_id\n" +
            "                         LEFT JOIN car c ON o.car_id = c.id) AS order_des\n" +
            "                  ON u.id = order_des.order_user_id\n";


    private final static String FIND_WITHOUT_ORDERS = "" +
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
    public List<User> findAll() throws SQLException, ConnectionPoolException {
        List<User> users = new ArrayList<>();
        var statementProvider = new BaseStatementProvider();
        statementProvider
                .append(FIND_WITH_ORDERS)
                .append("GROUP BY u.id,ud.id,dl.id");
        try (var prepareStatement = statementProvider.createPreparedStatement(connectionPool.getConnection())) {
            var resultSet = prepareStatement.executeQuery();
            while (resultSet.next()) {
                users.add(extractor.extractData(resultSet));
            }
        }
        return users;
    }

    @Override
    public Optional<User> findById(Long id) throws SQLException, ConnectionPoolException {
        var statementProvider = new BaseStatementProvider();
        statementProvider
                .append(FIND_WITH_ORDERS)
                .appendWithSingleArg("WHERE u.id = ?", id)
                .append("GROUP BY u.id,ud.id,dl.id");
        try (var prepareStatement = statementProvider.createPreparedStatement(connectionPool.getConnection())) {
            var resultSet = prepareStatement.executeQuery();
            User user = null;
            if (resultSet.next()) {
                user = extractor.extractData(resultSet);
            }
            return Optional.ofNullable(user);
        }
    }

    public Optional<User> findByLogin(String login) throws SQLException, ConnectionPoolException {
        var statementProvider = new BaseStatementProvider();
        statementProvider
                .append(FIND_WITHOUT_ORDERS)
                .appendWithSingleArg("WHERE u.login LIKE ?", login)
                .append("GROUP BY u.id,ud.id,dl.id");
        try (var prepareStatement = statementProvider.createPreparedStatement(connectionPool.getConnection())) {
            var resultSet = prepareStatement.executeQuery();
            User user = null;
            if (resultSet.next()) {
                user = extractor.extractData(resultSet);
            }
            return Optional.ofNullable(user);
        }
    }

    public Optional<User> findByEmail(String email) throws SQLException, ConnectionPoolException {
        var statementProvider = new BaseStatementProvider();
        statementProvider
                .append(FIND_WITHOUT_ORDERS)
                .appendWithSingleArg("WHERE u.email LIKE ?", email)
                .append("GROUP BY u.id,ud.id,dl.id");
        try (var prepareStatement = statementProvider.createPreparedStatement(connectionPool.getConnection())) {
            var resultSet = prepareStatement.executeQuery();
            User user = null;
            if (resultSet.next()) {
                user = extractor.extractData(resultSet);
            }
            return Optional.ofNullable(user);
        }
    }

    public List<User> findByRole(String role) throws SQLException, ConnectionPoolException {
        List<User> users = new ArrayList<>();
        var statementProvider = new BaseStatementProvider();
        statementProvider
                .append(FIND_WITHOUT_ORDERS)
                .appendWithSingleArg("WHERE u.role LIKE ?", role)
                .append("GROUP BY u.id,ud.id,dl.id");
        try (var prepareStatement = statementProvider.createPreparedStatement(connectionPool.getConnection())) {
            var resultSet = prepareStatement.executeQuery();
            while (resultSet.next()) {
                users.add(extractor.extractData(resultSet));
            }
        }
        return users;
    }

    public List<User> findByRegistrationDate(Instant registrationDate) throws SQLException, ConnectionPoolException {
        List<User> users = new ArrayList<>();
        var statementProvider = new BaseStatementProvider();
        statementProvider
                .append(FIND_WITHOUT_ORDERS)
                .appendWithSingleArg("WHERE ud.registration_date = ?", registrationDate)
                .append("GROUP BY u.id,ud.id,dl.id");
        try (var prepareStatement = statementProvider.createPreparedStatement(connectionPool.getConnection())) {
            var resultSet = prepareStatement.executeQuery();
            while (resultSet.next()) {
                users.add(extractor.extractData(resultSet));
            }
        }
        return users;
    }

    public List<User> findByRegistrationDateLess(Instant registrationDate) throws SQLException, ConnectionPoolException {
        List<User> users = new ArrayList<>();
        var statementProvider = new BaseStatementProvider();
        statementProvider
                .append(FIND_WITHOUT_ORDERS)
                .appendWithSingleArg("WHERE ud.registration_date < ?", registrationDate)
                .append("GROUP BY u.id,ud.id,dl.id");
        try (var prepareStatement = statementProvider.createPreparedStatement(connectionPool.getConnection())) {
            var resultSet = prepareStatement.executeQuery();
            while (resultSet.next()) {
                users.add(extractor.extractData(resultSet));
            }
        }
        return users;
    }

    public List<User> findByRegistrationDateMore(Instant registrationDate) throws SQLException, ConnectionPoolException {
        List<User> users = new ArrayList<>();
        var statementProvider = new BaseStatementProvider();
        statementProvider
                .append(FIND_WITHOUT_ORDERS)
                .appendWithSingleArg("WHERE ud.registration_date > ?", registrationDate)
                .append("GROUP BY u.id,ud.id,dl.id");
        try (var prepareStatement = statementProvider.createPreparedStatement(connectionPool.getConnection())) {
            var resultSet = prepareStatement.executeQuery();
            while (resultSet.next()) {
                users.add(extractor.extractData(resultSet));
            }
        }
        return users;
    }

    public List<User> findByPhone(String phone) throws SQLException, ConnectionPoolException {
        List<User> users = new ArrayList<>();
        var statementProvider = new BaseStatementProvider();
        statementProvider
                .append(FIND_WITHOUT_ORDERS)
                .appendWithSingleArg("WHERE ud.phone LIKE ?", phone)
                .append("GROUP BY u.id,ud.id,dl.id");
        try (var prepareStatement = statementProvider.createPreparedStatement(connectionPool.getConnection())) {
            var resultSet = prepareStatement.executeQuery();
            while (resultSet.next()) {
                users.add(extractor.extractData(resultSet));
            }
        }
        return users;
    }

    public List<User> findByBirthday(Instant birthday) throws SQLException, ConnectionPoolException {
        List<User> users = new ArrayList<>();
        var statementProvider = new BaseStatementProvider();
        statementProvider
                .append(FIND_WITHOUT_ORDERS)
                .appendWithSingleArg("WHERE ud.phone = ?", birthday)
                .append("GROUP BY u.id,ud.id,dl.id");
        try (var prepareStatement = statementProvider.createPreparedStatement(connectionPool.getConnection())) {
            var resultSet = prepareStatement.executeQuery();
            while (resultSet.next()) {
                users.add(extractor.extractData(resultSet));
            }
        }
        return users;
    }

    public List<User> findAllWithOrders() throws SQLException, ConnectionPoolException {
        List<User> users = new ArrayList<>();
        var statementProvider = new BaseStatementProvider();
        statementProvider
                .append(FIND_WITH_ORDERS)
                .append("WHERE user_id IN (SELECT od.user_id FROM orders od WHERE od.user_id IS NOT NULL)")
                .append("GROUP BY u.id,ud.id,dl.id");
        try (var prepareStatement = statementProvider.createPreparedStatement(connectionPool.getConnection())) {
            var resultSet = prepareStatement.executeQuery();
            while (resultSet.next()) {
                users.add(extractor.extractData(resultSet));
            }
        }
        return users;
    }

    public List<User> findAllWithoutOrders() throws SQLException, ConnectionPoolException {
        List<User> users = new ArrayList<>();
        var statementProvider = new BaseStatementProvider();
        statementProvider
                .append(FIND_WITHOUT_ORDERS)
                .append("WHERE user_id NOT IN (SELECT od.user_id FROM orders od WHERE od.user_id IS NOT NULL)")
                .append("GROUP BY u.id,ud.id,dl.id");
        try (var prepareStatement = statementProvider.createPreparedStatement(connectionPool.getConnection())) {
            var resultSet = prepareStatement.executeQuery();
            while (resultSet.next()) {
                users.add(extractor.extractData(resultSet));
            }
        }
        return users;
    }

    public List<User> findAllWithExpiredDriverLicense() throws SQLException, ConnectionPoolException {
        List<User> users = new ArrayList<>();
        var statementProvider = new BaseStatementProvider();
        statementProvider
                .append(FIND_WITHOUT_ORDERS)
                .appendWithSingleArg("WHERE dl.expired_date < ?", Instant.now())
                .append("GROUP BY u.id,ud.id,dl.id");
        try (var prepareStatement = statementProvider.createPreparedStatement(connectionPool.getConnection())) {
            var resultSet = prepareStatement.executeQuery();
            while (resultSet.next()) {
                users.add(extractor.extractData(resultSet));
            }
        }
        return users;
    }
}