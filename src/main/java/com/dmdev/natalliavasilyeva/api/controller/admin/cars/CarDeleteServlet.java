package com.dmdev.natalliavasilyeva.api.controller.admin.cars;

import com.dmdev.natalliavasilyeva.service.CarService;
import com.dmdev.natalliavasilyeva.service.ServiceFactory;
import com.dmdev.natalliavasilyeva.service.exception.CustomException;
import com.dmdev.natalliavasilyeva.utils.VariablesNameHolder;
import com.dmdev.natalliavasilyeva.utils.validator.DataValidator;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/admin-delete-car")
public class CarDeleteServlet extends HttpServlet {
    private final CarService carService = ServiceFactory.getInstance().getCarService();
    private DataValidator dataValidator = DataValidator.getInstance();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var carId = req.getParameter(VariablesNameHolder.CAR_ID);

        try {
            if (dataValidator.isValidData(VariablesNameHolder.CAR_ID, carId)) {
                boolean isCarDeleted = false;
                try {
                    isCarDeleted = carService.deleteCarById(Long.valueOf(carId));
                } catch (CustomException ex) {
                    resp.sendRedirect("/rentcar/welcome" + "?error_message=" + ex.getMessage());
                }
                if (isCarDeleted) {
                    resp.sendRedirect("/rentcar/welcome" + "?success_message=Car has been deleted successfully");
                }
            } else {
                String errorMassage = "Check provided data. Some fields contains incorrect information.";
                resp.sendRedirect("/rentcar/welcome" + "?incorrect=" + errorMassage);
            }
        } catch (CustomException | IllegalArgumentException ex) {
            resp.sendRedirect("/rentcar/admin-models" + "?error_message=" + ex.getMessage());
        }
    }
}