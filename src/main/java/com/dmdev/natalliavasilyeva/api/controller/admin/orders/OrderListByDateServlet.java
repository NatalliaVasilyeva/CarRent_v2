package com.dmdev.natalliavasilyeva.api.controller.admin.orders;

import com.dmdev.natalliavasilyeva.api.controller.util.JspHelper;
import com.dmdev.natalliavasilyeva.api.dto.responsedto.CarUserResponseDto;
import com.dmdev.natalliavasilyeva.api.dto.responsedto.OrderResponseDto;
import com.dmdev.natalliavasilyeva.api.mapper.CarMapper;
import com.dmdev.natalliavasilyeva.api.mapper.OrderMapper;
import com.dmdev.natalliavasilyeva.service.CarService;
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

@WebServlet("/admin-orders-by-date")
public class OrderListByDateServlet extends HttpServlet {
    private final OrderService orderService = ServiceFactory.getInstance().getOrderService();
    DataValidator dataValidator = DataValidator.getInstance();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String date = req.getParameter("date");
        try {
            if (!dataValidator.isValidData(VariablesNameHolder.BIRTHDAY, date)) {
                String errorMassage = "Check provided data. Some fields contains incorrect information.";
                resp.sendRedirect("/rentcar/welcome" + "?incorrect=" + errorMassage);
            } else {
                LocalDate localDate = DateTimeService.fromStringLocalDate(date);
                List<OrderResponseDto> orders = OrderMapper.toDtos(orderService.findAllCustomOrdersBetweenDates
                        (DateTimeService.fromLocalDateTimeDateToInstant(localDate.atStartOfDay()),
                                DateTimeService.fromLocalDateTimeDateToInstant(localDate.atTime(LocalTime.MAX))));

                req.setAttribute("orders", orders);

                req.getRequestDispatcher(JspHelper.getPath("admin-orders")).forward(req, resp);
            }
        } catch (CustomException | IllegalArgumentException ex) {
            resp.sendRedirect("/rentcar//admin-orders" + "?error_message=" + ex.getMessage());
        }
    }
}