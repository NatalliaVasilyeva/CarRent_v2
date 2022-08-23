package com.dmdev.natalliavasilyeva.api.controller.common.users;

import com.dmdev.natalliavasilyeva.service.ServiceFactory;
import com.dmdev.natalliavasilyeva.service.UserService;
import com.dmdev.natalliavasilyeva.service.exception.CustomException;
import com.dmdev.natalliavasilyeva.utils.VariablesNameHolder;
import com.dmdev.natalliavasilyeva.utils.validator.DataValidator;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.Objects;

@WebServlet("/change-password")
public class UserChangePasswordServlet extends HttpServlet {
    private final UserService userService = ServiceFactory.getInstance().getUserService();
    private final DataValidator dataValidator = DataValidator.getInstance();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        HttpSession session = req.getSession();
        var userId = (Long) session.getAttribute(VariablesNameHolder.USER_ID);
        var oldPassword = req.getParameter("old_password");
        var newPassword = req.getParameter("new_password");
        var confirmPassword = req.getParameter("confirm_password");

        try {
            if (!Objects.equals(newPassword, confirmPassword)) {
                String errorMassage = "New password doesn't match to confirm password";
                resp.sendRedirect("/rentcar/user-profile" + "?incorrect=" + errorMassage);
            } else if (!dataValidator.isValidData(VariablesNameHolder.PASSWORD, oldPassword) ||
                    !dataValidator.isValidData(VariablesNameHolder.PASSWORD, newPassword)) {
                String errorMassage = "Check provided data. Some fields contains incorrect information.";
                resp.sendRedirect("/rentcar/user-profile" + "?incorrect=" + errorMassage);
            } else {

                userService.updatePassword(userId, oldPassword, newPassword);
                resp.sendRedirect("/rentcar/user-profile" + "?success_message=" + "You changed data successfully");
            }
        } catch (CustomException | IllegalArgumentException ex) {
            resp.sendRedirect("/rentcar/welcome" + "?error_message=" + ex.getMessage());
        }
    }
}