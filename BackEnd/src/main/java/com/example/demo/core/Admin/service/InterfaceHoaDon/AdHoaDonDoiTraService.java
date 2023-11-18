package com.example.demo.core.Admin.service.InterfaceHoaDon;

import com.example.demo.core.Admin.model.response.AdminHoaDonResponse;

public interface AdHoaDonDoiTraService {

    AdminHoaDonResponse huyHoaDonTrahang(Integer idHD, String lyDo);

    AdminHoaDonResponse xacNhanHoaDonTraHang(Integer idHD);

    AdminHoaDonResponse congSoLuongSP(Integer idHD);

    AdminHoaDonResponse khongCongSoLuongSP(Integer idHD);

}
