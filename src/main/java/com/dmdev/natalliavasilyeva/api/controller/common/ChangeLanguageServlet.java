package com.dmdev.natalliavasilyeva.api.controller.common;

import com.dmdev.natalliavasilyeva.domain.model.Language;
import com.dmdev.natalliavasilyeva.domain.model.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/change-language")
public class ChangeLanguageServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String referer = req.getHeader("referer");

        Language language = req.getParameter("language") == null? Language.EN_US : Language.getEnum(req.getParameter("language"));

        req.getSession().setAttribute("locale", language.getLocale());

        User user = (User) req.getSession().getAttribute("user");

        if (user != null && referer != null) {
            resp.sendRedirect(referer);
        } else {
            resp.sendRedirect("/rentcar/welcome");
        }
    }
}