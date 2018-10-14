package com.acidz.filter;

import com.acidz.utils.MyRequest;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * Created by Acidz on 2018/9/28.
 */
public class EncodeFilter implements Filter {
    private String encoding;
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        //post方式编码修正
        req.setCharacterEncoding(encoding);
        resp.setCharacterEncoding(encoding);
        //get方式编码修正
        HttpServletRequest httpServletRequest = (HttpServletRequest) req;
        MyRequest myRequest = new MyRequest(httpServletRequest);
        chain.doFilter(myRequest, resp);
    }

    public void init(FilterConfig config) throws ServletException {
        encoding = config.getInitParameter("encoding");
    }

}
