package com.dmdev.natalliavasilyeva.api.controller.admin.cars;

import com.dmdev.natalliavasilyeva.api.dto.requestdto.CarDto;
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
import java.util.HashMap;
import java.util.Map;

@MultipartConfig(fileSizeThreshold = 1024 * 1024)
@WebServlet("/admin-create-car")
public class CarCreateServlet extends HttpServlet {
    private final CarService carService = ServiceFactory.getInstance().getCarService();
    private DataValidator dataValidator = DataValidator.getInstance();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var brandName = req.getParameter(VariablesNameHolder.BRAND_NAME);
        var modelName = req.getParameter(VariablesNameHolder.MODEL_NAME);
        var transmission = req.getParameter(VariablesNameHolder.TRANSMISSION).toUpperCase();
        var engineType = req.getParameter(VariablesNameHolder.ENGINE_TYPE).toUpperCase();
        var category = req.getParameter(VariablesNameHolder.CATEGORY_NAME);
        var color = req.getParameter(VariablesNameHolder.COLOR).toUpperCase();
        var yearOfProduction = req.getParameter(VariablesNameHolder.YEAR);
        var number = req.getParameter(VariablesNameHolder.CAR_NUMBER);
        var vin = req.getParameter(VariablesNameHolder.VIN);
        var isRepaired = req.getParameter(VariablesNameHolder.IS_REPAIRED);
        var imagePart = req.getPart(VariablesNameHolder.IMAGE);

        try {
            Map<String, String> createCar = new HashMap<>();
            createCar.put(VariablesNameHolder.BRAND_NAME, brandName);
            createCar.put(VariablesNameHolder.MODEL_NAME, modelName);
            createCar.put(VariablesNameHolder.TRANSMISSION, transmission);
            createCar.put(VariablesNameHolder.ENGINE_TYPE, engineType);
            createCar.put(VariablesNameHolder.CATEGORY_NAME, category);
            createCar.put(VariablesNameHolder.COLOR, color);
            createCar.put(VariablesNameHolder.YEAR, yearOfProduction);
            createCar.put(VariablesNameHolder.CAR_NUMBER, number);
            createCar.put(VariablesNameHolder.VIN, vin);
            createCar.put(VariablesNameHolder.IS_REPAIRED, isRepaired);

            if (dataValidator.isValidData(createCar)) {

                var imageName = imagePart.getSubmittedFileName();
                var imageContent = imagePart.getInputStream();

                var carDto = new CarDto(brandName, modelName, transmission, engineType, category, color, yearOfProduction,
                        number, vin, Boolean.parseBoolean(isRepaired), imageContent, imageName);
                Car car = null;
                try {
                    car = carService.createCar(CarMapper.fromDto(carDto));
                } catch (CustomException ex) {
                    resp.sendRedirect("/rentcar/welcome" + "?error_message=" + ex.getMessage());
                }
                if (car != null) {
                    resp.sendRedirect("/rentcar/welcome" + "?success_message=Car has been created successfully");
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