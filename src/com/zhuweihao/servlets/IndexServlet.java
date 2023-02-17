package com.zhuweihao.servlets;

import com.zhuweihao.dao.impl.FruitDAOImpl;
import com.zhuweihao.pojo.Fruit;
import com.zhuweihao.utils.JDBCUtils;
import com.zhuweihao.utils.StringUtil;

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

    private FruitDAOImpl fruitDAO = new FruitDAOImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //默认当前为第一页
        Integer page = 1;
        //获取当前页
        String pageStr = req.getParameter("page");
        if (StringUtil.idNotEmpty(pageStr)) {
            page = Integer.parseInt(pageStr);
        }
        HttpSession session = req.getSession();
        Connection connection = JDBCUtils.getConnection();
        //获取总页数
        Long fruitCount = fruitDAO.getCount(connection);
        Long pageCount = (fruitCount + 5 - 1) / 5;
        session.setAttribute("pageCount", pageCount);

        //判断翻页逻辑
        if (page <= 1) {
            page = 1;
            List<Fruit> fruitList = fruitDAO.getFruitList(connection, page);
            //保存到session作用域
            session.setAttribute("fruitList", fruitList);
            session.setAttribute("page", page);
        } else if (page <= pageCount) {
            List<Fruit> fruitList = fruitDAO.getFruitList(connection, page);
            //保存到session作用域
            session.setAttribute("fruitList", fruitList);
            session.setAttribute("page", page);
        } else {
            page = pageCount.intValue();
            List<Fruit> fruitList = fruitDAO.getFruitList(connection, page);
            //保存到session作用域
            session.setAttribute("fruitList", fruitList);
            session.setAttribute("page", page);
        }
        super.processTemplate("index", req, resp);
    }
}
