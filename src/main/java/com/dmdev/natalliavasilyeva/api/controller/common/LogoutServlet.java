package com.dmdev.natalliavasilyeva.api.controller.common;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.Enumeration;

@WebServlet("/logout")
public class LogoutServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HttpSession session = req.getSession();
        Enumeration<String> attrNames = session.getAttributeNames();
        while(attrNames.hasMoreElements()){
            session.removeAttribute(attrNames.nextElement());
        }
        resp.sendRedirect("/rentcar/welcome");
    }
}