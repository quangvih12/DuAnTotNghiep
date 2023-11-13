package com.example.demo.core.khachHang.service;

import com.example.demo.entity.User;

public interface KHUserService {

    User dangNhapGoogle(String email, String ten);

    User findByToken(String token);
}
