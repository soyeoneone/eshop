package com.acidz.admin;

import com.acidz.dao.AdminuserDAO;
import com.acidz.daoimpl.AdminuserDAOImpl;
import com.acidz.entity.Adminuser;
import com.acidz.servlet.BaseServlet;
import com.acidz.utils.MD5Utils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

/**
 * Created by Acidz on 2018/10/9.
 */
@WebServlet(name = "AdminuserServlet",urlPatterns = "/admin/adminuser")
public class AdminuserServlet extends BaseServlet {
    AdminuserDAO adminuserDAO = new AdminuserDAOImpl();

    public void readAll(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Adminuser> adminusers = adminuserDAO.queryAll();
        request.setAttribute("adminusers", adminusers);
        request.getRequestDispatcher("/admin/adminuser/list.jsp").forward(request, response);
    }

    public void add(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("name");
        String password = request.getParameter("password");
        password = MD5Utils.md5(password);
        Adminuser adminuser = new Adminuser();
        adminuser.setId(UUID.randomUUID().toString());
        adminuser.setName(name);
        adminuser.setPassword(password);
        adminuserDAO.add(adminuser);
        request.getRequestDispatcher("/admin/adminuser?method=readAll").forward(request, response);
    }

    public void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("name");
        String password = request.getParameter("password");
        password = MD5Utils.md5(password);
        Adminuser adminuser = adminuserDAO.login(name, password);
        if (adminuser == null) {
            request.setAttribute("msg", "账号或密码错误,请重新登陆!");
            request.getRequestDispatcher("/admin/index.jsp").forward(request, response);
        } else {
            request.getSession().setAttribute("adminuser", adminuser);
            request.getRequestDispatcher("/admin/home.jsp").forward(request, response);
        }
    }

    public void updatePassword(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String newpassword = request.getParameter("newpassword");
        newpassword = MD5Utils.md5(newpassword);
        Adminuser adminuser = (Adminuser) request.getSession().getAttribute("adminuser");
        adminuser.setPassword(newpassword);
        adminuserDAO.update(adminuser);
        request.getRequestDispatcher("/admin/index.jsp").forward(request, response);
    }

    public void checkPassword(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String password = request.getParameter("password");
        password = MD5Utils.md5(password);
        Adminuser adminuser = (Adminuser) request.getSession().getAttribute("adminuser");
        if (adminuser.getPassword().equals(password))
            response.getWriter().write("密码正确!");
        else
            response.getWriter().write("密码错误!");
    }
}
