package com.acidz.servlet;

import com.acidz.dao.ProductDAO;
import com.acidz.daoimpl.ProductDAOImpl;
import com.acidz.entity.Product;
import com.alibaba.fastjson.JSON;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Acidz on 2018/9/16.
 */
@WebServlet(name = "ProductServlet", urlPatterns = "/product")
public class ProductServlet extends BaseServlet {
    ProductDAO productDAO = new ProductDAOImpl();

    //查询某一category下的所有product
    public void read(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String cname = request.getParameter("cname");
        String cid = request.getParameter("cid");
        List<Product> products = productDAO.queryAllByCid(cid);
        request.setAttribute("products",products);
        request.setAttribute("cname",cname);
        readHistory(request, response);
        request.getRequestDispatcher("/product_list.jsp").forward(request, response);
    }

    //查询某一product的详细信息
    public void readInfo(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int pid = Integer.parseInt(request.getParameter("pid"));
        Map productInfo = productDAO.queryInfo(pid);
        request.setAttribute("productInfo", productInfo);
        addHistory(request, response);//添加历史浏览记录
        request.getRequestDispatcher("/product_info.jsp").forward(request, response);
    }

    //读取历史浏览记录
    public void readHistory(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Map<String, Product> historyMap = (Map<String, Product>) request.getSession().getAttribute("history");
        if (historyMap == null) {
            historyMap = new HashMap<>();
            Cookie cookie = getCookieByName(request.getCookies(), "history");
            if (cookie != null) {
                String[] pids = cookie.getValue().split(",");
                for (String pid : pids) {
                    historyMap.put(pid, productDAO.queryById(pid));
                }
            }
            request.getSession().setAttribute("historyMap", historyMap);
        }
    }

    //添加某一新product到浏览记录
    public void addHistory(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String pid = request.getParameter("pid");
        Map<String, Product> historyMap = (Map<String, Product>) request.getSession().getAttribute("historyMap");
        if (!historyMap.containsKey(pid)) {
            historyMap.put(pid, productDAO.queryById(pid));
            //将map中的浏览记录备份到cookie
            String cookieValue = "";
            for (Map.Entry<String, Product> entry : historyMap.entrySet()) {
                cookieValue += (entry.getKey()+",");
            }
            Cookie cookie = new Cookie("history", cookieValue);
            cookie.setMaxAge(7 * 24 * 3600);
            cookie.setPath("/");
            response.addCookie(cookie);
        }
    }

    public Cookie getCookieByName(Cookie[] cookies, String name) {
        if (cookies != null && cookies.length > 0) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(name)) {
                    return cookie;
                }
            }
        }
        return null;
    }

    //
    public void readToJson(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String cid = request.getParameter("cid");
        List<Product> products = productDAO.queryAllByCid(cid);
        response.setContentType("text/json;charset=UTF-8");
        response.getWriter().write(JSON.toJSONString(products));
    }
}
