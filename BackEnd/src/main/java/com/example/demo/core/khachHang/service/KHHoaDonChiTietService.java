package com.example.demo.core.khachHang.service;

import com.example.demo.core.khachHang.model.response.KHHoaDonChiTietResponse;

import java.util.List;

public interface KHHoaDonChiTietService {

    List<KHHoaDonChiTietResponse> findHDCTByIdHoaDon(Integer idHD);

}