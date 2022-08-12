package com.dmdev.natalliavasilyeva.domain.jpa;

import java.io.Serializable;
import java.time.Instant;

public class DriverLicenseJpa implements Entity, Serializable {

    private long id;
    private long userDetailsId;
    private String number;
    private Instant issueDate;
    private Instant expiredDate;

    public DriverLicenseJpa() {
    }

    private DriverLicenseJpa(long id, long userDetailsId, String number, Instant issueDate, Instant expiredDate) {
        this.id = id;
        this.userDetailsId = userDetailsId;
        this.number = number;
        this.issueDate = issueDate;
        this.expiredDate = expiredDate;
    }

    @Override
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }
    public long getUserDetailsId() {
        return userDetailsId;
    }
    public void setUserDetailsId(long userDetailsId) {
        this.userDetailsId = userDetailsId;
    }
    public String getNumber() {
        return number;
    }
    public void setNumber(String number) {
        this.number = number;
    }
    public Instant getIssueDate() {
        return issueDate;
    }
    public void setIssueDate(Instant issueDate) {
        this.issueDate = issueDate;
    }
    public Instant getExpiredDate() {
        return expiredDate;
    }
    public void setExpiredDate(Instant expiredDate) {
        this.expiredDate = expiredDate;
    }
    public static final class Builder {
        private long id;
        private long userDetailsId;
        private String number;
        private Instant issueDate;
        private Instant expiredDate;

        public Builder id(long id) {
            this.id = id;
            return this;
        }

        public Builder user(long userDetailsId) {
            this.userDetailsId = userDetailsId;
            return this;
        }

        public Builder number(String number) {
            this.number = number;
            return this;
        }

        public Builder issueDate(Instant issueDate) {
            this.issueDate = issueDate;
            return this;
        }

        public Builder expiredDate(Instant expiredDate) {
            this.expiredDate = expiredDate;
            return this;
        }

        public DriverLicenseJpa build() {
            DriverLicenseJpa driverLicenseJpa = new DriverLicenseJpa();
            driverLicenseJpa.id = this.id;
            driverLicenseJpa.userDetailsId = userDetailsId;
            driverLicenseJpa.number = this.number;
            driverLicenseJpa.issueDate = this.issueDate;
            driverLicenseJpa.expiredDate = this.expiredDate;

            return driverLicenseJpa;
        }
    }
}