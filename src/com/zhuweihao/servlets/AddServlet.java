package com.zhuweihao.servlets;

import com.zhuweihao.dao.impl.FruitDAOImpl;
import com.zhuweihao.pojo.Fruit;
import com.zhuweihao.utils.JDBCUtils;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.IOException;
import java.sql.Connection;

/**
 * @Author zhuweihao
 * @Date 2023/2/13 16:12
 * @Description com.zhuweihao.servlets
 * 1.获取用户（客户端）发过来的数据
 * 2.调用DAO中的方法完成添加功能
 * 3.在控制台打印添加成功
 */
public class AddServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        //get方式目前不需要设置编码（基于tomcat8）,tomcat8之前get请求的中文数据，需要如下转码方式
//        String fname = req.getParameter("fname");
//        //1.将字符串打散成字节数据
//        byte[] bytes = fname.getBytes("ISO-8859-1");
//        fname = new String(bytes, "UTF-8");

        //post方式下，需要设置编码，防止中文乱码
        req.setCharacterEncoding("UTF-8");

        String fname = req.getParameter("fname");
        int price = Integer.parseInt(req.getParameter("price"));
        int fcount = Integer.parseInt(req.getParameter("fcount"));
        String remark = req.getParameter("remark");

        System.out.println("fname = " + fname);
        System.out.println("price = " + price);
        System.out.println("fcount = " + fcount);
        System.out.println("remark = " + remark);

        Connection connection = JDBCUtils.getConnection();
        FruitDAOImpl fruitDAO = new FruitDAOImpl();
        fruitDAO.insert(connection, new Fruit(0, fname, price, fcount, remark));
    }

    @Override
    public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException {
        System.out.println("正在服务");
    }

    @Override
    public void destroy() {
        System.out.println("正在销毁");
    }

    @Override
    public void init() throws ServletException {
        System.out.println("正在初始化");
    }
}
