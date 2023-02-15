package com.zhuweihao.servlets;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * @Author zhuweihao
 * @Date 2023/2/14 13:29
 * @Description com.zhuweihao.servlets
 */
public class SessionServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse resp) throws ServletException, IOException {


        // 1.调用request对象的方法尝试获取HttpSession对象
        HttpSession session = request.getSession();
        // 2.调用HttpSession对象的isNew()方法
        boolean wetherNew = session.isNew();
        // 3.打印HttpSession对象是否为新对象
        System.out.println("wetherNew = " + (wetherNew ? "HttpSession对象是新的" : "HttpSession对象是旧的"));
        // 4.调用HttpSession对象的getId()方法
        String id = session.getId();
        // 5.打印JSESSIONID的值
        System.out.println("JSESSIONID = " + id);

        // 获取默认的最大闲置时间
        int maxInactiveIntervalSecond = session.getMaxInactiveInterval();
        System.out.println("maxInactiveIntervalSecond = " + maxInactiveIntervalSecond);
        // 设置默认的最大闲置时间
        session.setMaxInactiveInterval(15);
    }
}
