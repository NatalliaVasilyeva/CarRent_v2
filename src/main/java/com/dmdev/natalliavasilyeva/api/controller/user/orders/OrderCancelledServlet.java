package com.dmdev.natalliavasilyeva.api.controller.user.orders;

import com.dmdev.natalliavasilyeva.domain.model.Order;
import com.dmdev.natalliavasilyeva.domain.model.OrderStatus;
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

@WebServlet("/user-cancel-order")
public class OrderCancelledServlet extends HttpServlet {
    private final OrderService orderService = ServiceFactory.getInstance().getOrderService();
    private final DataValidator dataValidator = DataValidator.getInstance();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        var id = req.getParameter(VariablesNameHolder.ORDER_ID);
        if (dataValidator.isValidData(VariablesNameHolder.ORDER_ID, id)) {
            Order updatedOrder = null;
            try {
                updatedOrder = orderService.changeOrderStatus(Long.valueOf(id), OrderStatus.CANCELLED.name());
            } catch (CustomException ex) {
                resp.sendRedirect("/rentcar/user-orders" + "?error_message=" + ex.getMessage());
            }
            if (updatedOrder != null) {
                resp.sendRedirect("/rentcar/user-orders?success_message=Order has been cancelled successfully");
            }
        } else {
            String errorMassage = "Check provided data. Order id is incorrect.";
            resp.sendRedirect("/rentcar/user-orders" + "?incorrect=" + errorMassage);
        }
    }
}