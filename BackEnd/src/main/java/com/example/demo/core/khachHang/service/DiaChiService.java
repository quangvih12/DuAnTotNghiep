package com.example.demo.core.khachHang.service;

import com.example.demo.core.Admin.model.request.AdminDiaChiRequest;
import com.example.demo.entity.DiaChi;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

public interface DiaChiService {

    List<DiaChi> getUserByDiaChi(Integer idUser);

    HashMap<String, Object> addDiaChi(AdminDiaChiRequest request);

    HashMap<String, Object> updateDiaChi(AdminDiaChiRequest request, Integer id);

    Optional<DiaChi> delete(Integer id);
}
