package com.acidz.filter; /**
 * Created by Acidz on 2018/9/28.
 */

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;

@WebListener()
public class OnlineListener implements ServletContextListener,
        HttpSessionListener, HttpSessionAttributeListener {

    // Public constructor is required by servlet spec
    public OnlineListener() {
    }

    // -------------------------------------------------------
    // ServletContextListener implementation
    // -------------------------------------------------------
    public void contextInitialized(ServletContextEvent sce) {
      /* This method is called when the servlet context is
         initialized(when the Web application is deployed). 
         You can initialize servlet context related data here.
      */
      //在线人数统计,设置初值0
        sce.getServletContext().setAttribute("online",0);
        //历史访问量,从本地读取初值
        try {
            FileReader fileReader = new FileReader("D://history.txt");
            BufferedReader br = new BufferedReader(fileReader);
            String line = br.readLine();
            if (!(line != null && line.length() > 0)) {
                line = String.valueOf(0);
            }
            int history = Integer.parseInt(line);
            sce.getServletContext().setAttribute("history",history);
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void contextDestroyed(ServletContextEvent sce) {
      /* This method is invoked when the Servlet Context 
         (the Web application) is undeployed or 
         Application Server shuts down.
      */
      //在线人数统计,清空统计量
        sce.getServletContext().removeAttribute("online");
        //历史访问量,持久化访问量
        try {
            FileWriter fileWriter = new FileWriter("D://history.txt");
            Object history = sce.getServletContext().getAttribute("history");
            fileWriter.write(history.toString());
            fileWriter.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        sce.getServletContext().removeAttribute("history");
    }

    // -------------------------------------------------------
    // HttpSessionListener implementation
    // -------------------------------------------------------
    public void sessionCreated(HttpSessionEvent se) {
      /* Session is created. */
      //在线人数统计,增加人数
        Integer online = (Integer) se.getSession().getServletContext().getAttribute("online");
        se.getSession().getServletContext().setAttribute("online",++online);
        //历史访问量,增加访问量
        Integer history = (Integer)se.getSession().getServletContext().getAttribute("history");
        se.getSession().getServletContext().setAttribute("history",++history);
    }

    public void sessionDestroyed(HttpSessionEvent se) {
      /* Session is destroyed. */
      //在线人数统计,减少人数
        Integer online = (Integer) se.getSession().getServletContext().getAttribute("online");
        se.getSession().getServletContext().setAttribute("online",--online);
    }

    // -------------------------------------------------------
    // HttpSessionAttributeListener implementation
    // -------------------------------------------------------

    public void attributeAdded(HttpSessionBindingEvent sbe) {
      /* This method is called when an attribute 
         is added to a session.
      */
    }

    public void attributeRemoved(HttpSessionBindingEvent sbe) {
      /* This method is called when an attribute
         is removed from a session.
      */
    }

    public void attributeReplaced(HttpSessionBindingEvent sbe) {
      /* This method is invoked when an attibute
         is replaced in a session.
      */
    }
}
