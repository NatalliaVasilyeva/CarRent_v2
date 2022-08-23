package com.dmdev.natalliavasilyeva.api.controller.admin.orders;

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

@WebServlet("/admin-change-status-order")
public class OrderChangeStatusServlet extends HttpServlet {
    private final OrderService orderService = ServiceFactory.getInstance().getOrderService();
    private final DataValidator dataValidator = DataValidator.getInstance();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        var id = req.getParameter(VariablesNameHolder.ORDER_ID);
        var command = req.getParameter(VariablesNameHolder.ORDER_STATUS);
        var status = command.equals("confirm")? OrderStatus.NOT_PAYED.name() : command.equals("decline")? OrderStatus.DECLINED.name() : OrderStatus.PAYED.name();
        if (dataValidator.isValidData(VariablesNameHolder.ORDER_ID, id)) {
            Order updatedOrder = null;
            try {
                updatedOrder = orderService.changeOrderStatus(Long.valueOf(id), status);
            } catch (CustomException ex) {
                resp.sendRedirect("/rentcar/admin-orders" + "?error_message=" + ex.getMessage());
            }
            if (updatedOrder != null) {
                resp.sendRedirect("/rentcar/admin-orders?success_message=Order status has been changed successfully");
            }
        } else {
            String errorMassage = "Check provided data. Order id is incorrect.";
            resp.sendRedirect("/rentcar/admin-orders" + "?incorrect=" + errorMassage);
        }
    }
}