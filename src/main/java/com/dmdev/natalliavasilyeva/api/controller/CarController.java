package com.dmdev.natalliavasilyeva.api.controller;

import com.dmdev.natalliavasilyeva.api.dto.responsedto.CarUserResponseDto;
import com.dmdev.natalliavasilyeva.api.mapper.CarMapper;
import com.dmdev.natalliavasilyeva.service.CarService;
import com.dmdev.natalliavasilyeva.service.ServiceFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public class CarController {

    @WebServlet(name = "userCars", urlPatterns = {"/api/v1/user/cars"})
    public class CarListServlet extends HttpServlet {
        private final CarService carService = ServiceFactory.getInstance().getCarService();

        @Override
        protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
            List<CarUserResponseDto> cars = CarMapper.toShotDtos(carService.findAllCustomCarsAvailable());

            req.setAttribute("cars", cars);

            req.getRequestDispatcher("/userCars.jsp").forward(req,resp);
        }
    }
}