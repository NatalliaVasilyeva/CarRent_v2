package com.dmdev.natalliavasilyeva.api.controller.admin.brand;

import com.dmdev.natalliavasilyeva.api.controller.util.JspHelper;
import com.dmdev.natalliavasilyeva.api.dto.responsedto.BrandResponseDTO;
import com.dmdev.natalliavasilyeva.api.dto.responsedto.OrderResponseDto;
import com.dmdev.natalliavasilyeva.api.mapper.BrandMapper;
import com.dmdev.natalliavasilyeva.api.mapper.OrderMapper;
import com.dmdev.natalliavasilyeva.service.BrandService;
import com.dmdev.natalliavasilyeva.service.OrderService;
import com.dmdev.natalliavasilyeva.service.ServiceFactory;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/admin-brands")
public class BrandListAdminServlet extends HttpServlet {
    private final BrandService brandService = ServiceFactory.getInstance().getBrandService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        List<BrandResponseDTO> brandResponseDTOS = BrandMapper.toDtos(brandService.getAllCustomBrands());

        req.setAttribute("brands", brandResponseDTOS);

        req.getRequestDispatcher(JspHelper.getPath("admin-objects")).forward(req, resp);
    }
}