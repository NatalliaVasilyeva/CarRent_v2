package com.dmdev.natalliavasilyeva.api.controller.admin.categories;

import com.dmdev.natalliavasilyeva.api.dto.requestdto.BrandDto;
import com.dmdev.natalliavasilyeva.api.dto.requestdto.CategoryDto;
import com.dmdev.natalliavasilyeva.api.mapper.BrandMapper;
import com.dmdev.natalliavasilyeva.api.mapper.CategoryMapper;
import com.dmdev.natalliavasilyeva.domain.model.Brand;
import com.dmdev.natalliavasilyeva.domain.model.Category;
import com.dmdev.natalliavasilyeva.service.CategoryService;
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

@WebServlet("/admin-edit-category")
public class CategoryUpdateServlet extends HttpServlet {
    private final CategoryService categoryService = ServiceFactory.getInstance().getCategoryService();
    private DataValidator dataValidator = DataValidator.getInstance();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var categoryId = req.getParameter(VariablesNameHolder.CATEGORY_ID);
        var categoryName = req.getParameter(VariablesNameHolder.CATEGORY_NAME);
        var priceValue = req.getParameter(VariablesNameHolder.PRICE);

        if (dataValidator.isValidData(VariablesNameHolder.CATEGORY_ID, categoryId)
                && dataValidator.isValidData(VariablesNameHolder.CATEGORY_NAME, categoryName)
                && dataValidator.isValidData(VariablesNameHolder.PRICE, priceValue)) {
            var categoryDto = new CategoryDto(categoryName, BigDecimal.valueOf(Double.parseDouble(priceValue)));
            Category category = null;
            try {
                category = categoryService.updateCategory(Long.valueOf(categoryId), CategoryMapper.fromDto(categoryDto));
            } catch (CustomException ex) {
                resp.sendRedirect("/rentcar/welcome" + "?error_message=" + ex.getMessage());
            }
            if (category != null) {
                resp.sendRedirect("/rentcar/admin-categories" + "?success_message=Category has been updated successfully");
            }
        } else {
            String errorMassage = "Check provided data. Some fields contains incorrect information. Your changes have not been saved.";
            resp.sendRedirect("/rentcar/welcome" + "?incorrect=" + errorMassage);
        }
    }
}