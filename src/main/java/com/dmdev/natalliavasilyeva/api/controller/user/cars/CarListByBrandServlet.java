package com.dmdev.natalliavasilyeva.api.controller.user.cars;

import com.dmdev.natalliavasilyeva.api.controller.util.JspHelper;
import com.dmdev.natalliavasilyeva.api.dto.responsedto.CarUserResponseDto;
import com.dmdev.natalliavasilyeva.api.mapper.CarMapper;
import com.dmdev.natalliavasilyeva.domain.model.User;
import com.dmdev.natalliavasilyeva.service.CarService;
import com.dmdev.natalliavasilyeva.service.ServiceFactory;
import com.dmdev.natalliavasilyeva.utils.Converter;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;

@WebServlet("/cars-by-brand")
public class CarListByBrandServlet extends HttpServlet {
    private final CarService carService = ServiceFactory.getInstance().getCarService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String brand = Converter.convertToLowerCase(req.getParameter("brand"));
        List<CarUserResponseDto> cars = CarMapper.toShotDtosForUserList(carService.findAllCustomCarsByBrandName(brand));

        req.setAttribute("cars", cars);

        req.getRequestDispatcher(JspHelper.getPath("user-cars")).forward(req, resp);
    }
}