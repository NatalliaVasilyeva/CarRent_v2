package com.dmdev.natalliavasilyeva.api.controller.admin.model;

import com.dmdev.natalliavasilyeva.api.controller.util.JspHelper;
import com.dmdev.natalliavasilyeva.api.dto.responsedto.CategoryResponseDto;
import com.dmdev.natalliavasilyeva.api.dto.responsedto.ModelResponseDTO;
import com.dmdev.natalliavasilyeva.api.mapper.CategoryMapper;
import com.dmdev.natalliavasilyeva.api.mapper.ModelMapper;
import com.dmdev.natalliavasilyeva.service.CategoryService;
import com.dmdev.natalliavasilyeva.service.ModelService;
import com.dmdev.natalliavasilyeva.service.ServiceFactory;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/admin-models")
public class ModelsListAdminServlet extends HttpServlet {
    private final ModelService modelService = ServiceFactory.getInstance().getModelService();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        List<ModelResponseDTO> modelResponseDTOS = ModelMapper.toDtos(modelService.getAllCustomModels());

        req.setAttribute("models", modelResponseDTOS);

        req.getRequestDispatcher(JspHelper.getPath("admin-objects")).forward(req, resp);
    }
}