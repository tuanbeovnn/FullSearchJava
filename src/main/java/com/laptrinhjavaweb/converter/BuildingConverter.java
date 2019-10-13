package com.laptrinhjavaweb.converter;

import com.laptrinhjavaweb.dto.Buildingdto;
import com.laptrinhjavaweb.entity.BuildingEntity;
import org.modelmapper.ModelMapper;

public class BuildingConverter {
    public Buildingdto convertToDTO(BuildingEntity entity){
        // Ta phải convert dữ liệu từ entity sang dto và trả về là 1 dto
        //ModelMapper là trung gian, có nhiệm vụ check dto và entity nếu có trùng thì sẽ matching lại và set giá trị
        // lại cho nhau

        // add thư viện modelmapper trong file pom
        ModelMapper modelMapper = new ModelMapper();
        Buildingdto buildingdto = modelMapper.map(entity, Buildingdto.class);
        if(entity.getBuildingArea() !=null){
            buildingdto.setBuildingArea(String.valueOf(entity.getBuildingArea()));
        }
        if(entity.getNumberOfBasement() !=null){
            buildingdto.setNumberOfBasement(String.valueOf(entity.getNumberOfBasement()));
        }
        return buildingdto;
    }
}
