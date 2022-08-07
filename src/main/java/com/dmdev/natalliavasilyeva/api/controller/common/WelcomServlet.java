package com.dmdev.natalliavasilyeva.api.controller.common;

import com.dmdev.natalliavasilyeva.api.controller.util.JspHelper;
import com.dmdev.natalliavasilyeva.api.dto.responsedto.CarUserResponseDto;
import com.dmdev.natalliavasilyeva.api.mapper.CarMapper;
import com.dmdev.natalliavasilyeva.domain.model.User;
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

@WebServlet("/welcome")
public class WelcomServlet extends HttpServlet {
    private final CarService carService = ServiceFactory.getInstance().getCarService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<CarUserResponseDto> cars = CarMapper.toShotDtos(carService.findAllCustomCarsAvailable());

        req.setAttribute("cars", cars);
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