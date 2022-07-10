package com.dmdev.natalliavasilyeva.persistence.repository.jpa.rowmapper;

import com.dmdev.natalliavasilyeva.domain.jpa.DriverLicense;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

public class DriverLicenseResultExtractor implements ResultSetExtractor<DriverLicense> {

    private static final Logger logger = LoggerFactory.getLogger(DriverLicenseResultExtractor.class);

    @Override
    public DriverLicense extractData(ResultSet rs) throws SQLException {
        Timestamp issueDate = rs.getTimestamp("issue_date");
        Timestamp expiredDate = rs.getTimestamp("expired_date");

        return new DriverLicense.Builder()
                .id(rs.getLong("id"))
                .user(rs.getLong("user_details_id"))
                .number(rs.getString("number"))
                .issueDate(issueDate == null ? null : issueDate.toInstant())
                .expiredDate(expiredDate == null ? null : expiredDate.toInstant())
                .build();
    }
}