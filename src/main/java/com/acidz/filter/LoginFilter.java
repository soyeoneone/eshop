package com.acidz.filter;

import com.acidz.dao.UserDAO;
import com.acidz.daoimpl.UserDAOImpl;
import com.acidz.entity.User;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Acidz on 2018/9/28.
 */
@WebFilter("/*")
public class LoginFilter implements Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        UserDAO userDAO = new UserDAOImpl();
        HttpServletRequest request = (HttpServletRequest)req;
        HttpServletResponse response = (HttpServletResponse)resp;
        //判断cookie中有没有自动登陆的用户
        if (request.getSession().getAttribute("user") == null) {
            Cookie[] cookies = request.getCookies();
            String autoLoginUsername = getCookieByName(cookies,"autoLoginUsername");
            String autoLoginPassword = getCookieByName(cookies,"autoLoginPassword");
            if (autoLoginUsername != null && autoLoginPassword != null) {
                User user = userDAO.login(autoLoginUsername, autoLoginPassword);
                if (user == null) {
                    request.getRequestDispatcher("/login.jsp").forward(request, response);
                    return;
                } else {
                    request.getSession().setAttribute("user", user);
                }
            }
        }
        chain.doFilter(req, resp);
    }

    public void init(FilterConfig config) throws ServletException {

    }

    public String getCookieByName(Cookie[] cookies, String name){
        if (cookies != null && cookies.length > 0) {
            for (Cookie cookie : cookies) {
                if (name.equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }

}
