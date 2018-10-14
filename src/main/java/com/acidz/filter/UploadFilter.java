package com.acidz.filter;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.IOUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Acidz on 2018/10/8.
 */

public class UploadFilter implements Filter {
    private ServletContext servletContext;

    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest)req;
        // Check that we have a file upload request
        boolean isMultipart = ServletFileUpload.isMultipartContent(request);
        if (isMultipart) {
            // Create a factory for disk-based file items
            DiskFileItemFactory factory = new DiskFileItemFactory();
            // Configure a repository (to ensure a secure temp location is used)
            File repository = (File) servletContext.getAttribute("javax.servlet.context.tempdir");
            factory.setRepository(repository);
            // Create a new file upload handler
            ServletFileUpload upload = new ServletFileUpload(factory);
            // Parse the request
            List<FileItem> items = null;
            try {
                items = upload.parseRequest(request);
            } catch (FileUploadException e) {
                e.printStackTrace();
            }
            //处理每个FileItem,并放入一个map
            Map<String, String> beanMap = new HashMap<>();
            for (FileItem fileItem : items) {
                if (fileItem.isFormField()) {//普通文本域对象
                    String fieldName = fileItem.getFieldName();
                    String value = fileItem.getString("UTF-8");
                    if ("method".equals(fieldName))
                        request.setAttribute(fieldName, value);
                    else
                        beanMap.put(fieldName, value);
                } else {//文件上传
                    String fileName = fileItem.getName();
                    String filePath = servletContext.getRealPath("/products/1/" + fileName);
                    OutputStream outputStream = new FileOutputStream(filePath);
                    IOUtils.copy(fileItem.getInputStream(), outputStream);
                    //删除临时目录的文件缓存
                    fileItem.delete();
                    //图片路径存入数据库
                    beanMap.put(fileItem.getFieldName(), "products/1/" + fileName);
                }
            }
            //将beanMap放入request
            request.setAttribute("beanMap",beanMap);
        }
        chain.doFilter(request, resp);
    }

    public void init(FilterConfig config) throws ServletException {
        this.servletContext = config.getServletContext();
    }

}
