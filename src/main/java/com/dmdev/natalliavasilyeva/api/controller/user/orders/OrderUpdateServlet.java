package com.dmdev.natalliavasilyeva.api.controller.user.orders;

import com.dmdev.natalliavasilyeva.api.dto.requestdto.OrderUserUpdateDto;
import com.dmdev.natalliavasilyeva.api.mapper.OrderMapper;
import com.dmdev.natalliavasilyeva.domain.model.Order;
import com.dmdev.natalliavasilyeva.service.OrderService;
import com.dmdev.natalliavasilyeva.service.ServiceFactory;
import com.dmdev.natalliavasilyeva.service.exception.CustomException;
import com.dmdev.natalliavasilyeva.utils.VariablesNameHolder;
import com.dmdev.natalliavasilyeva.utils.validator.DataValidator;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Map;

@WebServlet("/user-update-order")
public class OrderUpdateServlet extends HttpServlet {
    private final OrderService orderService = ServiceFactory.getInstance().getOrderService();
    private final DataValidator dataValidator = DataValidator.getInstance();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        var id = req.getParameter("orderId");
        var startDate = req.getParameter("start-date");
        var endDate = req.getParameter("end-date");
        var insurance = req.getParameter("ensurance");
        var localStartDate = LocalDateTime.parse(startDate);
        var localEndDate = LocalDateTime.parse(endDate);

        if (dataValidator.isValidData(Map.of(
                VariablesNameHolder.ORDER_ID, id,
                VariablesNameHolder.START_RENTAL_DATE, startDate,
                VariablesNameHolder.END_RENTAL_DATE, endDate,
                VariablesNameHolder.IS_INSURANCE_NEEDED, insurance.toUpperCase())) && dataValidator.isValidDates(localStartDate, localEndDate)) {
            var orderDto = new OrderUserUpdateDto(localStartDate, localEndDate, Boolean.getBoolean(insurance));
            Order savedOrder = null;
            try {
                savedOrder = orderService.updateOrderByUser(Long.valueOf(id), OrderMapper.fromDto(orderDto));
            } catch (CustomException ex) {
                resp.sendRedirect("/rentcar/user-orders" + "?error_message=" + ex.getMessage());
            }
            if (savedOrder != null) {
                resp.sendRedirect("/rentcar/user-orders");
            }
        } else {
            String errorMassage = "Check provided data. Some fields contains incorrect information. Your changes have not been saved.";
            resp.sendRedirect("/rentcar/user-orders" + "?incorrect=" + errorMassage);
        }
    }
}