package com.example.demo.core.khachHang.service;

import com.example.demo.core.Admin.model.request.AdminUserRequest;
import com.example.demo.core.Admin.model.response.AdminUserResponse;
import com.example.demo.core.khachHang.model.request.KHUserRequest;
import com.example.demo.core.khachHang.model.response.KHUserResponse;
import com.example.demo.entity.DiaChi;
import com.example.demo.entity.User;

import java.util.List;
import java.util.Optional;

public interface KHUserService {

    KHUserResponse get(Integer id);

    KHUserResponse update(KHUserRequest user, Integer id);
}
