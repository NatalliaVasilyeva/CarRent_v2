package com.dmdev.natalliavasilyeva.api.controller.admin.cars;

import com.dmdev.natalliavasilyeva.api.controller.util.JspHelper;
import com.dmdev.natalliavasilyeva.api.dto.responsedto.CarAdminResponseDto;
import com.dmdev.natalliavasilyeva.api.dto.responsedto.CarUserResponseDto;
import com.dmdev.natalliavasilyeva.api.mapper.CarMapper;
import com.dmdev.natalliavasilyeva.service.CarService;
import com.dmdev.natalliavasilyeva.service.ServiceFactory;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/cars-by-years-admin")
public class CarListByYearsServlet extends HttpServlet {
    private final CarService carService = ServiceFactory.getInstance().getCarService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String yearFrom = req.getParameter("yearFrom");
        String yearTo = req.getParameter("yearTo");
        List<CarAdminResponseDto> cars = CarMapper.toDtos(carService.findAllCustomCarsBetweenYears(yearFrom, yearTo));

        req.setAttribute("adminCars", cars);

        req.getRequestDispatcher(JspHelper.getPath("welcome")).forward(req, resp);
    }
}