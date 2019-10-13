package com.laptrinhjavaweb.utils;

import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.InvocationTargetException;

public class FormUtil {
    public static <T> T toModel(Class<T> zClass, HttpServletRequest request) {// để get dc thông tin params ta sử dụng request parameter..
        T object = null;
        try {
            object = zClass.newInstance();
            BeanUtils.populate(object, request.getParameterMap());// pt populate dùng để đổ hết request.getparameter vào object và object chính là T và dựa vào key để map
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            System.out.print(e.getMessage());
        }
        return object;
    }
}
