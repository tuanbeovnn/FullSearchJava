package com.laptrinhjavaweb.service;

import com.laptrinhjavaweb.builder.BuildingSearchBuilder;
import com.laptrinhjavaweb.dto.Buildingdto;
import com.laptrinhjavaweb.paging.Pageable;

import java.awt.*;
import java.util.List;
import java.util.Map;

public interface IBuildingService {
        List<Buildingdto> findAll(BuildingSearchBuilder fieldSearch, Pageable pageable);

}
