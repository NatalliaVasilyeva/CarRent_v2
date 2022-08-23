package com.dmdev.natalliavasilyeva.api.controller.admin.cars;

import com.dmdev.natalliavasilyeva.api.dto.requestdto.CarShotDto;
import com.dmdev.natalliavasilyeva.api.mapper.CarMapper;
import com.dmdev.natalliavasilyeva.domain.model.Car;
import com.dmdev.natalliavasilyeva.service.CarService;
import com.dmdev.natalliavasilyeva.service.ServiceFactory;
import com.dmdev.natalliavasilyeva.service.exception.CustomException;
import com.dmdev.natalliavasilyeva.utils.VariablesNameHolder;
import com.dmdev.natalliavasilyeva.utils.validator.DataValidator;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

@MultipartConfig(fileSizeThreshold = 1024 * 1024)
@WebServlet("/admin-update-car")
public class CarUpdateServlet extends HttpServlet {
    private final CarService carService = ServiceFactory.getInstance().getCarService();
    private DataValidator dataValidator = DataValidator.getInstance();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var carId = req.getParameter(VariablesNameHolder.CAR_ID);
        var brandName = req.getParameter(VariablesNameHolder.BRAND_NAME);
        var color = req.getParameter(VariablesNameHolder.COLOR).toUpperCase();
        var yearOfProduction = req.getParameter(VariablesNameHolder.YEAR);
        var number = req.getParameter(VariablesNameHolder.CAR_NUMBER);
        var isRepaired = req.getParameter(VariablesNameHolder.IS_REPAIRED);
        var category = req.getParameter(VariablesNameHolder.CATEGORY_NAME);
        var imagePart = req.getPart(VariablesNameHolder.IMAGE);

        try {
            Map<String, String> updateCar = new HashMap<>();
            updateCar.put(VariablesNameHolder.CAR_ID, carId);
            updateCar.put(VariablesNameHolder.BRAND_NAME, brandName);
            updateCar.put(VariablesNameHolder.CATEGORY_NAME, category);
            updateCar.put(VariablesNameHolder.COLOR, color);
            updateCar.put(VariablesNameHolder.YEAR, yearOfProduction);
            updateCar.put(VariablesNameHolder.CAR_NUMBER, number);
            updateCar.put(VariablesNameHolder.IS_REPAIRED, isRepaired);

            if (dataValidator.isValidData(updateCar)) {

                String imageName = null;
                InputStream imageContent = null;
                if (imagePart != null) {
                    imageName = imagePart.getSubmittedFileName();
                    imageContent = imagePart.getInputStream();
                }

                var carDto = new CarShotDto(brandName, category, color, yearOfProduction, number, Boolean.parseBoolean(isRepaired), imageContent, imageName);
                Car car = null;
                try {
                    car = carService.updateCar(Long.valueOf(carId), CarMapper.fromShotDto(carDto));
                } catch (CustomException ex) {
                    resp.sendRedirect("/rentcar/welcome" + "?error_message=" + ex.getMessage());
                }
                if (car != null) {
                    resp.sendRedirect("/rentcar/welcome" + "?success_message=Car has been updated successfully");
                }
            } else {
                String errorMassage = "Check provided data. Some fields contains incorrect information. Your changes have not been saved.";
                resp.sendRedirect("/rentcar/welcome" + "?incorrect=" + errorMassage);
            }
        } catch (CustomException | IllegalArgumentException ex) {
            resp.sendRedirect("/rentcar/admin-models" + "?error_message=" + ex.getMessage());
        }
    }
}