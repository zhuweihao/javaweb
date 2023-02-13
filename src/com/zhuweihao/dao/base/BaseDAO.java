package com.zhuweihao.dao.base;

import com.zhuweihao.utils.JDBCUtils;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;

/**
 * @Author zhuweihao
 * @Date 2023/2/13 17:03
 * @Description com.zhuweihao.dao.base
 * 封装针对数据表的通用操作
 */
public class BaseDAO {
    /**
     * 执行 增删改
     *
     * @param conn 连接对象
     * @param sql  sql语句
     * @param args sql语句中占位符长度与可变形参长度相同
     * @return 失败返回0
     */
    public int update(Connection conn, String sql, Object... args) {
        PreparedStatement ps = null;
        try {
            //1.预编译sql语句，返回PreparedStatement的实例
            ps = conn.prepareStatement(sql);
            //2.填充占位符
            for (int i = 0; i < args.length; i++) {
                ps.setObject(i + 1, args[i]);
            }
            //3.执行
            return ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //4.资源的关闭
            JDBCUtils.closeConnection(null, ps);
        }
        return 0;
    }

    public <T> T getInstance(Class<T> clazz, String sql,Object... args){
        Connection conn=null;
        PreparedStatement ps= null;
        ResultSet rs=null;
        try{
            conn=JDBCUtils.getConnection();
            ps=conn.prepareStatement(sql);
            for (int i = 0; i < args.length; i++) {
                ps.setObject(i+1,args[i]);
            }
            rs=ps.executeQuery();
            //获取结果集的元数据：ResultSetMetaData
            ResultSetMetaData rsMetaData = rs.getMetaData();
            //通过ResultSetMetaData获取结果集中的列数
            int columnCount = rsMetaData.getColumnCount();
            if(rs.next()){
                T t = clazz.newInstance();
                //处理结果集一行数据中的每一个列
                for (int i = 0; i < columnCount; i++) {
                    //获取列值
                    Object columnvalue = rs.getObject(i + 1);
                    //获取每个列的列名
                    String columnLabel = rsMetaData.getColumnLabel(i + 1);
                    //给t对象指定的columnName属性，赋值为columnValue，通过反射
                    Field field = clazz.getDeclaredField(columnLabel);
                    field.setAccessible(true);
                    field.set(t,columnvalue);
                }
                return t;
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            JDBCUtils.closeConnection(conn,ps,rs);
        }
        return null;
    }
}
