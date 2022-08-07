//package com.dmdev.natalliavasilyeva.api.controller.filter;
//
//import com.dmdev.natalliavasilyeva.domain.model.Role;
//import jakarta.servlet.Filter;
//import jakarta.servlet.FilterChain;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.ServletRequest;
//import jakarta.servlet.ServletResponse;
//import jakarta.servlet.annotation.WebFilter;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import jakarta.servlet.http.HttpSession;
//
//import java.io.IOException;
//import java.util.Set;
//import java.util.stream.Collectors;
//import java.util.stream.Stream;
//
//
//@WebFilter("/*")
//public class AccessFilter implements Filter {
//
//    private static final Set<String> PUBLIC_PATHS = Set.of("/sing-in", "/sign-up", "/welcome","/change-language");
//    private static final Set<String> PUBLIC_JSP = Set.of("/index.jsp");
//    private static final Set<String> CLIENT_PATHS = Set.of("/user-cancel-order", "/user-orders", "/user-update-order", "/user-cars", "/cars-by-years",
//            "/cars-by-year", "/cars-by-category", "/cars-by-brand");
//
//    private static final Set<String> ADMIN_PATHS = Set.of("/user-cancel-order", "/user-orders", "/user-update-order", "/user-cars", "/cars-by-years",
//            "/cars-by-year", "/cars-by-category", "/cars-by-brand");
//
//    @Override
//    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain)
//            throws IOException, ServletException {
//        HttpServletRequest httpRequest = (HttpServletRequest) request;
//        HttpServletResponse httpResponse = (HttpServletResponse) response;
//        HttpSession session = httpRequest.getSession();
//        String requestURI = httpRequest.getServletPath();
//        Role role = (Role) session.getAttribute("role");
//
//        boolean isPermitted;
//
//        if (role == Role.CLIENT) {
//            isPermitted = Stream.concat(Stream.concat(PUBLIC_PATHS.stream(), CLIENT_PATHS.stream()), PUBLIC_JSP.stream()).anyMatch(requestURI::contains);
//        } else if (role == Role.ADMIN) {
//            isPermitted = Stream.concat(Stream.concat(PUBLIC_PATHS.stream(), ADMIN_PATHS.stream()), PUBLIC_JSP.stream()).anyMatch(requestURI::contains);
//        } else {
//            Set<String> combine  = Stream.concat(PUBLIC_PATHS.stream(), PUBLIC_JSP.stream()).collect(Collectors.toSet());
//            isPermitted = combine.contains(requestURI);
//        }
//
//        if (role == null && !isPermitted) {
//            session.invalidate();
//            httpResponse.sendRedirect("/rentcar/welcome");
//            return;
//        } else if (!isPermitted) {
//            httpRequest.getRequestDispatcher("ErrorPage403").forward(request, response);
//            return;
//        }
//        filterChain.doFilter(request, response);
//    }
//}