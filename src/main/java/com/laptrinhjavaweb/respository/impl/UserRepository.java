package com.laptrinhjavaweb.respository.impl;

import com.laptrinhjavaweb.entity.UserEntity;
import com.laptrinhjavaweb.respository.IUserRepository;

public class UserRepository extends SimpleJpaRepository<UserEntity> implements IUserRepository {
}
