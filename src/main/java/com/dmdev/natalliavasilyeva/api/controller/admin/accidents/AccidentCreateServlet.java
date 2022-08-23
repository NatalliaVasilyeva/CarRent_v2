package com.dmdev.natalliavasilyeva.api.controller.admin.accidents;

import com.dmdev.natalliavasilyeva.api.dto.requestdto.AccidentDto;
import com.dmdev.natalliavasilyeva.api.mapper.AccidentMapper;
import com.dmdev.natalliavasilyeva.service.AccidentService;
import com.dmdev.natalliavasilyeva.service.ServiceFactory;
import com.dmdev.natalliavasilyeva.service.exception.CustomException;
import com.dmdev.natalliavasilyeva.utils.VariablesNameHolder;
import com.dmdev.natalliavasilyeva.utils.validator.DataValidator;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/admin-add-accident")
public class AccidentCreateServlet extends HttpServlet {
    AccidentService accidentService = ServiceFactory.getInstance().getAccidentService();
    DataValidator dataValidator = DataValidator.getInstance();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        var orderId = req.getParameter(VariablesNameHolder.ORDER_ID);
        var accidentDate = req.getParameter(VariablesNameHolder.ACCIDENT_DATE);
        var description = req.getParameter(VariablesNameHolder.DESCRIPTION);
        var damage = req.getParameter(VariablesNameHolder.DAMAGE);
        var startRentalDate = req.getParameter(VariablesNameHolder.START_RENTAL_DATE);
        var endRentalDate = req.getParameter(VariablesNameHolder.END_RENTAL_DATE);

        try {
            Map<String, String> addAccidentData = new HashMap<>();
            addAccidentData.put(VariablesNameHolder.ORDER_ID, orderId);
            addAccidentData.put(VariablesNameHolder.ACCIDENT_DATE, accidentDate);
            addAccidentData.put(VariablesNameHolder.DESCRIPTION, description);
            addAccidentData.put(VariablesNameHolder.DAMAGE, damage);
            addAccidentData.put(VariablesNameHolder.START_RENTAL_DATE, startRentalDate);
            addAccidentData.put(VariablesNameHolder.END_RENTAL_DATE, endRentalDate);

            if (!dataValidator.isValidData(addAccidentData)) {
                String errorMassage = "Check provided data. Some fields contains incorrect information.";
                resp.sendRedirect("/rentcar/welcome" + "?incorrect=" + errorMassage);
            } else {
                LocalDateTime accidentDateTime = LocalDateTime.parse(accidentDate);
                LocalDateTime startDate = LocalDateTime.parse(startRentalDate);
                LocalDateTime endDate = LocalDateTime.parse(endRentalDate);

                if (accidentDateTime.isBefore(startDate) || accidentDateTime.isAfter(endDate)) {
                    throw new CustomException("Accident date must be between rental dates");
                }

                var accidentDto = new AccidentDto(Long.parseLong(orderId), accidentDateTime, description, BigDecimal.valueOf(Long.parseLong(damage)));
                accidentService.createAccident(AccidentMapper.fromDto(accidentDto));
                var prevPage = req.getHeader("referer");
                resp.sendRedirect(prevPage != null ? prevPage + "?success_message=" + "You add accident successfully" : "/rentcar/admin-orders" + "?success_message=" + "You add accident successfully");
            }
        } catch (CustomException | IllegalArgumentException ex) {
            resp.sendRedirect("/rentcar/admin-orders" + "?error_message=" + ex.getMessage());
        }
    }
}