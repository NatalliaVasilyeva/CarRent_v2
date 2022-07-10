package com.dmdev.natalliavasilyeva.persistence.repository.jpa.rowmapper;

import com.dmdev.natalliavasilyeva.domain.jpa.UserDetails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

public class UserDetailsResultExtractor implements ResultSetExtractor<UserDetails> {

    private static final Logger logger = LoggerFactory.getLogger(UserDetailsResultExtractor.class);

    @Override
    public UserDetails extractData(ResultSet rs) throws SQLException {
        Timestamp birthday = rs.getTimestamp("birthday");
        Timestamp registrationDate = rs.getTimestamp("birthday");

        return new UserDetails.Builder()
                .id(rs.getLong("id"))
                .user(rs.getLong("user_id"))
                .name(rs.getString("name"))
                .surname(rs.getString("surname"))
                .address(rs.getString("address"))
                .phone(rs.getString("phone"))
                .birthday(birthday == null ? null : birthday.toInstant())
                .registrationDate(registrationDate == null ? null : registrationDate.toInstant())
                .build();
    }
}