package com.dmdev.natalliavasilyeva.persistence.repository;

import com.dmdev.natalliavasilyeva.domain.model.Color;
import com.dmdev.natalliavasilyeva.domain.model.EngineType;
import com.dmdev.natalliavasilyeva.domain.model.OrderStatus;
import com.dmdev.natalliavasilyeva.domain.model.Role;
import com.dmdev.natalliavasilyeva.domain.model.Transmission;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static java.sql.Statement.RETURN_GENERATED_KEYS;

public class BaseStatementProvider {

    final List<Object> args;

    private String sql;

    public BaseStatementProvider() {
        this("");
    }

    public BaseStatementProvider(String sql) {
        this.sql = sql;
        args = new ArrayList<>();
    }

    protected PreparedStatement setArguments(PreparedStatement statement, List<Object> args) {
        var i = 1;

        // Append new argument types as needed
        for (Object arg : args) {
            try {
                if (arg instanceof Boolean) {
                    statement.setBoolean(i++, (Boolean) arg);
                } else if (arg instanceof Integer) {
                    statement.setInt(i++, (Integer) arg);
                } else if (arg instanceof Long) {
                    statement.setLong(i++, (Long) arg);
                } else if (arg instanceof BigDecimal) {
                    statement.setBigDecimal(i++, (BigDecimal) arg);
                } else if (arg instanceof Instant) {
                    statement.setTimestamp(i++, Timestamp.from((Instant) arg));
                } else if (arg instanceof String) {
                    statement.setString(i++, (String) arg);
                } else if (arg instanceof Transmission || arg instanceof EngineType || arg instanceof Color || arg instanceof OrderStatus || arg instanceof Role) {
                    statement.setString(i++, (String) ((Enum<?>) arg).name());
                } else if (arg.getClass().isArray() && arg instanceof String[]) {
                    var stringObjects = statement.getConnection().createArrayOf("VARCHAR", (Object[]) arg);
                    statement.setArray(i++, stringObjects);
                } else if (arg.getClass().isArray() && arg instanceof Long[]) {
                    var longObjects = statement.getConnection().createArrayOf("INT", (Object[]) arg);
                    statement.setArray(i++, longObjects);
                } else if (arg.getClass().isArray() && arg instanceof Instant[]) {
                    var timeStampObjects = statement.getConnection().createArrayOf("TIMESTAMP", (Object[]) arg);
                    statement.setArray(i++, timeStampObjects);
                } else {
                    // disallow anything that doesn't have a pre-defined structure (and cannot be validated at API level)
                    throw new IllegalArgumentException("Unsupported argument type provided");
                }
            } catch (SQLException e) {
                throw new RuntimeException(String.format("Something wrong with set arguments for prepare statement: %s", e.getCause()), e);
            }
        }

        return statement;
    }

    public BaseStatementProvider appendWithSingleArg(String query, Object arg) {
        return arg == null ? append(query) : append(query, Collections.singletonList(arg));
    }

    public BaseStatementProvider appendWithMultipleArgs(String query, Object... args) {
        return append(query, Arrays.stream(args).collect(Collectors.toList()));
//        return append(query, Arrays.stream(args).filter(Objects::nonNull).collect(Collectors.toList()));
    }

    public BaseStatementProvider appendWithMultipleArgs(String query, List<Object> objects) {
        return append(query, objects);
//        return append(query, Arrays.stream(args).filter(Objects::nonNull).collect(Collectors.toList()));
    }

    public BaseStatementProvider appendOptional(String query, Object arg) {
        if (arg != null) {
            return appendWithSingleArg(query, arg);
        }
        return this;
    }

    public BaseStatementProvider append(String query) {
        return append(query, Collections.emptyList());
    }

    public BaseStatementProvider append(String query, List<Object> args) {
        // prepare data
        long paramCount = query.chars().filter(ch -> ch == '?').count();
//        List<Object> argList = args.stream().filter(Objects::nonNull).collect(Collectors.toList());

        // check arguments for correct length
        if (paramCount == 0 && !args.isEmpty()) {
            throw new IllegalArgumentException("Incorrect number of arguments provided: query does not require arguments");
        } else if (args.size() != paramCount) {
            var message = String.format("Incorrect number of arguments provided: query requires %s argument(s)", paramCount);
            throw new IllegalArgumentException(message);
        }

        // append to query and args
        sql += query;
        this.args.addAll(args);

        // return object for chaining
        return this;
    }

    public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        return setArguments(preparedStatement, args);
    }

    public PreparedStatement createPreparedStatementWithGeneratedKeys(Connection connection) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement(sql, RETURN_GENERATED_KEYS);
        return setArguments(preparedStatement, args);
    }

    public String getSql() {
        return sql;
    }
}