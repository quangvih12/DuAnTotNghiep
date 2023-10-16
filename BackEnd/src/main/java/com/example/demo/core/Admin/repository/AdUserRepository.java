package com.example.demo.core.Admin.repository;

import com.example.demo.core.Admin.model.response.AdminUserResponse;
import com.example.demo.entity.Role;
import com.example.demo.entity.User;
import com.example.demo.reponsitory.UserReponsitory;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface AdUserRepository extends UserReponsitory {

    @Query("select a from User a where a.role = :role")
    List<AdminUserResponse> findUserByRole(@Param("role") Role role);


    @Query("select a from User a ")
    List<AdminUserResponse> getAllUser();

    Optional<User> findUsersByUserNameOrEmail(String userNam, String enail);


    User findByUserName( String username);

    Optional<User>  findByEmail(String email);
}
