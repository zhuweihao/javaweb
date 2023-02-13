package com.zhuweihao.dao.impl;

import com.zhuweihao.dao.FruitDAO;
import com.zhuweihao.dao.base.BaseDAO;
import com.zhuweihao.pojo.Fruit;

import java.sql.Connection;
import java.util.List;

/**
 * @Author zhuweihao
 * @Date 2023/2/13 16:58
 * @Description com.zhuweihao.dao.impl
 */
public class FruitDAOImpl extends BaseDAO implements FruitDAO {
    @Override
    public void insert(Connection connection, Fruit fruit) {
        String sql="insert into Fruit(fname,price,fcount,remark)values(?,?,?,?)";
        update(connection,sql,fruit.getFname(),fruit.getPrice(),fruit.getFcount(),fruit.getRemark());
    }

    @Override
    public void deleteById(Connection connection, int id) {

    }

    @Override
    public void updataById(Connection connection, Fruit fruit) {

    }

    @Override
    public Fruit getFruitById(Connection connection, int id) {
        return null;
    }

    @Override
    public List<Fruit> getAll(Connection connection) {
        return null;
    }

    @Override
    public Long getCount(Connection connection) {
        return null;
    }
}
