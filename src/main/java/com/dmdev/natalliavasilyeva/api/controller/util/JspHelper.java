package com.dmdev.natalliavasilyeva.api.controller.util;

public class JspHelper {
    private static final String JSP_FORMAT = "/WEB-INF/pages/%s.jsp";

    public static String getPath(String jspName) {
        return String.format(JSP_FORMAT, jspName);
    }
}