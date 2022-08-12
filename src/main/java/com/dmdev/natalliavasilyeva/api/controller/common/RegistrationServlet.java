package com.dmdev.natalliavasilyeva.api.controller.common;

import com.dmdev.natalliavasilyeva.api.dto.requestdto.UserDto;
import com.dmdev.natalliavasilyeva.api.mapper.UserMapper;
import com.dmdev.natalliavasilyeva.service.ServiceFactory;
import com.dmdev.natalliavasilyeva.service.UserService;
import com.dmdev.natalliavasilyeva.service.exception.CustomException;
import com.dmdev.natalliavasilyeva.utils.VariablesNameHolder;
import com.dmdev.natalliavasilyeva.utils.validator.DataValidator;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@WebServlet("/sign-up")
public class RegistrationServlet extends HttpServlet {
    UserService userService = ServiceFactory.getInstance().getUserService();
    DataValidator dataValidator = DataValidator.getInstance();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        var login = req.getParameter(VariablesNameHolder.LOGIN);
        var name = req.getParameter(VariablesNameHolder.USER_NAME);
        var surname = req.getParameter(VariablesNameHolder.SURNAME);
        var email = req.getParameter(VariablesNameHolder.EMAIL);
        var phone = req.getParameter(VariablesNameHolder.PHONE);
        var address = req.getParameter(VariablesNameHolder.ADDRESS);
        var birthday = req.getParameter(VariablesNameHolder.BIRTHDAY);
        var driverLicenseNumber = req.getParameter(VariablesNameHolder.DRIVER_LICENSE_NUMBER);
        var driverLicenseIssueDate = req.getParameter(VariablesNameHolder.DRIVER_LICENSE_ISSUE_DATE);
        var driverLicenseExpiredDate = req.getParameter(VariablesNameHolder.DRIVER_LICENSE_EXPIRED_DATE);
        var password = req.getParameter(VariablesNameHolder.PASSWORD);

        try {
            Map<String, String> signUpData = new HashMap<>();
            signUpData.put(VariablesNameHolder.LOGIN, login);
            signUpData.put(VariablesNameHolder.USER_NAME, name);
            signUpData.put(VariablesNameHolder.SURNAME, surname);
            signUpData.put(VariablesNameHolder.EMAIL, email);
            signUpData.put(VariablesNameHolder.PHONE, phone);
            signUpData.put(VariablesNameHolder.ADDRESS, address);
            signUpData.put(VariablesNameHolder.BIRTHDAY, birthday);
            signUpData.put(VariablesNameHolder.DRIVER_LICENSE_NUMBER, driverLicenseNumber);
            signUpData.put(VariablesNameHolder.DRIVER_LICENSE_ISSUE_DATE, driverLicenseIssueDate);
            signUpData.put(VariablesNameHolder.DRIVER_LICENSE_EXPIRED_DATE, driverLicenseExpiredDate);
            signUpData.put(VariablesNameHolder.PASSWORD, password);

            if (!dataValidator.isValidData(signUpData)) {
                String errorMassage = "Check provided data. Some fields contains incorrect information.";
                resp.sendRedirect("/rentcar/welcome" + "?incorrect=" + errorMassage);
            } else {
                LocalDateTime birthdayTime = Objects.equals(birthday, "") || birthday == null? null : LocalDate.parse(birthday).atStartOfDay();
                LocalDateTime driverLicenseIssueDateTime = Objects.equals(driverLicenseIssueDate, "") || driverLicenseIssueDate == null? null : LocalDate.parse(driverLicenseIssueDate).atStartOfDay();
                LocalDateTime driverLicenseExpiredDateTime = Objects.equals(driverLicenseExpiredDate, "") || driverLicenseExpiredDate == null? null : LocalDate.parse(driverLicenseExpiredDate).atStartOfDay();

                var userDto = new UserDto(email, login, password, name, surname, address, phone, birthdayTime, driverLicenseNumber, driverLicenseIssueDateTime, driverLicenseExpiredDateTime);
                userService.createUser(UserMapper.fromDto(userDto));
                resp.sendRedirect("/rentcar/welcome" + "?success_message=" + "You registration was successfully");
            }
        } catch (CustomException | IllegalArgumentException ex) {
            resp.sendRedirect("/rentcar/welcome" + "?error_message=" + ex.getMessage());
        }
    }
}