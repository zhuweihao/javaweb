package com.zhuweihao.servlets;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author zhuweihao
 * @Date 2023/2/14 14:59
 * @Description com.zhuweihao.servlets
 */
//@WebServlet(urlPatterns = {"/demo"},
//        initParams = {
//                @WebInitParam(name = "hello", value = "world"),
//                @WebInitParam(name = "test", value = "example")
//        })
public class demo extends HttpServlet {

    @Override
    public void init() throws ServletException {
        ServletConfig servletConfig = getServletConfig();
        String hello = servletConfig.getInitParameter("hello");
        System.out.println("hello = " + hello);

        ServletContext servletContext = getServletContext();
        String contextConfigLocation = servletContext.getInitParameter("contextConfigLocation");
        System.out.println("contextConfigLocation = " + contextConfigLocation);
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        System.out.println("demo----------");
        //服务器内部转发
        req.getRequestDispatcher("add").forward(req, resp);

        //客户端重定向
        //resp.sendRedirect("add");
    }
}
