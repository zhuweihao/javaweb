package com.zhuweihao.servlets;

import com.zhuweihao.dao.impl.FruitDAOImpl;
import com.zhuweihao.pojo.Fruit;
import com.zhuweihao.utils.JDBCUtils;
import com.zhuweihao.utils.StringUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Connection;

/**
 * @Author zhuweihao
 * @Date 2023/2/16 16:06
 * @Description com.zhuweihao.servlets
 */
@WebServlet("/edit.do")
public class EditServlet extends ViewBaseServlet{
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String fidStr = req.getParameter("fid");
        if (StringUtil.idNotEmpty(fidStr)){
            int fid = Integer.parseInt(fidStr);
            Connection connection = JDBCUtils.getConnection();
            FruitDAOImpl fruitDAO = new FruitDAOImpl();
            Fruit fruitById = fruitDAO.getFruitById(connection, fid);
            HttpSession session = req.getSession();
            session.setAttribute("fruit",fruitById);
            super.processTemplate("edit",req,resp);
        }
    }
}
