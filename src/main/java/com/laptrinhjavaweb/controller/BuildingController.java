package com.laptrinhjavaweb.controller;

import com.laptrinhjavaweb.builder.BuildingSearchBuilder;
import com.laptrinhjavaweb.dto.Buildingdto;
import com.laptrinhjavaweb.dto.Userdto;
import com.laptrinhjavaweb.paging.Pageable;
import com.laptrinhjavaweb.paging.impl.PageRequest;
import com.laptrinhjavaweb.service.IBuildingService;
import com.laptrinhjavaweb.service.IUserService;
import com.laptrinhjavaweb.service.impl.BuildingService;
import com.laptrinhjavaweb.service.impl.UserService;
import org.omg.PortableInterceptor.INACTIVE;

import java.sql.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BuildingController {
//    public static void main(String[] args) {
//        int page =1;
//        int limit =3;
//        String name = "";
//        String district ="";
//        Integer buildingArea = null;
//        Integer numberOfBasement = null;
//        IBuildingService buildingService = new BuildingService();
//        BuildingSearchBuilder buildingSearchBuilder = new BuildingSearchBuilder.Builder()
//                .setName(name).setDistrict(district).setBuildingArea(buildingArea).
//                        setNumberOfBasement(numberOfBasement).build();
//        Pageable pageable = new PageRequest(page, limit);
//        List<Buildingdto> buildings = buildingService.findAll(buildingSearchBuilder, pageable);
//        for (Buildingdto item: buildings){
//            System.out.println("ID: "+ item.getId() + " Name of Building: "+ item.getName()+" | Address: " + item.getStreet());
//        }
//
////        IUserService userService = new UserService();
////        List<Userdto> userdtos = userService.findAll(offset, limit);
////        for (Userdto item: userdtos){
////            System.out.println("UserName of Staff: "+ item.getUserName()+ " | FullName: "+ item.getFullName());
////        }
//    }
}
