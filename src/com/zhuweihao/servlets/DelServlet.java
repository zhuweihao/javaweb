package com.zhuweihao.servlets;

import com.zhuweihao.dao.impl.FruitDAOImpl;
import com.zhuweihao.utils.JDBCUtils;
import com.zhuweihao.utils.StringUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author zhuweihao
 * @Date 2023/2/16 17:07
 * @Description com.zhuweihao.servlets
 */
@WebServlet("/del.do")
public class DelServlet extends ViewBaseServlet {
    private FruitDAOImpl fruitDAO = new FruitDAOImpl();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String fidstr = req.getParameter("fid");
        if (StringUtil.idNotEmpty(fidstr)) {
            int fid = Integer.parseInt(fidstr);
            fruitDAO.deleteById(JDBCUtils.getConnection(),fid);
            resp.sendRedirect("index");
        }
    }
}
