package com.dmdev.natalliavasilyeva.persistence.repository.jpa.rowmapper;

import com.dmdev.natalliavasilyeva.domain.jpa.User;
import com.dmdev.natalliavasilyeva.persistence.exception.RepositoryException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UserResultExtractor implements ResultSetExtractor<User> {

    private static final Logger logger = LoggerFactory.getLogger(UserResultExtractor.class);

    @Override
    public User extractData(ResultSet rs) {
        try {
            return new User.Builder()
                    .id(rs.getLong("id"))
                    .login(rs.getString("login"))
                    .email(rs.getString("email"))
                    .role(rs.getString("role"))
                    .build();
        } catch (SQLException ex) {
            throw new RepositoryException(String.format("Exception for user jpa in extract data method: %s", ex.getCause()), ex);
        }
    }
}