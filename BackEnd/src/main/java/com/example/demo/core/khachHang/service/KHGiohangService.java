package com.example.demo.core.khachHang.service;

import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.List;

public interface KHGiohangService {

    List<?> getAll(HttpSession httpSession);

    ResponseEntity<HttpStatus> deleteGioHangCT(Integer id);

    ResponseEntity<HttpStatus> deleteAllGioHangCT();

    HashMap<String, Object> updateCongSoLuong(Integer id);

    HashMap<String, Object> updateTruSoLuong(Integer id);

    HashMap<String, Object> updateMauSacSize(Integer idghct, Integer idMauSacCT, Integer idSizeCT);
}
