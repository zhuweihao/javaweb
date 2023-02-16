package com.zhuweihao.servlets;

import com.zhuweihao.dao.impl.FruitDAOImpl;
import com.zhuweihao.pojo.Fruit;
import com.zhuweihao.utils.JDBCUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author zhuweihao
 * @Date 2023/2/16 16:36
 * @Description com.zhuweihao.servlets
 */
@WebServlet("/update.do")
public class UpdateServlet extends ViewBaseServlet{

    private FruitDAOImpl fruitDAO=new FruitDAOImpl();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        req.setCharacterEncoding("utf-8");
        String fname = req.getParameter("fname");
        int price = Integer.parseInt(req.getParameter("price"));
        String remark = req.getParameter("remark");
        int fcount = Integer.parseInt(req.getParameter("fcount"));
        int fid = Integer.parseInt(req.getParameter("fid"));

        fruitDAO.updataById(JDBCUtils.getConnection(),new Fruit(fid,fname,price,fcount,remark));

        //重定向，获取最新的库存信息
        resp.sendRedirect("index");
    }
}
