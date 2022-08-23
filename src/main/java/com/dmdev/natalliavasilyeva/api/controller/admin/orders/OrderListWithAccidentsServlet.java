package com.dmdev.natalliavasilyeva.api.controller.admin.orders;

import com.dmdev.natalliavasilyeva.api.controller.util.JspHelper;
import com.dmdev.natalliavasilyeva.api.dto.responsedto.OrderResponseDto;
import com.dmdev.natalliavasilyeva.api.mapper.OrderMapper;
import com.dmdev.natalliavasilyeva.service.OrderService;
import com.dmdev.natalliavasilyeva.service.ServiceFactory;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/admin-orders-with-accidents")
public class OrderListWithAccidentsServlet extends HttpServlet {
    private final OrderService orderService = ServiceFactory.getInstance().getOrderService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        List<OrderResponseDto> orders = OrderMapper.toDtos(orderService.findAllCustomOrdersWithAccidents());

        req.setAttribute("orders", orders);

        req.getRequestDispatcher(JspHelper.getPath("admin-orders")).forward(req, resp);
    }
}