package com.example.demo.core.khachHang.service;

import com.example.demo.core.khachHang.model.request.KHUserRequest;
import com.example.demo.core.khachHang.model.response.KHUserResponse;

public interface ThongTinService {

    KHUserResponse getAll(String token);

    KHUserResponse update(KHUserRequest user, Integer id);
}
