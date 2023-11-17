package com.example.demo.core.khachHang.service;

import com.example.demo.core.khachHang.model.request.LoginPayLoad;
import com.example.demo.entity.User;

public interface KHUserService {

    User dangNhapGoogle(String email, String ten, String anh);

    User findByToken(String token);

    String checkValiDate(LoginPayLoad loginPayload);

}
