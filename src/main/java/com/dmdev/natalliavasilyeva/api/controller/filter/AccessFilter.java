package com.dmdev.natalliavasilyeva.api.controller.filter;

import com.dmdev.natalliavasilyeva.domain.model.Role;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@WebFilter("/*")
public class AccessFilter implements Filter {

    private static final Set<String> PUBLIC_PATHS = Set.of("/sign-in", "/sign-up", "/welcome", "/change-language");
    private static final Set<String> PUBLIC_JSP = Set.of("/index.jsp");
    private static final Set<String> USER_JSP = Set.of("/user-profile.jsp");
    private static final Set<String> CLIENT_PATHS = Set.of("/user-cancel-order", "/user-orders", "/user-update-order", "/user-cars", "/cars-by-years",
            "/cars-by-year", "/cars-by-category", "/cars-by-brand", "/user-profile", "/logout", "/user-create-order", "/user-settings", "/change-password", "/user-download");

    private static final Set<String> ADMIN_PATHS = Set.of("/logout", "/admin-orders", "/admin-change-status-order", "/admin-add-accident",
            "/admin-orders-by-dates", "/admin-orders-by-car-number", "/admin-orders-by-status", "/admin-orders-with-accidents",
            "/admin-create-brand", "/admin-brands", "/admin-edit-brand", "/admin-create-price", "/admin-categories", "/admin-edit-category",
            "/admin-create-category", "/admin-models", "/admin-edit-model", "/admin-create-model", "/edit-car-admin", "/admin-cars",
            "/admin-create-car", "/admin-update-car", "/user-settings", "/change-password", "/report", "/user-profile", "/cars-by-brand-admin",
            "/cars-by-year-admin", "/cars-by-years-admin", "/cars-by-category-admin", "/cars-with-accidents-admin", "/cars-under-repair-admin",
            "/cars-by-number-admin", "/admin-delete-car");

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        HttpSession session = httpRequest.getSession();
        String requestURI = httpRequest.getServletPath();
        Role role = (Role) session.getAttribute("role");

        if ((isAdminPath(requestURI) && isAdminLoggedIn(role))) {
            filterChain.doFilter(request, response);
        } else if ((isClientPath(requestURI) && isClientLoggedIn(role))) {
            filterChain.doFilter(request, response);
        } else if (isPublicPath(requestURI) || isNotLoggedUser(role)) {
            filterChain.doFilter(request, response);
        } else if (shouldIncludeJSFile(requestURI)) {
            filterChain.doFilter(request, response);
        } else {
            var prevPage = ((HttpServletRequest) request).getHeader("referer");
            (httpResponse).sendRedirect(prevPage != null ? prevPage + "?error_message=You don't have permission to see this page or make these changes" : "/rentcar/welcome?error_message=You don't have permission to see this page or make these changes");
        }
    }

    private boolean isClientLoggedIn(Role role) {
        return role != null && role.equals(Role.CLIENT);
    }

    private boolean isAdminLoggedIn(Role role) {
        return role != null && role.equals(Role.ADMIN);
    }

    private boolean isNotLoggedUser(Role role) {
        return role == null;
    }

    private boolean isPublicPath(String uri) {
        return Stream.concat(PUBLIC_PATHS.stream(), PUBLIC_JSP.stream()).collect(Collectors.toSet()).contains(uri);
    }

    private boolean isAdminPath(String uri) {
        return Stream.concat(Stream.concat(PUBLIC_PATHS.stream(), ADMIN_PATHS.stream()), PUBLIC_JSP.stream()).anyMatch(uri::contains);
    }

    private boolean isClientPath(String uri) {
        return Stream.concat(Stream.concat(Stream.concat(PUBLIC_PATHS.stream(), CLIENT_PATHS.stream()), PUBLIC_JSP.stream()), USER_JSP.stream()).anyMatch(uri::contains);
    }
    private boolean shouldIncludeJSFile(String uri) {
        return uri.endsWith(".js");
    }
}