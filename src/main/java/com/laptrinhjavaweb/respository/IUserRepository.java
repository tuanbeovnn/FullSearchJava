package com.laptrinhjavaweb.respository;

import com.laptrinhjavaweb.entity.UserEntity;

public interface IUserRepository extends JpaRepository<UserEntity> {
        // lấy toàn bộ danh sách User
//    List<Userdto> findAll();
}
