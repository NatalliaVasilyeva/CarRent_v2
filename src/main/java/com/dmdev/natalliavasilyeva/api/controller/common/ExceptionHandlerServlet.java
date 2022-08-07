package com.dmdev.natalliavasilyeva.api.controller.common;

import com.dmdev.natalliavasilyeva.service.exception.CustomException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

import static jakarta.servlet.RequestDispatcher.ERROR_EXCEPTION;

import static jakarta.servlet.RequestDispatcher.FORWARD_SERVLET_PATH;

@WebServlet("/exceptionHandler")
public class ExceptionHandlerServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response) throws ServletException, IOException {
        processError(request, response);
    }

    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response) throws ServletException, IOException {
        processError(request, response);
    }

    private void processError(HttpServletRequest request,
                              HttpServletResponse response) throws ServletException, IOException {
        // Analyze the servlet exception
        CustomException throwable =  (CustomException) request
                .getAttribute(ERROR_EXCEPTION);
        String uri = (String)request.getAttribute(FORWARD_SERVLET_PATH);

//        request.setAttribute("error", new Error(throwable.getMessage(), throwable.getErrorCode()));
//        request.getRequestDispatcher(JspHelper.getPath(uri)).forward(request, response);
        response.setStatus(throwable.getErrorCode());
        response.setHeader("error", throwable.getMessage());
        response.sendRedirect("/user-cars");
//        response.sendError(throwable.getErrorCode(), throwable.getMessage());
    }
}