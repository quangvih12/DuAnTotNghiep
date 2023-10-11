package com.example.demo.core.Admin.service.InterfaceHoaDon;

import com.example.demo.core.Admin.model.response.AdminHoaDonResponse;
import com.example.demo.entity.HoaDon;

import java.util.List;

public interface AdHoaDonChoXacNhanService {

    List<AdminHoaDonResponse> getHoaDonChoXacNhan();

    HoaDon huyHoaDonChoXacNhan(Integer idHD);

    HoaDon xacNhanHoaDon(Integer idHD);
}
