package com.dmdev.natalliavasilyeva.api.controller.common.users;

import com.dmdev.natalliavasilyeva.api.controller.util.JspHelper;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;


@WebServlet("/user-settings")
public class UserSettingsServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            req.getRequestDispatcher(JspHelper.getPath("user-settings")).forward(req, resp);
        } catch (ServletException ex) {
            resp.sendRedirect("/rentcar/welcome" + "?error_message=" + ex.getMessage());
        }
    }
}