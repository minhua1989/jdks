package com.xf.jdks.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * Created by RenJun on 2017/5/15.
 */
@WebFilter(filterName="xssFilter",urlPatterns="/*")
public class XssFilter implements Filter{

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        XsslHttpServletRequestWrapper xssRequest = new XsslHttpServletRequestWrapper((HttpServletRequest)request);
        chain.doFilter(xssRequest , response);
    }

    @Override
    public void destroy() {

    }
}
