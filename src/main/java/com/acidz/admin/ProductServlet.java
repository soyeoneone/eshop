package com.acidz.admin;

import com.acidz.dao.CategoryDAO;
import com.acidz.dao.ProductDAO;
import com.acidz.daoimpl.CategoryDAOimpl;
import com.acidz.daoimpl.ProductDAOImpl;
import com.acidz.entity.Category;
import com.acidz.entity.PageBean;
import com.acidz.entity.Product;
import com.acidz.servlet.BaseServlet;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Created by Acidz on 2018/9/29.
 */
@WebServlet("/admin/product")
public class ProductServlet extends BaseServlet {
    CategoryDAO categoryDAO = new CategoryDAOimpl();
    ProductDAO productDAO = new ProductDAOImpl();

    public void readAll(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //接收参数
        int currentPage = Integer.parseInt(request.getParameter("currentPage"));
        String searchType = request.getParameter("searchType");
        String keyword = request.getParameter("keyword");
        if (keyword == null) {
            keyword = "";
        }
        //创建pageBean对象
        PageBean pageBean = new PageBean(currentPage,searchType,keyword);
        //查询数据库
        pageBean = productDAO.queryAll(pageBean);
        //完善pageBean对象
        pageBean.complete();
        request.setAttribute("pageBean", pageBean);
        request.getRequestDispatcher("/admin/product/list.jsp").forward(request, response);
    }

    public void toEdit (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String pid = request.getParameter("pid");
        Product product = productDAO.queryById(pid);
        List<Category> categoryList = categoryDAO.queryAll();
        request.setAttribute("categoryList",categoryList);
        request.setAttribute("product", product);
        request.getRequestDispatcher("/admin/product/edit.jsp").forward(request, response);
    }

    public void edit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Map<String, String> beanMap = (Map<String, String>) request.getAttribute("beanMap");
        Product product = new Product();
        try {
            BeanUtils.populate(product, beanMap);
//             product.setPid(UUID.randomUUID().toString());
            product.setPdate(new Date(System.currentTimeMillis()));
//             product.setPflag(0L);
            productDAO.update(product);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        request.getRequestDispatcher("/admin/product?method=readAll&currentPage=1&searchType=pname").forward(request, response);
    }

    public void delete (HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String pid = request.getParameter("pid");
        productDAO.delete(pid);
        response.getWriter().write("success");
    }

    public void add(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Map<String, String> beanMap = (Map<String, String>) request.getAttribute("beanMap");
        Product product = new Product();
        try {
            BeanUtils.populate(product, beanMap);
            product.setPid(UUID.randomUUID().toString());
            product.setPdate(new Date(System.currentTimeMillis()));
            product.setPflag(0L);
            productDAO.add(product);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        request.getRequestDispatcher("/admin/product?method=readAll&currentPage=1").forward(request, response);
    }
}
