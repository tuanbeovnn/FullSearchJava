package com.laptrinhjavaweb.mapper;

import com.laptrinhjavaweb.annotation.Column;
import com.laptrinhjavaweb.annotation.Entity;
import com.sun.xml.internal.ws.org.objectweb.asm.ClassAdapter;
import org.apache.commons.beanutils.BeanUtils;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.List;

public class ResultSetMapper<T> {
        // matching của field nên dc đặt tên là resulsetMapper
    public List<T> mapRow(ResultSet rs, Class<T> zClass){// vì resultset sẽ vào từng row để lấy dữ liệu nên đặt là mapRow
        // Cleancode : tham số tối đa chỉ có 3 tham số.
        //zClass chính là đối tượng class Buildingentity or là Userentity
        List<T> results = new ArrayList<>();
        try{
            if (zClass.isAnnotationPresent(Entity.class)){// kiểm tra annotation nếu là @entity
                ResultSetMetaData resultSetMetaData = rs.getMetaData();// đây là sau khi chạy results xong thì nó vào từng column để lấy giá trị ra
                //If you have to get metadata of a table like total number of column, column name, column type etc. ,
                // ResultSetMetaData interface is useful because it provides methods to get metadata from the ResultSet object.
                Field[] fields = zClass.getDeclaredFields();// khi có đối tượng class[building or userenity] để lấy dc thông tin field thì ta có method FIELDdeclare
                while (rs.next()){// đi vào resulset để duyệt từng row
                    T object = zClass.newInstance();// để trong while vì khi trả về sẽ trả về 1 list chứ nếu để ngoài sẽ trả về 1 phần tử cuối
                    for (int i =0;i<resultSetMetaData.getColumnCount();i++){// bây giờ vào column để lấy data ra
                        String columnName = resultSetMetaData.getColumnName(i+1); // lấy tên từng field của bảng

                        // Value có nhiều kiểu dữ liệu khác nhau nên dùng Object, cái chúng ta trả về chỉ là Object
                        Object columnValue = rs.getObject(i+1);
                        ColumnModel columnModel = new ColumnModel();
                        columnModel.setColumnName(columnName);
                        columnModel.setColumnValue(columnValue);
                        for (Field field: fields){
                            // khúc này là biết dc cái column nào giống vs database.
                            if (field.isAnnotationPresent(Column.class)){// kiểm tra annotation
                                Column column = field.getAnnotation(Column.class);
                                if (column.name().equals(columnName) && columnValue!=null){ // so sánh nếu nó bằng columnName
                                    // convert data
                                    // để convert data dc thì add vào file pom thư viện beanutils
                                    BeanUtils.setProperty(object,field.getName(), columnValue);// object chính là T ta truyền vào, và getname là cái tên field private String name,user...
                                    break;
                                }
                            }
                        }
                        convertResultSetToEntity(fields, columnModel, object);// để truyền thì ta sẻ truyền trong này
                        Class<?> parentClass = zClass.getSuperclass();
                        // check parentClass cha còn có class cha nào nữa ko nếu = null là ko còn
                        while(parentClass !=null){
                            Field[] fieldParents = parentClass.getDeclaredFields();
                            convertResultSetToEntity(fieldParents, columnModel, object);
                            parentClass = parentClass.getSuperclass();// kiểm tra ko còn cha nào nữa mới dừng lại
                        }
                    }
                    results.add(object);
                }
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return results;
    }
    // sau này sẽ có nhiều tham số nên thế là dùng kĩ thuật parameters list Object....object
    private void convertResultSetToEntity(Field[] fields, ColumnModel columnModel, Object... objects){
        T object = (T) objects[0];// quy định phần tử đầu tiên là T
        try{
            for (Field field: fields){
                Column column = field.getAnnotation(Column.class);
                if (column.name().equals(columnModel.getColumnName()) && columnModel.getColumnValue()  !=null){
                    BeanUtils.setProperty(object,field.getName(), columnModel.getColumnValue());
                    break;
                }
            }
        }catch (InvocationTargetException | IllegalAccessException e){
            System.out.println(e.getMessage());
        }

    }
    // khai báo 1 inner class, vì columnModel chỉ hoạt động trong phạm vi Resultset nên ta khai báo bên trong class resultset
    static class ColumnModel{
        private String columnName;

        public String getColumnName() {
            return columnName;
        }

        public void setColumnName(String columnName) {
            this.columnName = columnName;
        }

        public Object getColumnValue() {
            return columnValue;
        }

        public void setColumnValue(Object columnValue) {
            this.columnValue = columnValue;
        }

        private Object columnValue;

    }

}
