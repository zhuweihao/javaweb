package com.zhuweihao.servlets;

import com.zhuweihao.dao.impl.FruitDAOImpl;
import com.zhuweihao.pojo.Fruit;
import com.zhuweihao.utils.JDBCUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Connection;
import java.util.List;

/**
 * @Author zhuweihao
 * @Date 2023/2/14 15:56
 * @Description com.zhuweihao.servlets
 */
//Servlet从3.0版本开始支持注解方式的注册
@WebServlet("/index")
public class IndexServlet extends ViewBaseServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("成功进入");
        Connection connection = JDBCUtils.getConnection();
        FruitDAOImpl fruitDAO = new FruitDAOImpl();
        List<Fruit> fruitList = fruitDAO.getAll(connection);
        //保存到session作用域
        HttpSession session = req.getSession();
        session.setAttribute("fruitList",fruitList);

        super.processTemplate("index",req,resp);
    }
}
