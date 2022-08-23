package com.dmdev.natalliavasilyeva.api.controller.admin.orders;

import com.dmdev.natalliavasilyeva.api.controller.util.JspHelper;
import com.dmdev.natalliavasilyeva.api.dto.responsedto.OrderResponseDto;
import com.dmdev.natalliavasilyeva.api.mapper.OrderMapper;
import com.dmdev.natalliavasilyeva.service.OrderService;
import com.dmdev.natalliavasilyeva.service.ServiceFactory;
import com.dmdev.natalliavasilyeva.service.exception.CustomException;
import com.dmdev.natalliavasilyeva.utils.DateTimeService;
import com.dmdev.natalliavasilyeva.utils.VariablesNameHolder;
import com.dmdev.natalliavasilyeva.utils.validator.DataValidator;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@WebServlet("/admin-orders-by-car-number")
public class OrderListByCarNumberServlet extends HttpServlet {
    private final OrderService orderService = ServiceFactory.getInstance().getOrderService();
    DataValidator dataValidator = DataValidator.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String carNumber = req.getParameter(VariablesNameHolder.CAR_NUMBER);
        try {
            if (!dataValidator.isValidData(VariablesNameHolder.CAR_NUMBER, carNumber)) {
                String errorMassage = "Check provided data. Some fields contains incorrect information.";
                resp.sendRedirect("/rentcar/welcome" + "?incorrect=" + errorMassage);
            } else {
                List<OrderResponseDto> orders = OrderMapper.toDtos(orderService.findAllCustomOrdersByCarNumber(carNumber));

                req.setAttribute("orders", orders);

                req.getRequestDispatcher(JspHelper.getPath("admin-orders")).forward(req, resp);
            }
        } catch (CustomException | IllegalArgumentException ex) {
            resp.sendRedirect("/rentcar//admin-orders" + "?error_message=" + ex.getMessage());
        }
    }
}