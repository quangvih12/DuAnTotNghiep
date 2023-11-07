package com.example.demo.core.khachHang.service;

import com.example.demo.core.khachHang.model.response.KHHoaDonChitietResponse;

import java.util.List;

public interface KHHoaDonChiTietService {

    List<KHHoaDonChitietResponse> findHDCTByIdHoaDon(Integer idHD);

}
