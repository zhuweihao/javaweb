package com.zhuweihao.controllers;

import com.zhuweihao.dao.impl.FruitDAOImpl;
import com.zhuweihao.pojo.Fruit;
import com.zhuweihao.utils.JDBCUtils;
import com.zhuweihao.utils.StringUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.sql.Connection;
import java.util.List;

/**
 * @Author zhuweihao
 * @Date 2023/2/17 16:19
 * @Description com.zhuweihao.controllers
 */
public class FruitController {
    private FruitDAOImpl fruitDAO = new FruitDAOImpl();


    private String index(HttpServletRequest req) {
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
        return "index";
    }

    private String add(HttpServletRequest req) {
        String fname = req.getParameter("fname");
        int price = Integer.parseInt(req.getParameter("price"));
        int fcount = Integer.parseInt(req.getParameter("fcount"));
        String remark = req.getParameter("remark");

        Connection connection = JDBCUtils.getConnection();
        FruitDAOImpl fruitDAO = new FruitDAOImpl();
        fruitDAO.insert(connection, new Fruit(0, fname, price, fcount, remark));

        return "redirect:fruit.do";
    }

    private String del(HttpServletRequest req) {
        String fidstr = req.getParameter("fid");
        if (StringUtil.idNotEmpty(fidstr)) {
            int fid = Integer.parseInt(fidstr);
            fruitDAO.deleteById(JDBCUtils.getConnection(), fid);
            return "redirect:fruit.do";
        }
        return "error";
    }

    private String update(HttpServletRequest req) {
        String fname = req.getParameter("fname");
        int price = Integer.parseInt(req.getParameter("price"));
        String remark = req.getParameter("remark");
        int fcount = Integer.parseInt(req.getParameter("fcount"));
        int fid = Integer.parseInt(req.getParameter("fid"));
        fruitDAO.updataById(JDBCUtils.getConnection(), new Fruit(fid, fname, price, fcount, remark));
        //重定向，获取最新的库存信息
        return "redirect:fruit.do";
    }

    private String search(HttpServletRequest req) {
        String keyword = req.getParameter("keyword");
        List<Fruit> fruitListByFname = fruitDAO.getFruitListByFname(JDBCUtils.getConnection(), keyword);
        HttpSession session = req.getSession();
        session.setAttribute("fruitList", fruitListByFname);

        return "search";
    }

    private String edit(HttpServletRequest req) {
        String fidStr = req.getParameter("fid");
        if (StringUtil.idNotEmpty(fidStr)) {
            int fid = Integer.parseInt(fidStr);
            Connection connection = JDBCUtils.getConnection();
            FruitDAOImpl fruitDAO = new FruitDAOImpl();
            Fruit fruitById = fruitDAO.getFruitById(connection, fid);
            HttpSession session = req.getSession();
            session.setAttribute("fruit", fruitById);
            return "edit";
        }
        return "error";
    }

}
