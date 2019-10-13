package com.laptrinhjavaweb.dto;

import java.util.Date;

public class Buildingdto extends Abstractdto {

    // DTO cũng có thể xem như là 1 model

    // ACCESS MODIFIED : private, protected, public...
    // mặc định của integer lớn giá trị null
    private String name;
  //  private Integer numberOfBasement;
  //  private Integer buildingArea;
    private String numberOfBasement;
    private String buildingArea;
    private String strucTure;
    private String[] buildingTypes = new String[]{};
    private String costRentFrom;
    private String costRentTo;
    private String areaRentFrom;
    private String areaRentTo;
    private String staffId;

    public void setStaffId(String staffId) {
        this.staffId = staffId;
    }

    public String getStaffId() {
        return staffId;
    }

    public String getCostRentFrom() {
        return costRentFrom;
    }

    public String getAreaRentFrom() {
        return areaRentFrom;
    }

    public void setAreaRentFrom(String areaRentFrom) {
        this.areaRentFrom = areaRentFrom;
    }

    public String getAreaRentTo() {
        return areaRentTo;
    }

    public void setAreaRentTo(String areaRentTo) {
        this.areaRentTo = areaRentTo;
    }

    public void setCostRentFrom(String costRentFrom) {
        this.costRentFrom = costRentFrom;
    }

    public String getCostRentTo() {
        return costRentTo;
    }

    public void setCostRentTo(String costRentTo) {
        this.costRentTo = costRentTo;
    }

    public String[] getBuildingTypes() {
        return buildingTypes;
    }

    public void setBuildingTypes(String[] buildingTypes) {
        this.buildingTypes = buildingTypes;
    }

    public String getNumberOfBasement() {
        return numberOfBasement;
    }

    public void setNumberOfBasement(String numberOfBasement) {
        this.numberOfBasement = numberOfBasement;
    }

    public String getBuildingArea() {
        return buildingArea;
    }

    public void setBuildingArea(String buildingArea) {
        this.buildingArea = buildingArea;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

//    public Integer getNumberOfBasement() {
//        return numberOfBasement;
//    }

//    public void setNumberOfBasement(Integer numberOfBasement) {
//        this.numberOfBasement = numberOfBasement;
//    }

//    public Integer getBuildingArea() {
//        return buildingArea;
//    }

//    public void setBuildingArea(Integer buildingArea) {
//        this.buildingArea = buildingArea;
//    }

    public String getStrucTure() {
        return strucTure;
    }

    public void setStrucTure(String strucTure) {
        this.strucTure = strucTure;
    }

    public Integer getCostRent() {
        return costRent;
    }

    public void setCostRent(Integer costRent) {
        this.costRent = costRent;
    }

    public String getCostDescription() {
        return costDescription;
    }

    public void setCostDescription(String costDescription) {
        this.costDescription = costDescription;
    }

    public String getServiceCost() {
        return serviceCost;
    }

    public void setServiceCost(String serviceCost) {
        this.serviceCost = serviceCost;
    }

    public String getCarCost() {
        return carCost;
    }

    public void setCarCost(String carCost) {
        this.carCost = carCost;
    }

    public String getWard() {
        return ward;
    }

    public void setWard(String ward) {
        this.ward = ward;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getMotorbikeCost() {
        return motorbikeCost;
    }

    public void setMotorbikeCost(String motorbikeCost) {
        this.motorbikeCost = motorbikeCost;
    }

    public String getOverTimeCost() {
        return overTimeCost;
    }

    public void setOverTimeCost(String overTimeCost) {
        this.overTimeCost = overTimeCost;
    }


    private Integer costRent;

    private String costDescription;

    private String serviceCost;

    private String carCost;

    private String ward;

    private String street;

    private String motorbikeCost;

    private String overTimeCost;

    private String district;

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }
}
