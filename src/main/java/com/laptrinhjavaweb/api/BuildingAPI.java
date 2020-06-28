package com.laptrinhjavaweb.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.laptrinhjavaweb.builder.BuildingSearchBuilder;
import com.laptrinhjavaweb.dto.Buildingdto;
import com.laptrinhjavaweb.paging.Pageable;
import com.laptrinhjavaweb.paging.impl.PageRequest;
import com.laptrinhjavaweb.service.IBuildingService;
import com.laptrinhjavaweb.service.impl.BuildingService;
import com.laptrinhjavaweb.utils.FormUtil;
import com.laptrinhjavaweb.utils.HttpUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(urlPatterns = {"/api-building"})
public class BuildingAPI extends HttpServlet {
    private IBuildingService buildingService = new BuildingService();

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ObjectMapper mapper = new ObjectMapper();
        request.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        Buildingdto building = FormUtil.toModel(Buildingdto.class, request);
        BuildingSearchBuilder buildingSearchBuilder = new BuildingSearchBuilder.Builder()
                .setName(building.getName())
                .setDistrict(building.getDistrict())
                .setStreet(building.getStreet())
                .setWard(building.getWard())
                .setBuildingArea(building.getBuildingArea())
                .setNumberOfBasement(building.getNumberOfBasement())
                .setBuildingTypes(building.getBuildingTypes())
                .setAreaRentFrom(building.getAreaRentFrom())
                .setAreaRentTo(building.getAreaRentTo())
                .setCostRentFrom(building.getCostRentFrom())
                .setCostRentTo(building.getCostRentTo())
                .setStaffId(building.getStaffId())
                .build();
        Pageable pageable = new PageRequest(building.getPage(), building.getLimit());
        List<Buildingdto> buildings = buildingService.findAll(buildingSearchBuilder, pageable);
        mapper.writeValue(response.getOutputStream(), buildings);
    }
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        ObjectMapper mapper = new ObjectMapper();
        request.setCharacterEncoding("UTF-8");
        response.setContentType("application/json");
        Buildingdto buildingDto = HttpUtil.of(request.getReader()).toModel(Buildingdto.class);// nhận params là json và parse vào dto thông qua hàm tomodel
        buildingDto = buildingService.save(buildingDto);
        mapper.writeValue(response.getOutputStream(), buildingDto);// writevalue dùng để write json ra ngoài client từ dto
    }
    protected void doDelete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }
    protected void doPut(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

    }
}
