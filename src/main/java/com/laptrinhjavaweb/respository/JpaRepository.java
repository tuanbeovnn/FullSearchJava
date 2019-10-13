package com.laptrinhjavaweb.respository;

import com.laptrinhjavaweb.paging.Pageable;

import java.util.List;
import java.util.Map;

public interface JpaRepository<T> { // T là kiểu dữ liệu ko tường minh
        List<T> findAll(Map<String,Object> properties, Pageable pageable, Object... objects);// hàm này sẽ dùng chung cho tất cả
        List<T> findAll(Map<String,Object> properties, Object... objects);
        List<T> findAll(String sql, Pageable pageable, Object... objects);
}
