package com.example.demo.core.khachHang.repository;

import com.example.demo.entity.User;
import com.example.demo.reponsitory.UserReponsitory;

import java.util.Optional;

public interface KHUserRepository extends UserReponsitory {

    User findAllByUserName(String userName);

    Optional<User> findAllById(Integer id);

}
