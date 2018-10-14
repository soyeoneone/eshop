package com.acidz.servlet;

import com.acidz.dao.ProductDAO;
import com.acidz.daoimpl.ProductDAOImpl;
import com.acidz.entity.Cart;
import com.acidz.entity.Product;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Acidz on 2018/9/18.
 */
@WebServlet(name = "CartServlet", urlPatterns = "/cart")
public class CartServlet extends BaseServlet {
    ProductDAO productDAO = new ProductDAOImpl();

    //加入购物车
    public void addCart(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String pid = request.getParameter("pid");
        int quantity = Integer.parseInt(request.getParameter("quantity"));
        Product product = productDAO.queryById(pid);
        Map<String, Cart> cartMap = (Map<String, Cart>) request.getSession().getAttribute("cartMap");
        //购物车对象是否存在
        if (cartMap == null) {
            cartMap = new HashMap<>();
        }
        //购物车中是否有相同商品
        if (cartMap.containsKey(pid)) {
            cartMap.get(pid).setQuantity(cartMap.get(pid).getQuantity() + quantity);
        } else {
            Cart cart = new Cart();
            cart.setPid(pid);
            cart.setQuantity(quantity);
            cart.setPimage(product.getPimage());
            cart.setPname(product.getPname());
            cart.setShop_price(product.getShop_price());
            cartMap.put(pid, cart);
            request.getSession().setAttribute("cartMap", cartMap);
        }
        //用cookie持久化session,保存购物车信息
        Cookie cookie = new Cookie("JSESSIONID", request.getSession().getId());
        cookie.setMaxAge(7 * 24 * 3600);
        response.addCookie(cookie);
        request.getRequestDispatcher("/cart.jsp").forward(request, response);
    }

    //删除购物车中的一个product
    public void deleteById(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String pid = request.getParameter("pid");
        Map<String, Cart> cartMap = (Map<String, Cart>) request.getSession().getAttribute("cartMap");
        cartMap.remove(pid);
        request.getRequestDispatcher("/cart.jsp").forward(request, response);
    }


}
