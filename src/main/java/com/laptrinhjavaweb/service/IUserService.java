package com.laptrinhjavaweb.service;

import com.laptrinhjavaweb.dto.Userdto;

import java.util.List;

public interface IUserService {
    List<Userdto> findAll(int offset, int limit);
}
