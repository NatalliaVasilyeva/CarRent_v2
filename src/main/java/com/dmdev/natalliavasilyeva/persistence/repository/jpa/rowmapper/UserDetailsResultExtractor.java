package com.dmdev.natalliavasilyeva.persistence.repository.jpa.rowmapper;

import com.dmdev.natalliavasilyeva.domain.jpa.UserDetailsJpa;
import com.dmdev.natalliavasilyeva.persistence.exception.RepositoryException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

public class UserDetailsResultExtractor implements ResultSetExtractor<UserDetailsJpa> {

    private static final Logger logger = LoggerFactory.getLogger(UserDetailsResultExtractor.class);

    @Override
    public UserDetailsJpa extractData(ResultSet rs) {
        try {
            Timestamp birthday = rs.getTimestamp("birthday");
            Timestamp registrationDate = rs.getTimestamp("birthday");

            return new UserDetailsJpa.Builder()
                    .id(rs.getLong("id"))
                    .user(rs.getLong("user_id"))
                    .name(rs.getString("name"))
                    .surname(rs.getString("surname"))
                    .address(rs.getString("address"))
                    .phone(rs.getString("phone"))
                    .birthday(birthday == null ? null : birthday.toInstant())
                    .registrationDate(registrationDate == null ? null : registrationDate.toInstant())
                    .build();
        } catch (SQLException ex) {
            throw new RepositoryException(String.format("Exception for user details jpa in extract data method: %s", ex.getCause()), ex);
        }
    }
}