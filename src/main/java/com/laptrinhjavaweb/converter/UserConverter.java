package com.laptrinhjavaweb.converter;

import com.laptrinhjavaweb.dto.Userdto;
import com.laptrinhjavaweb.entity.UserEntity;
import org.modelmapper.ModelMapper;

public class UserConverter {
    public Userdto convertToDTO (UserEntity entity){
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(entity, Userdto.class);
    }
}
