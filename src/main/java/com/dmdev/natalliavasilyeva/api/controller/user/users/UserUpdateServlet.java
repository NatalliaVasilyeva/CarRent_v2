package com.dmdev.natalliavasilyeva.api.controller.user.users;

import com.dmdev.natalliavasilyeva.api.controller.util.JspHelper;
import com.dmdev.natalliavasilyeva.api.dto.requestdto.UserUpdateDto;
import com.dmdev.natalliavasilyeva.api.dto.responsedto.UserShotResponseDto;
import com.dmdev.natalliavasilyeva.api.mapper.UserMapper;
import com.dmdev.natalliavasilyeva.service.ServiceFactory;
import com.dmdev.natalliavasilyeva.service.UserService;
import com.dmdev.natalliavasilyeva.service.exception.CustomException;
import com.dmdev.natalliavasilyeva.utils.VariablesNameHolder;
import com.dmdev.natalliavasilyeva.utils.validator.DataValidator;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@WebServlet("/user-profile")
public class UserUpdateServlet extends HttpServlet {
    private final UserService userService = ServiceFactory.getInstance().getUserService();
    private final DataValidator dataValidator = DataValidator.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HttpSession session = req.getSession();
        var userId = String.valueOf(session.getAttribute(VariablesNameHolder.USER_ID));
        UserShotResponseDto user = UserMapper.toShotDto(userService.getCustomUserById(Long.valueOf(userId)));

        req.setAttribute("user", user);
        try {
            req.getRequestDispatcher(JspHelper.getPath("user-profile")).forward(req, resp);
        } catch (ServletException ex) {
            resp.sendRedirect("/rentcar/welcome" + "?error_message=" + ex.getMessage());
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        var userId = req.getParameter(VariablesNameHolder.USER_ID);
        var name = req.getParameter(VariablesNameHolder.USER_NAME);
        var surname = req.getParameter(VariablesNameHolder.SURNAME);
        var email = req.getParameter(VariablesNameHolder.EMAIL);
        var phone = req.getParameter(VariablesNameHolder.PHONE);
        var address = req.getParameter(VariablesNameHolder.ADDRESS);
        var driverLicenseNumber = req.getParameter(VariablesNameHolder.DRIVER_LICENSE_NUMBER);
        var driverLicenseIssueDate = req.getParameter(VariablesNameHolder.DRIVER_LICENSE_ISSUE_DATE);
        var driverLicenseExpiredDate = req.getParameter(VariablesNameHolder.DRIVER_LICENSE_EXPIRED_DATE);

        LocalDateTime driverLicenseIssueDateTime = Objects.equals(driverLicenseIssueDate, "") || driverLicenseIssueDate == null ? null : LocalDateTime.parse(driverLicenseIssueDate);
        LocalDateTime driverLicenseExpiredDateTime = Objects.equals(driverLicenseExpiredDate, "") || driverLicenseExpiredDate == null ? null : LocalDateTime.parse(driverLicenseExpiredDate);

        try {
            Map<String, String> userUpdateData = new HashMap<>();
            userUpdateData.put(VariablesNameHolder.USER_NAME, name);
            userUpdateData.put(VariablesNameHolder.SURNAME, surname);
            userUpdateData.put(VariablesNameHolder.EMAIL, email);
            userUpdateData.put(VariablesNameHolder.PHONE, phone);
            userUpdateData.put(VariablesNameHolder.ADDRESS, address);
            userUpdateData.put(VariablesNameHolder.DRIVER_LICENSE_NUMBER, driverLicenseNumber);
            userUpdateData.put(VariablesNameHolder.DRIVER_LICENSE_ISSUE_DATE, driverLicenseIssueDate);
            userUpdateData.put(VariablesNameHolder.DRIVER_LICENSE_EXPIRED_DATE, driverLicenseExpiredDate);

            if (!dataValidator.isValidData(userUpdateData) && dataValidator.isValidDates(driverLicenseIssueDateTime, driverLicenseExpiredDateTime)) {
                String errorMassage = "Check provided data. Some fields contains incorrect information.";
                resp.sendRedirect("/rentcar/user-profile" + "?incorrect=" + errorMassage);
            } else {

                var userDto = new UserUpdateDto(email, name, surname, address, phone, driverLicenseNumber, driverLicenseIssueDateTime, driverLicenseExpiredDateTime);
                userService.updateUser(Long.valueOf(userId), UserMapper.fromDto(userDto));
                resp.sendRedirect("/rentcar/user-profile" + "?success_message=" + "You changed data  successfully");
            }
        } catch (CustomException | IllegalArgumentException ex) {
            resp.sendRedirect("/rentcar/welcome" + "?error_message=" + ex.getMessage());
        }
    }
}