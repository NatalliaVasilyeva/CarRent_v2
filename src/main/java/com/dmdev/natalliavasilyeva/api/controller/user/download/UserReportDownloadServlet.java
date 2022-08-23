package com.dmdev.natalliavasilyeva.api.controller.user.download;

import com.dmdev.natalliavasilyeva.api.dto.responsedto.OrderResponseDto;
import com.dmdev.natalliavasilyeva.api.mapper.OrderMapper;
import com.dmdev.natalliavasilyeva.domain.model.Role;
import com.dmdev.natalliavasilyeva.service.OrderService;
import com.dmdev.natalliavasilyeva.service.ServiceFactory;
import com.dmdev.natalliavasilyeva.utils.VariablesNameHolder;
import com.dmdev.natalliavasilyeva.utils.WriteUtils;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@WebServlet("/user-download")
public class UserReportDownloadServlet extends HttpServlet {
    private final OrderService orderService = ServiceFactory.getInstance().getOrderService();
    private static final String IMAGE_PATH_DIRECTORY = "/Users/natallia.vasilyeva/myProjects/CarRent";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HttpSession session = req.getSession();
        var role = session.getAttribute("role");
        var userId = (Long) session.getAttribute(VariablesNameHolder.USER_ID);
        String reportName = role.toString() + LocalDateTime.now() + ".csv";

        List<OrderResponseDto> orderResponseDtoList = OrderMapper.toDtos(orderService.findAllCustomOrdersByUserId(userId));

        Path resultPath;

        if (role.equals(Role.getEnum("client"))) {
            resultPath = Path.of(IMAGE_PATH_DIRECTORY, "src", "main", "resources", "reports", "user", reportName);
        } else {
            resultPath = Path.of(IMAGE_PATH_DIRECTORY, "src", "main", "resources", "reports", "admin", reportName);
        }

        WriteUtils.writeResult(resultPath, orderResponseDtoList);

        resp.setContentType("text/csv");
        resp.setHeader("Content-disposition", "attachment; filename=" + reportName);

        Optional<InputStream> inputStream = Files.exists(resultPath)
                ? Optional.of(Files.newInputStream(resultPath))
                : Optional.empty();

        if (inputStream.isPresent()) {
            try(OutputStream outputStream = resp.getOutputStream()) {

                byte[] buffer = new byte[4096];
                int bytesRead = -1;

                while ((bytesRead = inputStream.get().read(buffer)) != -1) {
                    outputStream.write(buffer, 0, bytesRead);
                }
            }
        } else {
            String errorMassage = "Data doesn't exists";
            resp.sendRedirect("/rentcar/user-profile" + "?incorrect=" + errorMassage);
        }
    }
}