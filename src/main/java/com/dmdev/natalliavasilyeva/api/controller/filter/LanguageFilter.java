package com.dmdev.natalliavasilyeva.api.controller.filter;

import jakarta.servlet.DispatcherType;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebFilter(filterName = "LanguageFilter", urlPatterns = {"/"}, dispatcherTypes = DispatcherType.REQUEST)
public class LanguageFilter implements Filter {
    private static final String EN = "en_US";
    private static final String LOCALE = "locale";

    @Override
    public void init(FilterConfig filterConfig) {
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        HttpSession session = request.getSession();
        String language = (String) session.getAttribute(LOCALE);
        if (language == null) {
            language = EN;
            session.setAttribute(LOCALE, language);
        }

        session.setAttribute(LOCALE, language);
        filterChain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }
}