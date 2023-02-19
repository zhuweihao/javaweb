package com.zhuweihao.controllers;

import com.zhuweihao.dao.impl.FruitDAOImpl;
import com.zhuweihao.pojo.Fruit;
import com.zhuweihao.service.FruitService;
import com.zhuweihao.service.impl.FruitServiceImpl;
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
    private FruitService fruitService=new FruitServiceImpl();


    private String index(HttpServletRequest req,Integer page) {
        //默认当前为第一页
        Integer pageNo = 1;
        //获取当前页
        if (page!=null) {
            pageNo = page;
        }
        HttpSession session = req.getSession();
        Connection connection = JDBCUtils.getConnection();
        //获取总页数
        Integer pageCount = fruitService.getPageCount();
        session.setAttribute("pageCount", pageCount);

        //判断翻页逻辑
        if (pageNo <= 1) {
            pageNo = 1;
            List<Fruit> fruitList = fruitService.getFruitList(pageNo);
            //保存到session作用域
            session.setAttribute("fruitList", fruitList);
            session.setAttribute("page", pageNo);
        } else if (pageNo <= pageCount) {
            List<Fruit> fruitList = fruitService.getFruitList(pageNo);
            //保存到session作用域
            session.setAttribute("fruitList", fruitList);
            session.setAttribute("page", pageNo);
        } else {
            pageNo = pageCount;
            List<Fruit> fruitList = fruitService.getFruitList(pageNo);
            //保存到session作用域
            session.setAttribute("fruitList", fruitList);
            session.setAttribute("page", pageNo);
        }
        return "index";
    }

    private String add(HttpServletRequest req,String fname,Integer price,Integer fcount,String remark) {
        Connection connection = JDBCUtils.getConnection();
        fruitService.insert(new Fruit(0, fname, price, fcount, remark));

        return "redirect:fruit.do";
    }

    private String del(Integer fid) {
        if (fid!=null) {
            fruitService.delFruit(fid);
            return "redirect:fruit.do";
        }
        return "error";
    }

    private String update(Integer fid,String fname,Integer price,Integer fcount,String remark) {
        fruitService.updateFruit(new Fruit(fid, fname, price, fcount, remark));
        //重定向，获取最新的库存信息
        return "redirect:fruit.do";
    }

    private String search(HttpServletRequest req,String keyword) {
        List<Fruit> fruitListByFname = fruitService.getFruitListByFname(keyword);
        HttpSession session = req.getSession();
        session.setAttribute("fruitList", fruitListByFname);

        return "search";
    }

    private String edit(HttpServletRequest req ,Integer fid) {
        if (fid!=null) {
            Connection connection = JDBCUtils.getConnection();
            Fruit fruitById = fruitService.getFruitByFid(fid);
            HttpSession session = req.getSession();
            session.setAttribute("fruit", fruitById);
            return "edit";
        }
        return "error";
    }

}
