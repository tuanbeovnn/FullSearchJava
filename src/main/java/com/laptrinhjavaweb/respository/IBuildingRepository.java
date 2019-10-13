package com.laptrinhjavaweb.respository;

import com.laptrinhjavaweb.builder.BuildingSearchBuilder;
import com.laptrinhjavaweb.entity.BuildingEntity;
import com.laptrinhjavaweb.paging.Pageable;

import java.util.List;
import java.util.Map;

public interface IBuildingRepository extends JpaRepository<BuildingEntity> {// dữ liệu muốn trả về là buidlingdto nên ta truyền buildingdto vào.
    // lấy toàn bộ danh sách
//    List<BuildingEntity> findAll(int offset, int limit);
    List<BuildingEntity> findAll(Map<String, Object> params, Pageable pageable, BuildingSearchBuilder fieldSearch);// find all dùng ở đây là ko muốn dùng của thằng cha
}
