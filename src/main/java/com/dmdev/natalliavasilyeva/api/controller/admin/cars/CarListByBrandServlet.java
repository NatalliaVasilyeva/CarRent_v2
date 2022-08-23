package com.dmdev.natalliavasilyeva.api.controller.admin.cars;

import com.dmdev.natalliavasilyeva.api.controller.util.JspHelper;
import com.dmdev.natalliavasilyeva.api.dto.responsedto.CarAdminResponseDto;
import com.dmdev.natalliavasilyeva.api.dto.responsedto.CarUserResponseDto;
import com.dmdev.natalliavasilyeva.api.mapper.CarMapper;
import com.dmdev.natalliavasilyeva.service.CarService;
import com.dmdev.natalliavasilyeva.service.ServiceFactory;
import com.dmdev.natalliavasilyeva.service.exception.CustomException;
import com.dmdev.natalliavasilyeva.utils.Converter;
import com.dmdev.natalliavasilyeva.utils.VariablesNameHolder;
import com.dmdev.natalliavasilyeva.utils.validator.DataValidator;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/cars-by-brand-admin")
public class CarListByBrandServlet extends HttpServlet {
    private final CarService carService = ServiceFactory.getInstance().getCarService();
    DataValidator dataValidator = DataValidator.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String brand = Converter.convertToLowerCase(req.getParameter("brand"));
        try {
            if (!dataValidator.isValidData(VariablesNameHolder.BRAND_NAME, brand)) {

                String errorMassage = "Check provided data. Some fields contains incorrect information.";
                resp.sendRedirect("/rentcar/welcome" + "?incorrect=" + errorMassage);
            } else {
                List<CarAdminResponseDto> cars = CarMapper.toDtos(carService.findAllCustomCarsByBrandName(brand));

                req.setAttribute("adminCars", cars);

                req.getRequestDispatcher(JspHelper.getPath("welcome")).forward(req, resp);
            }
        } catch (CustomException | IllegalArgumentException ex) {
            resp.sendRedirect("/rentcar/welcome" + "?error_message=" + ex.getMessage());
        }
    }
}