package com.example.demo.core.Admin.service;


import com.example.demo.core.Admin.model.response.AdminUserResponse;
import com.example.demo.entity.Role;
import com.example.demo.entity.User;

import java.util.List;

public interface AdUserService {
    List<AdminUserResponse> getAllUserByRole(Role role);

    List<AdminUserResponse> getAllUser();
}
