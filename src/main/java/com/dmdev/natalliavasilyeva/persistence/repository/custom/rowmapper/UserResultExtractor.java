package com.dmdev.natalliavasilyeva.persistence.repository.custom.rowmapper;

import com.dmdev.natalliavasilyeva.domain.model.Car;
import com.dmdev.natalliavasilyeva.domain.model.CarRentalTime;
import com.dmdev.natalliavasilyeva.domain.model.DriverLicense;
import com.dmdev.natalliavasilyeva.domain.model.Order;
import com.dmdev.natalliavasilyeva.domain.model.Role;
import com.dmdev.natalliavasilyeva.domain.model.User;
import com.dmdev.natalliavasilyeva.persistence.utils.DateTimeService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class UserResultExtractor implements ResultSetExtractor<User> {

    private static final Logger logger = LoggerFactory.getLogger(UserResultExtractor.class);

    private static final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public User extractData(ResultSet rs) throws SQLException {

        Timestamp user_birthday = rs.getTimestamp("birthday");
        Timestamp userRegistrationDate = rs.getTimestamp("registration_date");
        String orderJson = getOrderDescription(rs);
        List<Order> orders =
                orderJson.isBlank() ? Collections.emptyList() :
                        this.mapJsonResultToOrder(rs.getString("order_description"));


        return new User.Builder()
                .userId(rs.getLong("user_id"))
                .login(rs.getString("login"))
                .email(rs.getString("email"))
                .role(Role.valueOf(rs.getString("role").toUpperCase()))
                .userDetailsId(rs.getLong("user_details_id"))
                .name(rs.getString("name"))
                .surname(rs.getString("surname"))
                .address(rs.getString("address"))
                .phone(rs.getString("phone"))
                .birthday(user_birthday == null ? null : user_birthday.toInstant())
                .registrationDate(userRegistrationDate == null ? null : userRegistrationDate.toInstant())
                .driverLicense(mapToDriverLicense(rs))
                .orders(orders)
                .build();
    }

    private DriverLicense mapToDriverLicense(ResultSet rs) throws SQLException {
        Timestamp licenseIssueDate = rs.getTimestamp("license_issue_date");
        Timestamp licenseExpiredDate = rs.getTimestamp("license_expired_date");

        return new DriverLicense.Builder()
                .number(rs.getString("driver_license_number"))
                .issueDate(licenseIssueDate == null ? null : licenseIssueDate.toInstant())
                .expiredDate(licenseExpiredDate == null ? null : licenseExpiredDate.toInstant())
                .build();
    }


    private List<Order> mapJsonResultToOrder(String ordersJson) {
        List<Order> orders = new ArrayList<>();
        if (ordersJson == null) {
            return orders;
        }

        ObjectMapper objectMapper = new ObjectMapper();

        try {
            List<Map<String, Object>> elements = objectMapper.readValue(ordersJson, ArrayList.class);
            for (Map<String, Object> element : elements) {
                if (element != null) {

                    // TODO: check null!!!!
                    Instant date = DateTimeService.fromStringDateToInstant((String) element.get("order_date"));
                    CarRentalTime carRentalTime = new CarRentalTime.Builder()

                            // TODO: check null!!!! Made
                            .start(DateTimeService.fromStringDateToInstant((String) element.get("start_rent_date")))
                            .end(DateTimeService.fromStringDateToInstant((String) element.get("end_rent_date")))
                            .build();
                    Car car = new Car.Builder()
                            .number((String) element.get("car_number"))
                            .build();

                    orders.add(new Order.Builder()
                            .date(date)
                            .carRentalTime(carRentalTime)
                            .car(car)
                            .build());
                }
            }
        } catch (JsonProcessingException e) {
            logger.error("Error deserializing elements");
        }
        return orders;
    }

    private String getOrderDescription(ResultSet rs) {
        String json;
        try {
            json = rs.getString("order_description");
        } catch (SQLException e) {
            json = "";
        }
        return json;
    }
}