package com.zhuweihao.service;

import com.zhuweihao.pojo.Fruit;

import java.util.List;

/**
 * @Author zhuweihao
 * @Date 2023/2/19 16:48
 * @Description com.zhuweihao.service
 */
public interface FruitService {
    //获取指定页面的库存列表信息
    List<Fruit> getFruitList(Integer page);
    //添加库存记录信息
    void insert(Fruit fruit);
    //根据id查看指定库存记录
    Fruit getFruitByFid(Integer fid);
    //删除特定库存记录
    void delFruit(Integer fid);
    //获取总页数
    Integer getPageCount();
    //修改特定库存记录
    void updateFruit(Fruit fruit);
    //获取搜索记录
    List<Fruit> getFruitListByFname(String fname);
}
