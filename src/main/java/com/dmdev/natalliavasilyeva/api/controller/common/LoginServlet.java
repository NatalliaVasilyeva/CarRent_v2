package com.dmdev.natalliavasilyeva.api.controller.common;

import com.dmdev.natalliavasilyeva.api.dto.requestdto.UserLoginDto;
import com.dmdev.natalliavasilyeva.api.mapper.UserMapper;
import com.dmdev.natalliavasilyeva.domain.model.Language;
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

@WebServlet("/sign-in")
public class LoginServlet extends HttpServlet {
    UserService userService = ServiceFactory.getInstance().getUserService();
    DataValidator validator = DataValidator.getInstance();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String login = req.getParameter(VariablesNameHolder.LOGIN);
        String password = req.getParameter(VariablesNameHolder.PASSWORD);
        HttpSession session = req.getSession();

        try {
            boolean validationResult = (validator.isValidData(VariablesNameHolder.LOGIN, login) && validator.isValidData(VariablesNameHolder.PASSWORD, password));

            if (!validationResult) {
                String errorMassage = "Check provided data. Some fields contains incorrect information.";
                resp.sendRedirect("/rentcar/welcome" + "?incorrect=" + errorMassage);
            } else {
                var userLoginDto = new UserLoginDto(login, password);
                var shotUser = userService.login(UserMapper.fromDto(userLoginDto));
                var user = UserMapper.toShotDtoFromShotUser(shotUser);

                session.setAttribute("userId", user.getId());
                session.setAttribute("login", user.getLogin());
                session.setAttribute("role", user.getRole());

                resp.sendRedirect("/rentcar/welcome");
            }
        } catch (CustomException | IllegalArgumentException ex) {
            resp.sendRedirect("/rentcar/welcome" + "?incorrect=" + ex.getMessage());
        }
    }
}