package com.acidz.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Acidz on 2018/10/9.
 */
@WebFilter(filterName = "AdminFilter")
public class AdminFilter implements Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;
        String uri = request.getRequestURI();
        String method = request.getParameter("method");
        if (!(uri.equals("/admin/index.jsp")||"login".equals(method))) {
            if (request.getSession().getAttribute("adminuser") == null) {
                response.setContentType("text/html;charset=utf-8");
                response.getWriter().write("非法请求!");
                return;
            }
        }
        chain.doFilter(request, response);
    }

    public void init(FilterConfig config) throws ServletException {

    }

}
