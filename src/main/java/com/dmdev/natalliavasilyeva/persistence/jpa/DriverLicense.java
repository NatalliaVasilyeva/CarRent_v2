package com.dmdev.natalliavasilyeva.persistence.jpa;

import java.io.Serializable;
import java.time.Instant;

public class DriverLicense implements Entity, Serializable {

    private long id;
    private long userDetailsId;
    private String number;
    private Instant issueDate;
    private Instant expiredDate;

    public DriverLicense() {
    }

    private DriverLicense(long id, long userDetailsId, String number, Instant issueDate, Instant expiredDate) {
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

    public long getUserDetailsId() {
        return userDetailsId;
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

        public DriverLicense build() {
            DriverLicense driverLicense = new DriverLicense();
            driverLicense.id = this.id;
            driverLicense.userDetailsId = userDetailsId;
            driverLicense.number = this.number;
            driverLicense.issueDate = this.issueDate;
            driverLicense.expiredDate = this.expiredDate;

            return driverLicense;
        }

    }
}