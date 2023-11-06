package com.example.demo.core.khachHang.repository;

import com.example.demo.entity.User;
import com.example.demo.reponsitory.UserReponsitory;

public interface KHUserRepository extends UserReponsitory {

    User findAllByUserName(String userName);
}
