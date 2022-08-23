package com.dmdev.natalliavasilyeva.api.controller.admin.brand;

import com.dmdev.natalliavasilyeva.api.dto.requestdto.BrandDto;
import com.dmdev.natalliavasilyeva.api.mapper.BrandMapper;
import com.dmdev.natalliavasilyeva.domain.model.Brand;
import com.dmdev.natalliavasilyeva.service.BrandService;
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

@WebServlet("/admin-edit-brand")
public class BrandUpdateServlet extends HttpServlet {
    private final BrandService brandService = ServiceFactory.getInstance().getBrandService();
    private DataValidator dataValidator = DataValidator.getInstance();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var brandId = req.getParameter(VariablesNameHolder.BRAND_ID);
        var brandName = req.getParameter(VariablesNameHolder.BRAND_NAME);

        if (dataValidator.isValidData(VariablesNameHolder.BRAND_NAME, brandName) && dataValidator.isValidData(VariablesNameHolder.BRAND_ID, brandId)) {
            var brandDto = new BrandDto(brandName);
            Brand brand = null;
            try {
                brand = brandService.updateBrand(Long.valueOf(brandId), BrandMapper.fromDto(brandDto));
            } catch (CustomException ex) {
                resp.sendRedirect("/rentcar/welcome" + "?error_message=" + ex.getMessage());
            }
            if (brand != null) {
                resp.sendRedirect("/rentcar/admin-brands" + "?success_message=Brand has been updated successfully");
            }
        } else {
            String errorMassage = "Check provided data. Some fields contains incorrect information. Your changes have not been saved.";
            resp.sendRedirect("/rentcar/welcome" + "?incorrect=" + errorMassage);
        }
    }
}