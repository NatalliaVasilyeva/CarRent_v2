package com.dmdev.natalliavasilyeva.api.controller.admin.brand;

import com.dmdev.natalliavasilyeva.api.dto.requestdto.BrandDto;
import com.dmdev.natalliavasilyeva.api.dto.requestdto.OrderDto;
import com.dmdev.natalliavasilyeva.api.mapper.BrandMapper;
import com.dmdev.natalliavasilyeva.api.mapper.OrderMapper;
import com.dmdev.natalliavasilyeva.domain.model.Brand;
import com.dmdev.natalliavasilyeva.domain.model.Order;
import com.dmdev.natalliavasilyeva.service.BrandService;
import com.dmdev.natalliavasilyeva.service.OrderService;
import com.dmdev.natalliavasilyeva.service.ServiceFactory;
import com.dmdev.natalliavasilyeva.service.exception.CustomException;
import com.dmdev.natalliavasilyeva.utils.VariablesNameHolder;
import com.dmdev.natalliavasilyeva.utils.validator.DataValidator;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Map;

@WebServlet("/admin-create-brand")
public class BrandCreateServlet extends HttpServlet {
    private final BrandService brandService = ServiceFactory.getInstance().getBrandService();
    private DataValidator dataValidator = DataValidator.getInstance();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var brandName = req.getParameter(VariablesNameHolder.BRAND_NAME);

        if (dataValidator.isValidData(VariablesNameHolder.BRAND_NAME, brandName)) {
            var brandDto = new BrandDto(brandName);
            Brand brand = null;
            try {
                brand = brandService.createBrand(BrandMapper.fromDto(brandDto));
            } catch (CustomException ex) {
                resp.sendRedirect("/rentcar/welcome" + "?error_message=" + ex.getMessage());
            }
            if (brand != null) {
                resp.sendRedirect("/rentcar/admin-brands" + "?success_message=Brand has been created successfully");
            }
        } else {
            String errorMassage = "Check provided data. Some fields contains incorrect information. Your changes have not been saved.";
            resp.sendRedirect("/rentcar/welcome" + "?incorrect=" + errorMassage);
        }
    }
}