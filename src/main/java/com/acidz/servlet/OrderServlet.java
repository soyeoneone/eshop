package com.acidz.servlet;

import com.acidz.dao.OrderitemDAO;
import com.acidz.dao.OrdersDAO;
import com.acidz.daoimpl.OrderitemDAOImpl;
import com.acidz.daoimpl.OrdersDAOImpl;
import com.acidz.entity.*;
import com.acidz.utils.Configuration;
import com.acidz.utils.PaymentForOnlineService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Acidz on 2018/9/19.
 */
@WebServlet(name = "OrderServlet", urlPatterns = "/order")
public class OrderServlet extends BaseServlet {
    OrdersDAO ordersDAO = new OrdersDAOImpl();
    OrderitemDAO orderitemDAO = new OrderitemDAOImpl();

    //提交订单,持久化订单数据到数据库
    public void commitOrder(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            request.getRequestDispatcher("/login.jsp").forward(request, response);
        }
        Map<String, Cart> cartMap = (Map<String, Cart>) request.getSession().getAttribute("cartMap");
        Orders order = new Orders();
        order.setOid(UUID.randomUUID().toString());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Timestamp ordertime = Timestamp.valueOf(simpleDateFormat.format(new Date()));
        order.setOrdertime(ordertime);
        order.setState(0);
        order.setUid(user.getUid());
        ordersDAO.add(order);
        double total = 0;
        for (Map.Entry<String, Cart> cartEntry : cartMap.entrySet()) {
            Orderitem orderitem = new Orderitem();
            orderitem.setItemid(UUID.randomUUID().toString());
            orderitem.setCount(cartEntry.getValue().getQuantity());
            double subtotal = cartEntry.getValue().getShop_price() * cartEntry.getValue().getQuantity();
            total += subtotal;
            orderitem.setSubtotal(subtotal);
            orderitem.setPid(cartEntry.getKey());
            orderitem.setOid(order.getOid());
            orderitemDAO.add(orderitem);
        }
        order.setTotal(total);
        ordersDAO.updateTotalPrice(order);
        request.setAttribute("order", order);
        request.getRequestDispatcher("/order_info.jsp").forward(request, response);
    }

    //确认订单,补充订单信息
    public void confirmOrder(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String orderId = request.getParameter("orderId");
        String address = request.getParameter("address");
        String name = request.getParameter("name");
        String telephone = request.getParameter("telephone");
        //更新订单信息
        ordersDAO.updateByOid(orderId, address, name, telephone);
        //与交易平台接口对接
        String pd_FrpId = request.getParameter("pd_FrpId");
        String keyValue = formatString(Configuration.getInstance().getValue("keyValue"));                    // 商家密钥
        String nodeAuthorizationURL = formatString(Configuration.getInstance().getValue("onlinePaymentReqURL"));    // 交易请求地址
        // 商家设置用户购买商品的支付信息
        // 银行编号必须大写
        pd_FrpId = pd_FrpId.toUpperCase();
        String p0_Cmd           = formatString("Buy");                                                                // 在线支付请求，固定值 ”Buy”
        String p1_MerId         = formatString(Configuration.getInstance().getValue("p1_MerId"));        // 商户编号
        String p2_Order         = formatString(orderId);                            // 商户订单号
        String p3_Amt           = formatString("0.01");       // 支付金额
        String p4_Cur           = formatString("CNY");       // 交易币种
        String p5_Pid           = formatString("1centtext");       // 商品名称
        String p6_Pcat          = formatString("");     // 商品种类
        String p7_Pdesc         = formatString("");   // 商品描述
        String p8_Url           = formatString(Configuration.getInstance().getValue("p8_Url"));       // 商户接收支付成功数据的地址
        String p9_SAF           = formatString(Configuration.getInstance().getValue("p9_SAF"));       // 需要填写送货信息 0：不需要  1:需要
        String pa_MP            = formatString("");         // 商户扩展信息
        String pr_NeedResponse  = formatString(Configuration.getInstance().getValue("pr_NeedResponse"));    // 是否需要应答机制
        String hmac             = formatString("");                                                                    // 交易签名串
        // 获得MD5-HMAC签名
        hmac = PaymentForOnlineService.getReqMd5HmacForOnlinePayment(p0_Cmd,
                p1_MerId, p2_Order, p3_Amt, p4_Cur, p5_Pid, p6_Pcat, p7_Pdesc,
                p8_Url, p9_SAF, pa_MP, pd_FrpId, pr_NeedResponse, keyValue);

        String requestURL=nodeAuthorizationURL+"?pd_FrpId="+pd_FrpId  +
                "&p0_Cmd="+p0_Cmd  +
                "&p1_MerId="+p1_MerId+
                "&p2_Order="+p2_Order+
                "&p3_Amt="+p3_Amt  +
                "&p4_Cur="+p4_Cur  +
                "&p5_Pid="+p5_Pid  +
                "&p6_Pcat="+p6_Pcat +
                "&p7_Pdesc="+p7_Pdesc+
                "&p8_Url="+p8_Url  +
                "&p9_SAF="+p9_SAF  +
                "&pa_MP="+pa_MP   +
                "&pr_NeedResponse="+pr_NeedResponse+"&hmac="+hmac;
        //跳转到
        response.sendRedirect(requestURL);
    }

    //从数据库读取我的订单
    public void readMyOrders(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            request.getRequestDispatcher("/login.jsp").forward(request, response);
            return;
        }
        List<MyOrders> myOrdersList = ordersDAO.queryAllByUid(user.getUid());
        //新建一个map对象用于存储订单,并根据key将最新的订单排在前面
        Map<Timestamp, List<MyOrders>> myOrdersMap = new TreeMap<>(new Comparator<Timestamp>() {
            @Override
            public int compare(Timestamp o1, Timestamp o2) {
                if (o2.after(o1))
                    return 1;
                else if (o2.before(o1))
                    return -1;
                return 0;
            }
        });
        //遍历数据库查询结果的list集合
        for (MyOrders myOrders : myOrdersList) {
            //若包含该订单,则加入集合
            Timestamp timestamp = new Timestamp(new Date().getTime());
            int nanos = timestamp.getNanos();
            if (myOrdersMap.containsKey(myOrders.getOrdertime())) {
                myOrdersMap.get(myOrders.getOrdertime()).add(myOrders);
            } else {//若无,则新建一个集合,并存入订单
                List<MyOrders> orderslist = new ArrayList<>();
                orderslist.add(myOrders);
                myOrdersMap.put(myOrders.getOrdertime(), orderslist);
            }
        }
        request.getSession().setAttribute("myOrdersMap", myOrdersMap);
        request.getRequestDispatcher("/order_list.jsp").forward(request, response);
    }

    public String formatString(String text) {
        if (text == null) {
            return "";
        }
        return text;
    }
}
