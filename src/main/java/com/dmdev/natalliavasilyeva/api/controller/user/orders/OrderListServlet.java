package com.dmdev.natalliavasilyeva.api.controller.user.orders;

import com.dmdev.natalliavasilyeva.api.controller.util.JspHelper;
import com.dmdev.natalliavasilyeva.api.dto.responsedto.OrderResponseDto;
import com.dmdev.natalliavasilyeva.api.mapper.OrderMapper;
import com.dmdev.natalliavasilyeva.service.OrderService;
import com.dmdev.natalliavasilyeva.service.ServiceFactory;
import com.dmdev.natalliavasilyeva.utils.VariablesNameHolder;
import com.dmdev.natalliavasilyeva.utils.validator.DataValidator;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;

@WebServlet("/user-orders")
public class OrderListServlet extends HttpServlet {
    private final OrderService orderService = ServiceFactory.getInstance().getOrderService();
    private DataValidator dataValidator = DataValidator.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        var userId = String.valueOf(session.getAttribute(VariablesNameHolder.USER_ID));
        if (!dataValidator.isValidData(VariablesNameHolder.USER_ID, userId)) {
            resp.sendRedirect("/rentcar/welcome");
        }
        List<OrderResponseDto> orders = OrderMapper.toDtos(orderService.findAllCustomOrdersByUserId(Long.valueOf(userId)));

        req.setAttribute("orders", orders);

        if (req.getParameter("incorrect") != null) {
            req.setAttribute("incorrect", req.getParameter("incorrect"));
        }
        if (req.getParameter("success_message") != null) {
            req.setAttribute("success_message", req.getParameter("success_message"));
        }
        if (req.getParameter("error_message") != null) {
            req.setAttribute("error_message", req.getParameter("error_message"));
        }

        req.getRequestDispatcher(JspHelper.getPath("user-orders")).forward(req, resp);
    }
}