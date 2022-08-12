package com.dmdev.natalliavasilyeva.api.controller.user.orders;

import com.dmdev.natalliavasilyeva.api.dto.requestdto.OrderDto;
import com.dmdev.natalliavasilyeva.api.mapper.OrderMapper;
import com.dmdev.natalliavasilyeva.domain.model.Order;
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

@WebServlet("/user-create-order")
public class OrderCreateServlet extends HttpServlet {
    private final OrderService orderService = ServiceFactory.getInstance().getOrderService();
    private DataValidator dataValidator = DataValidator.getInstance();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        var carId = req.getParameter(VariablesNameHolder.CAR_ID);
        var userId = String.valueOf(session.getAttribute(VariablesNameHolder.USER_ID));
        var passport =  req.getParameter(VariablesNameHolder.PASSPORT);
        var insurance = req.getParameter(VariablesNameHolder.IS_INSURANCE_NEEDED);
        var startRentalDate = req.getParameter(VariablesNameHolder.START_RENTAL_DATE);
        var endRentalDate = req.getParameter(VariablesNameHolder.END_RENTAL_DATE);

        var localStartDate = LocalDateTime.parse(startRentalDate);
        var localEndDate = LocalDateTime.parse(endRentalDate);

        if (dataValidator.isValidData(Map.of(
                VariablesNameHolder.CAR_ID, carId,
                VariablesNameHolder.USER_ID, userId,
                VariablesNameHolder.PASSPORT, passport,
                VariablesNameHolder.IS_INSURANCE_NEEDED, insurance.toUpperCase(),
                VariablesNameHolder.START_RENTAL_DATE, startRentalDate,
                VariablesNameHolder.END_RENTAL_DATE, endRentalDate
        )) && dataValidator.isValidDates(localStartDate, localEndDate)) {
            var orderDto = new OrderDto(Long.parseLong(carId), localStartDate, localEndDate, Long.parseLong(userId), passport, Boolean.getBoolean(insurance));
            Order createdOrder = null;
            try {
                createdOrder = orderService.createOrder(OrderMapper.fromDto(orderDto));
            } catch (CustomException ex) {
                resp.sendRedirect("/rentcar/welcome" + "?error_message=" + ex.getMessage());
            }
            if (createdOrder != null) {
                resp.sendRedirect("/rentcar/user-orders" + "?success_message=Order has been created successfully");
            }
        } else {
            String errorMassage = "Check provided data. Some fields contains incorrect information. Your changes have not been saved.";
            resp.sendRedirect("/rentcar/welcome" + "?incorrect=" + errorMassage);
        }
    }
}