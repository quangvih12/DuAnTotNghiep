package com.example.demo.core.khachHang.service;

import com.example.demo.core.khachHang.model.response.KHHoaDonResponse;

import java.util.List;

public interface KHHoaDonService {

    List<KHHoaDonResponse> getAll(Integer id);

    List<KHHoaDonResponse> getHoaDonTrangThai(Integer id, Integer trangThai);

    KHHoaDonResponse huyHoaDonChoXacNhan(Integer idHD, String lyDo);

    KHHoaDonResponse findById(Integer idHD);

}
