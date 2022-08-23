package com.dmdev.natalliavasilyeva.api.controller.admin.model;

import com.dmdev.natalliavasilyeva.api.dto.requestdto.CategoryDto;
import com.dmdev.natalliavasilyeva.api.dto.requestdto.ModelDto;
import com.dmdev.natalliavasilyeva.api.mapper.CategoryMapper;
import com.dmdev.natalliavasilyeva.api.mapper.ModelMapper;
import com.dmdev.natalliavasilyeva.domain.model.Category;
import com.dmdev.natalliavasilyeva.domain.model.Model;
import com.dmdev.natalliavasilyeva.service.CategoryService;
import com.dmdev.natalliavasilyeva.service.ModelService;
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
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/admin-create-model")
public class ModelCreateServlet extends HttpServlet {
    private final ModelService modelService = ServiceFactory.getInstance().getModelService();
    private DataValidator dataValidator = DataValidator.getInstance();


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var modelName = req.getParameter(VariablesNameHolder.MODEL_NAME);
        var transmission = req.getParameter(VariablesNameHolder.TRANSMISSION).toUpperCase();
        var engine = req.getParameter(VariablesNameHolder.ENGINE_TYPE).toUpperCase();
        var brandName = req.getParameter(VariablesNameHolder.BRAND_NAME);
        var categoryName = req.getParameter(VariablesNameHolder.CATEGORY_NAME);

        try {
            Map<String, String> createModel = new HashMap<>();
            createModel.put(VariablesNameHolder.MODEL_NAME, modelName);
            createModel.put(VariablesNameHolder.TRANSMISSION, transmission);
            createModel.put(VariablesNameHolder.ENGINE_TYPE, engine);
            createModel.put(VariablesNameHolder.BRAND_NAME, brandName);
            createModel.put(VariablesNameHolder.CATEGORY_NAME, categoryName);

            if (dataValidator.isValidData(createModel)) {

                var modelDTO = new ModelDto(modelName, transmission, engine, brandName, categoryName);
                Model model = null;
                try {
                    model = modelService.createModel(ModelMapper.fromDto(modelDTO));
                } catch (CustomException ex) {
                    resp.sendRedirect("/rentcar/welcome" + "?error_message=" + ex.getMessage());
                }
                if (model != null) {
                    resp.sendRedirect("/rentcar/admin-models" + "?success_message=Model has been created successfully");
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