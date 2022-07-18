package com.dmdev.natalliavasilyeva.api.controller.filter;

import javax.servlet.DispatcherType;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter(filterName = "JspFilter", urlPatterns = {"/WEB-INF/pages/*"}, dispatcherTypes = DispatcherType.REQUEST)
public class JspFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException {
        ((HttpServletResponse) response).sendError(HttpServletResponse.SC_NOT_FOUND);
    }

    @Override
    public void destroy() {

    }
}