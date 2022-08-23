package com.dmdev.natalliavasilyeva.api.controller.admin.categories;

import com.dmdev.natalliavasilyeva.api.controller.util.JspHelper;
import com.dmdev.natalliavasilyeva.api.dto.responsedto.CategoryResponseDto;
import com.dmdev.natalliavasilyeva.api.mapper.CategoryMapper;
import com.dmdev.natalliavasilyeva.service.CategoryService;
import com.dmdev.natalliavasilyeva.service.ServiceFactory;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/admin-categories")
public class CategoriesListAdminServlet extends HttpServlet {
    private final CategoryService categoryService = ServiceFactory.getInstance().getCategoryService();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        List<CategoryResponseDto> categoryResponseDtos = CategoryMapper.toDtos(categoryService.getAllCustomCategories());

        req.setAttribute("categories", categoryResponseDtos);

        req.getRequestDispatcher(JspHelper.getPath("admin-objects")).forward(req, resp);
    }
}