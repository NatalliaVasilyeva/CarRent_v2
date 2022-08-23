package com.dmdev.natalliavasilyeva.api.controller.common;

import com.dmdev.natalliavasilyeva.api.controller.util.JspHelper;
import com.dmdev.natalliavasilyeva.api.dto.responsedto.CarAdminResponseDto;
import com.dmdev.natalliavasilyeva.api.dto.responsedto.CarUserResponseDto;
import com.dmdev.natalliavasilyeva.api.mapper.CarMapper;
import com.dmdev.natalliavasilyeva.domain.model.Role;
import com.dmdev.natalliavasilyeva.service.CarService;
import com.dmdev.natalliavasilyeva.service.ServiceFactory;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

@WebServlet("/welcome")
public class WelcomeServlet extends HttpServlet {
    private final CarService carService = ServiceFactory.getInstance().getCarService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        var userRole = session.getAttribute("role");
        List<CarAdminResponseDto> adminCars;
        List<CarUserResponseDto> userCars;
        if (Objects.equals(userRole, Role.getEnum("ADMIN"))) {
            adminCars = CarMapper.toDtos(carService.findAllCustomCarsAvailable());
            req.setAttribute("adminCars", adminCars);
        }  else {
            userCars = CarMapper.toShotDtos(carService.findAllCustomCarsAvailable());
            req.setAttribute("userCars", userCars);
        }

        if (req.getParameter("incorrect") != null) {
            req.setAttribute("incorrect", req.getParameter("incorrect"));
        }
        if (req.getParameter("success_message") != null) {
            req.setAttribute("success_message", req.getParameter("success_message"));
        }
        if (req.getParameter("error_message") != null) {
            req.setAttribute("error_message", req.getParameter("error_message"));
        }

        req.getRequestDispatcher(JspHelper.getPath("welcome")).forward(req, resp);
    }
}