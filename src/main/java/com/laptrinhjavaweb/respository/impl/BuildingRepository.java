package com.laptrinhjavaweb.respository.impl;

import com.laptrinhjavaweb.builder.BuildingSearchBuilder;
import com.laptrinhjavaweb.entity.BuildingEntity;
import com.laptrinhjavaweb.paging.Pageable;
import com.laptrinhjavaweb.respository.IBuildingRepository;
import org.apache.commons.lang.StringUtils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BuildingRepository extends SimpleJpaRepository<BuildingEntity>
        implements IBuildingRepository {
    @Override
    public List<BuildingEntity> findAll(Map<String, Object> params, Pageable pageable, BuildingSearchBuilder fieldSearch) {
        StringBuilder sqlSearch = new StringBuilder("select * FROM building A");
        if (StringUtils.isNotBlank(fieldSearch.getStaffId())){
            sqlSearch.append(" INNER JOIN assignmentstaff assignmentstaff ON A.id = assignmentstaff.buildingid");
        }
        sqlSearch.append(" WHERE 1=1");
        sqlSearch=(this.createSQLfindAll(sqlSearch,params));
        String sqlSpecial = buildSqlSpecial(fieldSearch);
        sqlSearch.append(sqlSpecial);
        return this.findAll(sqlSearch.toString(),pageable);
    }
    private String buildSqlSpecial(BuildingSearchBuilder fieldSearch){
        StringBuilder result = new StringBuilder("");
        if (StringUtils.isNotBlank(fieldSearch.getCostRentFrom())){
            result.append(" AND A.costrent >= "+fieldSearch.getCostRentFrom()+"");
        }
        if (StringUtils.isNotBlank(fieldSearch.getCostRentTo())){
            result.append(" AND A.costrent <= "+fieldSearch.getCostRentTo()+"");
        }
        if (fieldSearch.getBuildingTypes().length>0){
            result.append(" AND (");
            //java 7
//            int i =0;
//            for (String item : fieldSearch.getBuildingTypes()){
//                if (i ==0 ){
//                    result.append("A.type LIKE '%"+item+"%'");
//                }else {
//                    result.append(" OR A.type LIKE '%"+item+"%'");
//                }
//                i++;
//            }
            // java 8
            result.append("A.type LIKE '%" + fieldSearch.getBuildingTypes()[0]+"%'");
            // dùng arrays stream check i ==0 hoặc i == 1 thì dùng filter trong stream
            Arrays.stream(fieldSearch.getBuildingTypes()).filter(item -> !item.equals(fieldSearch.getBuildingTypes()[0]))
                    .forEach(item -> result.append(" OR A.type LIKE '%"+item+"%'"));
            result.append(")");
        }
        if (StringUtils.isNotBlank(fieldSearch.getAreaRentFrom())|| StringUtils.isNotBlank(fieldSearch.getAreaRentTo())){
            result.append(" AND EXISTS (SELECT * FROM rentarea ra WHERE (ra.buildingid = A.id)");
            if (fieldSearch.getAreaRentFrom() !=null){
                result.append("AND ra.value >= "+fieldSearch.getAreaRentFrom()+"");
            }
            if (fieldSearch.getAreaRentTo() !=null){
                result.append("AND ra.value <= "+fieldSearch.getAreaRentTo()+"");
            }
        }
        if (StringUtils.isNotBlank(fieldSearch.getStaffId())){
            result.append(" AND assignmentstaff.staffid = "+fieldSearch.getStaffId()+"");
        }
        return result.toString();
    }
    // các câu lệnh liên quan đến sql thì nên bỏ vào repository
   /* @Override
    public List<BuildingEntity> findAll(Map<String, Object> params, Pageable pageable) {// Object ở đây có nhiều kiểu dữ liệu # nên dùng Object
      // phần controller put như nào thì trong đây phải get ra như thế vd numberOfBasement = numberOfBasement
//       String name = (String) params.get("name");
//       String district = (String) params.get("district");
//        StringBuilder where = new StringBuilder(" ");
    /* Student code
       StringBuilder where = new StringBuilder(" ");
       if (StringUtils.isNotBlank(name)){
            where.append("AND A.name like '%"+name+"%' ");
       }
        if (StringUtils.isNotBlank(district)){
            where.append("AND A.district LIKE '%"+district+"%' ");
        }
        if (params.get("buildingArea") !=null){
            Integer buildingArea = (Integer) params.get("buildingArea");
            where.append("and A.buildingarea = "+buildingArea+" ");
        }
        if (params.get("numberOfBasement") !=null){
            Integer numberOfBasement = (Integer) params.get("numberOfBasement");
            where.append("and A.numberOfBasement = "+numberOfBasement+" ");
        }
        */

      //  return this.findAll(params,pageable);

  //  }
}
