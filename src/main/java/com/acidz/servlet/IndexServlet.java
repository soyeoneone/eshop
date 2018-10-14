package com.acidz.servlet;

import com.acidz.dao.CategoryDAO;
import com.acidz.dao.UserDAO;
import com.acidz.daoimpl.CategoryDAOimpl;
import com.acidz.daoimpl.UserDAOImpl;
import com.acidz.entity.Category;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created by Acidz on 2018/9/16.
 */
@WebServlet(name = "IndexServlet", urlPatterns = "/toindex")
public class IndexServlet extends HttpServlet {
    CategoryDAO categoryDAO = new CategoryDAOimpl();
    UserDAO userDAO = new UserDAOImpl();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Category> categorys = categoryDAO.queryAll();
        request.getSession().setAttribute("categorys",categorys);
        request.getRequestDispatcher("/index.jsp").forward(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }


}
