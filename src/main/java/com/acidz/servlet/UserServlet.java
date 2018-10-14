package com.acidz.servlet;

import com.acidz.dao.UserDAO;
import com.acidz.daoimpl.UserDAOImpl;
import com.acidz.entity.User;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.Map;
import java.util.UUID;

/**
 * Created by Acidz on 2018/9/12.
 */
@WebServlet(name = "UserServlet" , urlPatterns = "/user")
public class UserServlet extends BaseServlet {
    UserDAO userDAO = new UserDAOImpl();

    public void logout(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //移除session中的用户
        HttpSession httpSession = request.getSession();
        httpSession.removeAttribute("user");
        //注销cookie中自动登陆的用户信息(措施:覆盖cookie信息)
        Cookie autoLoginUsername = new Cookie("autoLoginUsername", null);
        autoLoginUsername.setMaxAge(0);
        autoLoginUsername.setPath("/");
        Cookie autoLoginPassword = new Cookie("autoLoginPassword", null);
        autoLoginPassword.setMaxAge(0);
        autoLoginPassword.setPath("/");
        response.addCookie(autoLoginUsername);
        response.addCookie(autoLoginPassword);
        request.getRequestDispatcher("/index.jsp").forward(request, response);
    }

    public void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String rememberme = request.getParameter("rememberme");
        String autologin = request.getParameter("autologin");
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        User user = userDAO.login(username, password);
        if (user != null) {//登陆成功
            //记住我
            if ("remember".equals(rememberme)) {
                Cookie cookie = new Cookie("rememberme", username);
                cookie.setMaxAge(7 * 24 * 3600);
                cookie.setPath("/");
                response.addCookie(cookie);
            }
            //自动登陆
            if ("yes".equals(autologin)) {
                Cookie autoLoginUsername = new Cookie("autoLoginUsername", username);
                autoLoginUsername.setMaxAge(10 * 24 * 3600);
                autoLoginUsername.setPath("/");
                Cookie autoLoginPassword = new Cookie("autoLoginPassword", password);
                autoLoginPassword.setMaxAge(10 * 24 * 3600);
                autoLoginPassword.setPath("/");
                response.addCookie(autoLoginUsername);
                response.addCookie(autoLoginPassword);
            }
            request.getSession().setAttribute("user",user);
            request.getRequestDispatcher("/index.jsp").forward(request, response);
        } else {
            //登陆失败
            request.setAttribute("msg","账户或密码错误!");
            request.getRequestDispatcher("/login.jsp").forward(request, response);
        }
    }

    public void register(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Map<String,String[]> properties = request.getParameterMap();
        User user = new User();
        try {
            BeanUtils.populate(user, properties);
            user.setUid(UUID.randomUUID().toString());
            userDAO.add(user);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("注册出错了!");
        }
        request.getRequestDispatcher("/index.jsp").forward(request,response);
    }

    public void checkUsername(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        boolean result = userDAO.checkUsername(username);
        response.setContentType("text/html;charset=UTF-8");
        if (result) {
            response.getWriter().write("用户名可用");
        } else {
            response.getWriter().write("用户名已被占用");
        }

    }
}
