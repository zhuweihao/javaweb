package com.zhuweihao.service.impl;

import com.zhuweihao.dao.FruitDAO;
import com.zhuweihao.dao.impl.FruitDAOImpl;
import com.zhuweihao.pojo.Fruit;
import com.zhuweihao.service.FruitService;
import com.zhuweihao.utils.JDBCUtils;

import java.sql.Connection;
import java.util.List;

/**
 * @Author zhuweihao
 * @Date 2023/2/19 16:48
 * @Description com.zhuweihao.service.impl
 */
public class FruitServiceImpl implements FruitService {
    private FruitDAO fruitDAO=new FruitDAOImpl();

    private Connection connection= JDBCUtils.getConnection();

    @Override
    public List<Fruit> getFruitList(Integer page) {
        return fruitDAO.getFruitList(connection,page);
    }

    @Override
    public void insert(Fruit fruit) {
        fruitDAO.insert(connection,fruit);
    }

    @Override
    public Fruit getFruitByFid(Integer fid) {
        return fruitDAO.getFruitById(connection,fid);
    }

    @Override
    public void delFruit(Integer fid) {
        fruitDAO.deleteById(connection,fid);
    }

    @Override
    public Integer getPageCount() {
        Long count = fruitDAO.getCount(connection);
        Long l = (count + 5 - 1) / 5;
        return l.intValue();
    }

    @Override
    public void updateFruit(Fruit fruit) {
        fruitDAO.updataById(connection,fruit);
    }

    @Override
    public List<Fruit> getFruitListByFname(String fname) {
        return fruitDAO.getFruitListByFname(connection,fname);
    }
}
