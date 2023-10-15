package com.example.demo.core.Admin.service.impl;

import com.example.demo.core.Admin.model.response.AdminUserResponse;
import com.example.demo.core.Admin.repository.AdUserRepository;
import com.example.demo.core.Admin.service.AdUserService;
import com.example.demo.entity.Role;
import com.example.demo.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class UserServiceImpl implements AdUserService {
    @Autowired
    AdUserRepository userRepository;

    @Override
    public List<AdminUserResponse> getAllUserByRole(Role role) {
        return userRepository.findUserByRole(role) ;
    }

    @Override
    public List<AdminUserResponse> getAllUser() {
        return userRepository.getAllUser();
    }
}
