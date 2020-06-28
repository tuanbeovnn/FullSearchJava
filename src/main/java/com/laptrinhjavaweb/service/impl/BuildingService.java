package com.laptrinhjavaweb.service.impl;

import com.laptrinhjavaweb.builder.BuildingSearchBuilder;
import com.laptrinhjavaweb.converter.BuildingConverter;
import com.laptrinhjavaweb.dto.Buildingdto;
import com.laptrinhjavaweb.entity.BuildingEntity;
import com.laptrinhjavaweb.paging.Pageable;
import com.laptrinhjavaweb.respository.IBuildingRepository;
import com.laptrinhjavaweb.respository.impl.BuildingRepository;
import com.laptrinhjavaweb.service.IBuildingService;
import org.apache.commons.lang.StringUtils;

import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

public class BuildingService implements IBuildingService {
    private IBuildingRepository buildingRepository;
    private BuildingConverter buildingConverter;// tạo ra để gọi hàm từ  converter to todto

    public  BuildingService(){
       buildingRepository = new BuildingRepository();
       buildingConverter = new BuildingConverter();
    }
    @Override
    public List<Buildingdto> findAll(BuildingSearchBuilder fieldSearch, Pageable pageable) {
//        // java 7
////        List<Buildingdto> results = new ArrayList<>();
////        List<BuildingEntity> buildingEntities = buidingRepository.findAll();
////        for (BuildingEntity item: buildingEntities){
////            Buildingdto buildingdto = buildingConverter.convertToDTO(item);
////            results.add(buildingdto);
////        }
//        // Trongs stream thì sẽ có hàm map
//        Map<String, Object> properties = new HashMap<>();
//        properties.put("name", fieldSearch.getName());
//        properties.put("district", fieldSearch.getDistrict());
//        if (StringUtils.isNotBlank(fieldSearch.getBuildingArea())){
//            properties.put("buildingArea", Integer.parseInt(fieldSearch.getBuildingArea()));
//        }
//        if (StringUtils.isNotBlank(fieldSearch.getNumberOfBasement())){
//            properties.put("numberOfBasement", Integer.parseInt(fieldSearch.getNumberOfBasement()));
//        }
        // hàm này dùng để convert từ builder to map properties
        Map<String, Object> properties = convertToMapProperties(fieldSearch);
        List<BuildingEntity> buildingEntities=buildingRepository.findAll(properties,pageable,fieldSearch);
       // List<BuildingEntity> buildingEntities=buildingRepository.findAll(properties);
        return buildingEntities.stream()
                .map(item -> buildingConverter.convertToDTO(item)).collect(Collectors.toList());
    }

    @Override
    public Buildingdto save(Buildingdto buildingdto) {
        BuildingEntity buildingEntity = buildingConverter.convertToEntity(buildingdto);
        buildingEntity.setCreatedDate(new Date());
        buildingEntity.setCreatedBy("Tuan Nguyen");
        Long id = buildingRepository.insert(buildingEntity);
        return null;
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
        return result.toString();
    }

    private Map<String,Object> convertToMapProperties(BuildingSearchBuilder fieldSearch) {
        Map<String, Object> properties = new HashMap<>();
        // Để đi vào class và lấy từng properties thì ta dùng java reflection
            // bỏ qua những field sql đặc biệt vd: costrent from and to
            try {
                Field[] fields = BuildingSearchBuilder.class.getDeclaredFields();
                for (Field field: fields){
                    if (!field.getName().equals("buildingTypes") && !field.getName().startsWith("costRent") && !field.getName().startsWith("areaRent") && !field.getName().equals("staffId")){// buildingType này sẽ có câu query khác. và ko có tìm kiếm chung nên #
                        field.setAccessible(true); // hàm này dùng để access vào field của class buildingsearch vì trong đó tất cả các properties đều là private
                        if (field.get(fieldSearch) instanceof String){// hàm get lấy giá trị của field nhưng phải dc cấp quyền.
                            if (field.getName().equals("buildingArea") || field.getName().equals("numberOfBasement")){
                                if (field.get(fieldSearch) !=null && StringUtils.isNotEmpty(fieldSearch.getBuildingArea())){
                                    properties.put(field.getName().toLowerCase(), Integer.parseInt((String) field.get(fieldSearch)));
                                }
                            }else {
                                properties.put(field.getName().toLowerCase(), field.get(fieldSearch));
                            }
                        }
                    }
                }
            } catch (IllegalAccessException e) {
                System.out.println(e.getMessage());
            }
//        properties.put("name", fieldSearch.getName()); // các key này sẽ trùng vs key của buildingsearchbuilder
//        properties.put("district", fieldSearch.getDistrict());
//        properties.put("street", fieldSearch.getStreet());
//        properties.put("ward", fieldSearch.getWard());
//        if (StringUtils.isNotBlank(fieldSearch.getBuildingArea())){
//            properties.put("buildingArea", Integer.parseInt(fieldSearch.getBuildingArea()));
//        }
//        if (StringUtils.isNotBlank(fieldSearch.getNumberOfBasement())){
//            properties.put("numberOfBasement", Integer.parseInt(fieldSearch.getNumberOfBasement()));
//        }
        return  properties;
    }


}
