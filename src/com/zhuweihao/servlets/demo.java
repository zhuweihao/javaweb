package com.zhuweihao.servlets;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author zhuweihao
 * @Date 2023/2/14 14:59
 * @Description com.zhuweihao.servlets
 */
public class demo extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        System.out.println("demo----------");
        //服务器内部转发
        req.getRequestDispatcher("add").forward(req,resp);

        //客户端重定向
        //resp.sendRedirect("add");
    }
}
