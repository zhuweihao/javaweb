package com.zhuweihao.servlets;

import com.zhuweihao.dao.impl.FruitDAOImpl;
import com.zhuweihao.pojo.Fruit;
import com.zhuweihao.utils.JDBCUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

/**
 * @Author zhuweihao
 * @Date 2023/2/17 10:47
 * @Description com.zhuweihao.servlets
 */
@WebServlet("/search.do")
public class SearchServlet extends ViewBaseServlet {
    private FruitDAOImpl fruitDAO = new FruitDAOImpl();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        String keyword = req.getParameter("keyword");
        List<Fruit> fruitListByFname = fruitDAO.getFruitListByFname(JDBCUtils.getConnection(), keyword);
        HttpSession session = req.getSession();
        session.setAttribute("fruitList",fruitListByFname);

        super.processTemplate("search",req,resp);
    }
}
