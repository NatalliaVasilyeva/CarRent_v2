package com.dmdev.natalliavasilyeva.api.controller.util;

public final class JspHelper {

    private JspHelper() {
    }

    private static final String JSP_FORMAT = "/WEB-INF/pages/%s.jsp";

    public static String getPath(String jspName) {
        return String.format(JSP_FORMAT, jspName);
    }
}