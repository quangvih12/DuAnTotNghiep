package com.example.demo.core.khachHang.service.InterfaceHoaDonKH;

import com.example.demo.core.khachHang.model.response.KHHoaDonResponse;

import java.util.List;

public interface HoaDonKHService {

    List<KHHoaDonResponse> getAll(Integer id);

    List<KHHoaDonResponse> getHoaDonTrangThai(Integer id, Integer trangThai);

    KHHoaDonResponse huyHoaDonChoXacNhan(Integer idHD, String lyDo);

    KHHoaDonResponse findById(Integer idHD);

}
