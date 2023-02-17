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
        String sql = "insert into Fruit(fname,price,fcount,remark)values(?,?,?,?)";
        update(connection, sql, fruit.getFname(), fruit.getPrice(), fruit.getFcount(), fruit.getRemark());
    }

    @Override
    public void deleteById(Connection connection, int id) {
        String sql = "delete from Fruit where fid = ?";
        super.update(connection, sql, id);
    }

    @Override
    public void updataById(Connection connection, Fruit fruit) {
        String sql = "update Fruit set fname = ? , price = ? , fcount = ? , remark = ? where fid = ?";
        super.update(connection, sql, fruit.getFname(), fruit.getPrice(), fruit.getFcount(), fruit.getRemark(), fruit.getFid());
    }

    @Override
    public Fruit getFruitById(Connection connection, int id) {
        String sql = "select * from Fruit where fid = ?";
        return getInstance(connection, Fruit.class, sql, id);
    }

    @Override
    public List<Fruit> getAll(Connection connection) {
        String sql = "select * from Fruit";
        return getForList(connection, Fruit.class, sql);
    }

    @Override
    public Long getCount(Connection connection) {
        String sql = "select count(*) from Fruit";
        return super.getValue(connection, sql);
    }

    @Override
    public List<Fruit> getFruitList(Connection connection, Integer page) {
        String sql = "SELECT * FROM Fruit LIMIT ? , 5";
        return super.getForList(connection, Fruit.class, sql, (page - 1) * 5);
    }

    @Override
    public List<Fruit> getFruitListByFname(Connection connection, String fname) {
        String sql = "select * from Fruit where fname like ?";
        return super.getForList(connection, Fruit.class, sql, "%" + fname + "%");
    }
}
