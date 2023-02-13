package com.zhuweihao.dao;

import com.zhuweihao.pojo.Fruit;

import java.sql.Connection;
import java.util.List;

/**
 * @Author zhuweihao
 * @Date 2023/2/13 16:57
 * @Description com.zhuweihao.dao
 * 此接口用于规范针对Fruit表的常用操作
 */
public interface FruitDAO {
    /**
     * 将fruit对象插入到数据库中
     * @param connection
     * @param fruit
     */
    void insert(Connection connection, Fruit fruit);

    /**
     * 针对指定的id,删除表中的一条记录
     * @param connection
     * @param id
     */
    void deleteById(Connection connection,int id);

    /**
     * 针对内存中的fruit对象，修改数据表中指定的记录
     * @param connection
     * @param fruit
     */
    void updataById(Connection connection,Fruit fruit);

    /**
     * 针对指定的id查询对象的fruit对象
     * @param connection
     * @param id
     */
    Fruit getFruitById(Connection connection,int id);

    /**
     * 查询表中所有的记录构成的集合
     * @param connection
     * @return
     */
    List<Fruit> getAll(Connection connection);

    /**
     * 返回数据表中数据的条数
     * @param connection
     * @return
     */
    Long getCount(Connection connection);


}
