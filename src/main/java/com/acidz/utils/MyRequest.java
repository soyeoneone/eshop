package com.acidz.utils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.UnsupportedEncodingException;
import java.util.Map;

/**
 * Created by Acidz on 2018/9/28.
 */
public class MyRequest extends HttpServletRequestWrapper {
    public MyRequest(HttpServletRequest request) {
        super(request);
    }

    @Override
    public String getParameter(String name) {
        String encodename = super.getParameter(name);
        if (encodename != null && "GET".equalsIgnoreCase(super.getMethod())) {
            try {
                encodename = new String(encodename.getBytes("ISO-8859-1"), "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return encodename;
    }

    @Override
    public Map<String, String[]> getParameterMap() {
        boolean flag = false;
        if ("get".equalsIgnoreCase(super.getMethod())) {
            if (!flag) {
                Map<String, String[]> map = super.getParameterMap();
                for (String key : map.keySet()) {
                    String[] values = map.get(key);
                    for (int i=0; i<values.length; i++) {
                        try {
                            values[i] = new String(values[i].getBytes("ISO-8859-1"), "UTF-8");
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }
                    }
                }
                flag = true;
                return map;
            }
        }
        return super.getParameterMap();
    }
}
