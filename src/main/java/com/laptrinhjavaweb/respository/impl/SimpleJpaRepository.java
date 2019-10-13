package com.laptrinhjavaweb.respository.impl;

import com.laptrinhjavaweb.annotation.Entity;
import com.laptrinhjavaweb.annotation.Table;
import com.laptrinhjavaweb.mapper.ResultSetMapper;
import com.laptrinhjavaweb.paging.Pageable;
import com.laptrinhjavaweb.respository.JpaRepository;
import com.sun.org.apache.bcel.internal.generic.IF_ACMPEQ;
import org.apache.commons.lang.StringUtils;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SimpleJpaRepository<T> implements JpaRepository<T> {// ngoài T ta có thể thêm nhiều tham số khác nhau và nó chính là 1 object

    private Class<T> zClass;// định nghĩa 1 class vì chính T là 1 class
    // chúng ta có SimpleJpaRespontory là parent của Ibuilding và IUserrespontory
    // nhiệm vụ là lấy T và biến T thành class, từ T sẽ lấy ra dc Building or Userentity tại vì
    //Để truy cập mảng trong SimpleJpaRespontory<T> thì ta có đoạn code sau, tức T là phần tử thứ 0@SuppressWarnings("unchecked")
    public SimpleJpaRepository() {
        Type type = getClass().getGenericSuperclass();
        ParameterizedType parameterizedType =(ParameterizedType) type;
        zClass = (Class<T>) parameterizedType.getActualTypeArguments()[0];// truy cập vào để lấy T và trả về cho ta zclass
        // vì chúng ta đang cần vào Buidingentity để lấy table có tên name = building
    }
    @Override
    public List<T> findAll(Map<String,Object> properties, Pageable pageable, Object... where) {// properties chứa tham số
        String tableName ="";
        // kiểm tra trong annotation có table ko và tar3 về table name
        if (zClass.isAnnotationPresent(Entity.class) && zClass.isAnnotationPresent(Table.class)){
            Table tableClass = zClass.getAnnotation(Table.class);
            tableName = tableClass.name();
        }
        StringBuilder sql = new StringBuilder("select * from " +tableName+ " A");
        sql.append(" WHERE 1=1");
        sql = createSQLfindAll(sql, properties);
        if (where !=null && where.length ==1 ){
            sql.append(where[0]);
        }
        sql.append(" limit "+pageable.getOffset()+", "+pageable.getLimit()+"");
        ResultSetMapper<T> resultSetMapper = new ResultSetMapper<>();
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            connection = EntityManagerFactory.getConnection();
           // statement = connection.prepareStatement(sql.toString());// đoạn này chỉ mới new 1 đối tượng
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql.toString());// thực thi đối tượng
            return resultSetMapper.mapRow(resultSet,this.zClass);
        } catch (SQLException e) {
            return new ArrayList<>();
        } finally {
            try {
                if (connection !=null){
                    connection.close();
                }
                if (statement !=null){
                    statement.close();
                }
                if (resultSet !=null){
                    resultSet.close();
                }
            }catch (SQLException e){
                return new ArrayList<>();
            }
        }
    }

    protected StringBuilder createSQLfindAll(StringBuilder where, Map<String,Object> params) {
        if (params !=null && params.size() > 0){
            String[] keys = new String[params.size()]; // mảng chứa các key cũng chính là size của mảng
            Object[] values = new Object[params.size()];// mảng chứa các value
            int i =0; // khi put vào mảng phải xác định index
            for (Map.Entry<String,Object> item : params.entrySet()) {
                keys[i] = item.getKey();
                values[i] = item.getValue();
                i++;
            }
            for (int i1=0; i1<keys.length;i1++){
                if ((values[i1] instanceof String) && (StringUtils.isNotBlank(values[i1].toString()))){
                    where.append(" AND LOWER(A."+keys[i1]+") LIKE '%"+values[i1].toString()+"%' ");
                }else if ((values[i1] instanceof Integer) && (values[i1] !=null)){
                    where.append(" AND LOWER(A."+keys[i1]+") = "+values[i1]+" ");
                }else if ((values[i1] instanceof Long) && (values[i1] !=null)){
                    where.append(" AND LOWER(A."+keys[i1]+") = "+values[i1]+" ");
                }
            }
        }
        return where;
    }

    @Override
    public List<T> findAll(Map<String, Object> properties, Object... where) { // properties để chứa tham số search theo kiểu chung
        //Object... where chứa dc nhiều rất tham số dc gọi là parameter list
        String tableName ="";
        // kiểm tra trong annotation có table ko và tar3 về table name
        if (zClass.isAnnotationPresent(Entity.class) && zClass.isAnnotationPresent(Table.class)){
            Table tableClass = zClass.getAnnotation(Table.class);
            tableName = tableClass.name();
        }
        StringBuilder sql = new StringBuilder("select * from " +tableName+ " A  where 1=1 ");
        sql = createSQLfindAll(sql, properties);
        if (where !=null && where.length>0 ){
            sql.append(where[0]);
        }
        ResultSetMapper<T> resultSetMapper = new ResultSetMapper<>();
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            connection = EntityManagerFactory.getConnection();
            // statement = connection.prepareStatement(sql.toString());// đoạn này chỉ mới new 1 đối tượng
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql.toString());// thực thi đối tượng
            return resultSetMapper.mapRow(resultSet,this.zClass);
        } catch (SQLException e) {
            return new ArrayList<>();
        } finally {
            try {
                if (connection !=null){
                    connection.close();
                }
                if (statement !=null){
                    statement.close();
                }
                if (resultSet !=null){
                    resultSet.close();
                }
            }catch (SQLException e){
                return new ArrayList<>();
            }
        }
    }

    @Override
    public List<T> findAll(String sqlSearch, Pageable pageable, Object... objects) {
        StringBuilder sql = new StringBuilder(sqlSearch);
        sql.append(" limit "+pageable.getOffset()+", "+pageable.getLimit()+"");
        ResultSetMapper<T> resultSetMapper = new ResultSetMapper<>();
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            connection = EntityManagerFactory.getConnection();
            // statement = connection.prepareStatement(sql.toString());// đoạn này chỉ mới new 1 đối tượng
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sql.toString());// thực thi đối tượng
            return resultSetMapper.mapRow(resultSet,this.zClass);
        } catch (SQLException e) {
            return new ArrayList<>();
        } finally {
            try {
                if (connection !=null){
                    connection.close();
                }
                if (statement !=null){
                    statement.close();
                }
                if (resultSet !=null){
                    resultSet.close();
                }
            }catch (SQLException e){
                return new ArrayList<>();
            }
        }
    }
}
