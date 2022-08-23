package com.dmdev.natalliavasilyeva.api.controller.user.cars;

import com.dmdev.natalliavasilyeva.api.controller.util.JspHelper;
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

@WebServlet("/cars-by-category")
public class CarListByCategoryServlet extends HttpServlet {
    private final CarService carService = ServiceFactory.getInstance().getCarService();
    DataValidator dataValidator = DataValidator.getInstance();


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String category = Converter.convertToLowerCase(req.getParameter("category"));
        try {
            if (!dataValidator.isValidData(VariablesNameHolder.CATEGORY_NAME, category)) {
                String errorMassage = "Check provided data. Some fields contains incorrect information.";
                resp.sendRedirect("/rentcar/user-cars" + "?incorrect=" + errorMassage);
            } else {

                List<CarUserResponseDto> cars = CarMapper.toShotDtosForUserList(carService.findAllCustomCarsByCategoryName(category));

                req.setAttribute("cars", cars);

                req.getRequestDispatcher(JspHelper.getPath("user-cars")).forward(req, resp);
            }
        } catch (CustomException | IllegalArgumentException ex) {
            resp.sendRedirect("/rentcar/user-cars" + "?error_message=" + ex.getMessage());
        }
    }
}