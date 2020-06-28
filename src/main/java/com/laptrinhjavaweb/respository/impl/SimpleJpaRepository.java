package com.laptrinhjavaweb.respository.impl;

import com.laptrinhjavaweb.annotation.Column;
import com.laptrinhjavaweb.annotation.Entity;
import com.laptrinhjavaweb.annotation.Table;
import com.laptrinhjavaweb.mapper.ResultSetMapper;
import com.laptrinhjavaweb.paging.Pageable;
import com.laptrinhjavaweb.respository.JpaRepository;
import com.sun.org.apache.bcel.internal.generic.IF_ACMPEQ;
import org.apache.commons.lang.StringUtils;

import java.lang.reflect.Field;
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

    @Override
    public Long insert(Object object) {
        String sql = createSQLInsert();
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            Long id = null;
            connection = EntityManagerFactory.getConnection();
            connection.setAutoCommit(false);
            statement = connection.prepareStatement(sql.toString(), Statement.RETURN_GENERATED_KEYS);
            Class<?> aClass = object.getClass();
            Field[] fields = aClass.getDeclaredFields();
            for (int i = 0; i < fields.length; i++) {
                int index = i+1;
                Field field = fields[i];
                field.setAccessible(true);
                statement.setObject(index, field.get(object));// hàm setObject dựa vào kiểu dữ liệu của field để set đúng kiểu
            }
            Class<?> parentClass = aClass.getSuperclass();
            int indexParent = fields.length + 1;
            while (parentClass != null) {
                Field[] fieldsParent = parentClass.getDeclaredFields();
                for (int i = 0; i < fieldsParent.length; i++) {
                    Field field = fieldsParent[i];
                    field.setAccessible(true);
                    statement.setObject(indexParent, field.get(object));// hàm setObject dựa vào kiểu dữ liệu của field để set đúng kiểu
                    indexParent++;
                }
                parentClass = parentClass.getSuperclass();
            }
            statement.executeUpdate();
            resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                id = resultSet.getLong(1);
            }
            connection.commit();
            return id;
        }catch (SQLException | IllegalAccessException e) {
            if (connection !=null){
                try {
                    connection.rollback();
                }catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
                if (statement !=null ) {
                    statement.close();
                }
            }catch (SQLException e2) {
                e2.printStackTrace();
            }
        }
        return null;
    }

    private String createSQLInsert() {
        String tableName = "";
        if (zClass.isAnnotationPresent(Entity.class) && zClass.isAnnotationPresent(Table.class)) {
            Table tableClass = zClass.getAnnotation(Table.class);
            tableName = tableClass.name();
        }
        StringBuilder fields = new StringBuilder("");
        StringBuilder params = new StringBuilder("");
        for (Field field : zClass.getDeclaredFields()) {
            if (fields.length() > 1) {
                fields.append(",");
                params.append(",");
            }
            if (field.isAnnotationPresent(Column.class)) {
                Column column = field.getAnnotation(Column.class);
                fields.append(column.name());
                params.append("?");
            }
        }

        Class<?> parentClass = zClass.getSuperclass();
        while (parentClass != null) {
            for (Field field : parentClass.getDeclaredFields()) {
                if (fields.length() > 1) {
                    fields.append(",");
                    params.append(",");
                }
                if (field.isAnnotationPresent(Column.class)) {
                    Column column = field.getAnnotation(Column.class);
                    fields.append(column.name());
                    params.append("?");
                }
            }
            parentClass = parentClass.getSuperclass();
        }
        String sql = "INSERT INTO "+ tableName + "("+fields.toString()+") VALUES ("+params.toString()+")";
        return sql;
    }
}
