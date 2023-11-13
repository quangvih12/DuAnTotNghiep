package com.example.demo.core.khachHang.service;

import com.example.demo.core.khachHang.model.response.KHHoaDonResponse;

import java.util.List;

public interface KHHoaDonService {

    List<KHHoaDonResponse> getAll(String token);

    List<KHHoaDonResponse> getHoaDonTrangThai(String token, Integer trangThai);

    KHHoaDonResponse huyHoaDonChoXacNhan(Integer idHD, String lyDo);

    KHHoaDonResponse findById(Integer idHD);

}
