package com.laptrinhjavaweb.service.impl;

import com.laptrinhjavaweb.converter.UserConverter;
import com.laptrinhjavaweb.dto.Userdto;
import com.laptrinhjavaweb.respository.IUserRepository;
import com.laptrinhjavaweb.respository.impl.UserRepository;
import com.laptrinhjavaweb.service.IUserService;

import java.util.List;
import java.util.stream.Collectors;

public class UserService implements IUserService {

    @Override
    public List<Userdto> findAll(int offset, int limit) {
        return null;
    }
}
