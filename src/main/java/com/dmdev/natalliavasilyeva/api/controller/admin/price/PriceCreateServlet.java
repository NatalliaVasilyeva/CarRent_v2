package com.dmdev.natalliavasilyeva.api.controller.admin.price;

import com.dmdev.natalliavasilyeva.api.dto.requestdto.PriceDto;
import com.dmdev.natalliavasilyeva.api.mapper.PriceMapper;
import com.dmdev.natalliavasilyeva.domain.model.Price;
import com.dmdev.natalliavasilyeva.service.PriceService;
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

@WebServlet("/admin-create-price")
public class PriceCreateServlet extends HttpServlet {
    private final PriceService priceService = ServiceFactory.getInstance().getPriceService();
    private DataValidator dataValidator = DataValidator.getInstance();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        var priceValue = req.getParameter(VariablesNameHolder.PRICE);

        if (dataValidator.isValidData(VariablesNameHolder.PRICE, priceValue)) {
            var priceDto = new PriceDto(BigDecimal.valueOf(Long.parseLong(priceValue)));
            Price price = null;
            try {
                price = priceService.createPrice(PriceMapper.fromDto(priceDto));
            } catch (CustomException ex) {
                resp.sendRedirect("/rentcar/welcome" + "?error_message=" + ex.getMessage());
            }
            if (price != null) {
                resp.sendRedirect("/rentcar/admin-categories" + "?success_message=Price has been created successfully");
            }
        } else {
            String errorMassage = "Check provided data. Some fields contains incorrect information. Your changes have not been saved.";
            resp.sendRedirect("/rentcar/welcome" + "?incorrect=" + errorMassage);
        }
    }
}