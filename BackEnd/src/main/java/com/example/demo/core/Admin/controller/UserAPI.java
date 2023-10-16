package com.example.demo.core.Admin.controller;

import com.example.demo.core.Admin.model.response.AdminUserResponse;
import com.example.demo.core.Admin.service.AdUserService;
import com.example.demo.entity.Role;
import com.example.demo.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/api/user")
public class UserAPI {

    @Autowired
    AdUserService adUserService;

    @GetMapping("/getAllUser")
    public List<AdminUserResponse> getAllUser(){
        return  adUserService.getAllUser();
    }

    @GetMapping("/getAllUserByRole")
    public List<AdminUserResponse> getAllUserByRole(@RequestParam("role") Role role){
      return  adUserService.getAllUserByRole(role);
    }
}
