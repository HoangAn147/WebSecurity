package com.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Collection;

//https://www.baeldung.com/intercepting-filter-pattern-in-java
//https://clickjacker.io/how-to-fix-clickjacking
@WebFilter("/*")
public class XFrameHeaderFilter implements Filter {

    public static final String POLICY = "default-src 'self' 'unsafe-inline' ";
    public static final String POLICY2 = "Content-Security-Policy: default-src 'self';" +
            "script-src 'self' 'unsafe-inline' www.googletagmanager.com www.google-analytics.com tagmanager.google.com;" +
            "style-src 'self' 'unsafe-inline' tagmanager.google.com;" +
            "img-src 'self' data: www.googletagmanager.com www.google-analytics.com www.google.com www.google.com.vn i.ytimg.com https://*.gravatar.com https://images.dmca.com;" +
            "frame-src www.googletagmanager.com www.google.com www.youtube.com;" +
            "font-src 'self';" +
            "connect-src 'self' *.google.com google.com google.com.vn www.google-analytics.com;" +
            "object-src 'none'";
    public static final String POLICY3 = "*";
    public static final String POLICY4 = "default-src *;" +
            "script-src *;" +
            "style-src *;" +
            "img-src *;" +
            "frame-src *;" +
            "font-src *;" +
            "connect-src *;" +
            "object-src *";
    public static final String POLICY5 =  "script-src *;";

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {

        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;

        httpServletResponse.addHeader("X-Frame-Options", "DENY");
        httpServletResponse.addHeader("Content-Security-Policy", "frame-ancestors 'none'");
        //httpServletResponse.addHeader("Content-Security-Policy", XFrameHeaderFilter.POLICY);

        chain.doFilter(httpServletRequest,httpServletResponse);
        addSameSiteAttribute(httpServletResponse);

        //HttpSession session = httpServletRequest.getSession(false);
        /*if (session == null || session.getAttribute("username") == null) {
            FrontCommand command = new LoginCommand();
            command.init(httpServletRequest, httpServletResponse);
            command.process();
        } else {
            chain.doFilter(request, response);
        }*/

    }
    private void addSameSiteAttribute(HttpServletResponse response) {
        Collection<String> headers = response.getHeaders("Set-Cookie");
        boolean firstHeader = true;
        for (String header : headers) {
            if (firstHeader) {
                response.setHeader("Set-Cookie", String.format("%s; %s", header, "SameSite=Strict"));
                firstHeader = false;
                continue;
            }
            response.addHeader("Set-Cookie", String.format("%s; %s", header, "SameSite=Strict"));
        }
    }


    @Override public void init(FilterConfig filterConfig) throws ServletException { }
}
/*    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        ((HttpServletResponse) resp).setHeader("x-frame-options", "DENY");
        response.addHeader("X-Frame-Options", "DENY");
        response.addHeader("Content-Security-Policy", "frame-ancestors 'none'");
        chain.doFilter(req, resp);
    }*/