package com.acidz.admin;

import com.acidz.dao.CategoryDAO;
import com.acidz.daoimpl.CategoryDAOimpl;
import com.acidz.entity.Category;
import com.acidz.servlet.BaseServlet;
import com.alibaba.fastjson.JSON;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created by Acidz on 2018/9/29.
 */
@WebServlet(name = "CategoryServlet", urlPatterns = "/admin/category")
public class CategoryServlet extends BaseServlet {
    CategoryDAO categoryDAO = new CategoryDAOimpl();

    public void readCategorys(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Category> categories = categoryDAO.queryAll();
        response.setContentType("text/json;charset=utf-8");
        response.getWriter().write(JSON.toJSONString(categories));
    }

    public void readAll(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Category> categories = categoryDAO.queryAll();
        request.setAttribute("categories", categories);
        request.getRequestDispatcher("/admin/category/list.jsp").forward(request, response);
    }

    public void toEdit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String cid = request.getParameter("cid");
        Category category = categoryDAO.queryById(cid);
        request.setAttribute("category", category);
        request.getRequestDispatcher("/admin/category/edit.jsp").forward(request, response);
    }

    public void edit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String cid = request.getParameter("cid");
        String cname = request.getParameter("cname");
        Category category = new Category();
        category.setCid(cid);
        category.setCname(cname);
        category.setFid("0");
        categoryDAO.update(category);
        request.getRequestDispatcher("/admin/category?method=readAll").forward(request, response);
    }
}
