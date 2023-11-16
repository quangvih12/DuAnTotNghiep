package com.example.demo.core.khachHang.repository;

import com.example.demo.entity.User;
import com.example.demo.reponsitory.UserReponsitory;
import org.springframework.stereotype.Repository;

import java.util.Optional;
//@Repository("khUserRepo")
public interface KHUserRepository extends UserReponsitory {

    User findAllByUserName(String userName);

    Optional<User> findAllById(Integer id);

    User findUserByEmail(String email);
}
