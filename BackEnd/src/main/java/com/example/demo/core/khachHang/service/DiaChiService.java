package com.example.demo.core.khachHang.service;

import com.example.demo.core.Admin.model.request.AdminDiaChiRequest;
import com.example.demo.core.khachHang.model.request.KHDiaChiRequest;
import com.example.demo.entity.DiaChi;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

public interface DiaChiService {

    List<DiaChi> getUserByDiaChi(Integer idUser);

    HashMap<String, Object> addDiaChi(KHDiaChiRequest request);

    HashMap<String, Object> updateDiaChi(KHDiaChiRequest request, Integer id);

    Optional<DiaChi> delete(Integer id);
}